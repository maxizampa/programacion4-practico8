package com.programacion4.ejercicio8.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion4.ejercicio8.model.Prestamo;
import com.programacion4.ejercicio8.service.LibraryService;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final LibraryService libraryService;

    public PrestamoController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('LECTOR')")
    public ResponseEntity<Prestamo> solicitar(@RequestBody Long libroId, Authentication auth) {
        return ResponseEntity.status(201).body(libraryService.solicitarPrestamo(libroId, auth.getName()));
    }

    @GetMapping("/mis-prestamos")
    @PreAuthorize("hasRole('LECTOR')")
    public ResponseEntity<List<Prestamo>> misPrestamos(Authentication auth) {
        return ResponseEntity.ok(libraryService.getPrestamosByUser(auth.getName()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    public ResponseEntity<List<Prestamo>> todos() {
        return ResponseEntity.ok(libraryService.getAllPrestamos());
    }

    @PutMapping("/{id}/aprobar")
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    public ResponseEntity<Prestamo> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(libraryService.approvePrestamo(id));
    }
}
