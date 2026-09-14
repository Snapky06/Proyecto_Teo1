package com.proyecto.menus.cruds_menu;

import com.proyecto.cruds.PresupuestoDAO;
import com.proyecto.cruds.PresupuestoDetalleDAO;
import com.proyecto.funciones.ProcedimientosNegocioDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;
import java.math.BigDecimal;
import java.util.Scanner;

public class PresupuestoMenu extends MenuBase {

    private final PresupuestoDAO presupuestoDAO;
    private final PresupuestoDetalleDAO detalleDAO;
    private final ProcedimientosNegocioDAO negocioDAO;

    public PresupuestoMenu(Scanner scanner) {
        super(scanner);
        this.presupuestoDAO = new PresupuestoDAO();
        this.detalleDAO = new PresupuestoDetalleDAO();
        this.negocioDAO = new ProcedimientosNegocioDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE PRESUPUESTOS ---");
        System.out.println("1. Registrar presupuesto");
        System.out.println("2. Listar presupuestos");
        System.out.println("3. Consultar presupuesto");
        System.out.println("4. Actualizar presupuesto");
        System.out.println("5. Eliminar presupuesto");
        System.out.println("6. Gestionar detalles");
        System.out.println("7. Cerrar presupuesto");
        System.out.println("8. Crear presupuesto completo desde JSON");
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarPresupuesto();
                break;
            case 2:
                listarPresupuestos();
                break;
            case 3:
                consultarPresupuesto();
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
            case 7:
                cerrarPresupuesto();
                break;
            case 8:
                crearPresupuestoCompleto();
                break;
            case 0:
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void registrarPresupuesto() {
        int idUsuario = MenuHelper.leerEntero(scanner, "ID del usuario: ");
        String nombre = MenuHelper.leerTexto(scanner, "Nombre del presupuesto: ");
        short anioInicio = MenuHelper.leerShort(scanner, "Anio de inicio: ");
        short mesInicio = MenuHelper.leerMes(scanner, "Mes de inicio: ");
        short anioFin = MenuHelper.leerShort(scanner, "Anio de fin: ");
        short mesFin = MenuHelper.leerMes(scanner, "Mes de fin: ");

        if (!periodoValido(anioInicio, mesInicio, anioFin, mesFin)) {
            System.out.println("El periodo final no puede ser anterior al inicial.");
            return;
        }

        BigDecimal ingresos = MenuHelper.leerDecimal(scanner, "Total de ingresos planificados: ");
        BigDecimal gastos = MenuHelper.leerDecimal(scanner, "Total de gastos planificados: ");
        BigDecimal ahorro = MenuHelper.leerDecimal(scanner, "Total de ahorro planificado: ");

        presupuestoDAO.insertarPresupuesto(idUsuario, nombre, anioInicio, mesInicio, anioFin, mesFin, ingresos, gastos, ahorro, "ACTIVO", MenuHelper.USUARIO_SISTEMA);
    }

    private void listarPresupuestos() {
        int idUsuario = MenuHelper.leerEntero(scanner, "ID del usuario: ");
        presupuestoDAO.listarPresupuestos(idUsuario);
    }

    private void consultarPresupuesto() {
        int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto: ");
        presupuestoDAO.consultarPresupuesto(idPresupuesto);
    }

    private void actualizarPresupuesto() {
        int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto: ");
        String nombre = MenuHelper.leerTexto(scanner, "Nuevo nombre: ");
        short anioInicio = MenuHelper.leerShort(scanner, "Nuevo anio de inicio: ");
        short mesInicio = MenuHelper.leerMes(scanner, "Nuevo mes de inicio: ");
        short anioFin = MenuHelper.leerShort(scanner, "Nuevo anio de fin: ");
        short mesFin = MenuHelper.leerMes(scanner, "Nuevo mes de fin: ");

        if (!periodoValido(anioInicio, mesInicio, anioFin, mesFin)) {
            System.out.println("El periodo final no puede ser anterior al inicial.");
            return;
        }

        BigDecimal ingresos = MenuHelper.leerDecimal(scanner, "Nuevos ingresos planificados: ");
        BigDecimal gastos = MenuHelper.leerDecimal(scanner, "Nuevos gastos planificados: ");
        BigDecimal ahorro = MenuHelper.leerDecimal(scanner, "Nuevo ahorro planificado: ");
        String estado = MenuHelper.leerEstadoPresupuesto(scanner);

        presupuestoDAO.actualizarPresupuesto(idPresupuesto, nombre, anioInicio, mesInicio, anioFin, mesFin, ingresos, gastos, ahorro, estado, MenuHelper.USUARIO_SISTEMA);
    }

    private void eliminarPresupuesto() {
        int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto: ");
        if (MenuHelper.confirmar(scanner)) {
            presupuestoDAO.eliminarPresupuesto(idPresupuesto);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private void cerrarPresupuesto() {
        int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto: ");
        if (MenuHelper.confirmar(scanner)) {
            negocioDAO.cerrarPresupuesto(idPresupuesto, MenuHelper.USUARIO_SISTEMA);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private void crearPresupuestoCompleto() {
        int idUsuario = MenuHelper.leerEntero(scanner, "ID del usuario: ");
        String nombre = MenuHelper.leerTexto(scanner, "Nombre del presupuesto: ");
        short anioInicio = MenuHelper.leerShort(scanner, "Anio de inicio: ");
        short mesInicio = MenuHelper.leerMes(scanner, "Mes de inicio: ");
        short anioFin = MenuHelper.leerShort(scanner, "Anio de fin: ");
        short mesFin = MenuHelper.leerMes(scanner, "Mes de fin: ");

        if (!periodoValido(anioInicio, mesInicio, anioFin, mesFin)) {
            System.out.println("El periodo final no puede ser anterior al inicial.");
            return;
        }

        System.out.println("Escriba el JSON completo en una sola linea:");
        String json = scanner.nextLine().trim();
        if (json.isEmpty()) {
            System.out.println("El JSON no puede quedar vacio.");
            return;
        }

        presupuestoDAO.crearPresupuestoCompleto(idUsuario, nombre, anioInicio, mesInicio, anioFin, mesFin, json, MenuHelper.USUARIO_SISTEMA);
    }

    private void menuDetalles() {
        boolean salirDetalles = false;
        while (!salirDetalles) {
            System.out.println("\n--- MENU DE DETALLES DE PRESUPUESTO ---");
            System.out.println("1. Registrar detalle");
            System.out.println("2. Listar detalles");
            System.out.println("3. Consultar detalle");
            System.out.println("4. Actualizar detalle");
            System.out.println("5. Eliminar detalle");
            System.out.println("0. Volver");
            
            int opcion = MenuHelper.leerEntero(scanner, "Elige una opcion: ");
            switch (opcion) {
                case 1:
                    registrarDetalle();
                    break;
                case 2:
                    listarDetalles();
                    break;
                case 3:
                    consultarDetalle();
                    break;
                case 4:
                    actualizarDetalle();
                    break;
                case 5:
                    eliminarDetalle();
                    break;
                case 0:
                    salirDetalles = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarDetalle() {
        int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto: ");
        int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria: ");
        BigDecimal monto = MenuHelper.leerDecimal(scanner, "Monto mensual: ");
        String observaciones = MenuHelper.leerTextoOpcional(scanner, "Observaciones o vacio: ");

        detalleDAO.insertarDetalle(idPresupuesto, idSubcategoria, monto, observaciones, MenuHelper.USUARIO_SISTEMA);
    }

    private void listarDetalles() {
        int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto: ");
        detalleDAO.listarDetalles(idPresupuesto);
    }

    private void consultarDetalle() {
        int idDetalle = MenuHelper.leerEntero(scanner, "ID del detalle: ");
        detalleDAO.consultarDetalle(idDetalle);
    }

    private void actualizarDetalle() {
        int idDetalle = MenuHelper.leerEntero(scanner, "ID del detalle: ");
        BigDecimal monto = MenuHelper.leerDecimal(scanner, "Nuevo monto mensual: ");
        String observaciones = MenuHelper.leerTextoOpcional(scanner, "Nuevas observaciones o vacio: ");

        detalleDAO.actualizarDetalle(idDetalle, monto, observaciones, MenuHelper.USUARIO_SISTEMA);
    }

    private void eliminarDetalle() {
        int idDetalle = MenuHelper.leerEntero(scanner, "ID del detalle: ");
        if (MenuHelper.confirmar(scanner)) {
            detalleDAO.eliminarDetalle(idDetalle);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private boolean periodoValido(short anioInicio, short mesInicio, short anioFin, short mesFin) {
        int periodoInicio = (anioInicio * 12) + mesInicio;
        int periodoFin = (anioFin * 12) + mesFin;
        return periodoFin >= periodoInicio;
    }
}