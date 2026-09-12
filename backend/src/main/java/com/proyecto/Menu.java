package com.proyecto;

import com.proyecto.categoria.CategoriaDAO;
import com.proyecto.subcategoria.SubcategoriaDAO;
import com.proyecto.usuario.UsuarioDAO;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final UsuarioDAO usuarioDAO;
    private final CategoriaDAO categoriaDAO;
    private final SubcategoriaDAO subcategoriaDAO;

    public Menu() {
        scanner = new Scanner(System.in);
        usuarioDAO = new UsuarioDAO();
        categoriaDAO = new CategoriaDAO();
        subcategoriaDAO = new SubcategoriaDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== SISTEMA DE PRESUPUESTO PERSONAL ===");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Gestionar Categorias");
            System.out.println("3. Gestionar Subcategorias");
            System.out.println("4. Gestionar Presupuestos (Proximamente)");
            System.out.println("0. Salir del programa");

            int opcion = leerEntero("Elige una opcion: ");

            switch (opcion) {
                case 1:
                    menuUsuarios();
                    break;

                case 2:
                    menuCategorias();
                    break;

                case 3:
                    menuSubcategorias();
                    break;

                case 4:
                    System.out.println("Modulo en construccion.");
                    break;

                case 0:
                    salir = true;
                    System.out.println(
                            "Saliendo del sistema. Hasta pronto!"
                    );
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }

        scanner.close();
    }

    private void menuUsuarios() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE USUARIOS ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Consultar un Usuario por ID");
            System.out.println("4. Actualizar Usuario");
            System.out.println("5. Eliminar Usuario");
            System.out.println("0. Volver");

            int opcion = leerEntero("Elige una opcion: ");

            switch (opcion) {
                case 1:
                    System.out.print("Nombres: ");
                    String nombres = scanner.nextLine();

                    System.out.print("Apellidos: ");
                    String apellidos = scanner.nextLine();

                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();

                    BigDecimal salario =
                            leerDecimal("Salario base: ");

                    Date fecha =
                            new Date(System.currentTimeMillis());

                    usuarioDAO.insertarUsuario(
                            nombres,
                            apellidos,
                            correo,
                            fecha,
                            salario,
                            "ADMIN"
                    );
                    break;

                case 2:
                    usuarioDAO.listarUsuarios();
                    break;

                case 3:
                    int idConsulta =
                            leerEntero("ID del usuario a consultar: ");

                    usuarioDAO.consultarUsuario(idConsulta);
                    break;

                case 4:
                    int idActualizar =
                            leerEntero("ID del usuario a actualizar: ");

                    System.out.print("Nuevos nombres: ");
                    String nuevosNombres = scanner.nextLine();

                    System.out.print("Nuevos apellidos: ");
                    String nuevosApellidos = scanner.nextLine();

                    System.out.print("Nuevo correo: ");
                    String nuevoCorreo = scanner.nextLine();

                    BigDecimal nuevoSalario =
                            leerDecimal("Nuevo salario base: ");

                    usuarioDAO.actualizarUsuario(
                            idActualizar,
                            "ADMIN",
                            nuevosNombres,
                            nuevosApellidos,
                            nuevoCorreo,
                            nuevoSalario
                    );
                    break;

                case 5:
                    int idEliminar =
                            leerEntero("ID del usuario a eliminar: ");

                    usuarioDAO.eliminarUsuario(
                            idEliminar,
                            "ADMIN"
                    );
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void menuCategorias() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE CATEGORIAS ---");
            System.out.println("1. Registrar Categoria");
            System.out.println("2. Listar Categorias");
            System.out.println("3. Consultar Categoria por ID");
            System.out.println("4. Actualizar Categoria");
            System.out.println("5. Eliminar Categoria");
            System.out.println("0. Volver");

            int opcion = leerEntero("Elige una opcion: ");

            switch (opcion) {
                case 1:
                    registrarCategoria();
                    break;

                case 2:
                    int idUsuarioLista =
                            leerEntero("ID del usuario: ");

                    categoriaDAO.listarCategorias(idUsuarioLista);
                    break;

                case 3:
                    int idCategoriaConsulta =
                            leerEntero("ID de la categoria: ");

                    categoriaDAO.consultarCategoria(
                            idCategoriaConsulta
                    );
                    break;

                case 4:
                    actualizarCategoria();
                    break;

                case 5:
                    int idCategoriaEliminar =
                            leerEntero("ID de la categoria: ");

                    System.out.println(
                            "La categoria y sus subcategorias "
                                    + "seran eliminadas."
                    );
                    System.out.print("Desea continuar? Escriba SI: ");

                    String confirmacion = scanner.nextLine();

                    if (confirmacion.equalsIgnoreCase("SI")) {
                        categoriaDAO.eliminarCategoria(
                                idCategoriaEliminar
                        );
                    } else {
                        System.out.println("Operacion cancelada.");
                    }
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
        int idUsuario =
                leerEntero("ID del usuario propietario: ");

        System.out.println(
                "El tipo indica si es una categoria de ingresos "
                        + "o gastos."
        );
        System.out.println("Opciones validas: INGRESO o GASTO");

        String tipo = leerTipo();

        System.out.print("Nombre de la categoria: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripcion de la categoria: ");
        String descripcion = scanner.nextLine();

        System.out.println(
                "El icono puede ser comida, casa o transporte."
        );
        System.out.print(
                "Icono opcional, presione Enter para dejarlo vacio: "
        );
        String icono = scanner.nextLine();

        System.out.println(
                "El color debe tener el formato #RRGGBB."
        );
        System.out.println("Ejemplo: #FF0000");

        String colorHex = leerColor();

        System.out.println(
                "El orden indica la posicion en las listas."
        );

        short orden = leerOrden();

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
        int idCategoria =
                leerEntero("ID de la categoria a actualizar: ");

        System.out.println(
                "Opciones validas para el tipo: INGRESO o GASTO"
        );

        String tipo = leerTipo();

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();

        System.out.print(
                "Nuevo icono, presione Enter para dejarlo vacio: "
        );
        String icono = scanner.nextLine();

        System.out.println(
                "El color debe tener el formato #RRGGBB."
        );
        System.out.println("Ejemplo: #FF0000");

        String colorHex = leerColor();

        short orden = leerOrden();

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

    private void menuSubcategorias() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE SUBCATEGORIAS ---");
            System.out.println("1. Registrar Subcategoria");
            System.out.println("2. Listar Subcategorias");
            System.out.println("3. Consultar Subcategoria por ID");
            System.out.println("4. Actualizar Subcategoria");
            System.out.println("5. Eliminar Subcategoria");
            System.out.println("0. Volver");

            int opcion = leerEntero("Elige una opcion: ");

            switch (opcion) {
                case 1:
                    registrarSubcategoria();
                    break;

                case 2:
                    int idCategoriaLista =
                            leerEntero("ID de la categoria: ");

                    subcategoriaDAO.listarSubcategorias(
                            idCategoriaLista
                    );
                    break;

                case 3:
                    int idSubcategoriaConsulta =
                            leerEntero(
                                    "ID de la subcategoria: "
                            );

                    subcategoriaDAO.consultarSubcategoria(
                            idSubcategoriaConsulta
                    );
                    break;

                case 4:
                    actualizarSubcategoria();
                    break;

                case 5:
                    int idSubcategoriaEliminar =
                            leerEntero(
                                    "ID de la subcategoria: "
                            );

                    System.out.println(
                            "La subcategoria sera eliminada."
                    );
                    System.out.print(
                            "Desea continuar? Escriba SI: "
                    );

                    String confirmacion = scanner.nextLine();

                    if (confirmacion.equalsIgnoreCase("SI")) {
                        subcategoriaDAO.eliminarSubcategoria(
                                idSubcategoriaEliminar
                        );
                    } else {
                        System.out.println(
                                "Operacion cancelada."
                        );
                    }
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
        int idCategoria =
                leerEntero("ID de la categoria: ");

        System.out.print("Nombre de la subcategoria: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripcion de la subcategoria: ");
        String descripcion = scanner.nextLine();

        subcategoriaDAO.insertarSubcategoria(
                idCategoria,
                nombre,
                descripcion,
                "ADMIN"
        );
    }

    private void actualizarSubcategoria() {
        int idSubcategoria =
                leerEntero(
                        "ID de la subcategoria a actualizar: "
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

    private String leerTipo() {
        while (true) {
            System.out.print("Tipo: ");
            String tipo = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            if (tipo.equals("INGRESO")
                    || tipo.equals("GASTO")) {
                return tipo;
            }

            System.out.println(
                    "Tipo no valido. Escriba INGRESO o GASTO."
            );
        }
    }

    private String leerColor() {
        while (true) {
            System.out.print("Color hexadecimal: ");
            String color = scanner.nextLine().trim();

            if (color.isEmpty()) {
                return "";
            }

            if (color.matches("#[0-9A-Fa-f]{6}")) {
                return color;
            }

            System.out.println(
                    "Color no valido. Use un formato como #FF0000."
            );
        }
    }

    private short leerOrden() {
        while (true) {
            System.out.print(
                    "Orden de presentacion, use un numero como 1: "
            );

            String entrada = scanner.nextLine();

            try {
                short orden = Short.parseShort(entrada);

                if (orden >= 0) {
                    return orden;
                }

                System.out.println(
                        "El orden no puede ser negativo."
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe escribir un numero entero."
                );
            }
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe escribir un numero entero."
                );
            }
        }
    }

    private BigDecimal leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return new BigDecimal(entrada);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe escribir un numero valido."
                );
            }
        }
    }
}