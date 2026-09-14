package com.proyecto.menus;

import java.util.Scanner;

public abstract class MenuBase {

    protected final Scanner scanner;

    public MenuBase(Scanner scanner) {
        this.scanner = scanner;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = MenuHelper.leerEntero(scanner, "Elige una opcion: ");
            salir = ejecutarOpcion(opcion);
        }
    }

    protected abstract void mostrarMenu();

    protected abstract boolean ejecutarOpcion(int opcion);
}