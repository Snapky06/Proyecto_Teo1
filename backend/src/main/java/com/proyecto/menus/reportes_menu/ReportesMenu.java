package com.proyecto.menus.reportes_menu;

import com.proyecto.funciones.FuncionesObligacionDAO;
import com.proyecto.funciones.FuncionesTransaccionDAO;
import com.proyecto.funciones.ProcedimientosNegocioDAO;
import com.proyecto.menus.MenuHelper;
import java.math.BigDecimal;
import java.util.Scanner;

public class ReportesMenu {

    private final Scanner scanner;
    private final FuncionesTransaccionDAO transaccionDAO;
    private final FuncionesObligacionDAO obligacionDAO;
    private final ProcedimientosNegocioDAO negocioDAO;

    public ReportesMenu(Scanner scanner) {
        this.scanner = scanner;
        transaccionDAO = new FuncionesTransaccionDAO();
        obligacionDAO = new FuncionesObligacionDAO();
        negocioDAO = new ProcedimientosNegocioDAO();
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
                    balanceMensual();
                    break;

                case 2:
                    montoEjecutadoMes();
                    break;

                case 3:
                    porcentajeEjecucionMes();
                    break;

                case 4:
                    resumenCategoriaMes();
                    break;

                case 5:
                    obligacionesMes();
                    break;

                case 6:
                    diasHastaVencimiento();
                    break;

                case 7:
                    proyeccionGasto();
                    break;

                case 8:
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
        System.out.println("1. Balance mensual");
        System.out.println(
                "2. Monto ejecutado de una subcategoria"
        );
        System.out.println(
                "3. Porcentaje de ejecucion"
        );
        System.out.println(
                "4. Resumen de categoria"
        );
        System.out.println(
                "5. Estado de obligaciones del mes"
        );
        System.out.println(
                "6. Dias hasta vencimiento"
        );
        System.out.println(
                "7. Proyeccion de gasto mensual"
        );
        System.out.println(
                "8. Promedio de gasto por subcategoria"
        );
        System.out.println("0. Volver");
    }

    private void balanceMensual() {
        int idUsuario = ReportesHelper.leerId(
                scanner,
                "ID del usuario: "
        );

        int idPresupuesto = ReportesHelper.leerId(
                scanner,
                "ID del presupuesto: "
        );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        negocioDAO.calcularBalanceMensual(
                idUsuario,
                idPresupuesto,
                anio,
                mes
        );
    }

    private void montoEjecutadoMes() {
        int idSubcategoria = ReportesHelper.leerId(
                scanner,
                "ID de la subcategoria: "
        );

        int idPresupuesto = ReportesHelper.leerId(
                scanner,
                "ID del presupuesto: "
        );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        negocioDAO.calcularMontoEjecutadoMes(
                idSubcategoria,
                idPresupuesto,
                anio,
                mes
        );
    }

    private void porcentajeEjecucionMes() {
        int idSubcategoria = ReportesHelper.leerId(
                scanner,
                "ID de la subcategoria: "
        );

        int idPresupuesto = ReportesHelper.leerId(
                scanner,
                "ID del presupuesto: "
        );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        negocioDAO.calcularPorcentajeEjecucionMes(
                idSubcategoria,
                idPresupuesto,
                anio,
                mes
        );
    }

    private void resumenCategoriaMes() {
        int idCategoria = ReportesHelper.leerId(
                scanner,
                "ID de la categoria: "
        );

        int idPresupuesto = ReportesHelper.leerId(
                scanner,
                "ID del presupuesto: "
        );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        negocioDAO.obtenerResumenCategoriaMes(
                idCategoria,
                idPresupuesto,
                anio,
                mes
        );
    }

    private void obligacionesMes() {
        int idUsuario = ReportesHelper.leerId(
                scanner,
                "ID del usuario: "
        );

        int idPresupuesto = ReportesHelper.leerId(
                scanner,
                "ID del presupuesto: "
        );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes: "
        );

        negocioDAO.procesarObligacionesMes(
                idUsuario,
                anio,
                mes,
                idPresupuesto
        );
    }

    private void diasHastaVencimiento() {
        int idObligacion = ReportesHelper.leerId(
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
        int idSubcategoria = ReportesHelper.leerId(
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
        int idUsuario = ReportesHelper.leerId(
                scanner,
                "ID del usuario: "
        );

        int idSubcategoria = ReportesHelper.leerId(
                scanner,
                "ID de la subcategoria: "
        );

        int cantidadMeses =
                ReportesHelper.leerCantidadMeses(
                        scanner
                );

        BigDecimal resultado =
                transaccionDAO.obtenerPromedioGastoSubcategoria(
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