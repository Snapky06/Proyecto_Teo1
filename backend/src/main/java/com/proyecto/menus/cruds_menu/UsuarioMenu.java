package com.proyecto.menus.cruds_menu;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;
import com.proyecto.cruds.UsuarioDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;

public class UsuarioMenu extends MenuBase {
    private final UsuarioDAO usuarioDAO;

    public UsuarioMenu(Scanner scanner) {
        super(scanner);
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE USUARIOS ---");
        if (esAdmin) {
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Consultar Usuario por ID");
            System.out.println("4. Actualizar Usuario");
            System.out.println("5. Eliminar Usuario");
        } else {
            System.out.println("1. Consultar mi perfil");
            System.out.println("2. Actualizar mi perfil");
        }
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        if (esAdmin) {
            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    usuarioDAO.listarUsuarios();
                    break;
                case 3:
                    consultarUsuarioAdmin();
                    break;
                case 4:
                    actualizarUsuarioAdmin();
                    break;
                case 5:
                    eliminarUsuarioAdmin();
                    break;
                case 0:
                    return true;
                default:
                    System.out.println("Opcion no valida.");
            }
        } else {
            switch (opcion) {
                case 1:
                    usuarioDAO.consultarUsuario(usuarioActivoId);
                    break;
                case 2:
                    actualizarDatos(usuarioActivoId);
                    break;
                case 0:
                    return true;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
        return false;
    }

    public void registrarUsuario() {
        try {
            String nombres = MenuHelper.leerTexto(scanner, "Nombres: ");
            String apellidos = MenuHelper.leerTexto(scanner, "Apellidos: ");
            String correo = MenuHelper.leerTexto(scanner, "Correo: ");
            BigDecimal salario = MenuHelper.leerDecimal(scanner, "Salario base: ");
            Date fecha = new Date(System.currentTimeMillis());
            usuarioDAO.insertarUsuario(nombres, apellidos, correo, fecha, salario, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void consultarUsuarioAdmin() {
        try {
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE USUARIOS REGISTRADOS          ");
            System.out.println("=================================================");
            usuarioDAO.listarUsuarios();
            System.out.println("=================================================");
            int idConsulta = MenuHelper.leerEntero(scanner, "ID del usuario a consultar: ");
            usuarioDAO.consultarUsuario(idConsulta);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarUsuarioAdmin() {
        try {
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE USUARIOS REGISTRADOS          ");
            System.out.println("=================================================");
            usuarioDAO.listarUsuarios();
            System.out.println("=================================================");
            int idUsuario = MenuHelper.leerEntero(scanner, "ID del usuario a actualizar: ");
            actualizarDatos(idUsuario);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarDatos(int id) {
        try {
            String nombres = MenuHelper.leerTexto(scanner, "Nuevos nombres: ");
            String apellidos = MenuHelper.leerTexto(scanner, "Nuevos apellidos: ");
            String correo = MenuHelper.leerTexto(scanner, "Nuevo correo: ");
            BigDecimal salario = MenuHelper.leerDecimal(scanner, "Nuevo salario base: ");
            usuarioDAO.actualizarUsuario(id, MenuHelper.USUARIO_SISTEMA, nombres, apellidos, correo, salario);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void eliminarUsuarioAdmin() {
        try {
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE USUARIOS REGISTRADOS          ");
            System.out.println("=================================================");
            usuarioDAO.listarUsuarios();
            System.out.println("=================================================");
            int idUsuario = MenuHelper.leerEntero(scanner, "ID del usuario a eliminar: ");
            if (MenuHelper.confirmar(scanner)) {
                usuarioDAO.eliminarUsuario(idUsuario, MenuHelper.USUARIO_SISTEMA);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }
}