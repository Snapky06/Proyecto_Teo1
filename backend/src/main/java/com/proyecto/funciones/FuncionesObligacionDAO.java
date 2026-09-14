package com.proyecto.funciones;

import com.proyecto.BaseDAO;

public class FuncionesObligacionDAO extends BaseDAO {

    public Integer diasHastaVencimiento(int idObligacion) {
        String sql = "{ call fn_dias_hasta_vencimiento(?) }";
        return ejecutarFuncionEntera(sql, "Error al calcular los dias hasta el vencimiento", idObligacion);
    }
}