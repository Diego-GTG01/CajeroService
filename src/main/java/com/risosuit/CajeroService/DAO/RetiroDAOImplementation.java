package com.risosuit.CajeroService.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Retiro;
import com.risosuit.CajeroService.ML.Tarjeta;

@Repository
public class RetiroDAOImplementation implements IRetiro {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result retirar(Tarjeta tarjeta, int idCajero, double monto) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL RetiroSP(?,?,?,?) }",
                    (CallableStatementCallback<Boolean>) callablestatement -> {
                        callablestatement.setInt(1, tarjeta.getIdTarjeta());
                        callablestatement.setInt(2, idCajero);
                        callablestatement.setDouble(3, monto);
                        callablestatement.registerOutParameter(4, java.sql.Types.REF_CURSOR);
                        callablestatement.execute();

                        ResultSet resultSet = (ResultSet) callablestatement.getObject(4);
                        ArrayList<Retiro> retiros = new ArrayList<>();
                        while (resultSet.next()) {
                            Retiro retiro = new Retiro();
                            retiro.setFolio(resultSet.getInt("folio"));
                            retiro.setUsuario(resultSet.getString("usuario"));
                            retiro.setNumCuenta(resultSet.getString("NUMEROCUENTA"));
                            retiro.setNumTarjeta(resultSet.getString("TARJETAUSADA"));
                            retiro.setStatus(resultSet.getString("StatusTransaccion"));
                            retiro.setCantidad(resultSet.getInt("CANTIDAD"));
                            retiro.setTipo(resultSet.getString("TIPO"));
                            retiro.setDenominacion(resultSet.getString("DENOMINACION"));
                            retiro.setSubtotal(resultSet.getDouble("SUBTOTAL"));

                            retiros.add(retiro);
                        }

                        resultSet.close();
                        result.correct = true;
                        result.objects = new ArrayList<>(retiros);
                        result.message = "Retiro exitoso";
                        return true;
                    });

        } catch (DataAccessException dae) {
            result.correct = false;
            result.ex = dae;

            String fullMessage = dae.getMessage() != null ? dae.getMessage() : "";

            if (fullMessage.contains("20001") || fullMessage.contains("20002") ||
                    fullMessage.contains("20003") || fullMessage.contains("20004") ||
                    fullMessage.contains("20099")) {

                try {
                    int oraIndex = fullMessage.indexOf("ORA-200");

                    if (oraIndex != -1) {
                        String oraLine = fullMessage.substring(oraIndex);
                        if (oraLine.contains("\n")) {
                            oraLine = oraLine.split("\n")[0];
                        }

                        String cleanMessage = oraLine.replaceAll("ORA-\\d+:\\s*(Error:\\s*|Error inesperado:\\s*)?", "")
                                .trim();

                        result.message = cleanMessage;
                    } else {
                        result.message = "Transacción rechazada: Fondos, límites o datos inválidos.";
                    }
                } catch (Exception e) {
                    result.message = "Error de validación en el cajero automático.";
                }

            } else {
                result.message = "Error crítico en la base de datos: " + fullMessage;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.ex = ex;
            result.message = "Error al realizar el retiro: " + ex.getMessage();
        }

        return result;
    }
}
