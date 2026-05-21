
package com.risosuit.CajeroService.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risosuit.CajeroService.DAO.RetiroDAOImplementation;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Transaccion;


@RestController
@RequestMapping("/Cajero")
public class TransaccionRestController {

    @Autowired
    private RetiroDAOImplementation retiroDAO;

    @GetMapping
    public ResponseEntity demo() {
        Result result = new Result();
        try {
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.correct = false;
            result.ex = e;
            result.message = e.getLocalizedMessage();
            return ResponseEntity.internalServerError().body(result);
        }

    }

    @PostMapping("/transact")
    public ResponseEntity transact(@RequestBody Transaccion transaccion) {
        Result result = new Result();
        try {
            result = retiroDAO.retirar(transaccion.getTarjeta(), transaccion.getCajero().getIdCajero(), transaccion.getMonto());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.correct = false;
            result.ex = e;
            result.message = e.getLocalizedMessage();
            return ResponseEntity.internalServerError().body(result);
        }

    }

}
