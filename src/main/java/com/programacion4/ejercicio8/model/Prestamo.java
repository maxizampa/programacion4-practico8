package com.programacion4.ejercicio8.model;

public class Prestamo {
    private Long id;
    private Long libroId;
    private String username;
    private boolean aprobado;

    public Prestamo() {}

    public Prestamo(Long id, Long libroId, String username) {
        this.id = id;
        this.libroId = libroId;
        this.username = username;
        this.aprobado = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public boolean isAprobado() { return aprobado; }
    public void setAprobado(boolean aprobado) { this.aprobado = aprobado; }
}
