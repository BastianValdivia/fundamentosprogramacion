package tmbvs7;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Sistema de venta de entradas para el Teatro Moro.
// Gestiona ventas, aplica descuentos, y genera boletas.
// Actividad Formativa Semana 7 - Bastian A. Valdivia

public class TmBVS7 {

    // Variables estáticas para conteo e ingresos
    static int totalEntradasVendidas = 0;
    static double ingresosTotales = 0.0;
    static int contadorEntradas = 1;

    // Listas para almacenar detalles de ventas
    static ArrayList<String> listaCodigoEntrada = new ArrayList<>();
    static ArrayList<String> listaUbicacion = new ArrayList<>();
    static ArrayList<Double> listaPrecioBase = new ArrayList<>();
    static ArrayList<Double> listaDescuento = new ArrayList<>();
    static ArrayList<Double> listaCostoFinal = new ArrayList<>();

    // Capacidades por zona
    static int capacidadVIP = 5;
    static int capacidadPlatea = 5;
    static int capacidadBalcon = 5;

// Método principal que ejecuta el menú interactivo del sistema
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("\n==============================================");
        System.out.println("=== ¡BIENVENIDOS AL SISTEMA DE ENTRADAS DEL TEATRO MORO! ===");
        System.out.println("==============================================");

        while (continuar) {
            // Mostrar menú principal de opciones disponibles al usuario    
            System.out.println("\n============= MENÚ PRINCIPAL =============");
            System.out.println("Ubicaciones disponibles:");
            System.out.println("VIP     " + (capacidadVIP > 0 ? "(" + capacidadVIP + " disponibles) $10000" : "(AGOTADAS)"));
            System.out.println("PLATEA  " + (capacidadPlatea > 0 ? "(" + capacidadPlatea + " disponibles) $8000" : "(AGOTADAS)"));
            System.out.println("BALCÓN  " + (capacidadBalcon > 0 ? "(" + capacidadBalcon + " disponibles) $5000" : "(AGOTADAS)"));

            System.out.println("------------------------------------------");
            System.out.println("Descuentos disponibles:");
            System.out.println("🎓 Estudiantes: 10% | 👵 Tercera Edad (55+): 15%");
            System.out.println("------------------------------------------");
            System.out.println("1. Vender entrada");
            System.out.println("2. Ver resumen de ventas");
            System.out.println("3. Generar boleta");
            System.out.println("4. Ver ingresos totales");
            System.out.println("5. Salir");
            System.out.println("==========================================");

            int opcion = 0;
            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    venderEntrada(sc);
                    break;
                case 2:
                    mostrarResumen(sc);
                    break;

                case 3:
                    generarBoleta(sc);
                    break;
                case 4:
                    mostrarIngresosTotales(sc);
                    break;
                case 5:
                    System.out.println("\n========================================");
                    System.out.println("    ¡Gracias por usar el sistema del Teatro Moro!");
                    System.out.println("               ¡Hasta pronto! 🎭");
                    System.out.println("========================================");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        sc.close();
    }

// Registra la venta de entradas, aplica descuentos y actualiza listas
    public static void venderEntrada(Scanner sc) {
        System.out.println("\n========================================");
        System.out.println("         >>> VENTA DE ENTRADAS <<<      ");
        System.out.println("========================================");
// Solicitar y validar datos del cliente
        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        int edad = 0;
        while (true) {
            try {
                System.out.print("Ingrese su edad: ");
                edad = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese una edad válida.");
            }
        }

        String estudiante = "";
        while (true) {
            System.out.print("¿Es estudiante? (s/n): ");
            estudiante = sc.nextLine().trim().toLowerCase();
            if (estudiante.equals("s") || estudiante.equals("n")) {
                break;
            } else {
                System.out.println("Por favor, responda con 's' o 'n'.");
            }
        }

// Determinar tipo de cliente y aplicar descuentos si corresponde
        String tipoCliente;
        double descuento = 0.0;
        if (edad >= 55) {
            tipoCliente = "Tercera Edad";
            descuento = 0.15;
        } else if (estudiante.equals("s")) {
            tipoCliente = "Estudiante";
            descuento = 0.10;
        } else {
            tipoCliente = "General";
        }

// Mostrar ubicaciones disponibles y validar elección
        String ubicacion = "";
        double precioBase = 0.0;
        int disponibles = 0;

        while (true) {
            System.out.println("\nSeleccione ubicación (1-3):");
            if (capacidadVIP > 0) {
                System.out.println("1. VIP     - $10000 (" + capacidadVIP + " disponibles)");
            } else {
                System.out.println("1. VIP     - AGOTADAS");
            }
            if (capacidadPlatea > 0) {
                System.out.println("2. Platea  - $8000  (" + capacidadPlatea + " disponibles)");
            } else {
                System.out.println("2. Platea  - AGOTADAS");
            }
            if (capacidadBalcon > 0) {
                System.out.println("3. Balcón  - $5000  (" + capacidadBalcon + " disponibles)");
            } else {
                System.out.println("3. Balcón  - AGOTADAS");
            }

            try {
                System.out.print("Opción: ");
                int tipo = Integer.parseInt(sc.nextLine());

                if (tipo == 1 && capacidadVIP > 0) {
                    ubicacion = "VIP";
                    precioBase = 10000;
                    disponibles = capacidadVIP;
                    break;
                } else if (tipo == 2 && capacidadPlatea > 0) {
                    ubicacion = "Platea";
                    precioBase = 8000;
                    disponibles = capacidadPlatea;
                    break;
                } else if (tipo == 3 && capacidadBalcon > 0) {
                    ubicacion = "Balcón";
                    precioBase = 5000;
                    disponibles = capacidadBalcon;
                    break;
                } else {
                    System.out.println("Ubicación inválida o sin entradas disponibles.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
// Solicitar número de entradas y validar disponibilidad
        int cantidad;
        while (true) {
            try {
                System.out.print("¿Cuántas entradas desea?: ");
                cantidad = Integer.parseInt(sc.nextLine());
                if (cantidad > 0 && cantidad <= disponibles) {
                    break;
                } else {
                    System.out.println("Cantidad inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número válido.");
            }
        }
// Registrar cada entrada vendida con sus respectivos datos
        for (int i = 0; i < cantidad; i++) {
            double montoDesc = precioBase * descuento;
            double totalFinal = precioBase - montoDesc;

            listaCodigoEntrada.add("Entrada " + contadorEntradas);
            listaUbicacion.add(ubicacion);
            listaPrecioBase.add(precioBase);
            listaDescuento.add(montoDesc);
            listaCostoFinal.add(totalFinal);

            totalEntradasVendidas++;
            ingresosTotales += totalFinal;
            contadorEntradas++;
        }

        if (ubicacion.equals("VIP")) {
            capacidadVIP -= cantidad;
        } else if (ubicacion.equals("Platea")) {
            capacidadPlatea -= cantidad;
        } else {
            capacidadBalcon -= cantidad;
        }

        System.out.println("\n✅ Compra registrada con éxito.");
        System.out.println("Cliente: " + nombre + " | Tipo: " + tipoCliente);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Entradas: " + cantidad);
        System.out.println("Precio unitario: $" + (int) precioBase);
        System.out.println("Descuento aplicado: $" + (int) (precioBase * descuento) + " (" + (int) (descuento * 100) + "%)");
        System.out.println("Total final: $" + (int) ((precioBase - precioBase * descuento) * cantidad));

        volverAlMenu(sc);
    }

    // Muestra un resumen de todas las ventas realizadas
    public static void mostrarResumen(Scanner sc) {
        System.out.println("\n========================================");
        System.out.println("           >>> RESUMEN DE VENTAS <<<     ");
        System.out.println("========================================");

        if (listaCodigoEntrada.isEmpty()) {
            System.out.println("⚠️  ¡Aún no se han registrado ventas!");
        } else {
            for (int i = 0; i < listaCodigoEntrada.size(); i++) {
                String codigo = listaCodigoEntrada.get(i);
                String ubicacion = listaUbicacion.get(i);
                double base = listaPrecioBase.get(i);
                double descuento = listaDescuento.get(i);
                double total = listaCostoFinal.get(i);

                int porcentaje = (base != 0) ? (int) ((descuento / base) * 100) : 0;

                System.out.println("----------------------------------------");
                System.out.println("Código: " + codigo);
                System.out.println("Ubicación: " + ubicacion);
                System.out.println("Precio base: $" + (int) base);
                System.out.println("Descuento: $" + (int) descuento + " (" + porcentaje + "%)");
                System.out.println("Costo final: $" + (int) total);
            }
            System.out.println("----------------------------------------");
            System.out.println("Total de entradas vendidas: " + listaCodigoEntrada.size());
        }

        volverAlMenu(sc);
    }

    // Genera e imprime una boleta detallada por cada entrada vendida
    public static void generarBoleta(Scanner sc) {
        System.out.println("\n========================================");
        System.out.println("            >>> BOLETAS DETALLADAS <<<     ");
        System.out.println("========================================");

        if (listaCodigoEntrada.isEmpty()) {
            System.out.println("⚠️  ¡Aún no se han registrado ventas!");
        } else {
            for (int i = 0; i < listaCodigoEntrada.size(); i++) {
                String codigo = listaCodigoEntrada.get(i);
                String ubicacion = listaUbicacion.get(i);
                double base = listaPrecioBase.get(i);
                double descuento = listaDescuento.get(i);
                double total = listaCostoFinal.get(i);

                int porcentaje = (base != 0) ? (int) ((descuento / base) * 100) : 0;

                System.out.println("----------------------------------------");
                System.out.println("           Teatro Moro 🎭");
                System.out.println("----------------------------------------");
                System.out.println("Código: " + codigo);
                System.out.println("Ubicación: " + ubicacion);
                System.out.println("Costo Base: $" + (int) base);
                System.out.println("Descuento Aplicado: " + porcentaje + "%");
                System.out.println("Costo Final: $" + (int) total);
                System.out.println("----------------------------------------");
                System.out.println("Gracias por su visita al Teatro Moro");
                System.out.println("----------------------------------------");
            }
        }

        volverAlMenu(sc);
    }

    // Muestra la cantidad total de entradas vendidas y el ingreso acumulado
    public static void mostrarIngresosTotales(Scanner sc) {
        System.out.println("\n========================================");
        System.out.println("         >>> INGRESOS TOTALES <<<       ");
        System.out.println("========================================");

        System.out.println("Entradas vendidas: " + totalEntradasVendidas);
        System.out.println("Ingresos acumulados: $" + (int) ingresosTotales);

        volverAlMenu(sc);
    }

// Espera confirmación del usuario para volver al menú principal
    public static void volverAlMenu(Scanner sc) {
        System.out.println("\nPresione ENTER para volver al menú principal...");
        sc.nextLine();
    }
}
