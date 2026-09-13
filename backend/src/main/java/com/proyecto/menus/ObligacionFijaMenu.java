package com.proyecto.menus;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

import com.proyecto.cruds.ObligacionFijaDAO;

public class ObligacionFijaMenu {

    private final Scanner scanner;
    private final ObligacionFijaDAO obligacionDAO;

    public ObligacionFijaMenu(Scanner scanner) {
        this.scanner = scanner;
        obligacionDAO = new ObligacionFijaDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE OBLIGACIONES FIJAS ---");
            System.out.println("1. Registrar obligacion fija");
            System.out.println("2. Listar obligaciones fijas");
            System.out.println("3. Consultar obligacion fija");
            System.out.println("4. Actualizar obligacion fija");
            System.out.println("5. Eliminar obligacion fija");
            System.out.println("0. Volver");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    registrarObligacion();
                    break;

                case 2:
                    listarObligaciones();
                    break;

                case 3:
                    consultarObligacion();
                    break;

                case 4:
                    actualizarObligacion();
                    break;

                case 5:
                    eliminarObligacion();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarObligacion() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario: "
        );

        int idSubcategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la subcategoria: "
        );

        String nombre = leerTexto(
                "Nombre de la obligacion: "
        );

        String descripcion = leerTexto(
                "Descripcion: "
        );

        BigDecimal monto = MenuHelper.leerDecimal(
                scanner,
                "Monto fijo mensual: "
        );

        short diaVencimiento = leerDiaVencimiento();

        boolean vigente = leerBooleano(
                "La obligacion esta vigente? SI/NO: "
        );

        Date fechaInicio = leerFecha(
                "Fecha de inicio YYYY-MM-DD: "
        );

        Date fechaFin = leerFechaOpcional(
                "Fecha de fin YYYY-MM-DD o vacio: "
        );

        obligacionDAO.insertarObligacion(
                idUsuario,
                idSubcategoria,
                nombre,
                descripcion,
                monto,
                diaVencimiento,
                vigente,
                fechaInicio,
                fechaFin,
                "ADMIN"
        );
    }

    private void listarObligaciones() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario: "
        );

        obligacionDAO.listarObligaciones(idUsuario);
    }

    private void consultarObligacion() {
        int idObligacion = MenuHelper.leerEntero(
                scanner,
                "ID de la obligacion: "
        );

        obligacionDAO.consultarObligacion(idObligacion);
    }

    private void actualizarObligacion() {
        int idObligacion = MenuHelper.leerEntero(
                scanner,
                "ID de la obligacion: "
        );

        int idSubcategoria = MenuHelper.leerEntero(
                scanner,
                "Nuevo ID de la subcategoria: "
        );

        String nombre = leerTexto(
                "Nuevo nombre: "
        );

        String descripcion = leerTexto(
                "Nueva descripcion: "
        );

        BigDecimal monto = MenuHelper.leerDecimal(
                scanner,
                "Nuevo monto fijo mensual: "
        );

        short diaVencimiento = leerDiaVencimiento();

        boolean vigente = leerBooleano(
                "La obligacion esta vigente? SI/NO: "
        );

        Date fechaInicio = leerFecha(
                "Nueva fecha de inicio YYYY-MM-DD: "
        );

        Date fechaFin = leerFechaOpcional(
                "Nueva fecha de fin YYYY-MM-DD o vacio: "
        );

        obligacionDAO.actualizarObligacion(
                idObligacion,
                idSubcategoria,
                nombre,
                descripcion,
                monto,
                diaVencimiento,
                vigente,
                fechaInicio,
                fechaFin,
                "ADMIN"
        );
    }

    private void eliminarObligacion() {
        int idObligacion = MenuHelper.leerEntero(
                scanner,
                "ID de la obligacion: "
        );

        boolean confirmar = MenuHelper.confirmar(scanner);

        if (confirmar) {
            obligacionDAO.eliminarObligacion(idObligacion);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Este campo no puede quedar vacio.");
        }
    }

    private short leerDiaVencimiento() {
        while (true) {
            short dia = MenuHelper.leerShort(
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

    private boolean leerBooleano(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            if (respuesta.equals("SI")) {
                return true;
            }

            if (respuesta.equals("NO")) {
                return false;
            }

            System.out.println(
                    "Debe escribir SI o NO."
            );
        }
    }

    private Date leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {
                return Date.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Use el formato YYYY-MM-DD."
                );
            }
        }
    }

    private Date leerFechaOpcional(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                return null;
            }

            try {
                return Date.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Use el formato YYYY-MM-DD o deje vacio."
                );
            }
        }
    }
}