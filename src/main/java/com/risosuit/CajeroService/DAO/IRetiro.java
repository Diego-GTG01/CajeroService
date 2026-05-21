package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;

public interface IRetiro {
    Result retirar(int idTarjeta, int idCajero, double monto);

}
