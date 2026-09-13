package com.proyecto.menus.cruds_menu;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

import com.proyecto.cruds.UsuarioDAO;
import com.proyecto.menus.MenuHelper;

public class UsuarioMenu {

    private final Scanner scanner;
    private final UsuarioDAO usuarioDAO;

    public UsuarioMenu(Scanner scanner) {
        this.scanner = scanner;
        usuarioDAO = new UsuarioDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE USUARIOS ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Consultar Usuario por ID");
            System.out.println("4. Actualizar Usuario");
            System.out.println("5. Eliminar Usuario");
            System.out.println("0. Volver");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;

                case 2:
                    usuarioDAO.listarUsuarios();
                    break;

                case 3:
                    int idConsulta = MenuHelper.leerEntero(
                            scanner,
                            "ID del usuario: "
                    );

                    usuarioDAO.consultarUsuario(idConsulta);
                    break;

                case 4:
                    actualizarUsuario();
                    break;

                case 5:
                    eliminarUsuario();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarUsuario() {
        System.out.print("Nombres: ");
        String nombres = scanner.nextLine();

        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        BigDecimal salario = MenuHelper.leerDecimal(
                scanner,
                "Salario base: "
        );

        Date fecha = new Date(
                System.currentTimeMillis()
        );

        usuarioDAO.insertarUsuario(
                nombres,
                apellidos,
                correo,
                fecha,
                salario,
                "ADMIN"
        );
    }

    private void actualizarUsuario() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario: "
        );

        System.out.print("Nuevos nombres: ");
        String nombres = scanner.nextLine();

        System.out.print("Nuevos apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.print("Nuevo correo: ");
        String correo = scanner.nextLine();

        BigDecimal salario = MenuHelper.leerDecimal(
                scanner,
                "Nuevo salario base: "
        );

        usuarioDAO.actualizarUsuario(
                idUsuario,
                "ADMIN",
                nombres,
                apellidos,
                correo,
                salario
        );
    }

    private void eliminarUsuario() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario: "
        );

        if (MenuHelper.confirmar(scanner)) {
            usuarioDAO.eliminarUsuario(
                    idUsuario,
                    "ADMIN"
            );
        } else {
            System.out.println("Operacion cancelada.");
        }
    }
}