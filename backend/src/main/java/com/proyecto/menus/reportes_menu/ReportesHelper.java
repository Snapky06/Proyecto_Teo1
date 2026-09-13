package com.proyecto.menus.reportes_menu;

import com.proyecto.menus.MenuHelper;
import java.math.BigDecimal;
import java.util.Scanner;

public final class ReportesHelper {

    private ReportesHelper() {
    }

    public static int leerId(
            Scanner scanner,
            String mensaje) {

        while (true) {
            int id = MenuHelper.leerEntero(
                    scanner,
                    mensaje
            );

            if (id > 0) {
                return id;
            }

            System.out.println(
                    "El ID debe ser mayor que cero."
            );
        }
    }

    public static int leerCantidadMeses(
            Scanner scanner) {

        while (true) {
            int cantidad = MenuHelper.leerEntero(
                    scanner,
                    "Cantidad de meses: "
            );

            if (cantidad > 0) {
                return cantidad;
            }

            System.out.println(
                    "La cantidad debe ser mayor que cero."
            );
        }
    }

    public static void imprimirResultado(
            String nombre,
            Object resultado) {

        if (resultado == null) {
            System.out.println(
                    "No se pudo obtener el resultado."
            );
            return;
        }

        System.out.println(
                nombre + ": " + resultado
        );
    }

    public static void imprimirResultadoMonetario(
            String nombre,
            BigDecimal resultado) {

        imprimirResultado(nombre, resultado);
    }
}