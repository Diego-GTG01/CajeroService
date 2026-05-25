package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.DTO.UsuarioCompletoDTO;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Usuario;
import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Cuenta;

public interface IUsuario {
    Result<UsuarioCompletoDTO> getUsuarioByTarjeta(String NumTarjeta);
    Result<UsuarioCompletoDTO> getUsuarioById(int id);
    Result<Usuario> getUsuarioByEmail(String email);
    Result<Usuario> createUsuario(String email, String password, String NumTarjeta);
    Result<Tarjeta> getTarjetaByNumero(String numTarjeta);
    Result<Cuenta> getCuentaByUsuarioId(int idUsuario);
}