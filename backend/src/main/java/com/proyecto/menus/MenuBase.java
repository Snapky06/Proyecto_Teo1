package com.proyecto.menus;

import java.util.Scanner;

public abstract class MenuBase {
    protected final Scanner scanner;
    protected int usuarioActivoId;
    protected boolean esAdmin;

    public MenuBase(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setUsuarioActivo(int usuarioActivoId, boolean esAdmin) {
        this.usuarioActivoId = usuarioActivoId;
        this.esAdmin = esAdmin;
    }

    protected int obtenerIdUsuario() {
        if (esAdmin) {
            return MenuHelper.leerEntero(scanner, "ID del usuario objetivo");
        } else {
            return usuarioActivoId;
        }
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            try {
                int opcion = MenuHelper.leerOpcionMenu(scanner, "Elige una opcion: ");
                salir = ejecutarOpcion(opcion);
            } catch (MenuHelper.OperacionCanceladaException e) {
                System.out.println("\n[!] Operacion cancelada por el usuario.\n");
            }
        }
    }

    protected abstract void mostrarMenu();
    protected abstract boolean ejecutarOpcion(int opcion);
}