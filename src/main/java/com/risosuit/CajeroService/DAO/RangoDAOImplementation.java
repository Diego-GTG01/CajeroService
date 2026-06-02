package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Rango;
import com.risosuit.CajeroService.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RangoDAOImplementation implements IRango {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getAllRango() {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL GetAllRangoSP(?)}", (CallableStatementCallback<Boolean>) callablestatement -> {
                callablestatement.registerOutParameter(1, Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet resultset = (ResultSet) callablestatement.getObject(1);
                ArrayList<Rango> rangos = new ArrayList<>();
                while (resultset.next()) {
                    Rango rango = new Rango();
                    rango.setIdRango(resultset.getInt("idrango"));
                    rango.setNombre(resultset.getString("nombre"));
                    rango.setMaxRetiro(resultset.getInt("MAXRETIRO"));
                    rango.setMinRetiro(resultset.getInt("MINRETIRO"));
                    rangos.add(rango);

                }
                if (rangos.isEmpty()) {
                    result.correct = false;
                    result.message = "No hay Bancos";
                } else {
                    result.correct = true;
                    result.message = "Bancos Obtenidos";
                    result.objects = new ArrayList(rangos);
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
