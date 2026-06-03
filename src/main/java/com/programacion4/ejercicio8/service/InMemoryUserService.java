package com.programacion4.ejercicio8.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.programacion4.ejercicio8.model.UserModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InMemoryUserService implements UserDetailsService {

    private final Map<String, UserModel> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public InMemoryUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        // create a default admin and bibliotecario for convenience
        Set<String> adminRoles = new HashSet<>(); adminRoles.add("ROLE_ADMIN");
        users.put("admin", new UserModel("admin", passwordEncoder.encode("admin"), adminRoles));
        Set<String> bibRoles = new HashSet<>(); bibRoles.add("ROLE_BIBLIOTECARIO");
        users.put("bibliotecario", new UserModel("bibliotecario", passwordEncoder.encode("bib"), bibRoles));
    }

    public void register(String username, String rawPassword) {
        if (users.containsKey(username)) throw new IllegalArgumentException("Usuario ya existe");
        Set<String> roles = new HashSet<>(); roles.add("ROLE_LECTOR");
        users.put(username, new UserModel(username, passwordEncoder.encode(rawPassword), roles));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel u = users.get(username);
        if (u == null) throw new UsernameNotFoundException("Usuario no encontrado");
        Set<GrantedAuthority> auth = new HashSet<>();
        for (String r : u.getRoles()) auth.add(new SimpleGrantedAuthority(r));
        return new User(u.getUsername(), u.getPassword(), auth);
    }

    public UserModel getUser(String username) { return users.get(username); }
}
