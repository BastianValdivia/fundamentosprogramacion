package bvexp6s6tm;

import java.util.Scanner;

public class Bvexp6s6tm {

    // === Variables estáticas ===
    static int entradasVip = 5;
    static int entradasPlatea = 5;
    static int entradasGeneral = 5;
    static int contadorEntradas = 1;

    // Arrays para almacenar la compra (Variables de instancia)
    static String[] codigos = new String[15];
    static String[] nombres = new String[15];
    static int[] edades = new int[15];
    static String[] tiposCliente = new String[15];
    static String[] tiposEntrada = new String[15];
    static int[] preciosFinales = new int[15];

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("=== ¡Bienvenidos al sistema de entradas del Teatro Moro! ===");
            System.out.println("Entradas disponibles:");
            System.out.println("VIP (" + entradasVip + ") : $10000");
            System.out.println("PLATEA (" + entradasPlatea + ") : $8000");
            System.out.println("GENERAL (" + entradasGeneral + ") : $5000");
            System.out.println("Promociones:");
            System.out.println("Estudiantes (15-54 años): 10% de descuento");
            System.out.println("Menores de 15 años: 6% de descuento");
            System.out.println("Tercera Edad (55+): 8% de descuento");
            System.out.println("Compra de 3 o más entradas: 5% adicional");

            System.out.println("1 - Comprar entradas");
            System.out.println("2 - Eliminar/Modificar entradas");
            System.out.println("3 - Ver estadísticas");
            System.out.println("4 - Proceder al pago");
            System.out.println("5 - Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    comprarEntradas();
                    break;
                case "2":
                    eliminarModificarEntradas();
                    break;
                case "3":
                    verEstadisticas();
                    break;
                case "4":
                    confirmarPago();
                    break;
                case "5":
                    salir = true;
                    System.out.println("Gracias por visitar el Teatro Moro. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // === MÉTODO COMPRAR ENTRADAS ===
    public static void comprarEntradas() {
        try {
            System.out.print("Ingrese su nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese su edad: ");
            int edad = Integer.parseInt(scanner.nextLine());

            String esEstudiante = "";
            while (true) {
                System.out.print("¿Eres estudiante? (s/n): ");
                esEstudiante = scanner.nextLine().toLowerCase();
                if (esEstudiante.equals("s") || esEstudiante.equals("n")) {
                    break;
                } else {
                    System.out.println("Entrada inválida. Responda solo con 's' o 'n'.");
                }
            }

            String tipoCliente = "General";
            if (esEstudiante.equals("s") && edad >= 15 && edad <= 54) {
                tipoCliente = "Estudiante";
            } else if (edad < 15) {
                tipoCliente = "Menor de edad";
            } else if (edad >= 55) {
                tipoCliente = "Tercera Edad";
            }

            System.out.print("¿Qué tipo de entrada desea comprar? (VIP / PLATEA / GENERAL): ");
            String tipoEntrada = scanner.nextLine().toUpperCase();

            int precioBase = 0;
            int entradasDisponibles = 0;

            switch (tipoEntrada) { // === DEPURACIÓN 1: Verificar tipo de entrada ingresado por el usuario antes de reservar ===
                case "VIP":
                    precioBase = 5000; 
                    entradasDisponibles = entradasVip;
                    break;
                case "PLATEA":
                    precioBase = 8000;
                    entradasDisponibles = entradasPlatea;
                    break;
                case "GENERAL":
                    precioBase = 5000;
                    entradasDisponibles = entradasGeneral;
                    break;
                default:
                    System.out.println("Tipo de entrada no válido. Intente nuevamente.");
                    return;
            }

            System.out.print("¿Cuántas entradas desea comprar?: ");
            int cantidad = Integer.parseInt(scanner.nextLine());
            if (cantidad <= 0 || cantidad > entradasDisponibles) { // === DEPURACIÓN 2: Validar cantidad ingresada y disponibilidad de entradas ===
                System.out.println("Cantidad no válida o no hay suficientes entradas. Intente nuevamente.");
                return;
            }

            // Calcular descuentos
            double descuento = 0;
            if (tipoCliente.equals("Estudiante")) {
                descuento += 0.10;
            } else if (tipoCliente.equals("Menor de edad")) {
                descuento += 0.06;
            } else if (tipoCliente.equals("Tercera Edad")) {
                descuento += 0.08;
            }
            if (cantidad >= 3) {
                descuento += 0.05;
            }

            int totalSinDescuento = precioBase * cantidad;
            int totalConDescuento = (int) (totalSinDescuento * (1 - descuento));
            int precioPorEntrada = totalConDescuento / cantidad;

            // Guardar entradas
            System.out.println("=== RESUMEN DE COMPRA ===");
            for (int i = 0; i < cantidad; i++) {
                String codigo = "E" + contadorEntradas;
                codigos[contadorEntradas - 1] = codigo;
                nombres[contadorEntradas - 1] = nombre;
                edades[contadorEntradas - 1] = edad;
                tiposCliente[contadorEntradas - 1] = tipoCliente;
                tiposEntrada[contadorEntradas - 1] = tipoEntrada;
                preciosFinales[contadorEntradas - 1] = precioPorEntrada;

                System.out.println("Código: " + codigo + " | Entrada: " + tipoEntrada + " | Precio: $" + precioPorEntrada);
                contadorEntradas++;
            }

            System.out.println("Cliente: " + nombre + " (" + edad + " años - " + tipoCliente + ")");
            System.out.println("Total sin descuento: $" + totalSinDescuento);
            System.out.println("Descuento aplicado: " + (int) (descuento * 100) + "%");
            System.out.println("Total a pagar: $" + totalConDescuento);

            // Descontar del stock
            switch (tipoEntrada) {
                case "VIP":
                    entradasVip -= cantidad;
                    break;
                case "PLATEA":
                    entradasPlatea -= cantidad;
                    break;
                case "GENERAL":
                    entradasGeneral -= cantidad;
                    break;
            }

            System.out.print("¿Desea editar su compra? (s/n): ");
            String editar = scanner.nextLine().toLowerCase(); // === DEPURACIÓN 3: Confirmación final del usuario antes de registrar la reserva definitiva ===
            if (editar.equals("s")) {
                System.out.println("Regresando al menú principal para editar su compra...");
                return;
            } else {
                System.out.println("Compra reservada exitosamente. Puede proceder al pago cuando lo desee.");
            }

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en el proceso. Intente nuevamente. Detalles: " + e.getMessage());
        }
    }

// === OPCIÓN 2 ===
    public static void eliminarModificarEntradas() {
        try {
            System.out.println("\n=== ELIMINAR O MODIFICAR ENTRADAS ===");

            boolean hayEntradas = false;
            for (int i = 0; i < codigos.length; i++) {
                if (codigos[i] != null) {
                    hayEntradas = true;
                    break;
                }
            }

            if (!hayEntradas) {
                System.out.println("No hay entradas compradas aún.");
                return;
            }

            System.out.println("\nEntradas compradas:");
            for (int i = 0; i < codigos.length; i++) {
                if (codigos[i] != null) {
                    System.out.println("Código: " + codigos[i] + " | Cliente: " + nombres[i] + " | Entrada: " + tiposEntrada[i] + " | Precio: $" + preciosFinales[i]);
                }
            }

            System.out.print("\nIngrese los códigos de las entradas a eliminar (separados por coma, ej: E1,E2): ");
            String input = scanner.nextLine();
            String[] codigosAEliminar = input.toUpperCase().replace(" ", "").split(",");

            int eliminadas = 0;
            for (String codigoIngresado : codigosAEliminar) {
                boolean encontrado = false;
                for (int i = 0; i < codigos.length; i++) {
                    if (codigoIngresado.equals(codigos[i])) { // === DEPURACIÓN 4: Verificación de existencia del código ingresado para eliminar ===
                        // Reponer entrada al stock
                        switch (tiposEntrada[i]) {
                            case "VIP":
                                entradasVip++;
                                break;
                            case "PLATEA":
                                entradasPlatea++;
                                break;
                            case "GENERAL":
                                entradasGeneral++;
                                break;
                        }

                        // Eliminar entrada
                        codigos[i] = null;
                        nombres[i] = null;
                        edades[i] = 0;
                        tiposCliente[i] = null;
                        tiposEntrada[i] = null;
                        preciosFinales[i] = 0;

                        System.out.println("Entrada con código " + codigoIngresado + " eliminada correctamente.");
                        eliminadas++;
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    System.out.println("No se encontró el código: " + codigoIngresado + ". Revise e intente nuevamente.");
                }
            }

            if (eliminadas > 0) {
                System.out.println("\nTotal de entradas eliminadas: " + eliminadas);
            } else {
                System.out.println("\nNo se eliminó ninguna entrada.");
            }

            // Preguntar si quiere volver al menú principal
            boolean opcionValida = false;
            while (!opcionValida) {
                System.out.print("\n¿Desea volver al menú principal? (s/n): ");
                String volver = scanner.nextLine().toLowerCase();

                if (volver.equals("s")) {
                    opcionValida = true;
                    System.out.println("Volviendo al menú principal...");
                    return;
                } else if (volver.equals("n")) {
                    opcionValida = true;
                    System.out.println("Puede continuar operando en esta sección si lo desea.");
                } else {
                    System.out.println("Opción no válida. Intente con 's' o 'n'.");
                }
            }

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error durante la eliminación. Intente nuevamente.");
            System.out.println("Detalles técnicos: " + e.getMessage());
        }
    }

    // === OPCIÓN 3 ===
    public static void verEstadisticas() {
        try {
            System.out.println("\n=== RESUMEN DE COMPRA / ESTADÍSTICAS ===");

            boolean hayEntradas = false;
            int totalReservadas = 0;
            int totalBruto = 0;
            int totalNeto = 0;

            for (int i = 0; i < codigos.length; i++) {
                if (codigos[i] != null) {
                    hayEntradas = true;
                    totalReservadas++;

                    int precioBase = 0;
                    switch (tiposEntrada[i]) {
                        case "VIP":
                            precioBase = 10000;
                            break;
                        case "PLATEA":
                            precioBase = 8000;
                            break;
                        case "GENERAL":
                            precioBase = 5000;
                            break;
                    }

                    double descuento = 0;
                    if (tiposCliente[i].equals("Estudiante")) {
                        descuento += 0.10;
                    } else if (tiposCliente[i].equals("Menor de edad")) {
                        descuento += 0.06;
                    } else if (tiposCliente[i].equals("Tercera Edad")) {
                        descuento += 0.08;
                    }

                    // Contar entradas actuales del cliente
                    int entradasCliente = 0;
                    for (int j = 0; j < codigos.length; j++) {
                        if (codigos[j] != null && nombres[j] != null && nombres[j].equals(nombres[i]) && tiposEntrada[j] != null) {
                            entradasCliente++;
                        }
                    }

                    if (entradasCliente >= 3) {
                        descuento += 0.05;
                    }

                    int precioFinal = (int) (precioBase * (1 - descuento)); // === DEPURACIÓN 5: Confirmación del precio final calculado por entrada en estadísticas
                    totalBruto += precioBase;
                    totalNeto += precioFinal;

                    System.out.println("Código: " + codigos[i] + " | Cliente: " + nombres[i] + " | Tipo: " + tiposEntrada[i] + " | Precio final: $" + precioFinal);
                }
            }

            if (!hayEntradas) {
                System.out.println("No hay entradas reservadas actualmente.");
            } else {
                int totalDescuento = totalBruto - totalNeto;
                double porcentajeDescuento = (totalDescuento * 100.0) / totalBruto;

                System.out.println("\n--- Totales ---");
                System.out.println("Entradas reservadas: " + totalReservadas);
                System.out.println("Total bruto: $" + totalBruto);
                System.out.println("Descuento total: $" + totalDescuento + " (" + Math.round(porcentajeDescuento) + "%)");
                System.out.println("Total a pagar (neto): $" + totalNeto);
            }

            while (true) {
                System.out.print("\n¿Desea volver al menú principal? (s/n): ");
                String volver = scanner.nextLine().toLowerCase();

                if (volver.equals("s")) {
                    System.out.println("Volviendo al menú principal...");
                    return;
                } else if (volver.equals("n")) {
                    System.out.println("Puede continuar revisando esta sección.");
                    break;
                } else {
                    System.out.println("Opción no válida. Intente con 's' o 'n'.");
                }
            }

        } catch (Exception e) {
            System.out.println("Ocurrió un error al generar las estadísticas. Intente nuevamente.");
            System.out.println("Detalles técnicos: " + e.getMessage());
        }
    }

    // === OPCIÓN 4 ===
    public static void confirmarPago() {
        try {
            System.out.println("\n=== CONFIRMAR PAGO ===");

            boolean hayEntradas = false;
            int totalBruto = 0;
            int totalNeto = 0;

            for (int i = 0; i < codigos.length; i++) {
                if (codigos[i] != null) {
                    hayEntradas = true;
                    break;
                }
            }

            if (!hayEntradas) {
                System.out.println("No hay entradas reservadas para pagar.");
                return;
            }

            boolean confirmar = false;
            while (true) {
                System.out.print("¿Desea confirmar y realizar el pago? (s/n): ");
                String resp = scanner.nextLine().toLowerCase();
                if (resp.equals("s")) {
                    confirmar = true;
                    break;
                } else if (resp.equals("n")) {
                    System.out.println("Pago no realizado. Puede modificar sus entradas desde el menú.");
                    return;
                } else {
                    System.out.println("Opción inválida. Intente con 's' o 'n'.");
                }
            }

            if (confirmar) {
                System.out.println("\n╔══════════════════════════════╗");
                System.out.println("║       🎟️  BOLETA DE COMPRA       ║");
                System.out.println("╠══════════════════════════════╣");

                // 🔁 Primero, contamos entradas activas por cliente
                int[] entradasActivasPorCliente = new int[codigos.length];
                for (int i = 0; i < codigos.length; i++) {
                    if (codigos[i] != null && nombres[i] != null) {
                        for (int j = 0; j < codigos.length; j++) {
                            if (codigos[j] != null && nombres[j] != null
                                    && nombres[j].equals(nombres[i]) && tiposEntrada[j] != null) {
                                entradasActivasPorCliente[i]++;
                            }
                        }
                    }
                }

                for (int i = 0; i < codigos.length; i++) {
                    if (codigos[i] != null) {
                        int precioBase = 0;
                        switch (tiposEntrada[i]) {
                            case "VIP":
                                precioBase = 10000;
                                break;
                            case "PLATEA":
                                precioBase = 8000;
                                break;
                            case "GENERAL":
                                precioBase = 5000;
                                break;
                        }

                        double descuento = 0;
                        if (tiposCliente[i].equals("Estudiante")) {
                            descuento += 0.10;
                        } else if (tiposCliente[i].equals("Menor de edad")) {
                            descuento += 0.06;
                        } else if (tiposCliente[i].equals("Tercera Edad")) {
                            descuento += 0.08;
                        }

                        // Aplicar 5% solo si tiene 3 o más entradas activas
                        if (entradasActivasPorCliente[i] >= 3) { // === DEPURACIÓN 6: Verificando cantidad actual de entradas activas del cliente
                            descuento += 0.05;
                        }

                        int precioFinal = (int) (precioBase * (1 - descuento));
                        totalBruto += precioBase;
                        totalNeto += precioFinal;

                        System.out.println("Código: " + codigos[i]);
                        System.out.println("Cliente: " + nombres[i] + " | Edad: " + edades[i]);
                        System.out.println("Tipo entrada: " + tiposEntrada[i]);
                        System.out.println("Precio base: $" + precioBase);
                        System.out.println("Precio final c/descuento: $" + precioFinal);
                        System.out.println("──────────────────────────────");
                    }
                }

                int totalDescuento = totalBruto - totalNeto;
                double porcentaje = (totalDescuento * 100.0) / totalBruto;

                System.out.println("Total bruto: $" + totalBruto);
                System.out.println("Descuento aplicado: $" + totalDescuento + " (" + Math.round(porcentaje) + "%)");
                System.out.println("TOTAL A PAGAR: $" + totalNeto); // === DEPURACIÓN 7: Confirmación del cálculo final de descuento y total neto a pagar
                System.out.println("╠══════════════════════════════╣");
                System.out.println("║ ¡Gracias por su compra! 🙌    ║");
                System.out.println("╚══════════════════════════════╝");

                while (true) {
                    System.out.print("\n¿Desea volver al menú principal? (s/n): ");
                    String volver = scanner.nextLine().toLowerCase();

                    if (volver.equals("s")) {
                        System.out.println("Volviendo al menú principal...");
                        return;
                    } else if (volver.equals("n")) {
                        System.out.println("\nGracias por confiar en el Teatro Moro. ¡Hasta la próxima función! 🎭✨");
                        System.exit(0);
                    } else {
                        System.out.println("Opción inválida. Intente con 's' o 'n'.");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error al procesar el pago. Intente nuevamente.");
            System.out.println("Detalles técnicos: " + e.getMessage());
        }
    }

}
