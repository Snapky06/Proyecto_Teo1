package com.proyecto.menus;

import java.util.Scanner;

import com.proyecto.cruds.CategoriaDAO;

public class CategoriaMenu {

    private final Scanner scanner;
    private final CategoriaDAO categoriaDAO;

    public CategoriaMenu(Scanner scanner) {
        this.scanner = scanner;
        categoriaDAO = new CategoriaDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE CATEGORIAS ---");
            System.out.println("1. Registrar Categoria");
            System.out.println("2. Listar Categorias");
            System.out.println("3. Consultar Categoria por ID");
            System.out.println("4. Actualizar Categoria");
            System.out.println("5. Eliminar Categoria");
            System.out.println("0. Volver");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    registrarCategoria();
                    break;

                case 2:
                    int idUsuario = MenuHelper.leerEntero(
                            scanner,
                            "ID del usuario: "
                    );

                    categoriaDAO.listarCategorias(idUsuario);
                    break;

                case 3:
                    int idCategoria = MenuHelper.leerEntero(
                            scanner,
                            "ID de la categoria: "
                    );

                    categoriaDAO.consultarCategoria(idCategoria);
                    break;

                case 4:
                    actualizarCategoria();
                    break;

                case 5:
                    eliminarCategoria();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarCategoria() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario propietario: "
        );

        String tipo = MenuHelper.leerTipo(scanner);

        System.out.print("Nombre de la categoria: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        System.out.print("Icono opcional: ");
        String icono = scanner.nextLine();

        String colorHex = MenuHelper.leerColor(scanner);

        short orden = MenuHelper.leerPositivo(
                scanner,
                "Orden de presentacion: "
        );

        categoriaDAO.insertarCategoria(
                idUsuario,
                nombre,
                descripcion,
                tipo,
                icono,
                colorHex,
                orden,
                "ADMIN"
        );
    }

    private void actualizarCategoria() {
        int idCategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la categoria: "
        );

        String tipo = MenuHelper.leerTipo(scanner);

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();

        System.out.print("Nuevo icono: ");
        String icono = scanner.nextLine();

        String colorHex = MenuHelper.leerColor(scanner);

        short orden = MenuHelper.leerPositivo(
                scanner,
                "Nuevo orden de presentacion: "
        );

        categoriaDAO.actualizarCategoria(
                idCategoria,
                nombre,
                descripcion,
                tipo,
                icono,
                colorHex,
                orden,
                "ADMIN"
        );
    }

    private void eliminarCategoria() {
        int idCategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la categoria: "
        );

        System.out.println(
                "La categoria y sus subcategorias seran eliminadas."
        );

        if (MenuHelper.confirmar(scanner)) {
            categoriaDAO.eliminarCategoria(idCategoria);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }
}