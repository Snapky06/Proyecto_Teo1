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
            System.out.println("\n=== SISTEMA DE PRESUPUESTO PERSONAL ===");
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Gestionar Categorias (Proximamente)");
            System.out.println("3. Gestionar Presupuestos (Proximamente)");
            System.out.println("0. Salir del programa");
            System.out.print("Elige una opcion: ");
            
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
                    System.out.println("\nSaliendo del sistema. Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta de nuevo.");
            }
        }
        scanner.close();
    }

    private static void menuUsuarios(Scanner scanner, UsuarioDAO usuarioDAO) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE USUARIOS ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Consultar un Usuario por ID");
            System.out.println("4. Actualizar Usuario");
            System.out.println("5. Eliminar Usuario");
            System.out.println("0. Volver");
            System.out.print("Elige una opcion: ");
            
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
                case 3:
                    System.out.print("Ingresa el ID del usuario a consultar: ");
                    int idConsulta = Integer.parseInt(scanner.nextLine());
                    usuarioDAO.consultarUsuario(idConsulta);
                    break;
                case 4:
                    System.out.print("Ingresa el ID del usuario a actualizar: ");
                    int idActualizar = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nuevos Nombres: ");
                    String nuevosNombres = scanner.nextLine();
                    System.out.print("Nuevos Apellidos: ");
                    String nuevosApellidos = scanner.nextLine();
                    System.out.print("Nuevo Correo: ");
                    String nuevoCorreo = scanner.nextLine();
                    System.out.print("Nuevo Salario Base: ");
                    BigDecimal nuevoSalario = new BigDecimal(scanner.nextLine());

                    usuarioDAO.actualizarUsuario(idActualizar, "ADMIN", nuevosNombres, nuevosApellidos, nuevoCorreo, nuevoSalario);
                    break;
                case 5:
                    System.out.print("Ingresa el ID del usuario a eliminar: ");
                    int idEliminar = Integer.parseInt(scanner.nextLine());
                    usuarioDAO.eliminarUsuario(idEliminar, "ADMIN");
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Intentalo de nuevo.");
            }
        }
    }
}