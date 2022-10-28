package com.sistema.blog.controllers;

import com.sistema.blog.Security.JWTAuthresponseDTO;
import com.sistema.blog.Security.JwtTokenProvider;
import com.sistema.blog.dtos.LoginDTO;
import com.sistema.blog.dtos.RegistroDTO;
import com.sistema.blog.models.Rol;
import com.sistema.blog.models.Usuario;
import com.sistema.blog.repositories.RolRepository;
import com.sistema.blog.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthresponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generarToken(authentication);

        return  ResponseEntity.ok( new JWTAuthresponseDTO(token));

    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario (@RequestBody RegistroDTO registroDTO){

        if(usuarioRepository.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        if(usuarioRepository.existsByEmail(registroDTO.getEmail())){
            return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
    }
}
