package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Tarjeta;

public interface IRetiro {
    Result retirar(Tarjeta tarjeta, int idCajero, double monto);

}
