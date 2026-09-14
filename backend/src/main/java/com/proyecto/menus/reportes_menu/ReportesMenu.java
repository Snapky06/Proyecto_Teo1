package com.proyecto.menus.reportes_menu;

import com.proyecto.funciones.FuncionesObligacionDAO;
import com.proyecto.funciones.FuncionesPresupuestoDAO;
import com.proyecto.funciones.FuncionesTransaccionDAO;
import com.proyecto.funciones.ProcedimientosNegocioDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;
import java.math.BigDecimal;
import java.util.Scanner;

public class ReportesMenu extends MenuBase {

    private final FuncionesTransaccionDAO transaccionDAO;
    private final FuncionesObligacionDAO obligacionDAO;
    private final ProcedimientosNegocioDAO negocioDAO;
    private final FuncionesPresupuestoDAO presupuestoFuncionesDAO;

    public ReportesMenu(Scanner scanner) {
        super(scanner);
        this.transaccionDAO = new FuncionesTransaccionDAO();
        this.obligacionDAO = new FuncionesObligacionDAO();
        this.negocioDAO = new ProcedimientosNegocioDAO();
        this.presupuestoFuncionesDAO = new FuncionesPresupuestoDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE REPORTES ---");
        System.out.println("1. Balance mensual");
        System.out.println("2. Monto ejecutado de una subcategoria");
        System.out.println("3. Porcentaje de ejecucion");
        System.out.println("4. Resumen de categoria");
        System.out.println("5. Estado de obligaciones del mes");
        System.out.println("6. Dias hasta vencimiento");
        System.out.println("7. Proyeccion de gasto mensual");
        System.out.println("8. Promedio de gasto por subcategoria");
        System.out.println("9. Porcentaje ejecutado (Funcion directa)");
        System.out.println("10. Balance de subcategoria (Funcion directa)");
        System.out.println("11. Total presupuestado de categoria (Funcion directa)");
        System.out.println("12. Monto ejecutado (Funcion directa)");
        System.out.println("13. Total ejecutado de categoria (Funcion directa)");
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
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
            case 9:
                funcPorcentajeEjecutado();
                break;
            case 10:
                funcBalanceSubcategoria();
                break;
            case 11:
                funcTotalCategoria();
                break;
            case 12:
                funcMontoEjecutado();
                break;
            case 13:
                funcTotalEjecutadoCategoria();
                break;
            case 0:
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void balanceMensual() {
        int idUsuario = ReportesHelper.leerId(scanner, "ID del usuario: ");
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        negocioDAO.calcularBalanceMensual(idUsuario, idPresupuesto, anio, mes);
    }

    private void montoEjecutadoMes() {
        int idSubcategoria = ReportesHelper.leerId(scanner, "ID de la subcategoria: ");
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        negocioDAO.calcularMontoEjecutadoMes(idSubcategoria, idPresupuesto, anio, mes);
    }

    private void porcentajeEjecucionMes() {
        int idSubcategoria = ReportesHelper.leerId(scanner, "ID de la subcategoria: ");
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        negocioDAO.calcularPorcentajeEjecucionMes(idSubcategoria, idPresupuesto, anio, mes);
    }

    private void resumenCategoriaMes() {
        int idCategoria = ReportesHelper.leerId(scanner, "ID de la categoria: ");
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        negocioDAO.obtenerResumenCategoriaMes(idCategoria, idPresupuesto, anio, mes);
    }

    private void obligacionesMes() {
        int idUsuario = ReportesHelper.leerId(scanner, "ID del usuario: ");
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        negocioDAO.procesarObligacionesMes(idUsuario, anio, mes, idPresupuesto);
    }

    private void diasHastaVencimiento() {
        int idObligacion = ReportesHelper.leerId(scanner, "ID de la obligacion: ");
        Integer resultado = obligacionDAO.diasHastaVencimiento(idObligacion);
        ReportesHelper.imprimirResultado("Dias hasta vencimiento", resultado);
    }

    private void proyeccionGasto() {
        int idSubcategoria = ReportesHelper.leerId(scanner, "ID de la subcategoria: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        BigDecimal resultado = transaccionDAO.calcularProyeccionGastoMensual(idSubcategoria, anio, mes);
        ReportesHelper.imprimirResultadoMonetario("Proyeccion de gasto mensual", resultado);
    }

    private void promedioGasto() {
        int idUsuario = ReportesHelper.leerId(scanner, "ID del usuario: ");
        int idSubcategoria = ReportesHelper.leerId(scanner, "ID de la subcategoria: ");
        int cantidadMeses = ReportesHelper.leerCantidadMeses(scanner);
        BigDecimal resultado = transaccionDAO.obtenerPromedioGastoSubcategoria(idUsuario, idSubcategoria, cantidadMeses);
        ReportesHelper.imprimirResultadoMonetario("Promedio de gasto", resultado);
    }

    private void funcPorcentajeEjecutado() {
        int idSubcategoria = ReportesHelper.leerId(scanner, "ID de la subcategoria: ");
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        BigDecimal resultado = presupuestoFuncionesDAO.calcularPorcentajeEjecutado(idSubcategoria, idPresupuesto, anio, mes);
        ReportesHelper.imprimirResultadoMonetario("Porcentaje ejecutado (%)", resultado);
    }

    private void funcBalanceSubcategoria() {
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        int idSubcategoria = ReportesHelper.leerId(scanner, "ID de la subcategoria: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        BigDecimal resultado = presupuestoFuncionesDAO.obtenerBalanceSubcategoria(idPresupuesto, idSubcategoria, anio, mes);
        ReportesHelper.imprimirResultadoMonetario("Balance de la subcategoria", resultado);
    }

    private void funcTotalCategoria() {
        int idCategoria = ReportesHelper.leerId(scanner, "ID de la categoria: ");
        int idPresupuesto = ReportesHelper.leerId(scanner, "ID del presupuesto: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        BigDecimal resultado = presupuestoFuncionesDAO.obtenerTotalCategoriaMes(idCategoria, idPresupuesto, anio, mes);
        ReportesHelper.imprimirResultadoMonetario("Total presupuestado de la categoria", resultado);
    }

    private void funcMontoEjecutado() {
        int idSubcategoria = ReportesHelper.leerId(scanner, "ID de la subcategoria: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        BigDecimal resultado = transaccionDAO.calcularMontoEjecutado(idSubcategoria, anio, mes);
        ReportesHelper.imprimirResultadoMonetario("Monto ejecutado", resultado);
    }

    private void funcTotalEjecutadoCategoria() {
        int idCategoria = ReportesHelper.leerId(scanner, "ID de la categoria: ");
        short anio = MenuHelper.leerShort(scanner, "Anio: ");
        short mes = MenuHelper.leerMes(scanner, "Mes: ");
        BigDecimal resultado = transaccionDAO.obtenerTotalEjecutadoCategoriaMes(idCategoria, anio, mes);
        ReportesHelper.imprimirResultadoMonetario("Total ejecutado de la categoria", resultado);
    }
}