package com.proyecto.menus;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuHelper {
    public static final String USUARIO_SISTEMA = "ADMIN";

    public static class OperacionCanceladaException extends RuntimeException {
        public OperacionCanceladaException() {
            super("Operacion cancelada por el usuario.");
        }
    }

    private static void verificarCancelacion(String entrada) {
        if (entrada != null && (entrada.equalsIgnoreCase("CANCELAR") || entrada.equalsIgnoreCase("X"))) {
            throw new OperacionCanceladaException();
        }
    }

    public static java.sql.Date leerFecha(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (CANCELAR para salir): ");
            String entrada = scanner.nextLine().trim();
            verificarCancelacion(entrada);
            try {
                return java.sql.Date.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Use el formato YYYY-MM-DD.");
            }
        }
    }

    public static java.sql.Date leerFechaOpcional(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (o vacio / CANCELAR para salir): ");
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return null;
            }
            verificarCancelacion(entrada);
            try {
                return java.sql.Date.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Use el formato YYYY-MM-DD o deje vacio.");
            }
        }
    }

    public static short leerDiaVencimiento(Scanner scanner) {
        while (true) {
            short dia = leerShort(scanner, "Dia de vencimiento: ");
            if (dia >= 1 && dia <= 31) {
                return dia;
            }
            System.out.println("El dia debe estar entre 1 y 31.");
        }
    }

    public static boolean leerBooleano(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " [SI/NO] (CANCELAR para salir): ");
            String entrada = scanner.nextLine().trim().toUpperCase();
            verificarCancelacion(entrada);
            if (entrada.equals("SI")) {
                return true;
            }
            if (entrada.equals("NO")) {
                return false;
            }
            System.out.println("Debe escribir SI o NO.");
        }
    }

    public static String leerTipoTransaccion(Scanner scanner) {
        while (true) {
            System.out.print("Tipo INGRESO, GASTO o AHORRO (CANCELAR para salir): ");
            String tipo = scanner.nextLine().trim().toUpperCase();
            verificarCancelacion(tipo);
            if (tipo.equals("INGRESO") || tipo.equals("GASTO") || tipo.equals("AHORRO")) {
                return tipo;
            }
            System.out.println("Debe escribir INGRESO, GASTO o AHORRO.");
        }
    }

    public static String leerMetodoPago(Scanner scanner) {
        while (true) {
            System.out.print("Metodo EFECTIVO, TARJETA_DEBITO, TARJETA_CREDITO o TRANSFERENCIA (CANCELAR para salir): ");
            String metodo = scanner.nextLine().trim().toUpperCase();
            verificarCancelacion(metodo);
            if (metodo.equals("EFECTIVO") || metodo.equals("TARJETA_DEBITO") || 
                metodo.equals("TARJETA_CREDITO") || metodo.equals("TRANSFERENCIA")) {
                return metodo;
            }
            System.out.println("Metodo de pago no valido.");
        }
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (CANCELAR para salir): ");
            String entrada = scanner.nextLine().trim();
            verificarCancelacion(entrada);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero entero.");
            }
        }
    }

    public static short leerShort(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (CANCELAR para salir): ");
            String entrada = scanner.nextLine().trim();
            verificarCancelacion(entrada);
            try {
                return Short.parseShort(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero valido.");
            }
        }
    }

    public static short leerMes(Scanner scanner, String mensaje) {
        while (true) {
            short mes = leerShort(scanner, mensaje);
            if (mes >= 1 && mes <= 12) {
                return mes;
            }
            System.out.println("El mes debe estar entre 1 y 12.");
        }
    }

    public static short leerPositivo(Scanner scanner, String mensaje) {
        while (true) {
            short valor = leerShort(scanner, mensaje);
            if (valor >= 0) {
                return valor;
            }
            System.out.println("El valor no puede ser negativo.");
        }
    }

    public static BigDecimal leerDecimal(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (CANCELAR para salir): ");
            String entrada = scanner.nextLine().trim();
            verificarCancelacion(entrada);
            try {
                return new BigDecimal(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero valido.");
            }
        }
    }

    public static String leerTipo(Scanner scanner) {
        while (true) {
            System.out.print("Tipo INGRESO o GASTO (CANCELAR para salir): ");
            String tipo = scanner.nextLine().trim().toUpperCase();
            verificarCancelacion(tipo);
            if (tipo.equals("INGRESO") || tipo.equals("GASTO")) {
                return tipo;
            }
            System.out.println("Debe escribir INGRESO o GASTO.");
        }
    }

    public static String leerColor(Scanner scanner) {
        while (true) {
            System.out.print("Color hexadecimal o vacio (CANCELAR para salir): ");
            String color = scanner.nextLine().trim();
            verificarCancelacion(color);
            if (color.isEmpty()) {
                return "";
            }
            if (color.matches("#[0-9A-Fa-f]{6}")) {
                return color;
            }
            System.out.println("Use un formato como #FF0000.");
        }
    }

    public static String leerEstadoPresupuesto(Scanner scanner) {
        while (true) {
            System.out.print("Estado ACTIVO o FINALIZADO (CANCELAR para salir): ");
            String estado = scanner.nextLine().trim().toUpperCase();
            verificarCancelacion(estado);
            if (estado.equals("ACTIVO") || estado.equals("FINALIZADO")) {
                return estado;
            }
            System.out.println("Estado no valido.");
        }
    }

    public static boolean confirmar(Scanner scanner) {
        System.out.print("Escriba SI para continuar (CANCELAR para salir): ");
        String respuesta = scanner.nextLine().trim();
        verificarCancelacion(respuesta);
        return respuesta.equalsIgnoreCase("SI");
    }

    public static String leerTexto(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (CANCELAR para salir): ");
            String texto = scanner.nextLine().trim();
            verificarCancelacion(texto);
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este campo no puede quedar vacio.");
        }
    }

    public static String leerTextoOpcional(Scanner scanner, String mensaje) {
        System.out.print(mensaje + " (o vacio / CANCELAR para salir): ");
        String texto = scanner.nextLine().trim();
        verificarCancelacion(texto);
        if (texto.isEmpty()) {
            return null;
        }
        return texto;
    }

    public static int leerOpcionMenu(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero valido.");
            }
        }
    }
}