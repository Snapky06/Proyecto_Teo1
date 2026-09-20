package com.proyecto.menus.cruds_menu;

import java.util.Map;
import java.util.Scanner;
import com.proyecto.cruds.SubcategoriaDAO;
import com.proyecto.cruds.CategoriaDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;

public class SubcategoriaMenu extends MenuBase {
    private final SubcategoriaDAO subcategoriaDAO;
    private final CategoriaDAO categoriaDAO;

    public SubcategoriaMenu(Scanner scanner) {
        super(scanner);
        this.subcategoriaDAO = new SubcategoriaDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE SUBCATEGORIAS ---");
        System.out.println("1. Registrar Subcategoria");
        System.out.println("2. Listar Subcategorias");
        System.out.println("3. Consultar Subcategoria por ID");
        System.out.println("4. Actualizar Subcategoria");
        System.out.println("5. Eliminar Subcategoria");
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarSubcategoria();
                break;
            case 2:
                listarSubcategoriasFlujo();
                break;
            case 3:
                consultarSubcategoria();
                break;
            case 4:
                actualizarSubcategoria();
                break;
            case 5:
                eliminarSubcategoria();
                break;
            case 0:
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void registrarSubcategoria() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE CATEGORIAS DISPONIBLES        ");
            System.out.println("=================================================");
            categoriaDAO.listarCategorias(idUsuario);
            System.out.println("=================================================");
            
            int idCategoria = MenuHelper.leerEntero(scanner, "ID de la categoria padre");
            String nombre = MenuHelper.leerTexto(scanner, "Nombre de la subcategoria");
            String descripcion = MenuHelper.leerTexto(scanner, "Descripcion");
            subcategoriaDAO.insertarSubcategoria(idCategoria, nombre, descripcion, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void listarSubcategoriasFlujo() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE CATEGORIAS DISPONIBLES        ");
            System.out.println("=================================================");
            categoriaDAO.listarCategorias(idUsuario);
            System.out.println("=================================================");
            
            int idCategoria = MenuHelper.leerEntero(scanner, "ID de la categoria");
            subcategoriaDAO.listarSubcategorias(idCategoria);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void consultarSubcategoria() {
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
            subcategoriaDAO.consultarSubcategoria(idSubcategoria);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarSubcategoria() {
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
            
            int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria a actualizar");
            String nombre = MenuHelper.leerTexto(scanner, "Nuevo nombre");
            String descripcion = MenuHelper.leerTexto(scanner, "Nueva descripcion");
            subcategoriaDAO.actualizarSubcategoria(idSubcategoria, nombre, descripcion, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void eliminarSubcategoria() {
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
            
            int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria a eliminar");
            if (MenuHelper.confirmar(scanner)) {
                subcategoriaDAO.eliminarSubcategoria(idSubcategoria);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }
}