package com.proyecto.categoria;

public class Categoria {
    private Integer idCategoria;
    private Integer idUsuario;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String icono;
    private String colorHex;
    private Short ordenPresentacion;

    public Categoria() {
    }

    public Categoria(
            Integer idUsuario,
            String nombre,
            String descripcion,
            String tipo,
            String icono,
            String colorHex,
            Short ordenPresentacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.icono = icono;
        this.colorHex = colorHex;
        this.ordenPresentacion = ordenPresentacion;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public Short getOrdenPresentacion() {
        return ordenPresentacion;
    }

    public void setOrdenPresentacion(Short ordenPresentacion) {
        this.ordenPresentacion = ordenPresentacion;
    }
}