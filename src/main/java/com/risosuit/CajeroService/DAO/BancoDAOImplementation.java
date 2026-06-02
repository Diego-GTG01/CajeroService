package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.ML.Banco;
import com.risosuit.CajeroService.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class BancoDAOImplementation implements IBanco {

    @Autowired
    private JdbcTemplate jdbctemplate;

    @Override
    public Result getAllBanco() {
        Result result = new Result();
        try {
            jdbctemplate.execute("{CALL GetAllBancoSP(?)}", (CallableStatementCallback<Boolean>) callablestatement -> {

                callablestatement.registerOutParameter(1, Types.REF_CURSOR);
                callablestatement.execute();
                ResultSet resultset = (ResultSet) callablestatement.getObject(1);
                ArrayList<Banco> bancos = new ArrayList<>();

                while (resultset.next()) {
                    Banco banco = new Banco();
                    banco.setIdBanco(resultset.getInt("idBanco"));
                    banco.setNombre(resultset.getString("nombre"));
                    bancos.add(banco);
                }
                if (bancos.isEmpty()) {
                    result.correct = false;
                    result.message = "No hay Bancos";
                } else {
                    result.correct = true;
                    result.message = "Bancos Obtenidos";
                    result.objects = new ArrayList(bancos);
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
