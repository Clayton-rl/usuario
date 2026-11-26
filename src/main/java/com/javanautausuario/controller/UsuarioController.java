package com.javanautausuario.controller;

import com.javanautausuario.business.UsuarioService;
import com.javanautausuario.business.dtos.EnderecoDTO;
import com.javanautausuario.business.dtos.TelefoneDTO;
import com.javanautausuario.business.dtos.UsuarioDTO;
import com.javanautausuario.business.dtos.UsuarioLoginDTO;
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
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
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

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizarDadosEndereco(@RequestBody EnderecoDTO dto,
                                                              @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizarDadosEndereco(dto, id));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizarDadosTelefone(@RequestBody TelefoneDTO dto,
                                                              @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizarDadosTelefone(dto, id));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> salvarEnderco(@RequestBody EnderecoDTO dto,
                                                     @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.salvarEndereco(dto, token));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> salvarTelefone(@RequestBody TelefoneDTO dto,
                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.salvarTelefone(dto, token));
    }
}
