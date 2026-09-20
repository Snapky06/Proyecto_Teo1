package com.proyecto.menus.cruds_menu;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;
import java.util.Scanner;
import com.proyecto.cruds.ObligacionFijaDAO;
import com.proyecto.cruds.SubcategoriaDAO;
import com.proyecto.cruds.CategoriaDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;

public class ObligacionFijaMenu extends MenuBase {
    private final ObligacionFijaDAO obligacionDAO;
    private final SubcategoriaDAO subcategoriaDAO;
    private final CategoriaDAO categoriaDAO;

    public ObligacionFijaMenu(Scanner scanner) {
        super(scanner);
        this.obligacionDAO = new ObligacionFijaDAO();
        this.subcategoriaDAO = new SubcategoriaDAO();
        this.categoriaDAO = new CategoriaDAO();
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
        try {
            int idUsuario = obtenerIdUsuario();
            
            System.out.println("\n=================================================");
            System.out.println("          DIRECTORIO DE SUBCATEGORIAS            ");
            System.out.println("=================================================");
            Map<Integer, String> categorias = categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "INGRESO");
            categorias.putAll(categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "GASTO"));
            for (Map.Entry<Integer, String> entry : categorias.entrySet()) {
                System.out.println("\n-> Categoria ID: " + entry.getKey() + " | Nombre: " + entry.getValue());
                subcategoriaDAO.listarSubcategorias(entry.getKey());
            }
            System.out.println("=================================================");
            int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria");

            String nombre = MenuHelper.leerTexto(scanner, "Nombre de la obligacion");
            String descripcion = MenuHelper.leerTexto(scanner, "Descripcion");
            BigDecimal monto = MenuHelper.leerDecimal(scanner, "Monto fijo mensual");
            short diaVencimiento = MenuHelper.leerDiaVencimiento(scanner);
            boolean vigente = MenuHelper.leerBooleano(scanner, "La obligacion esta vigente?");
            Date fechaInicio = MenuHelper.leerFecha(scanner, "Fecha de inicio YYYY-MM-DD");
            Date fechaFin = MenuHelper.leerFechaOpcional(scanner, "Fecha de fin YYYY-MM-DD");
            
            obligacionDAO.insertarObligacion(idUsuario, idSubcategoria, nombre, descripcion, monto, diaVencimiento, vigente, fechaInicio, fechaFin, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void listarObligaciones() {
        try {
            int idUsuario = obtenerIdUsuario();
            obligacionDAO.listarObligaciones(idUsuario);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void consultarObligacion() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE OBLIGACIONES FIJAS            ");
            System.out.println("=================================================");
            obligacionDAO.listarObligaciones(idUsuario);
            System.out.println("=================================================");
            
            int idObligacion = MenuHelper.leerEntero(scanner, "ID de la obligacion");
            obligacionDAO.consultarObligacion(idObligacion);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarObligacion() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE OBLIGACIONES FIJAS            ");
            System.out.println("=================================================");
            obligacionDAO.listarObligaciones(idUsuario);
            System.out.println("=================================================");
            int idObligacion = MenuHelper.leerEntero(scanner, "ID de la obligacion a actualizar");

            System.out.println("\n=================================================");
            System.out.println("          DIRECTORIO DE SUBCATEGORIAS            ");
            System.out.println("=================================================");
            Map<Integer, String> categorias = categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "INGRESO");
            categorias.putAll(categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "GASTO"));
            for (Map.Entry<Integer, String> entry : categorias.entrySet()) {
                System.out.println("\n-> Categoria ID: " + entry.getKey() + " | Nombre: " + entry.getValue());
                subcategoriaDAO.listarSubcategorias(entry.getKey());
            }
            System.out.println("=================================================");
            int idSubcategoria = MenuHelper.leerEntero(scanner, "Nuevo ID de la subcategoria");

            String nombre = MenuHelper.leerTexto(scanner, "Nuevo nombre");
            String descripcion = MenuHelper.leerTexto(scanner, "Nueva descripcion");
            BigDecimal monto = MenuHelper.leerDecimal(scanner, "Nuevo monto fijo mensual");
            short diaVencimiento = MenuHelper.leerDiaVencimiento(scanner);
            boolean vigente = MenuHelper.leerBooleano(scanner, "La obligacion esta vigente?");
            Date fechaInicio = MenuHelper.leerFecha(scanner, "Nueva fecha de inicio YYYY-MM-DD");
            Date fechaFin = MenuHelper.leerFechaOpcional(scanner, "Nueva fecha de fin YYYY-MM-DD");
            
            obligacionDAO.actualizarObligacion(idObligacion, idSubcategoria, nombre, descripcion, monto, diaVencimiento, vigente, fechaInicio, fechaFin, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void eliminarObligacion() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE OBLIGACIONES FIJAS            ");
            System.out.println("=================================================");
            obligacionDAO.listarObligaciones(idUsuario);
            System.out.println("=================================================");
            
            int idObligacion = MenuHelper.leerEntero(scanner, "ID de la obligacion a eliminar");
            if (MenuHelper.confirmar(scanner)) {
                obligacionDAO.eliminarObligacion(idObligacion);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }
}