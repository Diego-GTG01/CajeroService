/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.DTO.TarjetaDTO;
import com.risosuit.CajeroService.ML.Banco;
import com.risosuit.CajeroService.ML.Rango;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
            jdbcTemplate.execute("{CALL GetByNumTarjeta(?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setString(1, NumTarjeta);
                        callableStatement.registerOutParameter(2, OracleTypes.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        if (resultSet.next()) {
                            Tarjeta tarjeta = new Tarjeta();
                            Usuario usuario  = new Usuario();
                            Rango rango = new Rango();
                            Banco banco = new Banco();
                            tarjeta.setUsuario(usuario);
                            tarjeta.setRango(rango);
                            tarjeta.setBanco(banco);
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

    @Override
    public Result getAllById(int idUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL GetAllTarjetasByIdUsuario(?, ?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setInt(1, idUsuario);
                        callableStatement.registerOutParameter(2, OracleTypes.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        ArrayList<Tarjeta> tarjetas = new ArrayList<>();
                        while (resultSet    .next()) {
                            Tarjeta tarjeta = new Tarjeta();
                            Usuario usuario = new Usuario();
                            Rango rango = new Rango();
                            Banco banco = new Banco();
                            tarjeta.setUsuario(usuario);
                            tarjeta.setRango(rango);
                            tarjeta.setBanco(banco);
                            tarjeta.setIdTarjeta(resultSet.getInt("idTarjeta"));

                            tarjeta.getUsuario().setIdUsuario(resultSet.getInt("idUsuario"));
                            tarjeta.getRango().setIdRango(resultSet.getInt("idRango"));
                            tarjeta.getRango().setNombre(resultSet.getString("nombreRango"));
                            tarjeta.getBanco().setIdBanco(resultSet.getInt("idBanco"));
                            tarjeta.getBanco().setNombre(resultSet.getString("nombreBanco"));

                            tarjeta.setNumTarjeta(resultSet.getString("NumTarjeta"));
                            tarjeta.setPin(resultSet.getString("pin"));
                            tarjeta.setFechaVencimiento(resultSet.getDate("fechaVencimiento"));
                            tarjeta.setStatus(resultSet.getInt("STATUS"));

                            tarjetas.add(tarjeta);
                        }
                        resultSet.close();

                        result.objects = new ArrayList<>(tarjetas);
                        result.correct = true;
                        result.message = "Tarjetas encontradas exitosamente.";

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

    public Result create(TarjetaDTO tarjeta) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL AddTarjetaUnicaSP(?, ?, ?, ?, ?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setInt(1, tarjeta.getIdUsuario());
                        callableStatement.setInt(2, tarjeta.getIdRango());
                        callableStatement.setInt(3, tarjeta.getIdBanco());
                        callableStatement.setString(4, tarjeta.getPin());
                        callableStatement.registerOutParameter(5, OracleTypes.REF_CURSOR);
                        callableStatement.execute();
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                        if (resultSet.next()) {
                            Tarjeta nuevaTarjeta = new Tarjeta();
                            Usuario usuario = new Usuario();
                            Rango rango = new Rango();
                            Banco banco = new Banco();
                            nuevaTarjeta.setUsuario(usuario);
                            nuevaTarjeta.setRango(rango);
                            nuevaTarjeta.setBanco(banco);
                            nuevaTarjeta.setIdTarjeta(resultSet.getInt("idTarjeta"));
                            nuevaTarjeta.setNumTarjeta(resultSet.getString("NumTarjeta"));
                            nuevaTarjeta.setFechaVencimiento(resultSet.getDate("fechaVencimiento"));
                            nuevaTarjeta.setStatus(resultSet.getInt("STATUS_TARJETA"));
                            nuevaTarjeta.getUsuario().setIdUsuario(resultSet.getInt("idUsuario"));
                            nuevaTarjeta.getRango().setIdRango(resultSet.getInt("idRango"));
                            nuevaTarjeta.getBanco().setIdBanco(resultSet.getInt("idBanco")); 
                            
                            result.object = nuevaTarjeta;
                            result.correct = true;
                            result.message = "Tarjeta creada exitosamente.";
                        } else {
                            result.correct = false;
                            result.message = "No se pudo crear la tarjeta.";
                            result.object = null;
                        }

                        return true;
                    });

        } catch (Exception e) {
            e.printStackTrace();
            result.correct = false;
            result.message = "Error al crear la tarjeta en la base de datos: " + e.getMessage();
            result.object = null;
            result.ex = e;
        }

        return result;
    }

    @Override
    public Result delete(int idTarjeta) {
            Result result = new Result();

        try {
            int rowsAffected = jdbcTemplate.update("DELETE FROM Tarjeta WHERE idTarjeta = ?", idTarjeta);
            if (rowsAffected > 0) {
                result.correct = true;
                result.message = "Tarjeta eliminada exitosamente.";
            } else {
                result.correct = false;
                result.message = "No se encontró ninguna tarjeta con el ID proporcionado.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.correct = false;
            result.message = "Error al eliminar la tarjeta de la base de datos: " + e.getMessage();
            result.ex = e;
        }

        return result;
    }
}
