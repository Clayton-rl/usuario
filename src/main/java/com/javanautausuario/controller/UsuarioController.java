package com.javanautausuario.controller;

import com.javanautausuario.business.UsuarioService;
import com.javanautausuario.business.dtos.UsuarioDTO;
import com.javanautausuario.business.dtos.UsuarioLoginDTO;
import com.javanautausuario.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioLoginDTO dto) {
        return ResponseEntity.ok(usuarioService.login(dto));
    }

    @GetMapping
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestBody UsuarioDTO dto,
                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizarDadosUsuario(dto, token));
    }
}
