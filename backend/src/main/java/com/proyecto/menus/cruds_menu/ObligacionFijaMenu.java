package com.proyecto.menus.cruds_menu;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;
import com.proyecto.cruds.ObligacionFijaDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;

public class ObligacionFijaMenu extends MenuBase {

    private final ObligacionFijaDAO obligacionDAO;

    public ObligacionFijaMenu(Scanner scanner) {
        super(scanner);
        this.obligacionDAO = new ObligacionFijaDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE OBLIGACIONES FIJAS ---");
        System.out.println("1. Registrar obligacion fija");
        System.out.println("2. Listar obligaciones fijas");
        System.out.println("3. Consultar obligacion fija");
        System.out.println("4. Actualizar obligacion fija");
        System.out.println("5. Eliminar obligacion fija");
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
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
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void registrarObligacion() {
        int idUsuario = MenuHelper.leerEntero(scanner, "ID del usuario: ");
        int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria: ");
        String nombre = MenuHelper.leerTexto(scanner, "Nombre de la obligacion: ");
        String descripcion = MenuHelper.leerTexto(scanner, "Descripcion: ");
        BigDecimal monto = MenuHelper.leerDecimal(scanner, "Monto fijo mensual: ");
        short diaVencimiento = MenuHelper.leerDiaVencimiento(scanner);
        boolean vigente = MenuHelper.leerBooleano(scanner, "La obligacion esta vigente? SI/NO: ");
        Date fechaInicio = MenuHelper.leerFecha(scanner, "Fecha de inicio YYYY-MM-DD: ");
        Date fechaFin = MenuHelper.leerFechaOpcional(scanner, "Fecha de fin YYYY-MM-DD o vacio: ");

        obligacionDAO.insertarObligacion(idUsuario, idSubcategoria, nombre, descripcion, monto, diaVencimiento, vigente, fechaInicio, fechaFin, MenuHelper.USUARIO_SISTEMA);
    }

    private void listarObligaciones() {
        int idUsuario = MenuHelper.leerEntero(scanner, "ID del usuario: ");
        obligacionDAO.listarObligaciones(idUsuario);
    }

    private void consultarObligacion() {
        int idObligacion = MenuHelper.leerEntero(scanner, "ID de la obligacion: ");
        obligacionDAO.consultarObligacion(idObligacion);
    }

    private void actualizarObligacion() {
        int idObligacion = MenuHelper.leerEntero(scanner, "ID de la obligacion: ");
        int idSubcategoria = MenuHelper.leerEntero(scanner, "Nuevo ID de la subcategoria: ");
        String nombre = MenuHelper.leerTexto(scanner, "Nuevo nombre: ");
        String descripcion = MenuHelper.leerTexto(scanner, "Nueva descripcion: ");
        BigDecimal monto = MenuHelper.leerDecimal(scanner, "Nuevo monto fijo mensual: ");
        short diaVencimiento = MenuHelper.leerDiaVencimiento(scanner);
        boolean vigente = MenuHelper.leerBooleano(scanner, "La obligacion esta vigente? SI/NO: ");
        Date fechaInicio = MenuHelper.leerFecha(scanner, "Nueva fecha de inicio YYYY-MM-DD: ");
        Date fechaFin = MenuHelper.leerFechaOpcional(scanner, "Nueva fecha de fin YYYY-MM-DD o vacio: ");

        obligacionDAO.actualizarObligacion(idObligacion, idSubcategoria, nombre, descripcion, monto, diaVencimiento, vigente, fechaInicio, fechaFin, MenuHelper.USUARIO_SISTEMA);
    }

    private void eliminarObligacion() {
        int idObligacion = MenuHelper.leerEntero(scanner, "ID de la obligacion: ");
        if (MenuHelper.confirmar(scanner)) {
            obligacionDAO.eliminarObligacion(idObligacion);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }
}