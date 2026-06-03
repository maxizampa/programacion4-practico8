package com.programacion4.ejercicio8.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.programacion4.ejercicio8.model.Libro;
import com.programacion4.ejercicio8.model.Prestamo;

@Service
public class LibraryService {
    private final Map<Long, Libro> libros = new ConcurrentHashMap<>();
    private final Map<Long, Prestamo> prestamos = new ConcurrentHashMap<>();
    private final AtomicLong libroId = new AtomicLong(1);
    private final AtomicLong prestamoId = new AtomicLong(1);

    public List<Libro> getLibros() { return new ArrayList<>(libros.values()); }
    public Libro createLibro(Libro l) {
        Long id = libroId.getAndIncrement(); l.setId(id); libros.put(id, l); return l;
    }
    public void deleteLibro(Long id) { libros.remove(id); }

    public Prestamo solicitarPrestamo(Long libroId, String username) {
        Long id = prestamoId.getAndIncrement();
        Prestamo p = new Prestamo(id, libroId, username);
        prestamos.put(id, p); return p;
    }

    public List<Prestamo> getPrestamosByUser(String username) {
        List<Prestamo> r = new ArrayList<>();
        for (Prestamo p : prestamos.values()) if (p.getUsername().equals(username)) r.add(p);
        return r;
    }

    public List<Prestamo> getAllPrestamos() { return new ArrayList<>(prestamos.values()); }

    public Prestamo approvePrestamo(Long id) {
        Prestamo p = prestamos.get(id); if (p == null) throw new IllegalArgumentException("Prestamo no encontrado");
        p.setAprobado(true); return p;
    }
}
