package com.proyecto.menus;

import com.proyecto.cruds.UsuarioDAO;
import com.proyecto.menus.cruds_menu.*;
import com.proyecto.menus.reportes_menu.ReportesMenu;
import java.util.Scanner;

public class Menu extends MenuBase {
    
    private final UsuarioMenu usuarioMenu;
    private final CategoriaMenu categoriaMenu;
    private final SubcategoriaMenu subcategoriaMenu;
    private final PresupuestoMenu presupuestoMenu;
    private final ObligacionFijaMenu obligacionFijaMenu;
    private final TransaccionMenu transaccionMenu;
    private final ReportesMenu reportesMenu;
    private final UsuarioDAO usuarioDAO;

    public Menu() {
        super(new Scanner(System.in));
        usuarioMenu = new UsuarioMenu(scanner);
        categoriaMenu = new CategoriaMenu(scanner);
        subcategoriaMenu = new SubcategoriaMenu(scanner);
        presupuestoMenu = new PresupuestoMenu(scanner);
        obligacionFijaMenu = new ObligacionFijaMenu(scanner);
        transaccionMenu = new TransaccionMenu(scanner);
        reportesMenu = new ReportesMenu(scanner);
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    public void iniciar() {
        while (true) {
            if (this.usuarioActivoId == 0) {
                mostrarMenuAuth();
            } else {
                super.iniciar(); 
                this.usuarioActivoId = 0;
                this.esAdmin = false;
            }
        }
    }

    private void mostrarMenuAuth() {
        try {
            System.out.println("\n=== BIENVENIDO AL SISTEMA DE PRESUPUESTO PERSONAL ===");
            System.out.println("1. Iniciar Sesion (Login)");
            System.out.println("2. Crear Usuario Nuevo");
            System.out.println("0. Salir del programa");
            
            int opcion = MenuHelper.leerOpcionMenu(scanner, "Elige una opcion: ");
            switch (opcion) {
                case 1:
                    String nombre = MenuHelper.leerTexto(scanner, "Ingrese su nombre");
                    Integer id = usuarioDAO.loginPorNombre(nombre);
                    
                    if (id != null) {
                        boolean adminStatus = nombre.equalsIgnoreCase("Admin");
                        this.setUsuarioActivo(id, adminStatus);
                        
                        usuarioMenu.setUsuarioActivo(id, adminStatus);
                        categoriaMenu.setUsuarioActivo(id, adminStatus);
                        subcategoriaMenu.setUsuarioActivo(id, adminStatus);
                        presupuestoMenu.setUsuarioActivo(id, adminStatus);
                        obligacionFijaMenu.setUsuarioActivo(id, adminStatus);
                        transaccionMenu.setUsuarioActivo(id, adminStatus);
                        reportesMenu.setUsuarioActivo(id, adminStatus);
                        
                        System.out.println("\nSesion iniciada con exito. Bienvenido, " + nombre + ".");
                    } else {
                        System.out.println("\nUsuario no encontrado o inactivo. Verifique su nombre.");
                    }
                    break;
                case 2:
                    System.out.println("\n--- REGISTRO DE NUEVO USUARIO ---");
                    usuarioMenu.registrarUsuario();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n=== PANEL PRINCIPAL ===\n");
        System.out.println("--- OPERACIONES DIARIAS ---");
        System.out.println(" 1. Menu de Transacciones");
        System.out.println("\n--- PANEL DE CONTROL Y ANALISIS ---");
        System.out.println(" 2. Generar Reportes PDF");
        System.out.println("\n--- PLANIFICACION FINANCIERA ---");
        System.out.println(" 3. Menu de Presupuestos");
        System.out.println(" 4. Menu de Obligaciones Fijas");
        System.out.println("\n--- CONFIGURACION DEL SISTEMA ---");
        System.out.println(" 5. Menu de Categorias");
        System.out.println(" 6. Menu de Subcategorias");
        if (esAdmin) {
            System.out.println(" 7. Menu de Usuarios (Admin)");
        } else {
            System.out.println(" 7. Mi Perfil de Usuario");
        }
        System.out.println(" 8. Guia de Uso / Instrucciones del Sistema");
        System.out.println(" 0. Cerrar Sesion\n");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                transaccionMenu.iniciar();
                break;
            case 2:
                reportesMenu.iniciar();
                break;
            case 3:
                presupuestoMenu.iniciar();
                break;
            case 4:
                obligacionFijaMenu.iniciar();
                break;
            case 5:
                categoriaMenu.iniciar();
                break;
            case 6:
                subcategoriaMenu.iniciar();
                break;
            case 7:
                usuarioMenu.iniciar();
                break;
            case 8:
                mostrarInstruccionesFlujo();
                break;
            case 0:
                System.out.println("Cerrando sesion...");
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void mostrarInstruccionesFlujo() {
        System.out.println("\n==================================================================");
        System.out.println("           GUIA DE USO Y FLUJO DEL SISTEMA FINANCIERO");
        System.out.println("==================================================================");
        System.out.println("1. CONFIGURACION DE CATEGORIAS (Opcion 5 y 6):");
        System.out.println("   - Crea tus Categorias (Ingreso o Gasto) y Subcategorias.");
        System.out.println("   - Anota los IDs que el sistema te muestra en pantalla.");
        System.out.println("2. PLANIFICACION FINANCIERA (Opcion 3):");
        System.out.println("   - Registra un Presupuesto indicando su nombre y periodo (Anio/Mes).");
        System.out.println("   - Gestiona sus detalles vinculando los IDs de tus subcategorias");
        System.out.println("     (o usa la carga masiva por JSON).");
        System.out.println("3. OBLIGACIONES FIJAS (Opcional - Opcion 4):");
        System.out.println("   - Registra tus gastos recurrentes mensuales.");
        System.out.println("4. OPERACIONES DIARIAS (Opcion 1):");
        System.out.println("   - Registra tus transacciones reales asociandolas a tus presupuestos.");
        System.out.println("5. ANALISIS Y REPORTES (Opcion 2):");
        System.out.println("   - Exporta los 6 reportes analiticos en formato PDF.");
        System.out.println("==================================================================\n");
    }
}