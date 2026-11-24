package com.javanautausuario.business;

import com.javanautausuario.business.converter.UsuarioConverter;
import com.javanautausuario.business.dtos.UsuarioDTO;
import com.javanautausuario.business.dtos.UsuarioLoginDTO;
import com.javanautausuario.infrastructure.entity.Usuario;
import com.javanautausuario.infrastructure.exceptions.ConflictException;
import com.javanautausuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanautausuario.infrastructure.repository.UsuarioRepository;
import com.javanautausuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvarUsuario(UsuarioDTO dto) {
        tratarEmailExiste(dto.getEmail());
        dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario entity = usuarioConverter.paraUsuario(dto);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(entity)
        );
    }

    public void tratarEmailExiste(String email) {
        try {
            boolean existe = verificarEmailExiste(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado: " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado: ", e.getCause());
        }
    }

    public Boolean verificarEmailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public String login(UsuarioLoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado: " + email)
        );
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
