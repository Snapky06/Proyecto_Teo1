package com.proyecto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n1. Usuarios\n2. Categorias\n3. Presupuestos\n0. Salir");
            String entrada = scanner.nextLine();
            int opcion = -1;
            try { opcion = Integer.parseInt(entrada); } catch (Exception e) {}

            switch (opcion) {
                case 1:
                    menuUsuarios(scanner, usuarioDAO);
                    break;
                case 2:
                case 3:
                    System.out.println("Modulo en construccion.");
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        scanner.close();
    }

    private static void menuUsuarios(Scanner scanner, UsuarioDAO usuarioDAO) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n1. Registrar Usuario\n2. Listar Usuarios\n0. Volver");
            String entrada = scanner.nextLine();
            int opcion = -1;
            try { opcion = Integer.parseInt(entrada); } catch (Exception e) {}

            switch (opcion) {
                case 1:
                    System.out.print("Nombres: ");
                    String nombres = scanner.nextLine();
                    System.out.print("Apellidos: ");
                    String apellidos = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    System.out.print("Salario Base: ");
                    BigDecimal salario = new BigDecimal(scanner.nextLine());
                    Date fecha = new Date(System.currentTimeMillis());

                    usuarioDAO.insertarUsuario(nombres, apellidos, correo, fecha, salario, "ADMIN");
                    break;
                case 2:
                    usuarioDAO.listarUsuarios();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }
}