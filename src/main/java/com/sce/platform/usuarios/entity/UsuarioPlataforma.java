package com.sce.platform.usuarios.entity;

import com.sce.platform.usuarios.enums.PlataformRole;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usuario_plataforma")
public class UsuarioPlataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
         name = "usuario_id",
         nullable = false,
         unique = true
    )
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(
         name = "role_plataforma",
         nullable = false,
         length = 50
    )
    private PlataformRole rolePlataforma;
}