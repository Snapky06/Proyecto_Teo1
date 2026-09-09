package com.proyecto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Usuario {

    private Integer id;
    private String nombres;
    private String apellidos;
    private String correo;
    private Date fechaRegistro;
    private BigDecimal salarioBase;
    private Boolean activo;
    private String creadoPor;
    private String modificadoPor;
    private Timestamp creadoEn;
    private Timestamp modificadoEn;

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, String correo, Date fechaRegistro, BigDecimal salarioBase, String creadoPor) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.salarioBase = salarioBase;
        this.creadoPor = creadoPor;
    }

    // Métodos para leer y asignar valores
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public BigDecimal getSalarioBase() { return salarioBase; }
    public void setSalarioBase(BigDecimal salarioBase) { this.salarioBase = salarioBase; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getCreadoPor() { return creadoPor; }
    public void setCreadoPor(String creadoPor) { this.creadoPor = creadoPor; }

    public String getModificadoPor() { return modificadoPor; }
    public void setModificadoPor(String modificadoPor) { this.modificadoPor = modificadoPor; }

    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    public Timestamp getModificadoEn() { return modificadoEn; }
    public void setModificadoEn(Timestamp modificadoEn) { this.modificadoEn = modificadoEn; }
}