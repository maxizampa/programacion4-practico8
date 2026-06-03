package com.programacion4.ejercicio8.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programacion4.ejercicio8.model.Libro;
import com.programacion4.ejercicio8.service.LibraryService;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibraryService libraryService;

    public LibroController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(libraryService.getLibros());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        Libro l = libraryService.createLibro(libro);
        return ResponseEntity.created(URI.create("/api/libros/" + l.getId())).body(l);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libraryService.deleteLibro(id);
        return ResponseEntity.ok().build();
    }
}
