package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;

public class CajeroDAOImplementation implements ICajero {

    @Override
    public Result getAllCajeros() {
        Result result = new Result();
        try {
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.message = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result getCajeroById(int idCajero) {
        return null;
    }

    @Override
    public Result createCajero(String nombre, String ubicacion, int idBanco) {
        return null;
    }

    @Override
    public Result deleteCajero(int id) {
        return null;
    }

}
