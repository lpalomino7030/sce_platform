package com.sce.platform.usuarios.entity;


import com.sce.platform.usuarios.enums.UsuarioEstado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "codigo_sce",
            nullable = false,
            unique = true,
            length = 10
    )
    private String codigoSce;

    @Column(name = "nombre_usuario", unique = true,nullable = false, length = 50)
    private String nombreUsuario;

    @Column(name = "correo", unique = true,nullable = false, length = 150)
    private String correo;

    @Column(name = "password_hash",nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private UsuarioEstado estado;

    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;
}
