package com.proyecto.funciones;

import com.proyecto.BaseDAO;

public class FuncionesSubcategoriaDAO extends BaseDAO {

    public Integer obtenerCategoriaPorSubcategoria(int idSubcategoria) {
        String sql = "{ call fn_obtener_categoria_por_subcategoria(?) }";
        return ejecutarFuncionEntera(sql, "Error al obtener la categoria de la subcategoria", idSubcategoria);
    }
}