package com.javanautausuario.business.converter;

import com.javanautausuario.business.dtos.EnderecoDTO;
import com.javanautausuario.business.dtos.TelefoneDTO;
import com.javanautausuario.business.dtos.UsuarioDTO;
import com.javanautausuario.infrastructure.entity.Endereco;
import com.javanautausuario.infrastructure.entity.Telefone;
import com.javanautausuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(paraListaEndereco(dto.getEnderecos()))
                .telefones(paraListaTelefone(dto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> dtos) {
        return dtos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> dtos) {
        return dtos.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO dto) {
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario entity) {
        return UsuarioDTO.builder()
                .nome(entity.getNome())
                .email(entity.getEmail())
                .senha(entity.getSenha())
                .enderecos(paraListaEnderecoDTO(entity.getEnderecos()))
                .telefones(paraListaTelefoneDTO(entity.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> entity) {
        return entity.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco entity) {
        return EnderecoDTO.builder()
                .id(entity.getId())
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> entity) {
        return entity.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone entity) {
        return TelefoneDTO.builder()
                .id(entity.getId())
                .ddd(entity.getDdd())
                .numero(entity.getNumero())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO dto, Usuario entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .build();
    }

    public Endereco paraEndercoEntity(EnderecoDTO dto, Long idUsuario) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario) {
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .usuario_id(idUsuario)
                .build();
    }
}
