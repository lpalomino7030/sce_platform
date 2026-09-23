package com.sce.platform.usuarios.enums;

public enum SolicitudIntegracionEstado {
    // Esperando decisión
    PENDING,
    //Aprobada; puede crearse la relación
    APPROVED,
    //Aprobada; puede crearse la relación
    REJECTED,
    //Venció el plazo
    EXPIRED,
    //El solicitante retiró la solicitud
    CANCELED
}
