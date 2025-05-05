package bvs8tm;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BVS8TM {

    static int stockVIP = 5;
    static int stockPlatea = 5;
    static int stockPalco = 5;
    static double ingresosTotales = 0.0;
    static int reservasProcesadas = 0;

    static final int PRECIO_VIP = 10000;
    static final int PRECIO_PLATEA = 8000;
    static final int PRECIO_PALCO = 5000;

    static ArrayList<Reserva> reservas = new ArrayList<>();

    public static void main(String[] args) {
        mostrarMenu();
    }

    public static void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("║     🎭 Bienvenidos al sistema del Teatro Moro 🎭     ");
            System.out.println("╚════════════════════════════════════════════════╝\n");

            // Promociones disponibles
            System.out.println("🎟️ Promociones disponibles:");
            System.out.println(" - Estudiantes .......... 10%");
            System.out.println(" - Tercera Edad (55+) ... 15%");
            System.out.println(" - Menores de edad (<18)  8%");
            System.out.println(" - 3 o más entradas ...... 5%\n");

            // Tipos de asientos
            System.out.println("🪑 Tipos de asientos y precios:");
            System.out.println(" - VIP     .......... $10.000 (Stock: " + (stockVIP == 0 ? "AGOTADO" : stockVIP) + ")");
            System.out.println(" - Platea  .......... $8.000  (Stock: " + (stockPlatea == 0 ? "AGOTADO" : stockPlatea) + ")");
            System.out.println(" - Palco   .......... $5.000  (Stock: " + (stockPalco == 0 ? "AGOTADO" : stockPalco) + ")\n");

            System.out.println("════════════════════════════════════════════════");
            System.out.println("Menú Principal:");
            System.out.println(" 1. Registrar cliente y reservar entradas");
            System.out.println(" 2. Editar reserva");
            System.out.println(" 3. Proceder al pago");
            System.out.println(" 4. Ver ingresos totales (solo personal autorizado)");
            System.out.println(" 5. Salir del sistema\n");

            System.out.print("Seleccione una opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) { // DEPURACIÓN 1: Evaluar opción seleccionada en el menú principal
                    case 1:
                        registrarClienteYReservar();
                        break;
                    case 2:
                        editarReserva();
                        break;
                    case 3:
                        if (reservas.isEmpty()) {
                            System.out.println("📭 No hay reservas registradas para pagar.\n");
                            break;
                        }

                        double totalPago = 0.0;

                        System.out.println("\n🔎 --- RESUMEN DE RESERVAS ---");
                        for (Reserva r : reservas) {
                            System.out.println("ID Reserva: " + r.getId());
                            System.out.println("Cliente: " + r.getNombreCliente());
                            System.out.println("Asiento: " + r.getTipoAsiento().toUpperCase());
                            System.out.println("Cantidad: " + r.getCantidad());
                            System.out.println("Precio unitario: $" + r.getPrecioBase());

                            // Recalcular si es necesario
                            if (r.getPrecioFinal() == 0.0) {
                                double dcto = 0.0;
                                if (r.isEstudiante()) {
                                    dcto += 0.10;
                                }
                                if (r.getEdad() >= 55) {
                                    dcto += 0.15;
                                }
                                if (r.getEdad() < 18) {
                                    dcto += 0.08;
                                }
                                if (r.getCantidad() >= 3) {
                                    dcto += 0.05;
                                }

                                int subtotal = r.getCantidad() * r.getPrecioBase();
                                double finalPago = subtotal - (subtotal * dcto);

                                r.setDescuento(dcto);
                                r.setPrecioFinal(finalPago);
                            }

                            System.out.println("Descuento: " + (int) (r.getDescuento() * 100) + "%");
                            System.out.println("Total: $" + (int) r.getPrecioFinal());
                            System.out.println("-----------------------------------");

                            totalPago += r.getPrecioFinal();
                        }

                        System.out.println("💳 TOTAL A PAGAR: $" + (int) totalPago);

                        System.out.print("\n¿Confirmar pago? (s/n): ");
                        String confirmar = sc.nextLine().toLowerCase();

                        if (confirmar.equalsIgnoreCase("s")) {
                            // Mostrar boleta detallada
                            System.out.println("\n🧾 ╔══════════════════════════════════════════════╗");
                            System.out.println("   ║             BOLETA DE COMPRA - TEATRO MORO            ║");
                            System.out.println("   ╚══════════════════════════════════════════════╝");

                            for (Reserva r : reservas) {
                                int subtotal = r.getCantidad() * r.getPrecioBase();

                                System.out.println("\n🎟️ Reserva N°" + r.getId());
                                System.out.println("👤 Cliente: " + r.getNombreCliente());
                                System.out.println("Edad: " + r.getEdad() + (r.getEdad() >= 55 ? " (Tercera Edad)" : ""));
                                System.out.println("Estudiante: " + (r.isEstudiante() ? "Sí" : "No"));
                                System.out.println("Asiento: " + r.getTipoAsiento().toUpperCase());
                                System.out.println("Cantidad: " + r.getCantidad());
                                System.out.println("Precio unitario: $" + r.getPrecioBase());
                                System.out.println("Subtotal: $" + subtotal);
                                System.out.println("Descuento aplicado: " + (int) (r.getDescuento() * 100) + "%");
                                System.out.println("💰 Total final: $" + (int) r.getPrecioFinal());
                                System.out.println("----------------------------------------");
                            }

                            System.out.println("💳 TOTAL GENERAL PAGADO: $" + (int) totalPago);
                            System.out.println("\n🎭 ¡Gracias por su visita al Teatro Moro!");
                            System.out.println("📆 Recuerde llegar 15 minutos antes de la función.");
                            System.out.println("══════════════════════════════════════════════");

                            ingresosTotales += totalPago;
                            reservas.clear();

                            System.out.print("\n¿Desea volver al menú principal? (s/n): ");
                            String volver = sc.nextLine().toLowerCase();
                            if (volver.equals("n")) {
                                System.out.println("👋 ¡Hasta la próxima función!");
                                return; // se mantiene en el menú mientras no diga 'n'
                            }

                        } else {
                            System.out.println("🔁 Pago cancelado. Puede seguir editando sus reservas.");
                        }
                        break;

                    case 4:
                        System.out.print("🔐 Ingrese clave de acceso: ");
                        String clave = sc.nextLine().trim();

                        if (clave.equals("fdprogramacion")) {
                            System.out.println("\n🎟️ INTRANET DEL TEATRO MORO 🎟️");
                            System.out.println("💰 Ingresos totales acumulados: $" + (int) ingresosTotales);
                            System.out.println("🧾 Reservas registradas: " + reservas.size());
                        } else {
                            System.out.println("🚫 Acceso denegado. Esta sección es solo para personal autorizado.");
                        }

                        System.out.print("¿Volver al menú principal? (s/n): ");
                        String volverAdmin = sc.nextLine().toLowerCase();
                        if (!volverAdmin.equals("s")) {
                            System.out.println("👋 Hasta luego.");
                            break; // ← Esto es clave para que vuelva al menú sin salirse del loop
                        }
                        break;

                    case 5:
                        System.out.println("\nGracias por usar el sistema del Teatro Moro 🎭");
                        break;
                    default:
                        System.out.println("⚠️ Opción no válida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("⚠️ Error: Ingrese un número válido.\n");
                sc.nextLine();
            }

        } while (opcion != 5);

        sc.close();
    }

    public static void registrarClienteYReservar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Registro de Cliente y Reserva de Entradas ---");
        System.out.print("Ingrese su nombre y apellido: "); // DEPURACIÓN 2: Entrada de nombre del cliente para verificar entrada vacía o mal formateada
        String nombre = sc.nextLine();
        if (nombre.trim().isEmpty()) {
            System.out.println("⚠️ El nombre no puede estar vacío.");
            return;
        }

        int edad = 0;
        while (true) {
            try {
                System.out.print("Ingrese su edad: "); // DEPURACIÓN 3: Validación de edad ingresada, errores comunes: texto o valor negativo
                edad = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Error: Debe ingresar un número válido.");
                sc.nextLine();
            }
        }

        String respuestaEstudiante;
        boolean esEstudiante = false;
        while (true) {
            System.out.print("¿Es usted estudiante? (s/n): "); // DEPURACIÓN 4: Validar entrada 's' o 'n' para determinar si es estudiante
            respuestaEstudiante = sc.nextLine().toLowerCase();
            if (respuestaEstudiante.equals("s")) {
                esEstudiante = true;
                break;
            } else if (respuestaEstudiante.equals("n")) {
                esEstudiante = false;
                break;
            } else {
                System.out.println("⚠️ Respuesta inválida. Ingrese 's' o 'n'.");
            }
        }

        // Mostrar opciones
        System.out.println("\n🪑 Tipos de asientos disponibles:"); // DEPURACIÓN 5: Mostrar asientos disponibles antes de seleccionar tipo
        System.out.println(" - VIP     .......... $10.000 (Stock: " + stockVIP + ")");
        System.out.println(" - Platea  .......... $8.000  (Stock: " + stockPlatea + ")");
        System.out.println(" - Palco   .......... $5.000  (Stock: " + stockPalco + ")");

        String tipoAsiento = "";
        while (true) {
            System.out.print("\nIngrese el tipo de asiento (VIP / Platea / Palco): "); // DEPURACIÓN 6: Validar tipo de asiento ingresado (mayúsculas, agotados, mal escrito)
            tipoAsiento = sc.nextLine().toLowerCase();

            if (!(tipoAsiento.equals("vip") || tipoAsiento.equals("platea") || tipoAsiento.equals("palco"))) {
                System.out.println("❌ Opción inválida. Debe ser 'VIP', 'Platea' o 'Palco'.");
                continue;
            }

            boolean sinStock = (tipoAsiento.equals("vip") && stockVIP == 0)
                    || (tipoAsiento.equals("platea") && stockPlatea == 0)
                    || (tipoAsiento.equals("palco") && stockPalco == 0);

            if (sinStock) {
                System.out.println("❌ No hay stock disponible para ese tipo de asiento.");
            } else {
                break;
            }
        }

        int cantidad = 0;
        while (true) {
            try {
                System.out.print("Ingrese la cantidad de entradas (mínimo 1): "); // DEPURACIÓN 7: Validar cantidad ingresada, y que no supere el stock disponible
                cantidad = sc.nextInt();
                sc.nextLine();
                if (cantidad <= 0) {
                    System.out.println("⚠️ Debe ingresar al menos una entrada.");
                    continue;
                }

                boolean superaStock = (tipoAsiento.equals("vip") && cantidad > stockVIP)
                        || (tipoAsiento.equals("platea") && cantidad > stockPlatea)
                        || (tipoAsiento.equals("palco") && cantidad > stockPalco);

                if (superaStock) {
                    System.out.println("❌ No hay suficiente stock para esa cantidad.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Error: Debe ingresar un número válido.");
                sc.nextLine();
            }
        }

        // Restar stock
        switch (tipoAsiento) {
            case "vip" ->
                stockVIP -= cantidad;
            case "platea" ->
                stockPlatea -= cantidad;
            case "palco" ->
                stockPalco -= cantidad;
        }

        // Obtener precio base
        int precioBase = switch (tipoAsiento) {
            case "vip" ->
                PRECIO_VIP;
            case "platea" ->
                PRECIO_PLATEA;
            case "palco" ->
                PRECIO_PALCO;
            default ->
                0;
        };

        Reserva nueva = new Reserva(nombre, edad, esEstudiante, tipoAsiento, cantidad, precioBase);
        reservas.add(nueva);

        double descuento = 0.0; // DEPURACIÓN 8: Calcular y verificar descuentos aplicados correctamente
        if (esEstudiante) {
            descuento += 0.10;
        }
        if (edad >= 55) {
            descuento += 0.15;
        }
        if (edad < 18) {
            descuento += 0.08;
        }
        if (cantidad >= 3) {
            descuento += 0.05;
        }

        int subtotal = cantidad * precioBase;
        double totalConDescuento = subtotal - (subtotal * descuento);

        nueva.setDescuento(descuento);
        nueva.setPrecioFinal(totalConDescuento); // DEPURACIÓN 9: Confirmar cálculo de precio final con descuento aplicado

        System.out.println("\n✅ Reserva registrada. N°" + nueva.getId());
        System.out.println("Cliente: " + nombre);
        System.out.println("Estudiante: " + (esEstudiante ? "Sí" : "No"));
        System.out.println("Asiento: " + tipoAsiento + " | Cantidad: " + cantidad);
        System.out.println("Precio base: $" + precioBase);
        System.out.println("⚠️ Descuentos se aplicarán en el proceso de pago.\n");

        String volver;
        do {
            System.out.print("¿Volver al menú principal? (s/n): ");
            volver = sc.nextLine().trim().toLowerCase();
            if (!volver.equals("s") && !volver.equals("n")) {
                System.out.println("⚠️ Opción inválida. Ingrese 's' para sí o 'n' para no.");
            }
        } while (!volver.equals("s") && !volver.equals("n"));

        if (volver.equals("n")) {
            String pagar;
            do {
                System.out.print("¿Desea proceder al pago ahora? (s/n): ");
                pagar = sc.nextLine().trim().toLowerCase();
                if (!pagar.equals("s") && !pagar.equals("n")) {
                    System.out.println("⚠️ Opción inválida. Ingrese 's' para sí o 'n' para no.");
                }
            } while (!pagar.equals("s") && !pagar.equals("n"));

            if (pagar.equals("s")) {
                procederPago(sc);
            } else {
                System.out.println("🔁 Volviendo al menú principal...");
            }
        }

    }

    public static void editarReserva() {
        Scanner sc = new Scanner(System.in);

        if (reservas.isEmpty()) {
            System.out.println("\n📭 No hay reservas registradas para editar.\n");
            return;
        }

        System.out.println("\n--- 📝 Lista de reservas registradas ---");
        for (Reserva r : reservas) {
            System.out.println("ID: " + r.getId() + " | Cliente: " + r.getNombreCliente() + " | Asiento: " + r.getTipoAsiento()
                    + " | Cantidad: " + r.getCantidad() + " | Precio base: $" + r.getPrecioBase());
        }

        int idBuscar;
        Reserva seleccionada = null;

        while (true) {
            try {
                System.out.println("\n🟢 Ingrese el número de ID mostrado arriba para seleccionar la reserva:"); // DEPURACIÓN 10: Confirmar cálculo de precio final con descuento aplicado
                System.out.print("✏️ ID de reserva a editar: "); // DEPURACIÓN 11: Captura del ID de reserva ingresado para edición
                idBuscar = sc.nextInt();
                sc.nextLine();
                for (Reserva r : reservas) { // DEPURACIÓN 12: Validar si la reserva con el ID existe en la lista para permitir edición
                    if (r.getId() == idBuscar) {
                        seleccionada = r;
                        break;
                    }
                }
                if (seleccionada == null) {
                    System.out.println("⚠️ ID no encontrado. Intente nuevamente.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Error: debe ingresar un número válido.");
                sc.nextLine();
            }
        }

        System.out.println("\nReserva seleccionada:"); // DEPURACIÓN 13: Confirmar que los datos de la reserva seleccionada se muestran correctamente
        System.out.println("Cliente: " + seleccionada.getNombreCliente());
        System.out.println("Asiento actual: " + seleccionada.getTipoAsiento());
        System.out.println("Cantidad actual: " + seleccionada.getCantidad());
        System.out.println("ID reserva: " + seleccionada.getId());
        System.out.println("Precio base: $" + seleccionada.getPrecioBase());
        System.out.println("Descuento aplicado: " + (int) (seleccionada.getDescuento() * 100) + "%");
        System.out.println("Precio final actual: $" + seleccionada.getPrecioFinal());

        while (true) {
            System.out.println("\n¿Qué desea hacer con esta reserva?");
            System.out.println(" 1. Cambiar tipo de asiento");
            System.out.println(" 2. Cambiar cantidad de entradas");
            System.out.println(" 3. Eliminar esta reserva");
            System.out.println(" 4. Recalcular descuentos");
            System.out.println(" 5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcionEditar = -1; // DEPURACIÓN 14: Verificar opción seleccionada en el menú de edición de reserva
            try {
                opcionEditar = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Error: debe ingresar un número válido.");
                sc.nextLine();
                continue;
            }

            switch (opcionEditar) {

                case 1:
                    System.out.println("\n🪑 Tipos de asientos disponibles:");
                    System.out.println(" - VIP     .......... $10.000 (Stock: " + (stockVIP == 0 ? "AGOTADO" : stockVIP) + ")");
                    System.out.println(" - Platea  .......... $8.000  (Stock: " + (stockPlatea == 0 ? "AGOTADO" : stockPlatea) + ")");
                    System.out.println(" - Palco   .......... $5.000  (Stock: " + (stockPalco == 0 ? "AGOTADO" : stockPalco) + ")");
                    System.out.print("Ingrese el nuevo tipo de asiento (VIP / Platea / Palco): "); // DEPURACIÓN 15: Validar tipo de asiento introducido y stock correspondiente
                    String nuevoTipo = sc.nextLine().trim();

                    if (!nuevoTipo.equalsIgnoreCase("vip") && !nuevoTipo.equalsIgnoreCase("platea") && !nuevoTipo.equalsIgnoreCase("palco")) {
                        System.out.println("❌ Tipo de asiento no válido.");
                        break;
                    }

                    int stockDisponible = 0;
                    int nuevoPrecio = 0;
                    switch (nuevoTipo.toLowerCase()) {
                        case "vip":
                            stockDisponible = stockVIP;
                            nuevoPrecio = PRECIO_VIP;
                            break;
                        case "platea":
                            stockDisponible = stockPlatea;
                            nuevoPrecio = PRECIO_PLATEA;
                            break;
                        case "palco":
                            stockDisponible = stockPalco;
                            nuevoPrecio = PRECIO_PALCO;
                            break;
                    }

                    if (stockDisponible < seleccionada.getCantidad()) {
                        System.out.println("❌ No hay suficiente stock disponible para cambiar a " + nuevoTipo + ".");
                        break;
                    }

                    // Devolver stock anterior
                    switch (seleccionada.getTipoAsiento().toLowerCase()) {
                        case "vip":
                            stockVIP += seleccionada.getCantidad();
                            break;
                        case "platea":
                            stockPlatea += seleccionada.getCantidad();
                            break;
                        case "palco":
                            stockPalco += seleccionada.getCantidad();
                            break;
                    }

                    // Descontar nuevo stock
                    switch (nuevoTipo.toLowerCase()) {
                        case "vip":
                            stockVIP -= seleccionada.getCantidad();
                            break;
                        case "platea":
                            stockPlatea -= seleccionada.getCantidad();
                            break;
                        case "palco":
                            stockPalco -= seleccionada.getCantidad();
                            break;
                    }

                    seleccionada.setTipoAsiento(nuevoTipo.toLowerCase());
                    seleccionada.setPrecioBase(nuevoPrecio);
                    System.out.println("✅ Tipo de asiento actualizado correctamente.");
                    System.out.println("⚠️ Recuerda recalcular el descuento antes de proceder al pago.");
                    break;

                case 2:
                    System.out.print("Ingrese la nueva cantidad de entradas (mínimo 1): "); // DEPURACIÓN 16: Verificar que la cantidad ingresada sea válida y que el stock lo permita
                    int nuevaCantidad;
                    try {
                        nuevaCantidad = Integer.parseInt(sc.nextLine());
                        if (nuevaCantidad < 1) {
                            System.out.println("⚠️ Debe ingresar al menos 1 entrada.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Entrada inválida. Ingrese un número válido.");
                        break;
                    }

                    String tipo = seleccionada.getTipoAsiento();
                    int stockActual = switch (tipo) {
                        case "vip" ->
                            stockVIP + seleccionada.getCantidad();
                        case "platea" ->
                            stockPlatea + seleccionada.getCantidad();
                        case "palco" ->
                            stockPalco + seleccionada.getCantidad();
                        default ->
                            0;
                    };

                    if (nuevaCantidad > stockActual) {
                        System.out.println("⚠️ No hay suficiente stock para modificar a esa cantidad.");
                        break;
                    }

                    // Devolver stock previo y descontar nuevo
                    switch (tipo) {
                        case "vip" -> {
                            stockVIP = stockActual - nuevaCantidad;
                        }
                        case "platea" -> {
                            stockPlatea = stockActual - nuevaCantidad;
                        }
                        case "palco" -> {
                            stockPalco = stockActual - nuevaCantidad;
                        }
                    }

                    seleccionada.setCantidad(nuevaCantidad);
                    seleccionada.setPrecioFinal(0); // Se requiere recálculo
                    System.out.println("✅ Cantidad de entradas actualizada correctamente.");
                    System.out.println("⚠️ Recuerda recalcular el descuento antes de proceder al pago.");

                    // Menú local de decisión
                    System.out.print("¿Volver al menú de edición de la reserva? (s/n): ");
                    String continuar2 = sc.nextLine().toLowerCase();
                    if (!continuar2.equals("s")) {
                        System.out.print("¿Desea proceder al pago ahora? (s/n): ");
                        String pagar2 = sc.nextLine().toLowerCase();
                        if (pagar2.equals("s")) {
                            procederPago(sc);
                            return;
                        } else {
                            System.out.println("🔁 Regresando al menú de edición de la reserva...");
                        }
                    }
                    break;

                case 3:
                    System.out.print("¿Estás seguro de que deseas eliminar esta reserva? (s/n): ");
                    String confirmacion = sc.nextLine().toLowerCase();
                    if (confirmacion.equals("s")) {
                        // Devolver stock al eliminar
                        switch (seleccionada.getTipoAsiento()) {
                            case "vip" ->
                                stockVIP += seleccionada.getCantidad();
                            case "platea" ->
                                stockPlatea += seleccionada.getCantidad();
                            case "palco" ->
                                stockPalco += seleccionada.getCantidad();
                        }

                        reservas.remove(seleccionada); // DEPURACIÓN 17: Confirmar eliminación de reserva y verificación de reintegración de stock
                        System.out.println("🗑️ Reserva eliminada exitosamente.");

                        // Si no hay más reservas, salir al menú principal
                        if (reservas.isEmpty()) {
                            System.out.println("📭 No hay más reservas activas. Regresando al menú principal...");
                            return;
                        }

                        //Menú local
                        System.out.print("¿Desea editar otra reserva? (s/n): ");
                        String otra = sc.nextLine().toLowerCase();
                        if (!otra.equals("s")) {
                            System.out.print("¿Desea proceder al pago ahora? (s/n): ");
                            String pagar3 = sc.nextLine().toLowerCase();
                            if (pagar3.equals("s")) {
                                procederPago(sc);
                                return;
                            } else {
                                System.out.println("🔁 Regresando al menú de edición de la reserva...");
                            }
                        }
                    } else {
                        System.out.println("❎ Operación cancelada.");
                    }
                    break;

                case 4:
                    double descuento = 0.0;

                    // Verificamos condiciones para aplicar descuentos
                    if (seleccionada.isEstudiante()) {
                        descuento += 0.10;
                    }
                    if (seleccionada.getEdad() >= 55) {
                        descuento += 0.15;
                    }
                    if (seleccionada.getEdad() < 18) {
                        descuento += 0.08;
                    }
                    if (seleccionada.getCantidad() >= 3) {
                        descuento += 0.05;
                    }

                    // Cálculo del precio final con base en la cantidad y precio base
                    int subtotal = seleccionada.getCantidad() * seleccionada.getPrecioBase(); // DEPURACIÓN 18: Validar recálculo correcto del descuento y actualización del total
                    double totalConDescuento = subtotal - (subtotal * descuento);

                    // Guardar en la reserva
                    seleccionada.setDescuento(descuento);
                    seleccionada.setPrecioFinal(totalConDescuento);

                    // Mostrar resultados
                    System.out.println("\n💰 Recalculando descuentos..."); // DEPURACIÓN 19: Inicia proceso de recálculo de descuentos y total
                    System.out.println("Subtotal: $" + subtotal);
                    System.out.println("Descuento aplicado: " + (int) (descuento * 100) + "%");
                    System.out.println("Total a pagar: $" + (int) totalConDescuento);
                    System.out.println("✅ Descuento actualizado correctamente.\n"); // DEPURACIÓN 20: Confirmar que los valores calculados son correctos

                    System.out.print("¿Volver al menú de edición de la reserva? (s/n): "); // DEPURACIÓN 21: Control de flujo posterior al recálculo
                    String volver = sc.nextLine().toLowerCase();
                    if (volver.equals("s")) {
                        continue; // vuelve al menú de edición
                    } else {
                        System.out.print("¿Desea proceder al pago ahora? (s/n): ");
                        String pagar = sc.nextLine().toLowerCase();
                        if (pagar.equals("s")) {
                            procederPago(sc);
                            return; // sale de la edición porque ya pagó
                        } else {
                            System.out.println("🔁 Volviendo al menú de edición de la reserva...");
                            continue;
                        }
                    }

                case 5:
                    System.out.println("🔁 Volviendo al menú principal...\n"); // DEPURACIÓN 22: Confirmar cierre correcto del sistema
                    return;
                default:
                    System.out.println("⚠️ Opción inválida. Intente nuevamente.");
            }
        }

    }

    public static void procederPago(Scanner sc) {
        if (reservas.isEmpty()) {
            System.out.println("\n📭 No hay reservas pendientes.");
            return;
        }

        System.out.println("\n--- 💳 Procesando pago y generando boletas ---"); // DEPURACIÓN 23: Inicia la generación de boletas para cada reserva activa

        for (Reserva r : reservas) { // DEPURACIÓN 24: Evaluar contenido de cada reserva antes de imprimir boleta
            System.out.println("\n╔══════════════════════════════════════════════╗");
            System.out.println("║             🎟️  Boleta de Entrada               ║");
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.printf("║ ID de Reserva: %-30s ║\n", r.getId());
            System.out.printf("║ Cliente: %-35s ║\n", r.getNombreCliente());
            System.out.printf("║ Tipo de asiento: %-28s ║\n", r.getTipoAsiento());
            System.out.printf("║ Cantidad de entradas: %-24s ║\n", r.getCantidad());
            System.out.printf("║ Precio unitario: $%-25d ║\n", r.getPrecioBase());
            System.out.printf("║ Subtotal: $%-33d ║\n", (r.getCantidad() * r.getPrecioBase()));
            System.out.printf("║ Descuento aplicado: %-24s ║\n", (int) (r.getDescuento() * 100) + "%");
            System.out.printf("║ Total a pagar: $%-28d ║\n", (int) r.getPrecioFinal());
            System.out.println("╠══════════════════════════════════════════════╣");
            System.out.println("║    🙏 ¡Gracias por su visita al Teatro Moro!     ║");
            System.out.println("╚══════════════════════════════════════════════╝");
        }

        for (Reserva r : reservas) { // DEPURACIÓN 25: Acumular ingresos totales por cada reserva procesada
            ingresosTotales += r.getPrecioFinal();
        }

        reservasProcesadas += reservas.size();
        reservas.clear(); // DEPURACIÓN 26: Eliminar todas las reservas después del pago para evitar duplicación

        System.out.print("¿Desea volver al menú principal? (s/n): ");
        String volver = sc.nextLine().trim().toLowerCase();
        while (!volver.equals("s") && !volver.equals("n")) {
            System.out.print("⚠️ Por favor, ingrese 's' o 'n': ");
            volver = sc.nextLine().trim().toLowerCase();
        }
        if (volver.equals("n")) {
            System.out.println("👋 ¡Hasta la próxima función!");
            System.exit(0);
        }

        return;

    }

}

// Clase Reserva 
class Reserva {

    private static int contador = 1;

    private int id;
    private String nombreCliente;
    private int edad;
    private boolean esEstudiante;
    private String tipoAsiento;
    private int cantidad;
    private int precioBase;
    private double descuento;
    private double precioFinal;

    public Reserva(String nombreCliente, int edad, boolean esEstudiante,
            String tipoAsiento, int cantidad, int precioBase) {
        this.id = contador++;
        this.nombreCliente = nombreCliente;
        this.edad = edad;
        this.esEstudiante = esEstudiante;
        this.tipoAsiento = tipoAsiento;
        this.cantidad = cantidad;
        this.precioBase = precioBase;
        this.descuento = 0.0;
        this.precioFinal = 0.0;
    }

    public int getId() {
        return id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isEstudiante() {
        return esEstudiante;
    }

    public String getTipoAsiento() {
        return tipoAsiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public void setTipoAsiento(String tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }
}
