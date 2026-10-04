package com.sce.platform.usuarios.entity;

import com.sce.platform.usuarios.enums.PlatformRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuario_plataforma")
@Entity
public class UsuarioPlataforma
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(
            name = "usuario_id",
            nullable = false,
            unique = true
    )
    private UUID usuarioId;

    @Column(name = "role_plataforma", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlatformRole rolePlataforma;
}
