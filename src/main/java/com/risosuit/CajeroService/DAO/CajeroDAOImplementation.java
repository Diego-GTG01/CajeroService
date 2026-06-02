package com.risosuit.CajeroService.DAO;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.risosuit.CajeroService.ML.Cajero;
import com.risosuit.CajeroService.ML.Result;

@Repository
public class CajeroDAOImplementation implements ICajero {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getAllCajeros() {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{ CALL GetAllCajerosSp(?) }", (CallableStatementCallback<Boolean>) callablestatement -> {
                callablestatement.registerOutParameter(1, Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet rs = (ResultSet) callablestatement.getObject(1);
                List<Cajero> cajeros = new ArrayList<>();
                Cajero cajeroActual;
                while (rs.next()) {
                    cajeroActual = new Cajero();
                    cajeroActual.setIdCajero(rs.getInt("IDCAJERO"));
                    cajeroActual.setUbicacion(rs.getString("UBICACION"));
                    cajeroActual.setTotal(rs.getDouble("TOTAL"));
                    cajeroActual.setEstado(rs.getString("ESTADO"));
                    cajeros.add(cajeroActual);

                }
                result.objects = new ArrayList<>(cajeros);
                return null;
            });

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
    public Result createCajero(String ubicacion, String estado) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL AddCajeroSP(?,?)}", (CallableStatementCallback<Boolean>) callablestatement -> {
                callablestatement.setString(1, ubicacion);
                callablestatement.setString(2, "Activo");

                if (callablestatement.executeUpdate() > 0) {
                    result.correct = true;
                    result.message = "Creacion exitosa";
                } else {
                    result.correct = false;
                    result.message = "Algo salió mal";
                }

                return true;
            });
            return result;

        } catch (Exception e) {
            result.correct = false;
            result.message = e.getLocalizedMessage();
            result.ex = e;
            return result;
        }
    }

    @Override
    public Result deleteCajero(int id) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL deleteCajeroSP(?)}", (CallableStatementCallback<Boolean>) callablestatement -> {
                callablestatement.setInt(1, id);
                if (callablestatement.executeUpdate() > 0) {
                    result.correct = true;
                    result.message = "Cajero Eliminado";
                }

                return true;
            });
            return result;

        } catch (Exception e) {
            result.correct = false;
            result.message = e.getLocalizedMessage();
            result.ex = e;
            return result;
        }
    }

}
