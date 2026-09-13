package com.proyecto.menus;

import com.proyecto.menus.cruds_menu.CategoriaMenu;
import com.proyecto.menus.cruds_menu.ObligacionFijaMenu;
import com.proyecto.menus.cruds_menu.PresupuestoMenu;
import com.proyecto.menus.cruds_menu.SubcategoriaMenu;
import com.proyecto.menus.cruds_menu.TransaccionMenu;
import com.proyecto.menus.cruds_menu.UsuarioMenu;
import com.proyecto.menus.reportes_menu.ReportesMenu;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    private final UsuarioMenu usuarioMenu;
    private final CategoriaMenu categoriaMenu;
    private final SubcategoriaMenu subcategoriaMenu;
    private final PresupuestoMenu presupuestoMenu;
    private final ObligacionFijaMenu obligacionFijaMenu;
    private final TransaccionMenu transaccionMenu;
    private final ReportesMenu reportesMenu;

    public Menu() {
        scanner = new Scanner(System.in);

        usuarioMenu = new UsuarioMenu(scanner);
        categoriaMenu = new CategoriaMenu(scanner);
        subcategoriaMenu = new SubcategoriaMenu(scanner);
        presupuestoMenu = new PresupuestoMenu(scanner);
        obligacionFijaMenu = new ObligacionFijaMenu(scanner);
        transaccionMenu = new TransaccionMenu(scanner);
        reportesMenu = new ReportesMenu(scanner);
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();

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

                case 7:
                    reportesMenu.iniciar();
                    break;

                case 0:
                    salir = true;
                    System.out.println(
                            "Saliendo del sistema. Hasta pronto!"
                    );
                    break;

                default:
                    System.out.println(
                            "Opcion no valida."
                    );
            }
        }

        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println(
                "\n=== SISTEMA DE PRESUPUESTO PERSONAL ==="
        );
        System.out.println("1. Gestionar Usuarios");
        System.out.println("2. Gestionar Categorias");
        System.out.println("3. Gestionar Subcategorias");
        System.out.println("4. Gestionar Presupuestos");
        System.out.println("5. Gestionar Obligaciones Fijas");
        System.out.println("6. Gestionar Transacciones");
        System.out.println("7. Reportes");
        System.out.println("0. Salir del programa");
    }
}