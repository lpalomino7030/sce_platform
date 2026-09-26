package com.sce.platform.usuarios.entity;

import com.sce.platform.usuarios.enums.PlataformRole;
import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "usuario_plataforma")
@Entity
public class UsuarioPlataforma
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "usuario_id", unique = true)
    UUID usuarioId;

    @Column(name = "role_plataforma", nullable = false)
    @Enumerated(EnumType.STRING)
    PlataformRole rolePlataforma;
}
