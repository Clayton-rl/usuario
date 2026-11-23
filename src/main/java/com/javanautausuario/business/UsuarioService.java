package com.javanautausuario.business;

import com.javanautausuario.business.converter.UsuarioConverter;
import com.javanautausuario.business.dtos.UsuarioDTO;
import com.javanautausuario.infrastructure.entity.Usuario;
import com.javanautausuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO dto) {
        Usuario entity = usuarioConverter.paraUsuario(dto);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(entity)
        );
    }
}
