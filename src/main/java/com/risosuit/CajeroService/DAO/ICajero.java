package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;

public interface ICajero {
    Result getAllCajeros();
    Result getCajeroById(int idCajero);
    Result createCajero(String nombre, String ubicacion, int idBanco);
    Result deleteCajero(int id);

}
