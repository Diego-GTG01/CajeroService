
package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.DTO.TarjetaDTO;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Tarjeta;

public interface ITarjeta {
    Result findByNumTarjeta(String NumTarjeta);

    Result getAllById(int idUsuario);

    Result create(TarjetaDTO tarjeta);

    Result delete(int idTarjeta);

}
