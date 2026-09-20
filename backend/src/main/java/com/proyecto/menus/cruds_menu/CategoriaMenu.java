package com.proyecto.menus.cruds_menu;

import java.util.Scanner;
import com.proyecto.cruds.CategoriaDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;

public class CategoriaMenu extends MenuBase {
    private final CategoriaDAO categoriaDAO;

    public CategoriaMenu(Scanner scanner) {
        super(scanner);
        this.categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE CATEGORIAS ---");
        System.out.println("1. Registrar Categoria");
        System.out.println("2. Listar Categorias");
        System.out.println("3. Consultar Categoria por ID");
        System.out.println("4. Actualizar Categoria");
        System.out.println("5. Eliminar Categoria");
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarCategoria();
                break;
            case 2:
                int idUsuario = obtenerIdUsuario();
                categoriaDAO.listarCategorias(idUsuario);
                break;
            case 3:
                consultarCategoria();
                break;
            case 4:
                actualizarCategoria();
                break;
            case 5:
                eliminarCategoria();
                break;
            case 0:
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void registrarCategoria() {
        try {
            int idUsuario = obtenerIdUsuario();
            String tipo = MenuHelper.leerTipo(scanner);
            String nombre = MenuHelper.leerTexto(scanner, "Nombre de la categoria");
            String descripcion = MenuHelper.leerTexto(scanner, "Descripcion");
            String icono = MenuHelper.leerTextoOpcional(scanner, "Icono opcional");
            if (icono == null) icono = "";
            String colorHex = MenuHelper.leerColor(scanner);
            short orden = MenuHelper.leerPositivo(scanner, "Orden de presentacion");
            categoriaDAO.insertarCategoria(idUsuario, nombre, descripcion, tipo, icono, colorHex, orden, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void consultarCategoria() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE CATEGORIAS DISPONIBLES        ");
            System.out.println("=================================================");
            categoriaDAO.listarCategorias(idUsuario);
            System.out.println("=================================================");
            
            int idCategoria = MenuHelper.leerEntero(scanner, "ID de la categoria");
            categoriaDAO.consultarCategoria(idCategoria);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarCategoria() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE CATEGORIAS DISPONIBLES        ");
            System.out.println("=================================================");
            categoriaDAO.listarCategorias(idUsuario);
            System.out.println("=================================================");
            
            int idCategoria = MenuHelper.leerEntero(scanner, "ID de la categoria");
            String tipo = MenuHelper.leerTipo(scanner);
            String nombre = MenuHelper.leerTexto(scanner, "Nuevo nombre");
            String descripcion = MenuHelper.leerTexto(scanner, "Nueva descripcion");
            String icono = MenuHelper.leerTextoOpcional(scanner, "Nuevo icono");
            if (icono == null) icono = "";
            String colorHex = MenuHelper.leerColor(scanner);
            short orden = MenuHelper.leerPositivo(scanner, "Nuevo orden de presentacion");
            categoriaDAO.actualizarCategoria(idCategoria, nombre, descripcion, tipo, icono, colorHex, orden, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void eliminarCategoria() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE CATEGORIAS DISPONIBLES        ");
            System.out.println("=================================================");
            categoriaDAO.listarCategorias(idUsuario);
            System.out.println("=================================================");
            
            int idCategoria = MenuHelper.leerEntero(scanner, "ID de la categoria");
            if (MenuHelper.confirmar(scanner)) {
                categoriaDAO.eliminarCategoria(idCategoria);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }
}