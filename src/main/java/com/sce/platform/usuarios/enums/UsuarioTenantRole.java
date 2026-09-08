package com.sce.platform.usuarios.enums;

public enum UsuarioTenantRole {
 OWNER, ADMIN, USER;

 public boolean puedeCrear(UsuarioTenantRole role) {

  return switch (this) {
   case OWNER ->
           role == ADMIN || role == USER;

   case ADMIN ->
           role == USER;

   case USER ->
           false;
  };
 }
}
