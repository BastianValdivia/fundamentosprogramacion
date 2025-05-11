package eftbastianvaldivias9;

import java.util.Scanner;
import java.util.ArrayList;

public class EFTBastianValdiviaS9 {
    
     // ===================== VARIABLES ESTÁTICAS Y GLOBALES =====================

    // 🏷️ Constantes generales
    public final static int NUM_ZONAS = 5;      // Cantidad de zonas del teatro
    public final static int NUM_ASIENTOS = 5;   // Asientos por zona

    // 🪑 Zonas y precios
    public static final String[] zonas = {"VIP", "Palco", "Platea Baja", "Platea Alta", "Galería"};
    public static final int[] precios = {20000, 15000, 10000, 8000, 5000};

    // 🎟️ Control de stock y asientos
    public static int[] stock = {5, 5, 5, 5, 5};                      // Entradas disponibles por zona
    public static boolean[][] asientosOcupados = new boolean[NUM_ZONAS][NUM_ASIENTOS];

    // 💾 Registro de ventas temporales (previas al pago)
    public static ArrayList<Entrada> entradasVendidas = new ArrayList<>();

    // 📊 Estadísticas permanentes (ventas procesadas)
    public static int[] ingresosPorZona = {0, 0, 0, 0, 0};
    public static int[] descuentosPorZona = {0, 0, 0, 0, 0};
    public static int[] entradasVendidasPorZona = {0, 0, 0, 0, 0};

    // 🎤 Escáner global para entradas de usuario
    public static Scanner input = new Scanner(System.in);


    public static void main(String[] args) {

        mostrarBienvenida();
        menuPrincipal();

    }

    public static void mostrarBienvenida() {
        // Recalcular stock desde cero basado en asientos ocupados
        for (int i = 0; i < stock.length; i++) {
            int libres = 0;
            for (boolean ocupado : asientosOcupados[i]) {
                if (!ocupado) {
                    libres++;
                }
            }
            stock[i] = libres;
        }

        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                             🎭  TEATRO MORO - SISTEMA DE ENTRADAS  🎭             ");
        System.out.println("                           \"Donde cada butaca guarda una historia...\"              ");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝\n");

        System.out.println("🎫 PROMOCIONES ESPECIALES:");
        System.out.println("──────────────────────────────────────────────────────────────────────────────");
        System.out.printf(" %-12s ➤ %s%n", "👶 Niños", "Pequeños soñadores (0-12 años) - 10% de descuento");
        System.out.printf(" %-12s ➤ %s%n", "👩 Mujeres", "Protagonistas del arte - 20% de descuento");
        System.out.printf(" %-12s ➤ %s%n", "🎓 Estudiantes", "Cultivadores del saber - 15% de descuento");
        System.out.printf(" %-12s ➤ %s%n", "👴 Tercera Edad", "Guardianes de historias (60+ años) - 25% de descuento");
        System.out.println("⚠️  Solo se aplicará el descuento mayor. No acumulables.\n");

        System.out.println("🪑 ZONAS DISPONIBLES Y SU ENCANTO:");
        System.out.println("──────────────────────────────────────────────────────────────────────────────");
        System.out.printf(" %-15s | %-40s | %-10s | %s%n", "Zona", "Descripción escénica", "Precio", "Entradas");
        System.out.println("──────────────────────────────────────────────────────────────────────────────");

        for (int i = 0; i < zonas.length; i++) {
            String estado = (stock[i] == 0) ? "AGOTADO" : stock[i] + " 🪑";
            System.out.printf(" %-15s | %-40s | $%,8d |   %s%n",
                    zonas[i],
                    descripcionZonaPoetica(i),
                    precios[i],
                    estado);
        }

        System.out.println("\n════════════════════════════════════════════════════════════════════════════════════\n");
    }

    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("══════════════════════════════════════════════════════════");
            System.out.println("🎭 MENÚ PRINCIPAL - VIVE LA EXPERIENCIA DEL TEATRO MORO ?");
            System.out.println("══════════════════════════════════════════════════════════");
            System.out.println("  1️⃣  Comprar entradas para una función inolvidable");
            System.out.println("  2️⃣  Ver ingresos (Solo personal autorizado)");
            System.out.println("  3️⃣  Salir del sistema");
            System.out.print("\n🎟️  ¿Qué desea hacer hoy? → ");

            opcion = input.nextInt();
            System.out.println();

            switch (opcion) {
                case 1:
                    comprarEntradas();
                    break;
                case 2:
                    verIngresos();  // ← Esto llama el método real que pide la clave
                    break;

                case 3:
                    System.out.println("🎭 Gracias por elegir el Teatro Moro. ¡Que la función continúe en su corazón!\n");
                    break;
                default:
                    System.out.println("⚠️  Opción inválida. Intente nuevamente.\n");
            }

        } while (opcion != 5);
    }

    public static String descripcionZonaPoetica(int i) {
        switch (i) {
            case 0:
                return "El corazón dorado frente al telón";
            case 1:
                return "Altura de gala, vista imperial";
            case 2:
                return "Eco vibrante de emociones cercanas";
            case 3:
                return "Refugio elevado para la contemplación";
            case 4:
                return "Donde el arte susurra desde lo alto";
            default:
                return "Espacio sin nombre aún...";
        }
    }

    public static void comprarEntradas() {
        System.out.println("🎟️ INICIO DE COMPRA DE ENTRADAS");
        System.out.println("───────────────────────────────────────────────");

        int cantidad;
        do {
            System.out.print("¿Cuántas entradas desea comprar? (10 max) o 0 para volver al menú: "); // 🎯 DEPURACIÓN: Validar cantidad de entradas ingresadas (evitar bucles infinitos o valores negativos)
            while (!input.hasNextInt()) {
                System.out.print("❌ Ingrese un número válido: ");
                input.next();
            }
            cantidad = input.nextInt();
            input.nextLine(); // limpiar buffer

            if (cantidad == 0) {
                System.out.println("🔙 Has cancelado la compra. Volviendo al menú principal...\n");
                mostrarBienvenida();
                menuPrincipal();
                return;
            }

        } while (cantidad < 1 || cantidad > 10);

        for (int i = 1; i <= cantidad; i++) {
            System.out.println("════════════════════════════════════════════════════════");
            System.out.printf("         📌 INGRESO DE DATOS PARA ENTRADA #%d        %n", i);
            System.out.println("════════════════════════════════════════════════════════");
            System.out.println("💬 Complete los siguientes datos del espectador:");
            System.out.println("────────────────────────────────────────────────────────");

            // 1. Nombre completo
            String nombre;
            do {
                System.out.print("Ingrese Nombre y Apellido: ");
                nombre = input.nextLine().trim();

                if (nombre.isEmpty() || nombre.replaceAll("\\s+", "").length() < 2) {
                    System.out.println("⚠️ Nombre inválido. Debe contener al menos 2 caracteres reales. Intente nuevamente.\n");
                }
            } while (nombre.isEmpty() || nombre.replaceAll("\\s+", "").length() < 2);

            // 2. Género
            String generoInput;
            String generoFinal = null;

            do {
                System.out.print("Ingrese Género (M = Mujer / H = Hombre / O = Otro): ");
                generoInput = input.nextLine().trim().toLowerCase();

                if (generoInput.equals("m") || generoInput.equals("mujer")) {
                    generoFinal = "M";
                } else if (generoInput.equals("h") || generoInput.equals("hombre")) {
                    generoFinal = "H";
                } else if (generoInput.equals("o") || generoInput.equals("otro")) {
                    generoFinal = "O";
                } else {
                    System.out.println("⚠️ Opción inválida. Escriba M, H u O (o sus equivalentes). Intente nuevamente.\n");
                }

            } while (generoFinal == null);

            // 3. Edad
            int edad;
            do {
                System.out.print("Ingrese Edad: "); // 🎯 DEPURACIÓN: Validación de edad ingresada, errores comunes: texto o valor negativo
                while (!input.hasNextInt()) {
                    System.out.print("❌ Ingrese una edad válida: ");
                    input.next();
                }
                edad = input.nextInt();
                input.nextLine();
            } while (edad < 0 || edad > 120);

            // 4. Selección de zona
            int zonaElegida;
            do {
                System.out.println("════════════════════════════════════════════════════════════════════");
                System.out.println("                       🪑 ZONAS DISPONIBLES                         ");
                System.out.println("════════════════════════════════════════════════════════════════════");

                System.out.println("💬 Elija la zona que mejor se adapte a su experiencia deseada:");
                System.out.println("──────────────────────────────────────────────────────────────────────");

                for (int z = 0; z < zonas.length; z++) {
                    System.out.printf(" %d️⃣  %-13s │ Precio: $%,6d │ Disponibles: %d%n",
                            z + 1, zonas[z], precios[z], stock[z]);
                }

                System.out.println("──────────────────────────────────────────────────────────────────────");
                System.out.print("🎟️  Elija una zona para su entrada (1-5): "); // 🎯 DEPURACIÓN: Comprobar selección correcta de zona y verificar stock disponible

                while (!input.hasNextInt()) {
                    System.out.print("❌ Ingrese un número válido: ");
                    input.next();
                }
                zonaElegida = input.nextInt() - 1;
                input.nextLine();
            } while (zonaElegida < 0 || zonaElegida >= zonas.length || stock[zonaElegida] == 0);

            // 5. Mapa de asientos y selección
            mostrarMapaAsientos(zonaElegida);
            int asientoElegido;
            do {
                System.out.print("Seleccione número de asiento (1-5): "); // 🎯 DEPURACIÓN: Validar selección de número de asiento y comprobar disponibilidad
                while (!input.hasNextInt()) {
                    System.out.print("❌ Ingrese un número válido (1-5): ");
                    input.next();
                }
                asientoElegido = input.nextInt() - 1;
                input.nextLine();

                if (asientoElegido < 0 || asientoElegido >= 5) {
                    System.out.println("⚠️ Número de asiento fuera de rango. Intente nuevamente.");
                } else if (asientosOcupados[zonaElegida][asientoElegido]) {
                    System.out.println("❌ Ese asiento ya está ocupado. Elija otro.");
                }

            } while (asientoElegido < 0 || asientoElegido >= 5 || asientosOcupados[zonaElegida][asientoElegido]);

            // Marcar asiento como ocupado
            asientosOcupados[zonaElegida][asientoElegido] = true;
            stock[zonaElegida]--;

            // 6. Calcular descuento
            int descuento = calcularDescuento(generoFinal, edad);

            // 7. Crear ID personalizado
            String id = zonas[zonaElegida].substring(0, 3).toUpperCase() + "-" + (asientoElegido + 1);

            // 8. Crear objeto Entrada
            Entrada nueva = new Entrada(id, nombre, generoFinal, edad, zonas[zonaElegida], asientoElegido + 1, precios[zonaElegida], descuento);
            // 9. Guardar y mostrar
            entradasVendidas.add(nueva);
            nueva.mostrarResumen();
        }

        // Final de la compra - Submenú de decisiones
        int opcion;
        do {
            System.out.println("════════════════════════════════════════════════════");
            System.out.println("         🧾 ¿QUÉ DESEA HACER A CONTINUACIÓN?          ");
            System.out.println("════════════════════════════════════════════════════");
            System.out.println("────────────────────────────────────────────────────");
            System.out.println(" 1️⃣  Editar entradas compradas");
            System.out.println(" 2️⃣  Proceder al pago");
            System.out.println(" 3️⃣  Volver al menú principal");
            System.out.println("────────────────────────────────────────────────────");
            System.out.print("Seleccione una opción → ");

            while (!input.hasNextInt()) {
                System.out.print("❌ Ingrese un número válido: ");
                input.next();
            }
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    editarEntradas();
                    break;
                case 2:
                    System.out.println("❓ ¿Está seguro que desea confirmar el pago?"); // 🎯 DEPURACIÓN FINAL: Confirmar antes de procesar el pago definitivo
                    System.out.println(" 1️⃣  Sí, proceder al pago");
                    System.out.println(" 2️⃣  No, volver al menú anterior");
                    System.out.print("Seleccione una opción → ");

                    int confirmarPago;
                    do {
                        while (!input.hasNextInt()) {
                            System.out.print("❌ Ingrese una opción válida: ");
                            input.next();
                        }
                        confirmarPago = input.nextInt();
                        input.nextLine();
                    } while (confirmarPago != 1 && confirmarPago != 2);

                    if (confirmarPago == 1) {
                        procesarPago();
                    } else {
                        System.out.println("🔙 Volviendo al menú anterior...\n");
                    }
                    break;

                case 3:
                    System.out.println("🔁 Volviendo al menú principal...\n");
                    break;
                default:
                    System.out.println("⚠️  Opción no válida. Intente nuevamente.\n");
            }
        } while (opcion != 3);

    }

    public static void mostrarMapaAsientos(int zonaElegida) {
        System.out.println("\n══════════════════════════════════════════════════════════════════");
        System.out.println("             🎭 MAPA DE ASIENTOS (X = ocupado) 🎭                ");
        System.out.println("══════════════════════════════════════════════════════════════════\n");

        System.out.println("                     🎬  ESCENARIO  🎬");
        System.out.println("────────────────────────────────────────────────────────────");

        for (int i = 0; i < zonas.length; i++) {
            System.out.printf(" %-15s → ", zonas[i]);
            for (int j = 0; j < 5; j++) {
                if (asientosOcupados[i][j]) {
                    System.out.print("[x] ");
                } else {
                    System.out.print("[" + (j + 1) + "] ");
                }
            }
            System.out.println();
        }

        System.out.println("────────────────────────────────────────────────────────────");
        System.out.printf("* Seleccione un asiento disponible en la zona: 🎟️ %s%n%n", zonas[zonaElegida]);
    }

    public static int calcularDescuento(String genero, int edad) {
        int descuento = 0;
        if (edad <= 12) {
            descuento = 10;
        }
        if (edad >= 60) {
            descuento = Math.max(descuento, 25);
        }
        if (genero.equals("M")) {
            descuento = Math.max(descuento, 20);
        }
        System.out.print("¿Es estudiante? (S/N): ");
        String esEstudiante = input.nextLine().trim().toUpperCase();
        if (esEstudiante.equals("S")) {
            descuento = Math.max(descuento, 15);
        }
        return descuento;
    }

    public static void editarEntradas() {
        if (entradasVendidas.isEmpty()) {
            System.out.println("⚠️  No hay entradas para editar.\n");
            return;
        }

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("             ✏️ EDICIÓN DE ENTRADAS COMPRADAS             ");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("💬 Estas son sus entradas actuales:\n");

        for (int i = 0; i < entradasVendidas.size(); i++) {
            System.out.println("🟡 Entrada #" + (i + 1));
            entradasVendidas.get(i).mostrarResumen();
        }

        System.out.print("🔢 Ingrese el ID de la entrada que desea editar (ej: VIP-1), o 0 para volver: ");
        String idElegido = input.nextLine().trim().toUpperCase();

        if (idElegido.equals("0")) {
            System.out.println("🔙 Volviendo al menú anterior...\n");
            return;
        }

// Buscar entrada por ID
        Entrada entradaSeleccionada = null;
        for (Entrada e : entradasVendidas) {
            if (e.id.equalsIgnoreCase(idElegido)) {
                entradaSeleccionada = e;
                break;
            }
        }

        if (entradaSeleccionada == null) {
            System.out.println("⚠️ No se encontró una entrada con ese ID. Intente nuevamente.\n");
            return;
        }

        int opcion;
        do {
            System.out.println("════════════════════════════════════");
            System.out.println("     ¿Qué desea modificar?         ");
            System.out.println("════════════════════════════════════");
            System.out.println(" 1️⃣  Cambiar nombre");
            System.out.println(" 2️⃣  Cambiar edad/género (recalcula descuento)");
            System.out.println(" 3️⃣  Cambiar zona y asiento");
            System.out.println(" 4️⃣  Eliminar entrada");
            System.out.println(" 5️⃣  ➕ Agregar nueva entrada");
            System.out.println(" 6️⃣  🔙 Volver al menú anterior");

            while (!input.hasNextInt()) {
                System.out.print("❌ Ingrese un número válido: ");
                input.next();
            }
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("✏️ Ingrese nuevo nombre y apellido: ");
                    String nuevoNombre = input.nextLine().trim();
                    if (!nuevoNombre.isEmpty()) {
                        entradaSeleccionada.nombreCompleto = nuevoNombre;
                        System.out.println("✅ Nombre actualizado correctamente.\n");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese nuevo género (M/H/O): ");
                    String nuevoGenero = input.nextLine().trim().toUpperCase();
                    System.out.print("Ingrese nueva edad: ");
                    int nuevaEdad = input.nextInt();
                    input.nextLine();
                    entradaSeleccionada.genero = nuevoGenero;
                    entradaSeleccionada.edad = nuevaEdad;
                    entradaSeleccionada.descuentoAplicado = calcularDescuento(nuevoGenero, nuevaEdad);
                    entradaSeleccionada.precioFinal = entradaSeleccionada.precioBase
                            - (entradaSeleccionada.precioBase * entradaSeleccionada.descuentoAplicado / 100);
                    System.out.println("✅ Descuento y datos actualizados.\n");
                    break;

                case 3:
                    // DEPURACIÓN 3: Reasignación de zona y asiento
                    int zonaAnterior = getZonaIndex(entradaSeleccionada.zona);
                    int asientoAnterior = entradaSeleccionada.asiento - 1;

                    // Mostrar zonas (sin modificar stock todavía)
                    System.out.println("Seleccione nueva zona:");
                    for (int z = 0; z < zonas.length; z++) {
                        int disponibles = 0;
                        for (boolean ocupado : asientosOcupados[z]) {
                            if (!ocupado) {
                                disponibles++;
                            }
                        }
                        System.out.printf(" %d️⃣ %s ($%,d) [%d disponibles]%n",
                                z + 1, zonas[z], precios[z], disponibles);
                    }

                    int nuevaZona;
                    do {
                        System.out.print("Ingrese número de nueva zona (1-5): ");
                        while (!input.hasNextInt()) {
                            System.out.print("❌ Ingrese un número válido (1-5): ");
                            input.next();
                        }
                        nuevaZona = input.nextInt() - 1;
                        input.nextLine();
                    } while (nuevaZona < 0 || nuevaZona >= zonas.length);

                    mostrarMapaAsientos(nuevaZona);

                    int nuevoAsiento;
                    do {
                        System.out.print("Ingrese número de nuevo asiento: ");
                        while (!input.hasNextInt()) {
                            System.out.print("❌ Ingrese un número válido: ");
                            input.next();
                        }
                        nuevoAsiento = input.nextInt() - 1;
                        input.nextLine();
                    } while (nuevoAsiento < 0 || nuevoAsiento >= 5 || asientosOcupados[nuevaZona][nuevoAsiento]);

                    // Solo ahora que el nuevo está confirmado:
                    // Libera el anterior
                    // Libera el asiento anterior sin modificar stock
                    asientosOcupados[zonaAnterior][asientoAnterior] = false;

// Libera asiento anterior
                    asientosOcupados[zonaAnterior][asientoAnterior] = false;

// Solo modifica stock si la zona cambia
                    if (zonaAnterior != nuevaZona) {
                        stock[zonaAnterior]++;
                        stock[nuevaZona]--;
                    }

// Marca nuevo asiento como ocupado
                    asientosOcupados[nuevaZona][nuevoAsiento] = true;

                    // Marca el nuevo
                    asientosOcupados[nuevaZona][nuevoAsiento] = true;
                    stock[nuevaZona]--;

                    // Actualiza datos de la entrada
                    entradaSeleccionada.zona = zonas[nuevaZona];
                    entradaSeleccionada.asiento = nuevoAsiento + 1;
                    entradaSeleccionada.id = zonas[nuevaZona].substring(0, 3).toUpperCase() + "-" + (nuevoAsiento + 1);
                    entradaSeleccionada.precioBase = precios[nuevaZona];
                    entradaSeleccionada.precioFinal = entradaSeleccionada.precioBase
                            - (entradaSeleccionada.precioBase * entradaSeleccionada.descuentoAplicado / 100);

                    System.out.println("✅ Zona y asiento actualizados exitosamente.\n");
                    break;

                case 4:
                    System.out.println("⚠️ Entrada eliminada.");
                    entradasVendidas.remove(entradaSeleccionada);
                    asientosOcupados[getZonaIndex(entradaSeleccionada.zona)][entradaSeleccionada.asiento - 1] = false;
                    stock[getZonaIndex(entradaSeleccionada.zona)]++;
                    return;

                case 5:
                    System.out.println("➕ Agregar nueva entrada al grupo actual.\n");
                    agregarUnaEntrada();
                    break;

                case 6:
                    System.out.println("🔙 Regresando al menú anterior...\n");
                    return;  // ← Esto hace que salga del método y vuelva al post-compra

                default:
                    System.out.println("⚠️ Opción inválida.");
            }

        } while (opcion != 5);
    }

    public static int getZonaIndex(String zona) {
        for (int i = 0; i < zonas.length; i++) {
            if (zonas[i].equalsIgnoreCase(zona)) {
                return i;
            }
        }
        return -1;
    }

    public static void agregarUnaEntrada() {
        System.out.println("════════════════════════════════════════════════");
        System.out.println("      ➕ NUEVA ENTRADA ADICIONAL AL GRUPO       ");
        System.out.println("════════════════════════════════════════════════");

        // 1. Datos personales
        String nombre;
        do {
            System.out.print("Ingrese Nombre y Apellido: ");
            nombre = input.nextLine().trim();
        } while (nombre.isEmpty());

        String genero;
        do {
            System.out.print("Ingrese Género (M = Mujer / H = Hombre / O = Otro): ");
            genero = input.nextLine().trim().toUpperCase();
        } while (!(genero.equals("M") || genero.equals("H") || genero.equals("O")));

        int edad;
        do {
            System.out.print("Ingrese Edad: ");
            while (!input.hasNextInt()) {
                System.out.print("❌ Edad inválida. Ingrese un número válido entre 0 y 120: ");
                input.next();
            }
            edad = input.nextInt();
            input.nextLine(); // limpiar buffer

            if (edad < 0 || edad > 120) {
                System.out.println("⚠️ Edad fuera de rango permitido. Intente nuevamente.\n");
            }

        } while (edad < 0 || edad > 120);

        
        //Zona y asiento
        int zonaElegida;
        do {
            System.out.println("════════════════════════════════════════════════════════════════════");
            System.out.println("                       🪑 ZONAS DISPONIBLES                         ");
            System.out.println("════════════════════════════════════════════════════════════════════");
            for (int z = 0; z < zonas.length; z++) {
                String estado = (stock[z] == 0) ? "AGOTADO" : stock[z] + " disponibles";
                System.out.printf(" %d️⃣  %-13s │ Precio: $%,6d │ %s%n",
                        z + 1, zonas[z], precios[z], estado);
            }

            System.out.print("🎟️  Elija una zona para su entrada (1-5): ");
            while (!input.hasNextInt()) {
                System.out.print("❌ Ingrese un número válido (1-5): ");
                input.next();
            }
            zonaElegida = input.nextInt() - 1;
            input.nextLine();

            if (zonaElegida < 0 || zonaElegida >= zonas.length) {
                System.out.println("⚠️ Opción inválida. Seleccione una zona válida.\n");
            } else if (stock[zonaElegida] == 0) {
                System.out.println("❌ Zona sin stock disponible. Elija otra zona.\n");
            }

        } while (zonaElegida < 0 || zonaElegida >= zonas.length || stock[zonaElegida] == 0);

// Mapa y selección de asiento
        mostrarMapaAsientos(zonaElegida);
        int asientoElegido;
        do {
            System.out.print("Seleccione número de asiento (1-5): ");
            while (!input.hasNextInt()) {
                System.out.print("❌ Ingrese un número válido (1-5): ");
                input.next();
            }
            asientoElegido = input.nextInt() - 1;
            input.nextLine();

            if (asientoElegido < 0 || asientoElegido >= 5) {
                System.out.println("⚠️ Número de asiento fuera de rango. Intente nuevamente.");
            } else if (asientosOcupados[zonaElegida][asientoElegido]) {
                System.out.println("❌ Ese asiento ya está ocupado. Elija otro.");
            }

        } while (asientoElegido < 0 || asientoElegido >= 5 || asientosOcupados[zonaElegida][asientoElegido]);

// Marcar asiento como ocupado y restar stock
        asientosOcupados[zonaElegida][asientoElegido] = true;
        stock[zonaElegida]--;

// Calcular descuento
        int descuento = calcularDescuento(genero, edad);

// Crear ID y entrada
        String id = zonas[zonaElegida].substring(0, 3).toUpperCase() + "-" + (asientoElegido + 1);
        Entrada nueva = new Entrada(id, nombre, genero, edad, zonas[zonaElegida],
                asientoElegido + 1, precios[zonaElegida], descuento);

// Guardar y mostrar resumen
        entradasVendidas.add(nueva);
        System.out.println("✅ Entrada creada exitosamente:");
        nueva.mostrarResumen();

    }

    public static void procesarPago() {
        if (entradasVendidas.isEmpty()) {
            System.out.println("⚠️ No hay entradas para procesar.\n");
            return;
        }

        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("              🎟️ PROCESANDO BOLETAS DE ENTRADA              ");
        System.out.println("════════════════════════════════════════════════════════════\n");

        int totalBruto = 0;
        int totalFinal = 0;

        for (Entrada e : entradasVendidas) {
            totalBruto += e.precioBase;
            totalFinal += e.precioFinal;

            System.out.println("══════════════════════════════════════");
            System.out.println("         🎟️ BOLETA DE ENTRADA         ");
            System.out.println("══════════════════════════════════════");
            System.out.println("🎭 Teatro Moro — “Donde cada butaca guarda una historia...”");
            System.out.printf("🪑 Zona: %-12s 🎫 ID: %s%n", e.zona, e.id);
            System.out.println("👤 Nombre: " + e.nombreCompleto);
            System.out.printf("💰 Precio: $%,6d   🔖 Descuento: %d%%%n", e.precioBase, e.descuentoAplicado);
            System.out.printf("✅ Total pagado: $%,6d%n", e.precioFinal);
            System.out.println("────────────────────────────────────────");
            System.out.println("🕒 Recuerde llegar 15 minutos antes de la función.\n");

            // REGISTRAR LA VENTA DEFINITIVA EN LAS ESTADÍSTICAS
            int zonaIndex = getZonaIndex(e.zona);
            ingresosPorZona[zonaIndex] += e.precioFinal;
            descuentosPorZona[zonaIndex] += (e.precioBase - e.precioFinal);
            entradasVendidasPorZona[zonaIndex]++;

        }

        int ahorro = totalBruto - totalFinal;

        System.out.println("💵 RESUMEN DE COMPRA:");
        System.out.println("─────────────────────────────────────────────");
        System.out.printf("💳 Total sin descuentos:     $%,6d%n", totalBruto);
        System.out.printf("🎟️ Total pagado:             $%,6d%n", totalFinal);
        System.out.printf("🎁 Ahorro total:              $%,6d%n", ahorro);
        System.out.println("─────────────────────────────────────────────");
        // 🔄 Limpiar entradas vendidas tras el pago
        entradasVendidas.clear();

        // Pregunta final
        System.out.println("\n¿Desea salir del sistema o volver al menú principal?");
        System.out.println(" 1️⃣  Volver al menú principal");
        System.out.println(" 2️⃣  Salir del sistema");
        System.out.print("Seleccione una opción → ");

        int decision;
        do {
            while (!input.hasNextInt()) {
                System.out.print("❌ Ingrese una opción válida: ");
                input.next();
            }
            decision = input.nextInt();
            input.nextLine();
        } while (decision != 1 && decision != 2);

        if (decision == 1) {
            System.out.println("🔁 Redirigiendo al menú principal...\n");
            mostrarBienvenida(); // 🎭 Bienvenida teatral
            menuPrincipal();     // 🎟️ Opciones
        } else {
            System.out.println("🎭 Gracias por confiar en el Teatro Moro. ¡Que la obra viva en su corazón!\n");
            System.exit(0);
        }

    }

    public static void verIngresos() {
        final String CLAVE = "efts9";
        int intentos = 3;
        boolean accesoConcedido = false;

        System.out.println("══════════════════════════════════════════════════════════");
        System.out.println("  🔐 ACCESO RESTRINGIDO - PERSONAL AUTORIZADO SOLAMENTE     ");
        System.out.println("══════════════════════════════════════════════════════════");
        System.out.println("💼 Ingrese la clave de acceso (3 intentos) o 0 para volver al menú:");

        while (intentos > 0) {
            System.out.print("🔑 Clave → ");
            String ingreso = input.nextLine().trim();

            if (ingreso.equalsIgnoreCase("0")) {
                System.out.println("🔙 Has cancelado el acceso. Volviendo al menú principal...\n");
                mostrarBienvenida();
                menuPrincipal();
                return;
            }

            if (ingreso.isEmpty()) {
                System.out.println("⚠️ Ingrese una clave válida.");
                continue; // no descuenta intento
            }

            if (ingreso.equals(CLAVE)) {
                accesoConcedido = true;
                break;
            } else {
                intentos--;
                if (intentos > 0) {
                    System.out.println("❌ Clave incorrecta. Intentos restantes: " + intentos);
                }
            }

        }

        if (!accesoConcedido) {
            System.out.println("\n🚫 Acceso denegado. Se han agotado los intentos.");
            System.out.println("🎭 Por seguridad, el sistema se cerrará ahora.");
            System.exit(0);
        }

        // Acceso concedido
        System.out.println("\n✅ Acceso autorizado. Bienvenido al resumen de ingresos.\n");

        int totalEntradas = 0;
        int totalBruto = 0;
        int totalDescuentos = 0;

        for (int i = 0; i < zonas.length; i++) {
            totalEntradas += entradasVendidasPorZona[i];
            totalBruto += ingresosPorZona[i] + descuentosPorZona[i];
            totalDescuentos += descuentosPorZona[i];
        }
        int totalFinal = totalBruto - totalDescuentos;

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("              💵 RESUMEN DETALLADO DE INGRESOS            ");
        System.out.println("═══════════════════════════════════════════════════════");

        for (int i = 0; i < zonas.length; i++) {
            System.out.printf("🎟️ Zona: %-15s | Vendidas: %-2d | Recaudado: $%6d | Descuentos: $%4d\n",
                    zonas[i],
                    entradasVendidasPorZona[i],
                    ingresosPorZona[i],
                    descuentosPorZona[i]);
        }

        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.printf("📊 Entradas totales vendidas:        %d%n", totalEntradas);
        System.out.printf("💳 Total sin descuentos:             $%,6d%n", totalBruto);
        System.out.printf("✅ Total efectivamente recaudado:    $%,6d%n", totalFinal);
        System.out.printf("🎁 Descuentos otorgados totales:    $%,6d%n", totalDescuentos);
        System.out.println("─────────────────────────────────────────────────────────────");

        // Pregunta final
        System.out.println("\n¿Desea volver al menú principal o salir del sistema?");
        System.out.println(" 1️⃣  Volver al menú principal");
        System.out.println(" 2️⃣  Salir del sistema");
        System.out.print("Seleccione una opción → ");

        int decision;
        do {
            while (!input.hasNextInt()) {
                System.out.print("❌ Ingrese una opción válida: ");
                input.next();
            }
            decision = input.nextInt();
            input.nextLine();
        } while (decision != 1 && decision != 2);

        if (decision == 1) {
            System.out.println("🔁 Redirigiendo al menú principal...\n");
            mostrarBienvenida();
            menuPrincipal();
        } else {
            System.out.println("🎭 Fin de sesión administrativa. ¡Gracias por su gestión!\n");
            System.exit(0);
        }
    }

}

// Clase que representa una entrada individual para el Teatro Moro
class Entrada {

    String id;                // ID único para identificar la entrada
    String nombreCompleto;   // Nombre y apellido del cliente
    String genero;           // Género declarado
    int edad;                // Edad ingresada
    String zona;             // Zona elegida (VIP, Palco, etc.)
    int asiento;             // Número de asiento seleccionado
    int precioBase;          // Precio original según la zona
    int descuentoAplicado;   // Porcentaje de descuento aplicado
    int precioFinal;         // Precio después del descuento

    // Constructor para inicializar todos los datos de una entrada
    public Entrada(String id, String nombreCompleto, String genero, int edad,
            String zona, int asiento, int precioBase, int descuentoAplicado) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.genero = genero;
        this.edad = edad;
        this.zona = zona;
        this.asiento = asiento;
        this.precioBase = precioBase;
        this.descuentoAplicado = descuentoAplicado;
        this.precioFinal = calcularPrecioFinal();
    }

    // Método que calcula el precio con el descuento
    private int calcularPrecioFinal() {
        return precioBase - (precioBase * descuentoAplicado / 100);
    }

    // Muestra resumen estético de la entrada
    public void mostrarResumen() {
        System.out.println("══════════════════════════════════════");
        System.out.println("       📋 RESUMEN DE LA ENTRADA       ");
        System.out.println("══════════════════════════════════════");
        System.out.println("🎫 ID Entrada:        " + id);
        System.out.println("👤 Cliente:           " + nombreCompleto);
        System.out.println("🔢 Edad:              " + edad + " años");
        System.out.println("🚻 Género:            " + genero);
        System.out.println("🪑 Zona / Asiento:    " + zona + " / [" + asiento + "]");
        System.out.println("💰 Precio Base:       $" + precioBase);
        System.out.println("🔖 Descuento:         " + descuentoAplicado + "%");
        System.out.println("✅ Precio Final:      $" + precioFinal);
        System.out.println("════════════════════════════════════════════════════════\n");

    }

    public static int getZonaIndex(String zona) {
        for (int i = 0; i < eftbastianvaldivias9.EFTBastianValdiviaS9.zonas.length; i++) {
            if (eftbastianvaldivias9.EFTBastianValdiviaS9.zonas[i].equalsIgnoreCase(zona)) {
                return i;
            }
        }
        return -1;
    }

}
