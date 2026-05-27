
package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;

public interface ITarjeta {
    Result findByNumTarjeta(String NumTarjeta);
    
}
