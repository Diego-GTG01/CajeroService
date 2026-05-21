package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;

public interface IUsuario {
    Result getUsuarioByTarjeta(String NumTarjeta);
    Result getUsuarioById(int id);
    Result getUsuarioByEmail(String email);
    Result createUsuario(String email, String password, String NumTarjeta);
    

}
