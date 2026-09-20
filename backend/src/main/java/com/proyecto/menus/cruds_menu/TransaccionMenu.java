package com.proyecto.menus.cruds_menu;

import com.proyecto.cruds.TransaccionDAO;
import com.proyecto.funciones.FuncionesPresupuestoDAO;
import com.proyecto.funciones.ProcedimientosNegocioDAO;
import com.proyecto.cruds.PresupuestoDAO;
import com.proyecto.cruds.SubcategoriaDAO;
import com.proyecto.cruds.CategoriaDAO;
import com.proyecto.menus.MenuBase;
import com.proyecto.menus.MenuHelper;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;
import java.util.Scanner;

public class TransaccionMenu extends MenuBase {
    private final TransaccionDAO transaccionDAO;
    private final ProcedimientosNegocioDAO negocioDAO;
    private final FuncionesPresupuestoDAO presupuestoFuncionesDAO;
    private final PresupuestoDAO presupuestoDAO;
    private final SubcategoriaDAO subcategoriaDAO;
    private final CategoriaDAO categoriaDAO;

    public TransaccionMenu(Scanner scanner) {
        super(scanner);
        this.transaccionDAO = new TransaccionDAO();
        this.negocioDAO = new ProcedimientosNegocioDAO();
        this.presupuestoFuncionesDAO = new FuncionesPresupuestoDAO();
        this.presupuestoDAO = new PresupuestoDAO();
        this.subcategoriaDAO = new SubcategoriaDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENU DE TRANSACCIONES ---");
        System.out.println("1. Registrar transaccion");
        System.out.println("2. Registrar transaccion completa");
        System.out.println("3. Listar transacciones");
        System.out.println("4. Consultar transaccion");
        System.out.println("5. Actualizar transaccion");
        System.out.println("6. Eliminar transaccion");
        System.out.println("0. Volver");
    }

    @Override
    protected boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarTransaccion();
                break;
            case 2:
                registrarTransaccionCompleta();
                break;
            case 3:
                listarTransacciones();
                break;
            case 4:
                consultarTransaccion();
                break;
            case 5:
                actualizarTransaccion();
                break;
            case 6:
                eliminarTransaccion();
                break;
            case 0:
                return true;
            default:
                System.out.println("Opcion no valida.");
        }
        return false;
    }

    private void registrarTransaccion() {
        try {
            int idUsuario = obtenerIdUsuario();

            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            
            short anio = MenuHelper.leerShort(scanner, "Anio presupuestal");
            short mes = MenuHelper.leerMes(scanner, "Mes presupuestal");

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

            Integer idObligacion = leerIdObligacion();
            String tipo = MenuHelper.leerTipoTransaccion(scanner);
            String descripcion = MenuHelper.leerTexto(scanner, "Descripcion");
            BigDecimal monto = MenuHelper.leerDecimal(scanner, "Monto");
            Date fecha = MenuHelper.leerFecha(scanner, "Fecha YYYY-MM-DD");

            Boolean vigente = presupuestoFuncionesDAO.validarVigenciaPresupuesto(fecha, idPresupuesto);
            if (vigente != null && !vigente) {
                System.out.println("\n[ADVERTENCIA] La fecha de esta transaccion esta fuera de la vigencia del presupuesto.");
                System.out.println("El sistema permite registrarla, pero se sugiere revisar los datos.\n");
            }

            String metodoPago = MenuHelper.leerMetodoPago(scanner);
            String numeroFactura = MenuHelper.leerTextoOpcional(scanner, "Numero de factura o vacio");
            String observaciones = MenuHelper.leerTextoOpcional(scanner, "Observaciones o vacio");

            transaccionDAO.insertarTransaccion(idUsuario, idPresupuesto, anio, mes, idSubcategoria, idObligacion, tipo, descripcion, monto, fecha, metodoPago, numeroFactura, observaciones, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void registrarTransaccionCompleta() {
        try {
            int idUsuario = obtenerIdUsuario();

            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "ID del presupuesto");
            
            short anio = MenuHelper.leerShort(scanner, "Anio presupuestal");
            short mes = MenuHelper.leerMes(scanner, "Mes presupuestal");

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

            String tipo = MenuHelper.leerTipoTransaccion(scanner);
            String descripcion = MenuHelper.leerTexto(scanner, "Descripcion");
            BigDecimal monto = MenuHelper.leerDecimal(scanner, "Monto");
            Date fecha = MenuHelper.leerFecha(scanner, "Fecha YYYY-MM-DD");

            Boolean vigente = presupuestoFuncionesDAO.validarVigenciaPresupuesto(fecha, idPresupuesto);
            if (vigente != null && !vigente) {
                System.out.println("\n[ADVERTENCIA] La fecha de esta transaccion esta fuera de la vigencia del presupuesto.");
                System.out.println("El sistema permite registrarla, pero se sugiere revisar los datos.\n");
            }

            String metodoPago = MenuHelper.leerMetodoPago(scanner);
            negocioDAO.registrarTransaccionCompleta(idUsuario, idPresupuesto, anio, mes, idSubcategoria, tipo, descripcion, monto, fecha, metodoPago, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void listarTransacciones() {
        try {
            int idUsuario = obtenerIdUsuario();
            transaccionDAO.listarTransacciones(idUsuario);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void consultarTransaccion() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE TRANSACCIONES DISPONIBLES     ");
            System.out.println("=================================================");
            transaccionDAO.listarTransacciones(idUsuario);
            System.out.println("=================================================");
            
            int idTransaccion = MenuHelper.leerEntero(scanner, "ID de la transaccion");
            transaccionDAO.consultarTransaccion(idTransaccion);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void actualizarTransaccion() {
        try {
            int idUsuario = obtenerIdUsuario();

            System.out.println("\n=================================================");
            System.out.println("          LISTA DE TRANSACCIONES DISPONIBLES     ");
            System.out.println("=================================================");
            transaccionDAO.listarTransacciones(idUsuario);
            System.out.println("=================================================");
            int idTransaccion = MenuHelper.leerEntero(scanner, "ID de la transaccion a actualizar");

            System.out.println("\n=================================================");
            System.out.println("          LISTA DE PRESUPUESTOS DISPONIBLES      ");
            System.out.println("=================================================");
            presupuestoDAO.listarPresupuestos(idUsuario);
            System.out.println("=================================================");
            int idPresupuesto = MenuHelper.leerEntero(scanner, "Nuevo ID del presupuesto");
            
            short anio = MenuHelper.leerShort(scanner, "Nuevo anio presupuestal");
            short mes = MenuHelper.leerMes(scanner, "Nuevo mes presupuestal");

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
            int idSubcategoria = MenuHelper.leerEntero(scanner, "Nuevo ID de la subcategoria");

            Integer idObligacion = leerIdObligacion();
            String tipo = MenuHelper.leerTipoTransaccion(scanner);
            String descripcion = MenuHelper.leerTexto(scanner, "Nueva descripcion");
            BigDecimal monto = MenuHelper.leerDecimal(scanner, "Nuevo monto");
            Date fecha = MenuHelper.leerFecha(scanner, "Nueva fecha YYYY-MM-DD");

            Boolean vigente = presupuestoFuncionesDAO.validarVigenciaPresupuesto(fecha, idPresupuesto);
            if (vigente != null && !vigente) {
                System.out.println("\n[ADVERTENCIA] La fecha de esta transaccion esta fuera de la vigencia del presupuesto.\n");
            }

            String metodoPago = MenuHelper.leerMetodoPago(scanner);
            String numeroFactura = MenuHelper.leerTextoOpcional(scanner, "Nuevo numero de factura o vacio");
            String observaciones = MenuHelper.leerTextoOpcional(scanner, "Nuevas observaciones o vacio");

            transaccionDAO.actualizarTransaccion(idTransaccion, idPresupuesto, anio, mes, idSubcategoria, idObligacion, tipo, descripcion, monto, fecha, metodoPago, numeroFactura, observaciones, MenuHelper.USUARIO_SISTEMA);
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private void eliminarTransaccion() {
        try {
            int idUsuario = obtenerIdUsuario();
            System.out.println("\n=================================================");
            System.out.println("          LISTA DE TRANSACCIONES DISPONIBLES     ");
            System.out.println("=================================================");
            transaccionDAO.listarTransacciones(idUsuario);
            System.out.println("=================================================");
            
            int idTransaccion = MenuHelper.leerEntero(scanner, "ID de la transaccion a eliminar");
            if (MenuHelper.confirmar(scanner)) {
                transaccionDAO.eliminarTransaccion(idTransaccion);
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (MenuHelper.OperacionCanceladaException e) {
            System.out.println("\n[!] Operacion cancelada por el usuario.\n");
        }
    }

    private Integer leerIdObligacion() {
        while (true) {
            System.out.print("ID de la obligacion o 0 si no aplica (CANCELAR para salir): ");
            String entrada = scanner.nextLine().trim();
            if (entrada.equalsIgnoreCase("CANCELAR") || entrada.equalsIgnoreCase("X")) {
                throw new MenuHelper.OperacionCanceladaException();
            }
            try {
                int id = Integer.parseInt(entrada);
                if (id == 0) {
                    return null;
                }
                if (id > 0) {
                    return id;
                }
                System.out.println("El ID debe ser positivo o cero.");
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero entero.");
            }
        }
    }
}