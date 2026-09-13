package com.proyecto.menus.cruds_menu;

import java.util.Scanner;

import com.proyecto.cruds.SubcategoriaDAO;
import com.proyecto.menus.MenuHelper;

public class SubcategoriaMenu {

    private final Scanner scanner;
    private final SubcategoriaDAO subcategoriaDAO;

    public SubcategoriaMenu(Scanner scanner) {
        this.scanner = scanner;
        subcategoriaDAO = new SubcategoriaDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE SUBCATEGORIAS ---");
            System.out.println("1. Registrar Subcategoria");
            System.out.println("2. Listar Subcategorias");
            System.out.println("3. Consultar Subcategoria por ID");
            System.out.println("4. Actualizar Subcategoria");
            System.out.println("5. Eliminar Subcategoria");
            System.out.println("0. Volver");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    registrarSubcategoria();
                    break;

                case 2:
                    int idCategoria = MenuHelper.leerEntero(
                            scanner,
                            "ID de la categoria: "
                    );

                    subcategoriaDAO.listarSubcategorias(
                            idCategoria
                    );
                    break;

                case 3:
                    int idSubcategoria = MenuHelper.leerEntero(
                            scanner,
                            "ID de la subcategoria: "
                    );

                    subcategoriaDAO.consultarSubcategoria(
                            idSubcategoria
                    );
                    break;

                case 4:
                    actualizarSubcategoria();
                    break;

                case 5:
                    eliminarSubcategoria();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarSubcategoria() {
        int idCategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la categoria: "
        );

        System.out.print("Nombre de la subcategoria: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        subcategoriaDAO.insertarSubcategoria(
                idCategoria,
                nombre,
                descripcion,
                "ADMIN"
        );
    }

    private void actualizarSubcategoria() {
        int idSubcategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la subcategoria: "
        );

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();

        subcategoriaDAO.actualizarSubcategoria(
                idSubcategoria,
                nombre,
                descripcion,
                "ADMIN"
        );
    }

    private void eliminarSubcategoria() {
        int idSubcategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la subcategoria: "
        );

        if (MenuHelper.confirmar(scanner)) {
            subcategoriaDAO.eliminarSubcategoria(
                    idSubcategoria
            );
        } else {
            System.out.println("Operacion cancelada.");
        }
    }
}