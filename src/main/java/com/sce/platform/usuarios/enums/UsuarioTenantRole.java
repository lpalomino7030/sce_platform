package com.sce.platform.usuarios.enums;

public enum UsuarioTenantRole {
 OWNER, ADMIN, USER;

 public boolean puedeCrear(UsuarioTenantRole role) {

  return switch (role) {
   case OWNER ->
           this == ADMIN || this == USER;

   case ADMIN ->
           this == USER;

   case USER ->
           false;
  };
 }
}
