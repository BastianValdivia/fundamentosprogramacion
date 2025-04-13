package javaapplication57;

import java.util.ArrayList;
import java.util.Scanner;

public class JavaApplication57 {

    // ====== variables estáticas ======
    static ArrayList<Entrada> entradasTemporales = new ArrayList<>(); // Entradas aún no confirmadas
    static ArrayList<Entrada> entradasVendidas = new ArrayList<>();   // Entradas ya compradas
    static int contadorEntradas = 1; // Contador único para generar códigos de entrada

    static int disponiblesVIP = 30;
    static int disponiblesPlatea = 40;
    static int disponiblesGeneral = 30;

    static int totalVentas = 0;
    static int ingresosTotales = 0;
    static double totalDescuentos = 0;

     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean seguir = true;

        while (seguir) {
            boolean volverAlMenu = true;

            // ====== MENÚ PRINCIPAL ======
            System.out.println("\n====== SISTEMA DE ENTRADAS - TEATRO MORO ======");
            System.out.println("¡Bienvenidos al Sistema de Entradas del Teatro Moro!");
            System.out.println("Entradas disponibles:");

            int vipEnCarrito = 0, plateaEnCarrito = 0, generalEnCarrito = 0;
            for (Entrada e : entradasTemporales) {
                switch (e.getTipo()) {
                    case "vip": vipEnCarrito++; break;
                    case "platea": plateaEnCarrito++; break;
                    case "general": generalEnCarrito++; break;
                }
            }

            System.out.println("VIP ($20000): " + (disponiblesVIP - vipEnCarrito));
            System.out.println("Platea ($15000): " + (disponiblesPlatea - plateaEnCarrito));
            System.out.println("General ($10000): " + (disponiblesGeneral - generalEnCarrito));
            System.out.println("\n=== PROMOCIONES ===");
            System.out.println("🎓 Estudiante (18 a 54 años): 10% de descuento");
            System.out.println("👴 Tercera edad (55+ años): 15% de descuento");
            System.out.println("💥 5% adicional si compras 3 o más entradas");
            System.out.println("======================================");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver Promociones");
            System.out.println("3. Buscar entrada por código");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Ver estadísticas");
            System.out.println("6. Proceder al pago");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("⚠️ Por favor, ingrese un número válido: ");
                scanner.next();
            }

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    // ====== OPCIÓN 1: COMPRAR ENTRADA ======
                    System.out.println("\n====== COMPRA DE ENTRADAS ======");

                    // Variables locales
                    System.out.print("Ingrese su nombre: ");
                    String nombreCliente = scanner.nextLine();

                    int edad = 0;
                    boolean edadValida = false;
                    while (!edadValida) {
                        System.out.print("Ingrese su edad: ");
                        if (scanner.hasNextInt()) {
                            edad = scanner.nextInt();
                            if (edad > 0) {
                                edadValida = true;
                            } else {
                                System.out.println("⚠️ La edad debe ser mayor que 0.");
                            }
                        } else {
                            System.out.println("⚠️ Ingrese un número válido.");
                            scanner.next();
                        }
                    }

                    scanner.nextLine(); // Limpiar buffer
                    String tipoCliente = "General";
                    double descuentoCliente = 0;

                    if (edad >= 18 && edad <= 54) {
                        System.out.print("¿Eres estudiante? (s/n): ");
                        String estudiante = scanner.nextLine();
                        if (estudiante.equalsIgnoreCase("s")) {
                            tipoCliente = "Estudiante";
                            descuentoCliente = 0.10;
                        }
                    } else if (edad >= 55) {
                        tipoCliente = "Tercera Edad";
                        descuentoCliente = 0.15;
                    }

                    System.out.println("---------------------------------");
                    System.out.println("👤 Nombre: " + nombreCliente);
                    System.out.println("🎫 Tipo de cliente: " + tipoCliente);
                    System.out.println("💸 Descuento aplicado: " + (int) (descuentoCliente * 100) + "%");
                    System.out.println("---------------------------------");

                    System.out.print("Seleccione tipo de entrada (VIP / Platea / General): ");
                    String tipoEntrada = scanner.nextLine().toLowerCase();

                    int disponibles = 0;
                    int precioBase = 0;

                    switch (tipoEntrada) {
                        case "vip":
                            disponibles = disponiblesVIP;
                            precioBase = 20000;
                            break;
                        case "platea":
                            disponibles = disponiblesPlatea;
                            precioBase = 15000;
                            break;
                        case "general":
                            disponibles = disponiblesGeneral;
                            precioBase = 10000;
                            break;
                        default:
                            System.out.println("⚠️ Tipo de entrada no válido.");
                            break;
                    }

                    if (disponibles > 0) {
                        System.out.print("¿Cuántas entradas desea?: ");
                        int cantidad = scanner.nextInt();
                        scanner.nextLine();

                        if (cantidad > 0 && cantidad <= disponibles) {
                            double descuentoExtra = (cantidad >= 3) ? 0.05 : 0.0;
                            double precioFinal = precioBase * cantidad * (1 - descuentoCliente) * (1 - descuentoExtra);

                            System.out.println("🧾 Total a pagar: $" + precioFinal);

                            System.out.print("Códigos generados: ");
                            for (int i = 0; i < cantidad; i++) {
                                String codigo = "e" + contadorEntradas++;
                                entradasTemporales.add(new Entrada(codigo, tipoEntrada, precioFinal / cantidad));
                                System.out.print(codigo + " ");
                            }

                            System.out.print("\n¿Desea editar o eliminar su compra antes de confirmar? (s/n): ");
                            String editar = scanner.nextLine();
                            if (editar.equalsIgnoreCase("s")) {
                                System.out.println("🔄 Volviendo al menú para editar...");
                                break;
                            }

                            // Confirmar compra
                            entradasVendidas.addAll(entradasTemporales);
                            totalVentas += entradasTemporales.size();
                            for (Entrada e : entradasTemporales) {
                                ingresosTotales += e.getPrecioFinal();
                            }
                            totalDescuentos += precioBase * cantidad - precioFinal;

                            if (tipoEntrada.equals("vip")) {
                                disponiblesVIP -= cantidad;
                            }
                            if (tipoEntrada.equals("platea")) {
                                disponiblesPlatea -= cantidad;
                            }
                            if (tipoEntrada.equals("general")) {
                                disponiblesGeneral -= cantidad;
                            }

                            entradasTemporales.clear();
                            System.out.println("✅ ¡Compra confirmada y realizada con éxito!");
                        } else {
                            System.out.println("⚠️ Cantidad inválida o insuficiente disponibilidad.");
                        }
                    } else {
                        System.out.println("⚠️ No hay entradas disponibles para ese tipo.");
                    }
                    System.out.print("¿Desea volver al menú principal? (s/n): ");
                    String volverMenu = scanner.nextLine();
                    if (!volverMenu.equalsIgnoreCase("s")) {
                        opcion = 0; // Esto hace que se salga del menú (termina el programa)
                    }

                    break;

                case 2:
                    // ====== OPCIÓN 2: VER PROMOCIONES ======
                    System.out.println("\n=== PROMOCIONES ===");
                    System.out.println("🎓 Estudiante (18 a 54 años): 10% de descuento");
                    System.out.println("👴 Tercera edad (55+ años): 15% de descuento");
                    System.out.println("💥 5% adicional si compras 3 o más entradas");
                    break;

                case 3:
                    // ====== OPCIÓN 3: BUSCAR ENTRADA ======
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese código de entrada a buscar: ");
                    String codigoBuscar = scanner.nextLine().trim();

                    boolean encontrado = false;
                    for (Entrada e : entradasTemporales) {
                        if (e.getCodigo().equalsIgnoreCase(codigoBuscar)) {
                            System.out.println("🔍 Entrada en carrito (no confirmada):");
                            System.out.println("➡ Código: " + e.getCodigo());
                            System.out.println("➡ Tipo: " + e.getTipo());
                            System.out.println("➡ Precio: $" + e.getPrecioFinal());
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        for (Entrada e : entradasVendidas) {
                            if (e.getCodigo().equalsIgnoreCase(codigoBuscar)) {
                                System.out.println("✅ Entrada confirmada:");
                                System.out.println("➡ Código: " + e.getCodigo());
                                System.out.println("➡ Tipo: " + e.getTipo());
                                System.out.println("➡ Precio: $" + e.getPrecioFinal());
                                encontrado = true;
                                break;
                            }
                        }
                    }

                    if (!encontrado) {
                        System.out.println("⚠️ Entrada no encontrada.");
                    }

                    break;

                case 4:
                    // ====== OPCIÓN 4: ELIMINAR UNA O MÁS ENTRADAS DEL CARRITO ======
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese los códigos de entrada a eliminar (separados por coma): ");
                    String codigosInput = scanner.nextLine();
                    String[] codigos = codigosInput.split(",");

                    ArrayList<String> eliminadas = new ArrayList<>();
                    ArrayList<String> noEncontradas = new ArrayList<>();

                    for (String codigo : codigos) {
                        String codTrim = codigo.trim();
                        boolean encontradoYEliminado = false;

                        for (int i = 0; i < entradasTemporales.size(); i++) {
                            Entrada e = entradasTemporales.get(i);
                            if (e.getCodigo().equalsIgnoreCase(codTrim)) {
                                // Devolver al stock según el tipo
                                switch (e.getTipo()) {
                                    case "vip":
                                        disponiblesVIP++;
                                        break;
                                    case "platea":
                                        disponiblesPlatea++;
                                        break;
                                    case "general":
                                        disponiblesGeneral++;
                                        break;
                                }

                                entradasTemporales.remove(i);
                                eliminadas.add(codTrim);
                                encontradoYEliminado = true;
                                break;
                            }
                        }

                        if (!encontradoYEliminado) {
                            noEncontradas.add(codTrim);
                        }
                    }

                    // Mostrar resultados
                    if (!eliminadas.isEmpty()) {
                        System.out.println("🗑️ Entradas eliminadas del carrito: " + String.join(", ", eliminadas));
                    }
                    if (!noEncontradas.isEmpty()) {
                        System.out.println("⚠️ Estas entradas no se encontraron en el carrito o ya fueron confirmadas: " + String.join(", ", noEncontradas));
                    }
                    if (eliminadas.isEmpty()) {
                        System.out.println("⚠️ No se eliminó ninguna entrada.");
                    }
                    break;

                case 5:
                    // ====== OPCIÓN 5: ESTADÍSTICAS ======
                    System.out.println("\n====== 📊 ESTADÍSTICAS DE VENTAS ======");

                    int totalVendidasVIP = 0;
                    int totalVendidasPlatea = 0;
                    int totalVendidasGeneral = 0;

                    for (Entrada e : entradasVendidas) {
                        switch (e.getTipo()) {
                            case "vip":
                                totalVendidasVIP++;
                                break;
                            case "platea":
                                totalVendidasPlatea++;
                                break;
                            case "general":
                                totalVendidasGeneral++;
                                break;
                        }
                    }

                    System.out.println("➡ Total entradas vendidas: " + totalVentas);
                    System.out.println("➡ Ingresos totales: $" + ingresosTotales);
                    System.out.println("➡ Total descuentos aplicados: $" + (int) totalDescuentos
                            + " (" + (totalVentas > 0 ? (int) ((totalDescuentos / (ingresosTotales + totalDescuentos)) * 100) : 0) + "%)");

                    System.out.println("➡ Entradas VIP vendidas: " + totalVendidasVIP);
                    System.out.println("➡ Entradas Platea vendidas: " + totalVendidasPlatea);
                    System.out.println("➡ Entradas General vendidas: " + totalVendidasGeneral);
                    System.out.println("=========================================");

                    System.out.print("¿Desea volver al menú principal? (s/n): ");
                    String volverEst = scanner.nextLine();
                    if (!volverEst.equalsIgnoreCase("s")) {
                        opcion = 0; // Salir si no desea volver
                    }
                    break;

                case 6:
                    // ====== OPCIÓN 6: PROCEDER AL PAGO ======
                    if (entradasTemporales.isEmpty()) {
                        System.out.println("⚠️ No hay entradas en el carrito para pagar.");
                        break;
                    }

                    double totalFinal = 0;
                    double totalSinDescuento = 0;

                    for (Entrada e : entradasTemporales) {
                        totalFinal += e.getPrecioFinal();
                        // Suponiendo que los precios originales son fijos según tipo
                        switch (e.getTipo()) {
                            case "vip":
                                totalSinDescuento += 20000;
                                break;
                            case "platea":
                                totalSinDescuento += 15000;
                                break;
                            case "general":
                                totalSinDescuento += 10000;
                                break;
                        }
                    }

                    double descuentoAplicado = totalSinDescuento - totalFinal;
                    double porcentajeDescuento = (descuentoAplicado / totalSinDescuento) * 100;

                    System.out.println("\n====== CONFIRMACIÓN DE PAGO ======");
                    System.out.println("🧾 Total sin descuento: $" + totalSinDescuento);
                    System.out.printf("💸 Descuento total: $%.0f (%.0f%%)\n", descuentoAplicado, porcentajeDescuento);
                    System.out.println("✅ Total a pagar: $" + totalFinal);

                    System.out.print("¿Desea confirmar y pagar? (s/n): ");
                    String confirmarPago = scanner.nextLine();
                    if (confirmarPago.equalsIgnoreCase("s")) {
                        entradasVendidas.addAll(entradasTemporales);
                        totalVentas += entradasTemporales.size();
                        ingresosTotales += totalFinal;
                        totalDescuentos += descuentoAplicado;

                        entradasTemporales.clear();
                        System.out.println("✅ ¡Compra realizada con éxito!");
                    } else {
                        System.out.println("⏪ Compra no confirmada. Puede seguir editando el carrito.");
                    }

                    System.out.print("¿Desea volver al menú principal? (s/n): ");
                    String volver = scanner.nextLine();
                    if (!volver.equalsIgnoreCase("s")) {
                        opcion = 0;
                    }

                    break;

                case 0:
                    System.out.println("👋 ¡Gracias por usar el sistema del Teatro Moro!");
                    break;

                default:
                    System.out.println("⚠️ Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ====== CLASE ENTRADA DENTRO DEL MISMO ARCHIVO ======
    static class Entrada {

        private String codigo;
        private String tipo;
        private double precioFinal;

        public Entrada(String codigo, String tipo, double precioFinal) {
            this.codigo = codigo;
            this.tipo = tipo;
            this.precioFinal = precioFinal;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getTipo() {
            return tipo;
        }

        public double getPrecioFinal() {
            return precioFinal;
        }
    }
}
