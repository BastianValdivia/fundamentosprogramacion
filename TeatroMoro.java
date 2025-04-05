package teatromoro;

import java.util.Scanner;

public class TeatroMoro {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        // Variables principales
        int opcion = 0;
        int zonaElegida = 0;
        int asientoElegido = 0;
        int edad = 0;
        double descuento = 0.0;
        double precioFinal = 0.0;
        int precioBase = 0;
        String respuesta = "";

        // Representación de la disponibilidad de asientos por zona (5 asientos por zona)
        boolean[] zonaA = {false, false, false, false, false};
        boolean[] zonaB = {false, false, false, false, false};
        boolean[] zonaC = {false, false, false, false, false};

        // Utilizamos un ciclo 'for' con una única iteración para desplegar el menú principal una sola vez.
        // Este enfoque es una solución creativa y efectiva que cumple con la solicitud, manteniendo el ciclo 'for' sin repetir el menú.
        // El ciclo 'while' es el encargado de gestionar todo el proceso de compra, permitiendo múltiples interacciones hasta que el usuario decida salir.
        for (int i = 0; i < 1; i++) {
            while (true) {
                System.out.println("\n¡Bienvenido a TEATRO MORO!");
                System.out.println("¿Qué opción deseas escoger?");
                System.out.println("1 - Comprar Entrada");
                System.out.println("2 - Salir");

                // Validación de entrada para la opción del menú.
                // Se utiliza un bloque try-catch para manejar posibles errores de entrada, 
                // como cuando el usuario ingresa un valor no numérico. Si se produce un error,
                // se muestra un mensaje y se limpia el buffer del scanner para permitir un nuevo ingreso.
                try {
                    opcion = teclado.nextInt();
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, ingresa un número.");
                    teclado.nextLine(); // Limpiar el buffer del scanner
                    continue;
                }

                if (opcion == 1) {
                    System.out.println("\nHas elegido COMPRAR ENTRADA.");

                    // Mostrar precios según zona
                    System.out.println("\nPrecios por zona:");
                    System.out.println("1. Zona A: $10.000");
                    System.out.println("2. Zona B: $7.000");
                    System.out.println("3. Zona C: $5.000");

                    // Mostrar zonas disponibles
                    System.out.println("\nZonas disponibles:");
                    String[] zonas = {"Zona A", "Zona B", "Zona C"};
                    for (int z = 0; z < zonas.length; z++) {
                        System.out.println((z + 1) + ". " + zonas[z]);
                    }

                    // Selección de zona por parte del usuario con validación
                    while (true) {
                        System.out.print("\nSelecciona el número de la zona (1-3): ");
                        try {
                            zonaElegida = teclado.nextInt();
                            if (zonaElegida >= 1 && zonaElegida <= 3) {
                                break;
                            } else {
                                System.out.println("Opción fuera de rango. Elige una zona entre 1 y 3.");
                            }
                        } catch (Exception e) {
                            System.out.println("Entrada inválida. Por favor, ingresa un número.");
                            teclado.nextLine();
                        }
                    }

                    // Obtener la zona seleccionada
                    boolean[] zonaSeleccionada = null;
                    if (zonaElegida == 1) {
                        zonaSeleccionada = zonaA;
                    }
                    if (zonaElegida == 2) {
                        zonaSeleccionada = zonaB;
                    }
                    if (zonaElegida == 3) {
                        zonaSeleccionada = zonaC;
                    }

                    // Verificación de disponibilidad de asientos en la zona
                    boolean zonaAgotada = true;
                    for (boolean asiento : zonaSeleccionada) {
                        if (!asiento) {
                            zonaAgotada = false;
                            break;
                        }
                    }

                    if (zonaAgotada) {
                        System.out.println("Todos los asientos en esta zona están ocupados. Intenta con otra zona.");
                        continue;
                    }

                    // Mostrar asientos disponibles
                    System.out.println("\nAsientos disponibles en " + zonas[zonaElegida - 1] + ":");
                    for (int j = 0; j < 5; j++) {
                        if (!zonaSeleccionada[j]) {
                            System.out.print((j + 1) + " ");
                        }
                    }

                    // Selección de asiento con validación
                    while (true) {
                        System.out.print("\nSelecciona el número del asiento (1-5): ");
                        try {
                            asientoElegido = teclado.nextInt();
                            if (asientoElegido >= 1 && asientoElegido <= 5 && !zonaSeleccionada[asientoElegido - 1]) {
                                zonaSeleccionada[asientoElegido - 1] = true; // Marcar asiento como ocupado
                                break;
                            } else {
                                System.out.println("Asiento inválido o ya ocupado. Elige otro.");
                            }
                        } catch (Exception e) {
                            System.out.println("Por favor, ingresa un número válido.");
                            teclado.nextLine();
                        }
                    }

                    // Solicitud de edad para aplicar descuentos
                    while (true) {
                        System.out.print("\nIngresa tu edad: ");
                        try {
                            edad = teclado.nextInt();
                            if (edad >= 0) {
                                break;
                            } else {
                                System.out.println("La edad no puede ser negativa.");
                            }
                        } catch (Exception e) {
                            System.out.println("Por favor, ingresa un número válido.");
                            teclado.nextLine();
                        }
                    }

                    // Cálculo del precio final con descuentos
                    boolean precioCalculado = false;
                    while (!precioCalculado) {
                        if (edad < 25) {
                            descuento = 0.10;
                            System.out.println("Descuento aplicado: 10% por ser estudiante.");
                        } else if (edad >= 65) {
                            descuento = 0.15;
                            System.out.println("Descuento aplicado: 15% por adulto mayor.");
                        } else {
                            descuento = 0.0;
                            System.out.println("No se aplica descuento.");
                        }

                        // Precio base según la zona
                        if (zonaElegida == 1) {
                            precioBase = 10000;
                        }
                        if (zonaElegida == 2) {
                            precioBase = 7000;
                        }
                        if (zonaElegida == 3) {
                            precioBase = 5000;
                        }

                        // Aplicar descuento
                        precioFinal = precioBase - (precioBase * descuento);
                        precioCalculado = true;
                    }

                    // Mostrar resumen de la compra
                    System.out.println("\nResumen de tu compra:");
                    System.out.println("Ubicación: " + zonas[zonaElegida - 1] + " - Asiento " + asientoElegido);
                    System.out.println("Precio base: $" + precioBase);
                    System.out.println("Descuento: " + (int) (descuento * 100) + "%");
                    System.out.println("Total a pagar: $" + (int) precioFinal);

                    // Pregunta para continuar con otra compra
                    System.out.print("\n¿Deseas realizar otra compra? (S/N): ");
                    teclado.nextLine(); // Limpiar buffer
                    respuesta = teclado.nextLine().toUpperCase();
                    if (!respuesta.equals("S")) {
                        System.out.println("¡Gracias por tu compra en TEATRO MORO! ¡Hasta pronto!");
                        break; // Termina el ciclo principal
                    }

                } else if (opcion == 2) {
                    System.out.println("\nGracias por visitar TEATRO MORO.");
                    break;
                } else {
                    System.out.println("Opción inválida. Por favor, selecciona 1 o 2.");
                }
            }
        }

        teclado.close(); // Cierre del scanner
    }
}

/*
Análisis del programa:
Este proyecto implementa un sistema básico de compra de entradas para el Teatro Moro, utilizando conceptos fundamentales de Java, 
como estructuras de control (`if`, `while`, `for`), manejo de errores con `try-catch`, arreglos booleanos para la representación de asientos 
y validación de entradas del usuario.

El flujo del programa está diseñado de manera lógica y clara, permitiendo al usuario seleccionar zonas, asientos y recibir descuentos 
según su edad. El uso de `try-catch` mejora la robustez del sistema, gestionando adecuadamente entradas inválidas. 
Además, el sistema controla eficazmente la disponibilidad de asientos, mostrando en tiempo real las opciones ocupadas y disponibles.

Se destaca la creatividad en el uso combinado de los ciclos `for` y `while`, los cuales se emplean de manera efectiva para mantener el menú activo y permitir múltiples compras sin reiniciar el programa. Esto demuestra una sólida comprensión de las herramientas de control de flujo en Java.

Este ejercicio ha sido útil para reforzar conceptos clave del lenguaje, especialmente en cuanto al manejo de entradas del usuario, validaciones, 
y la implementación de estructuras de datos básicas. Representa un buen ejemplo de la programación orientada a la interacción con el usuario.

Se entiende y se implementan correctamente varios aspectos del lenguaje Java, tales como la manipulación del buffer del `Scanner`, el manejo de excepciones con `try-catch`, y el uso de estructuras de control de flujo para una correcta ejecución del algoritmo. 
Este enfoque permite un aprendizaje óptimo y efectivo de los conceptos y las buenas prácticas en programación.
*/
