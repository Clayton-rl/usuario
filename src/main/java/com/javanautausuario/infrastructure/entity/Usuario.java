package com.javanautausuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Endereco> enderecos;
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Telefone> telefones;
}
