package com.risosuit.CajeroService.DAO;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.risosuit.CajeroService.ML.Result;
@Repository
public class RetiroDAOImplementation implements IRetiro {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result retirar(int idTarjeta, int idCajero, double monto) {
        Result result = new Result();
        try {

            jdbcTemplate.execute("{CALL RetiroSP(?,?,?,?) }",
                    (CallableStatementCallback<Boolean>) callablestatement -> {
                        callablestatement.setInt(1, idTarjeta);
                        callablestatement.setInt(2, idCajero);
                        callablestatement.setDouble(3, monto);
                        callablestatement.registerOutParameter(4, java.sql.Types.REF_CURSOR);
                        callablestatement.execute();
                        ResultSet resultSet = (ResultSet) callablestatement.getObject(4);
                        while (resultSet.next()) {
                            int totalColumnas = resultSet.getMetaData().getColumnCount();

                            for (int i = 1; i <= totalColumnas; i++) {
                                System.out.print(resultSet.getString(i) + " | ");
                            }
                            System.out.println();

                        }

                        resultSet.close();
                        result.correct = true;

                        result.message = "Retiro exitoso";
                        return true;
                    });

        } catch (Exception ex) {
            result.correct = false;
            result.ex = ex;
            result.message = "Error al realizar el retiro: " + ex.getMessage();
        }
        return result;
    }

}
