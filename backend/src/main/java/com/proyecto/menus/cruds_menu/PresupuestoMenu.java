package com.proyecto.menus.cruds_menu;

import com.proyecto.cruds.PresupuestoDAO;
import com.proyecto.cruds.PresupuestoDetalleDAO;
import com.proyecto.funciones.ProcedimientosNegocioDAO;
import com.proyecto.cruds.SubcategoriaDAO;
import com.proyecto.cruds.CategoriaDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class PresupuestoMenu extends MenuBase {
    private final PresupuestoDAO presupuestoDAO;
    private final PresupuestoDetalleDAO detalleDAO;
    private final ProcedimientosNegocioDAO negocioDAO;
    private final SubcategoriaDAO subcategoriaDAO;
    private final CategoriaDAO categoriaDAO;

    public PresupuestoMenu(Scanner scanner) {
        super(scanner);
        this.presupuestoDAO = new PresupuestoDAO();
        this.detalleDAO = new PresupuestoDetalleDAO();
        this.negocioDAO = new ProcedimientosNegocioDAO();
        this.subcategoriaDAO = new SubcategoriaDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE PRESUPUESTOS ---");
        System.out.println("1. Registrar presupuesto");
        System.out.println("2. Listar presupuestos");
        System.out.println("3. Consultar presupuesto");
        System.out.println("4. Actualizar presupuesto");
        System.out.println("5. Eliminar presupuesto");
        System.out.println("6. Gestionar detalles");
        System.out.println("7. Cerrar presupuesto");
        System.out.println("8. Crear presupuesto completo desde JSON");
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarPresupuesto();
                break;
            case 2:
                listarPresupuestos();
                break;
            case 3:
                consultarPresupuesto();
                break;
            case 4:
                actualizarPresupuesto();
                break;
            case 5:
                eliminarPresupuesto();
                break;
            case 6:
                menuDetalles();
                break;
            case 7:
                cerrarPresupuesto();
                break;
            case 8:
                crearPresupuestoCompleto();
                break;
            case 0:
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void registrarPresupuesto() {
        try {
            int idUsuario = obtenerIdUsuario();
            String nombre = MenuHelper.leerTexto(scanner, "Nombre del presupuesto");
            short anioInicio = MenuHelper.leerShort(scanner, "Anio de inicio");
            short mesInicio = MenuHelper.leerMes(scanner, "Mes de inicio");
            short anioFin = MenuHelper.leerShort(scanner, "Anio de fin");
            short mesFin = MenuHelper.leerMes(scanner, "Mes de fin");
            
            if (!periodoValido(anioInicio, mesInicio, anioFin, mesFin)) {
                System.out.println("El periodo final no puede ser anterior al inicial.");
                return;
            }
            
            BigDecimal ingresos = BigDecimal.ZERO;
            BigDecimal gastos = BigDecimal.ZERO;
            BigDecimal ahorro = BigDecimal.ZERO;
            
            presupuestoDAO.insertarPresupuesto(idUsuario, nombre, anioInicio, mesInicio, anioFin, mesFin, ingresos, gastos, ahorro, "ACTIVO", MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void listarPresupuestos() {
        try {
            int idUsuario = obtenerIdUsuario();
            presupuestoDAO.listarPresupuestos(idUsuario);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void consultarPresupuesto() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            presupuestoDAO.consultarPresupuesto(idPresupuesto);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarPresupuesto() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            String nombre = MenuHelper.leerTexto(scanner, "Nuevo nombre");
            short anioInicio = MenuHelper.leerShort(scanner, "Nuevo anio de inicio");
            short mesInicio = MenuHelper.leerMes(scanner, "Nuevo mes de inicio");
            short anioFin = MenuHelper.leerShort(scanner, "Nuevo anio de fin");
            short mesFin = MenuHelper.leerMes(scanner, "Nuevo mes de fin");
            
            if (!periodoValido(anioInicio, mesInicio, anioFin, mesFin)) {
                System.out.println("El periodo final no puede ser anterior al inicial.");
                return;
            }
            
            BigDecimal ingresos = BigDecimal.ZERO;
            BigDecimal gastos = BigDecimal.ZERO;
            BigDecimal ahorro = BigDecimal.ZERO;
            String estado = MenuHelper.leerEstadoPresupuesto(scanner);
            
            presupuestoDAO.actualizarPresupuesto(idPresupuesto, nombre, anioInicio, mesInicio, anioFin, mesFin, ingresos, gastos, ahorro, estado, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void eliminarPresupuesto() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            if (MenuHelper.confirmar(scanner)) {
                presupuestoDAO.eliminarPresupuesto(idPresupuesto);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void cerrarPresupuesto() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            if (MenuHelper.confirmar(scanner)) {
                negocioDAO.cerrarPresupuesto(idPresupuesto, MenuHelper.USUARIO_SISTEMA);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void crearPresupuestoCompleto() {
        try {
            int idUsuario = obtenerIdUsuario();
            String nombre = MenuHelper.leerTexto(scanner, "Nombre del presupuesto");
            short anioInicio = MenuHelper.leerShort(scanner, "Anio de inicio");
            short mesInicio = MenuHelper.leerMes(scanner, "Mes de inicio");
            short anioFin = MenuHelper.leerShort(scanner, "Anio de fin");
            short mesFin = MenuHelper.leerMes(scanner, "Mes de fin");
            
            if (!periodoValido(anioInicio, mesInicio, anioFin, mesFin)) {
                System.out.println("El periodo final no puede ser anterior al inicial.");
                return;
            }
            
            System.out.println("Escriba el JSON completo en una sola linea (o CANCELAR):");
            String json = scanner.nextLine().trim();
            if (json.equalsIgnoreCase("CANCELAR") || json.equalsIgnoreCase("X")) {
                throw new MenuHelper.OperacionCanceladaException();
            }
            if (json.isEmpty()) {
                System.out.println("El JSON no puede quedar vacio.");
                return;
            }
            
            presupuestoDAO.crearPresupuestoCompleto(idUsuario, nombre, anioInicio, mesInicio, anioFin, mesFin, json, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void menuDetalles() {
        boolean salirDetalles = false;
        while (!salirDetalles) {
            System.out.println("\n--- MENU DE DETALLES DE PRESUPUESTO ---");
            System.out.println("1. Registrar detalle");
            System.out.println("2. Listar detalles");
            System.out.println("3. Consultar detalle");
            System.out.println("4. Actualizar detalle");
            System.out.println("5. Eliminar detalle");
            System.out.println("0. Volver");
            
            try {
                int opcion = MenuHelper.leerOpcionMenu(scanner, "Elige una opcion: ");
                switch (opcion) {
                    case 1:
                        registrarDetalle();
                        break;
                    case 2:
                        listarDetalles();
                        break;
                    case 3:
                        consultarDetalle();
                        break;
                    case 4:
                        actualizarDetalle();
                        break;
                    case 5:
                        eliminarDetalle();
                        break;
                    case 0:
                        salirDetalles = true;
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (MenuHelper.OperacionCanceladaException e) {
                System.out.println("\n[!] Operacion cancelada. Saliendo del menu de detalles...\n");
                salirDetalles = true;
            }
        }
    }

    private void registrarDetalle() {
        try {
            int idUsuario = obtenerIdUsuario();
            
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            
            System.out.println("\n=================================================");
            System.out.println("          DIRECTORIO DE SUBCATEGORIAS            ");
            System.out.println("=================================================");
            Map<Integer, String> categorias = categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "INGRESO");
            categorias.putAll(categoriaDAO.obtenerCategoriasPorTipo(idUsuario, "GASTO"));
            for (Map.Entry<Integer, String> entry : categorias.entrySet()) {
                System.out.println("\n-> Categoria ID: " + entry.getKey() + " | Nombre: " + entry.getValue());
                subcategoriaDAO.listarSubcategorias(entry.getKey());
            }
            System.out.println("=================================================");
            int idSubcategoria = MenuHelper.leerEntero(scanner, "ID de la subcategoria");
            
            BigDecimal monto = MenuHelper.leerDecimal(scanner, "Monto mensual");
            String observaciones = MenuHelper.leerTextoOpcional(scanner, "Observaciones o vacio");
            
            detalleDAO.insertarDetalle(idPresupuesto, idSubcategoria, monto, observaciones, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void listarDetalles() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            detalleDAO.listarDetalles(idPresupuesto);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void consultarDetalle() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            
            System.out.println("\n=================================================");
            System.out.println("          DETALLES DEL PRESUPUESTO               ");
            System.out.println("=================================================");
            detalleDAO.listarDetalles(idPresupuesto);
            System.out.println("=================================================");
            int idDetalle = MenuHelper.leerEntero(scanner, "ID del detalle a consultar");
            
            detalleDAO.consultarDetalle(idDetalle);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarDetalle() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            
            System.out.println("\n=================================================");
            System.out.println("          DETALLES DEL PRESUPUESTO               ");
            System.out.println("=================================================");
            detalleDAO.listarDetalles(idPresupuesto);
            System.out.println("=================================================");
            int idDetalle = MenuHelper.leerEntero(scanner, "ID del detalle a actualizar");
            
            BigDecimal monto = MenuHelper.leerDecimal(scanner, "Nuevo monto mensual");
            String observaciones = MenuHelper.leerTextoOpcional(scanner, "Nuevas observaciones o vacio");
            
            detalleDAO.actualizarDetalle(idDetalle, monto, observaciones, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void eliminarDetalle() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            
            System.out.println("\n=================================================");
            System.out.println("          DETALLES DEL PRESUPUESTO               ");
            System.out.println("=================================================");
            detalleDAO.listarDetalles(idPresupuesto);
            System.out.println("=================================================");
            int idDetalle = MenuHelper.leerEntero(scanner, "ID del detalle a eliminar");
            
            if (MenuHelper.confirmar(scanner)) {
                detalleDAO.eliminarDetalle(idDetalle);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private boolean periodoValido(short anioInicio, short mesInicio, short anioFin, short mesFin) {
        int periodoInicio = (anioInicio * 12) + mesInicio;
        int periodoFin = (anioFin * 12) + mesFin;
        return periodoFin >= periodoInicio;
    }
}