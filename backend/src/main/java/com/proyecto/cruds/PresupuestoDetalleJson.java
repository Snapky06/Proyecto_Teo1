package com.proyecto.cruds;

import java.math.BigDecimal;

public class PresupuestoDetalleJson {

    private int id_subcategoria;
    private BigDecimal monto_mensual;

    public int getId_subcategoria() {
        return id_subcategoria;
    }

    public BigDecimal getMonto_mensual() {
        return monto_mensual;
    }
}