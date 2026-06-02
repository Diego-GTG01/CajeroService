package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;

public interface ICajero {
    Result getAllCajeros();
    Result getCajeroById(int idCajero);
    Result createCajero(String ubicacion, String estado);
    Result deleteCajero(int id);

}
