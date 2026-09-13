package com.proyecto.menus;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

import com.proyecto.cruds.TransaccionDAO;

public class TransaccionMenu {

    private final Scanner scanner;
    private final TransaccionDAO transaccionDAO;

    public TransaccionMenu(Scanner scanner) {
        this.scanner = scanner;
        transaccionDAO = new TransaccionDAO();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENU DE TRANSACCIONES ---");
            System.out.println("1. Registrar transaccion");
            System.out.println("2. Listar transacciones");
            System.out.println("3. Consultar transaccion");
            System.out.println("4. Actualizar transaccion");
            System.out.println("5. Eliminar transaccion");
            System.out.println("0. Volver");

            int opcion = MenuHelper.leerEntero(
                    scanner,
                    "Elige una opcion: "
            );

            switch (opcion) {
                case 1:
                    registrarTransaccion();
                    break;

                case 2:
                    listarTransacciones();
                    break;

                case 3:
                    consultarTransaccion();
                    break;

                case 4:
                    actualizarTransaccion();
                    break;

                case 5:
                    eliminarTransaccion();
                    break;

                case 0:
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private void registrarTransaccion() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario: "
        );

        int idPresupuesto = MenuHelper.leerEntero(
                scanner,
                "ID del presupuesto: "
        );

        short anio = MenuHelper.leerShort(
                scanner,
                "Anio presupuestal: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Mes presupuestal: "
        );

        int idSubcategoria = MenuHelper.leerEntero(
                scanner,
                "ID de la subcategoria: "
        );

        Integer idObligacion = leerIdObligacion();

        String tipo = leerTipoTransaccion();

        String descripcion = leerTexto(
                "Descripcion: "
        );

        BigDecimal monto = MenuHelper.leerDecimal(
                scanner,
                "Monto: "
        );

        Date fecha = leerFecha(
                "Fecha YYYY-MM-DD: "
        );

        String metodoPago = leerMetodoPago();

        String numeroFactura = leerTextoOpcional(
                "Numero de factura o vacio: "
        );

        String observaciones = leerTextoOpcional(
                "Observaciones o vacio: "
        );

        transaccionDAO.insertarTransaccion(
                idUsuario,
                idPresupuesto,
                anio,
                mes,
                idSubcategoria,
                idObligacion,
                tipo,
                descripcion,
                monto,
                fecha,
                metodoPago,
                numeroFactura,
                observaciones,
                "ADMIN"
        );
    }

    private void listarTransacciones() {
        int idUsuario = MenuHelper.leerEntero(
                scanner,
                "ID del usuario: "
        );

        transaccionDAO.listarTransacciones(idUsuario);
    }

    private void consultarTransaccion() {
        int idTransaccion = MenuHelper.leerEntero(
                scanner,
                "ID de la transaccion: "
        );

        transaccionDAO.consultarTransaccion(idTransaccion);
    }

    private void actualizarTransaccion() {
        int idTransaccion = MenuHelper.leerEntero(
                scanner,
                "ID de la transaccion: "
        );

        int idPresupuesto = MenuHelper.leerEntero(
                scanner,
                "Nuevo ID del presupuesto: "
        );

        short anio = MenuHelper.leerShort(
                scanner,
                "Nuevo anio presupuestal: "
        );

        short mes = MenuHelper.leerMes(
                scanner,
                "Nuevo mes presupuestal: "
        );

        int idSubcategoria = MenuHelper.leerEntero(
                scanner,
                "Nuevo ID de la subcategoria: "
        );

        Integer idObligacion = leerIdObligacion();

        String tipo = leerTipoTransaccion();

        String descripcion = leerTexto(
                "Nueva descripcion: "
        );

        BigDecimal monto = MenuHelper.leerDecimal(
                scanner,
                "Nuevo monto: "
        );

        Date fecha = leerFecha(
                "Nueva fecha YYYY-MM-DD: "
        );

        String metodoPago = leerMetodoPago();

        String numeroFactura = leerTextoOpcional(
                "Nuevo numero de factura o vacio: "
        );

        String observaciones = leerTextoOpcional(
                "Nuevas observaciones o vacio: "
        );

        transaccionDAO.actualizarTransaccion(
                idTransaccion,
                idPresupuesto,
                anio,
                mes,
                idSubcategoria,
                idObligacion,
                tipo,
                descripcion,
                monto,
                fecha,
                metodoPago,
                numeroFactura,
                observaciones,
                "ADMIN"
        );
    }

    private void eliminarTransaccion() {
        int idTransaccion = MenuHelper.leerEntero(
                scanner,
                "ID de la transaccion: "
        );

        boolean confirmar = MenuHelper.confirmar(scanner);

        if (confirmar) {
            transaccionDAO.eliminarTransaccion(idTransaccion);
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private Integer leerIdObligacion() {
        while (true) {
            System.out.print(
                    "ID de la obligacion o 0 si no aplica: "
            );

            String entrada = scanner.nextLine().trim();

            try {
                int id = Integer.parseInt(entrada);

                if (id == 0) {
                    return null;
                }

                if (id > 0) {
                    return id;
                }

                System.out.println(
                        "El ID debe ser positivo o cero."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe escribir un numero entero."
                );
            }
        }
    }

    private String leerTipoTransaccion() {
        while (true) {
            System.out.print(
                    "Tipo INGRESO, GASTO o AHORRO: "
            );

            String tipo = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            if (tipo.equals("INGRESO")
                    || tipo.equals("GASTO")
                    || tipo.equals("AHORRO")) {
                return tipo;
            }

            System.out.println(
                    "Debe escribir INGRESO, GASTO o AHORRO."
            );
        }
    }

    private String leerMetodoPago() {
        while (true) {
            System.out.print(
                    "Metodo EFECTIVO, TARJETA_DEBITO, "
                            + "TARJETA_CREDITO o TRANSFERENCIA: "
            );

            String metodo = scanner.nextLine()
                    .trim()
                    .toUpperCase();

            if (metodo.equals("EFECTIVO")
                    || metodo.equals("TARJETA_DEBITO")
                    || metodo.equals("TARJETA_CREDITO")
                    || metodo.equals("TRANSFERENCIA")) {
                return metodo;
            }

            System.out.println(
                    "Metodo de pago no valido."
            );
        }
    }

    private String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Este campo no puede quedar vacio.");
        }
    }

    private String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        String texto = scanner.nextLine().trim();

        if (texto.isEmpty()) {
            return null;
        }

        return texto;
    }

    private Date leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            try {
                return Date.valueOf(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Use el formato YYYY-MM-DD."
                );
            }
        }
    }
}