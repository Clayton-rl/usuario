package com.javanautausuario.business;

import com.javanautausuario.business.converter.UsuarioConverter;
import com.javanautausuario.business.dtos.EnderecoDTO;
import com.javanautausuario.business.dtos.TelefoneDTO;
import com.javanautausuario.business.dtos.UsuarioDTO;
import com.javanautausuario.business.dtos.UsuarioLoginDTO;
import com.javanautausuario.infrastructure.entity.Endereco;
import com.javanautausuario.infrastructure.entity.Telefone;
import com.javanautausuario.infrastructure.entity.Usuario;
import com.javanautausuario.infrastructure.exceptions.ConflictException;
import com.javanautausuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanautausuario.infrastructure.repository.EnderecoRepository;
import com.javanautausuario.infrastructure.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado: " + email)
            ));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado: ", e.getCause());
        }
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(UsuarioDTO dto, String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
        Usuario entity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado:" + email)
        );
        entity = usuarioConverter.updateUsuario(dto, entity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(entity));
    }

    public EnderecoDTO atualizarDadosEndereco(EnderecoDTO dto, Long idEndereco) {
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado: " + idEndereco)
        );
        entity = usuarioConverter.updateEndereco(dto, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(entity));
    }

    public TelefoneDTO atualizarDadosTelefone(TelefoneDTO dto, Long idTelefone) {
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado: " + idTelefone)
        );
        entity = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(entity));
    }

    public EnderecoDTO salvarEndereco(EnderecoDTO dto, String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado: " + email)
        );
        Endereco entity = usuarioConverter.paraEndercoEntity(dto, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(entity));
    }

    public TelefoneDTO salvarTelefone(TelefoneDTO dto, String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado: " + email)
        );
        Telefone entity = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(entity));
    }
}
