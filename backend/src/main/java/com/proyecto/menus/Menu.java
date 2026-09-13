package com.proyecto.menus;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner;
    private final UsuarioMenu usuarioMenu;
    private final CategoriaMenu categoriaMenu;
    private final SubcategoriaMenu subcategoriaMenu;
    private final PresupuestoMenu presupuestoMenu;
    private final ObligacionFijaMenu obligacionFijaMenu;
    private final TransaccionMenu transaccionMenu;

    public Menu() {
        scanner = new Scanner(System.in);

        usuarioMenu = new UsuarioMenu(scanner);
        categoriaMenu = new CategoriaMenu(scanner);
        subcategoriaMenu = new SubcategoriaMenu(scanner);
        presupuestoMenu = new PresupuestoMenu(scanner);
        obligacionFijaMenu = new ObligacionFijaMenu(scanner);
        transaccionMenu = new TransaccionMenu(scanner);
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println(
                    "\n=== SISTEMA DE PRESUPUESTO PERSONAL ==="
            );
            System.out.println("1. Gestionar Usuarios");
            System.out.println("2. Gestionar Categorias");
            System.out.println("3. Gestionar Subcategorias");
            System.out.println("4. Gestionar Presupuestos");
            System.out.println("5. Gestionar Obligaciones Fijas");
            System.out.println("6. Gestionar Transacciones");
            System.out.println("0. Salir del programa");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    usuarioMenu.iniciar();
                    break;

                case 2:
                    categoriaMenu.iniciar();
                    break;

                case 3:
                    subcategoriaMenu.iniciar();
                    break;

                case 4:
                    presupuestoMenu.iniciar();
                    break;

                case 5:
                    obligacionFijaMenu.iniciar();
                    break;

                case 6:
                    transaccionMenu.iniciar();
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
}