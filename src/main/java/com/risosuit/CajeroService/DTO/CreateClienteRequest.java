package com.risosuit.CajeroService.DTO;

import com.risosuit.CajeroService.ML.Cuenta;
import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateClienteRequest", description = """
        Objeto utilizado para registrar un nuevo cliente en el sistema.

        Incluye:
        - Información personal del usuario.
        - Información de la cuenta bancaria.
        - Información de la tarjeta asociada.
        """)
public class CreateClienteRequest {

    @Schema(description = "Datos personales del cliente", requiredMode = Schema.RequiredMode.REQUIRED)
    private Usuario usuario;

    @Schema(description = "Información de la cuenta bancaria del cliente", requiredMode = Schema.RequiredMode.REQUIRED)
    private Cuenta cuenta;

    @Schema(description = "Información de la tarjeta asociada al cliente", requiredMode = Schema.RequiredMode.REQUIRED)
    private Tarjeta tarjeta;

    public CreateClienteRequest() {
    }

    public CreateClienteRequest(Usuario usuario, Cuenta cuenta, Tarjeta tarjeta) {
        this.usuario = usuario;
        this.cuenta = cuenta;
        this.tarjeta = tarjeta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
}
