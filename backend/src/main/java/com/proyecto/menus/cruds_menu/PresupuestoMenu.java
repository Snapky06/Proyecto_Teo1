package com.proyecto.menus.cruds_menu;

import java.math.BigDecimal;
import java.util.Scanner;

import com.proyecto.cruds.PresupuestoDAO;
import com.proyecto.cruds.PresupuestoDetalleDAO;
import com.proyecto.menus.MenuHelper;

public class PresupuestoMenu {

    private final Scanner scanner;
    private final PresupuestoDAO presupuestoDAO;
    private final PresupuestoDetalleDAO detalleDAO;

    public PresupuestoMenu(Scanner scanner) {
        this.scanner = scanner;
        presupuestoDAO = new PresupuestoDAO();
        detalleDAO = new PresupuestoDetalleDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE PRESUPUESTOS ---");
            System.out.println("1. Registrar Presupuesto");
            System.out.println("2. Listar Presupuestos");
            System.out.println("3. Consultar Presupuesto");
            System.out.println("4. Actualizar Presupuesto");
            System.out.println("5. Eliminar Presupuesto");
            System.out.println("6. Gestionar Detalles");
            System.out.println("0. Volver");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    registrarPresupuesto();
                    break;

                case 2:
                    int idUsuario = MenuHelper.leerEntero(
                            scanner,
                            "ID del usuario: "
                    );

                    presupuestoDAO.listarPresupuestos(idUsuario);
                    break;

                case 3:
                    int idPresupuesto = MenuHelper.leerEntero(
                            scanner,
                            "ID del presupuesto: "
                    );

                    presupuestoDAO.consultarPresupuesto(
                            idPresupuesto
                    );
                    break;

                case 4:
                    actualizarPresupuesto();
                    break;

                case 5:
                    eliminarPresupuesto();
                    break;

                case 6:
                    menuDetalles();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarPresupuesto() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario: "
        );

        System.out.print("Nombre del presupuesto: ");
        String nombre = scanner.nextLine();

        short anioInicio = MenuHelper.leerShort(
                scanner,
                "Anio de inicio: "
        );

        short mesInicio = MenuHelper.leerMes(
                scanner,
                "Mes de inicio: "
        );

        short anioFin = MenuHelper.leerShort(
                scanner,
                "Anio de fin: "
        );

        short mesFin = MenuHelper.leerMes(
                scanner,
                "Mes de fin: "
        );

        BigDecimal ingresos = MenuHelper.leerDecimal(
                scanner,
                "Total de ingresos planificados: "
        );

        BigDecimal gastos = MenuHelper.leerDecimal(
                scanner,
                "Total de gastos planificados: "
        );

        BigDecimal ahorro = MenuHelper.leerDecimal(
                scanner,
                "Total de ahorro planificado: "
        );

        String estado = MenuHelper.leerEstadoPresupuesto(
                scanner
        );

        presupuestoDAO.insertarPresupuesto(
                idUsuario,
                nombre,
                anioInicio,
                mesInicio,
                anioFin,
                mesFin,
                ingresos,
                gastos,
                ahorro,
                estado,
                "ADMIN"
        );
    }

    private void actualizarPresupuesto() {
        int idPresupuesto = MenuHelper.leerEntero(
                scanner,
                "ID del presupuesto: "
        );

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        short anioInicio = MenuHelper.leerShort(
                scanner,
                "Nuevo anio de inicio: "
        );

        short mesInicio = MenuHelper.leerMes(
                scanner,
                "Nuevo mes de inicio: "
        );

        short anioFin = MenuHelper.leerShort(
                scanner,
                "Nuevo anio de fin: "
        );

        short mesFin = MenuHelper.leerMes(
                scanner,
                "Nuevo mes de fin: "
        );

        BigDecimal ingresos = MenuHelper.leerDecimal(
                scanner,
                "Nuevos ingresos planificados: "
        );

        BigDecimal gastos = MenuHelper.leerDecimal(
                scanner,
                "Nuevos gastos planificados: "
        );

        BigDecimal ahorro = MenuHelper.leerDecimal(
                scanner,
                "Nuevo ahorro planificado: "
        );

        String estado = MenuHelper.leerEstadoPresupuesto(
                scanner
        );

        presupuestoDAO.actualizarPresupuesto(
                idPresupuesto,
                nombre,
                anioInicio,
                mesInicio,
                anioFin,
                mesFin,
                ingresos,
                gastos,
                ahorro,
                estado,
                "ADMIN"
        );
    }

    private void eliminarPresupuesto() {
        int idPresupuesto = MenuHelper.leerEntero(
                scanner,
                "ID del presupuesto: "
        );

        System.out.println(
                "Tambien se eliminaran sus detalles."
        );

        if (MenuHelper.confirmar(scanner)) {
            presupuestoDAO.eliminarPresupuesto(
                    idPresupuesto
            );
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private void menuDetalles() {
        boolean salir = false;

        while (!salir) {
            System.out.println(
                    "\n--- MENU DE DETALLES DE PRESUPUESTO ---"
            );
            System.out.println("1. Registrar Detalle");
            System.out.println("2. Listar Detalles");
            System.out.println("3. Consultar Detalle");
            System.out.println("4. Actualizar Detalle");
            System.out.println("5. Eliminar Detalle");
            System.out.println("0. Volver");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    registrarDetalle();
                    break;

                case 2:
                    int idPresupuesto = MenuHelper.leerEntero(
                            scanner,
                            "ID del presupuesto: "
                    );

                    detalleDAO.listarDetalles(idPresupuesto);
                    break;

                case 3:
                    int idDetalle = MenuHelper.leerEntero(
                            scanner,
                            "ID del detalle: "
                    );

                    detalleDAO.consultarDetalle(idDetalle);
                    break;

                case 4:
                    actualizarDetalle();
                    break;

                case 5:
                    int idDetalleEliminar = MenuHelper.leerEntero(
                            scanner,
                            "ID del detalle: "
                    );

                    if (MenuHelper.confirmar(scanner)) {
                        detalleDAO.eliminarDetalle(
                                idDetalleEliminar
                        );
                    } else {
                        System.out.println(
                                "Operacion cancelada."
                        );
                    }
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarDetalle() {
        int idPresupuesto = MenuHelper.leerEntero(
                scanner,
                "ID del presupuesto: "
        );

        int idSubcategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la subcategoria: "
        );

        BigDecimal monto = MenuHelper.leerDecimal(
                scanner,
                "Monto mensual: "
        );

        System.out.print("Observaciones: ");
        String observaciones = scanner.nextLine();

        detalleDAO.insertarDetalle(
                idPresupuesto,
                idSubcategoria,
                monto,
                observaciones,
                "ADMIN"
        );
    }

    private void actualizarDetalle() {
        int idDetalle = MenuHelper.leerEntero(
                scanner,
                "ID del detalle: "
        );

        BigDecimal monto = MenuHelper.leerDecimal(
                scanner,
                "Nuevo monto mensual: "
        );

        System.out.print("Nuevas observaciones: ");
        String observaciones = scanner.nextLine();

        detalleDAO.actualizarDetalle(
                idDetalle,
                monto,
                observaciones,
                "ADMIN"
        );
    }
}