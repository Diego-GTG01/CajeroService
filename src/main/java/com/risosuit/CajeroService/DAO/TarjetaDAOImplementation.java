/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Tarjeta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TarjetaDAOImplementation implements ITarjeta {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result findByNumTarjeta(String NumTarjeta) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL GetByNumTarjeta(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, NumTarjeta);
                callableStatement.registerOutParameter(2, OracleTypes.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                if (resultSet.next()) {
                    Tarjeta tarjeta = new Tarjeta();
                    tarjeta.setIdTarjeta(resultSet.getInt("idTarjeta"));

                    tarjeta.getUsuario().setIdUsuario(resultSet.getInt("idUsuario"));
                    tarjeta.getRango().setIdRango(resultSet.getInt("idRango"));
                    tarjeta.getBanco().setIdBanco(resultSet.getInt("idBanco"));

                    tarjeta.setNumTarjeta(resultSet.getString("NumTarjeta"));
                    tarjeta.setPin(resultSet.getString("pin"));
                    tarjeta.setFechaVencimiento(resultSet.getDate("fechaVencimiento"));
                    tarjeta.setStatus(resultSet.getInt("STATUS"));

                    result.object = tarjeta;
                    result.correct = true;
                    result.message = "Tarjeta encontrada exitosamente.";
                } else {
                    result.correct = false;
                    result.message = "No se encontró ninguna tarjeta con el número proporcionado.";
                    result.object = null;
                }

                return true;
            });

        } catch (Exception e) {
            e.printStackTrace();
            result.correct = false;
            result.message = "Error al buscar la tarjeta en la base de datos: " + e.getMessage();
            result.object = null;
            result.ex = e;
        }

        return result;
    }
}
