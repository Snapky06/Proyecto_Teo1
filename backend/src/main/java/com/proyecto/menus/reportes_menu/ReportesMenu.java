package com.proyecto.menus.reportes_menu;

import com.proyecto.funciones.FuncionesObligacionDAO;
import com.proyecto.funciones.FuncionesPresupuestoDAO;
import com.proyecto.funciones.FuncionesTransaccionDAO;
import com.proyecto.menus.MenuHelper;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

public class ReportesMenu {

    private final Scanner scanner;
    private final FuncionesPresupuestoDAO presupuestoDAO;
    private final FuncionesTransaccionDAO transaccionDAO;
    private final FuncionesObligacionDAO obligacionDAO;

    public ReportesMenu(Scanner scanner) {
        this.scanner = scanner;
        presupuestoDAO = new FuncionesPresupuestoDAO();
        transaccionDAO = new FuncionesTransaccionDAO();
        obligacionDAO = new FuncionesObligacionDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    resumenMensual();
                    break;

                case 2:
                    distribucionGastosCategoria();
                    break;

                case 3:
                    cumplimientoPresupuesto();
                    break;

                case 4:
                    estadoObligaciones();
                    break;

                case 5:
                    proyeccionGasto();
                    break;

                case 6:
                    promedioGasto();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println(
                            "Opcion no valida."
                    );
            }
        }
    }

    private void mostrarMenu() {
        System.out.println(
                "\n--- MENU DE REPORTES ---"
        );
        System.out.println(
                "1. Resumen mensual de ingresos y gastos"
        );
        System.out.println(
                "2. Distribucion de gastos por categoria"
        );
        System.out.println(
                "3. Cumplimiento del presupuesto"
        );
        System.out.println(
                "4. Estado de obligaciones fijas"
        );
        System.out.println(
                "5. Proyeccion de gasto mensual"
        );
        System.out.println(
                "6. Promedio de gasto por subcategoria"
        );
        System.out.println("0. Volver");
    }

    private void resumenMensual() {
        int idSubcategoria =
                ReportesHelper.leerId(
                        scanner,
                        "ID de la subcategoria: "
                );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        BigDecimal resultado =
                transaccionDAO.calcularMontoEjecutado(
                        idSubcategoria,
                        anio,
                        mes
                );

        ReportesHelper.imprimirResultadoMonetario(
                "Monto ejecutado del mes",
                resultado
        );
    }

    private void distribucionGastosCategoria() {
        int idCategoria =
                ReportesHelper.leerId(
                        scanner,
                        "ID de la categoria: "
                );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        BigDecimal resultado =
                transaccionDAO
                        .obtenerTotalEjecutadoCategoriaMes(
                                idCategoria,
                                anio,
                                mes
                        );

        ReportesHelper.imprimirResultadoMonetario(
                "Total ejecutado de la categoria",
                resultado
        );
    }

    private void cumplimientoPresupuesto() {
        int idCategoria =
                ReportesHelper.leerId(
                        scanner,
                        "ID de la categoria: "
                );

        int idPresupuesto =
                ReportesHelper.leerId(
                        scanner,
                        "ID del presupuesto: "
                );

        int idSubcategoria =
                ReportesHelper.leerId(
                        scanner,
                        "ID de la subcategoria: "
                );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        BigDecimal presupuestado =
                presupuestoDAO.obtenerTotalCategoriaMes(
                        idCategoria,
                        idPresupuesto,
                        anio,
                        mes
                );

        BigDecimal balance =
                presupuestoDAO.obtenerBalanceSubcategoria(
                        idPresupuesto,
                        idSubcategoria,
                        anio,
                        mes
                );

        BigDecimal porcentaje =
                presupuestoDAO.calcularPorcentajeEjecutado(
                        idSubcategoria,
                        idPresupuesto,
                        anio,
                        mes
                );

        ReportesHelper.imprimirResultadoMonetario(
                "Total presupuestado de la categoria",
                presupuestado
        );

        ReportesHelper.imprimirResultadoMonetario(
                "Balance de la subcategoria",
                balance
        );

        ReportesHelper.imprimirResultadoMonetario(
                "Porcentaje de ejecucion",
                porcentaje
        );
    }

    private void estadoObligaciones() {
        int idObligacion =
                ReportesHelper.leerId(
                        scanner,
                        "ID de la obligacion: "
                );

        Integer resultado =
                obligacionDAO.diasHastaVencimiento(
                        idObligacion
                );

        ReportesHelper.imprimirResultado(
                "Dias hasta vencimiento",
                resultado
        );
    }

    private void proyeccionGasto() {
        int idSubcategoria =
                ReportesHelper.leerId(
                        scanner,
                        "ID de la subcategoria: "
                );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        BigDecimal resultado =
                transaccionDAO.calcularProyeccionGastoMensual(
                        idSubcategoria,
                        anio,
                        mes
                );

        ReportesHelper.imprimirResultadoMonetario(
                "Proyeccion de gasto mensual",
                resultado
        );
    }

    private void promedioGasto() {
        int idUsuario =
                ReportesHelper.leerId(
                        scanner,
                        "ID del usuario: "
                );

        int idSubcategoria =
                ReportesHelper.leerId(
                        scanner,
                        "ID de la subcategoria: "
                );

        int cantidadMeses =
                ReportesHelper.leerCantidadMeses(
                        scanner
                );

        BigDecimal resultado =
                transaccionDAO
                        .obtenerPromedioGastoSubcategoria(
                                idUsuario,
                                idSubcategoria,
                                cantidadMeses
                        );

        ReportesHelper.imprimirResultadoMonetario(
                "Promedio de gasto",
                resultado
        );
    }
}