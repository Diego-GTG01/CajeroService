package com.risosuit.CajeroService.ML;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Usuario", description = "Representa la información básica de un cliente dentro del sistema bancario.")
public class Usuario {

    @Schema(description = "Identificador único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int idUsuario;

    @Schema(description = "Nombre(s) del usuario", example = "Diego")
    private String nombre;

    @Schema(description = "Apellido paterno del usuario", example = "Gómez")
    private String apellidoPaterno;

    @Schema(description = "Apellido materno del usuario", example = "Tagle")
    private String apellidoMaterno;

    @Schema(description = "Número celular del usuario", example = "5512345678")
    private String celular;

    @Schema(description = "Número telefónico fijo", example = "5556781234")
    private String telefono;

    @Schema(description = "Correo electrónico del usuario", example = "diego@gmail.com")
    private String email;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
