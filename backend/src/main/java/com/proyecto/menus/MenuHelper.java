package com.proyecto.menus;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuHelper {
    
public static java.sql.Date leerFecha(
        Scanner scanner,
        String mensaje) {

    while (true) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine();

        try {
            return java.sql.Date.valueOf(entrada);
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Use el formato YYYY-MM-DD."
            );
        }
    }
}

public static java.sql.Date leerFechaOpcional(
        Scanner scanner,
        String mensaje) {

    while (true) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();

        if (entrada.isEmpty()) {
            return null;
        }

        try {
            return java.sql.Date.valueOf(entrada);
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Use el formato YYYY-MM-DD o deje vacio."
            );
        }
    }
}

public static short leerDiaVencimiento(
        Scanner scanner) {

    while (true) {
        short dia = leerShort(
                scanner,
                "Dia de vencimiento: "
        );

        if (dia >= 1 && dia <= 31) {
            return dia;
        }

        System.out.println(
                "El dia debe estar entre 1 y 31."
        );
    }
}

public static boolean leerBooleano(
        Scanner scanner,
        String mensaje) {

    while (true) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine()
                .trim()
                .toUpperCase();

        if (entrada.equals("SI")) {
            return true;
        }

        if (entrada.equals("NO")) {
            return false;
        }

        System.out.println(
                "Debe escribir SI o NO."
        );
    }
}

public static String leerTipoTransaccion(
        Scanner scanner) {

    while (true) {
        System.out.print(
                "Tipo INGRESO, GASTO o AHORRO: "
        );

        String tipo = scanner.nextLine()
                .trim()
                .toUpperCase();

        if (tipo.equals("INGRESO")
                || tipo.equals("GASTO")
                || tipo.equals("AHORRO")) {
            return tipo;
        }

        System.out.println(
                "Debe escribir INGRESO, GASTO o AHORRO."
        );
    }
}

public static String leerMetodoPago(
        Scanner scanner) {

    while (true) {
        System.out.print(
                "Metodo EFECTIVO, TARJETA_DEBITO, "
                        + "TARJETA_CREDITO o TRANSFERENCIA: "
        );

        String metodo = scanner.nextLine()
                .trim()
                .toUpperCase();

        if (metodo.equals("EFECTIVO")
                || metodo.equals("TARJETA_DEBITO")
                || metodo.equals("TARJETA_CREDITO")
                || metodo.equals("TRANSFERENCIA")) {
            return metodo;
        }

        System.out.println(
                "Metodo de pago no valido."
        );
    }
}

    public static int leerEntero(
            Scanner scanner,
            String mensaje) {

        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe escribir un numero entero."
                );
            }
        }
    }

    public static short leerShort(
            Scanner scanner,
            String mensaje) {

        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return Short.parseShort(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe escribir un numero valido."
                );
            }
        }
    }

    public static short leerMes(
            Scanner scanner,
            String mensaje) {

        while (true) {
            short mes = leerShort(scanner, mensaje);

            if (mes >= 1 && mes <= 12) {
                return mes;
            }

            System.out.println(
                    "El mes debe estar entre 1 y 12."
            );
        }
    }

    public static short leerPositivo(
            Scanner scanner,
            String mensaje) {

        while (true) {
            short valor = leerShort(scanner, mensaje);

            if (valor >= 0) {
                return valor;
            }

            System.out.println(
                    "El valor no puede ser negativo."
            );
        }
    }

    public static BigDecimal leerDecimal(
            Scanner scanner,
            String mensaje) {

        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return new BigDecimal(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe escribir un numero valido."
                );
            }
        }
    }

    public static String leerTipo(Scanner scanner) {
        while (true) {
            System.out.print("Tipo INGRESO o GASTO: ");

            String tipo = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            if (tipo.equals("INGRESO")
                    || tipo.equals("GASTO")) {
                return tipo;
            }

            System.out.println(
                    "Debe escribir INGRESO o GASTO."
            );
        }
    }

    public static String leerColor(Scanner scanner) {
        while (true) {
            System.out.print(
                    "Color hexadecimal o vacio: "
            );

            String color = scanner.nextLine().trim();

            if (color.isEmpty()) {
                return "";
            }

            if (color.matches("#[0-9A-Fa-f]{6}")) {
                return color;
            }

            System.out.println(
                    "Use un formato como #FF0000."
            );
        }
    }

    public static String leerEstadoPresupuesto(
            Scanner scanner) {

        while (true) {
            System.out.print(
                    "Estado ACTIVO o FINALIZADO: "
            );

            String estado = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            if (estado.equals("ACTIVO")
                    || estado.equals("FINALIZADO")) {
                return estado;
            }

            System.out.println(
                    "Estado no valido."
            );
        }
    }

    public static boolean confirmar(Scanner scanner) {
        System.out.print(
                "Escriba SI para continuar: "
        );

        String respuesta = scanner.nextLine();

        return respuesta.equalsIgnoreCase("SI");
    }
}