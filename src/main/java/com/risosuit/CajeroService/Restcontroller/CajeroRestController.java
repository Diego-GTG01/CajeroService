package com.risosuit.CajeroService.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risosuit.CajeroService.DAO.CajeroDAOImplementation;
import com.risosuit.CajeroService.ML.Cajero;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Tarjeta;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cajero")

@CrossOrigin(origins = "*")
public class CajeroRestController {

    @Autowired
    private CajeroDAOImplementation cajeroDAO;

    @GetMapping("getAll")
    public ResponseEntity GetAllCajeros() {
        Result result = new Result();
        try {
            result = cajeroDAO.getAllCajeros();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.correct = false;
            result.ex = e;
            result.message = e.getLocalizedMessage();
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping("add")
    public ResponseEntity addCajero(@RequestBody Cajero cajero) {
        Result result = new Result();
        try {
            result = cajeroDAO.createCajero(cajero.getUbicacion(), "");
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(result);
        }

    }

    @DeleteMapping("delete/{idCajero}")
    public ResponseEntity delete(@PathVariable("idCajero") int idCajero) {
        Result result = new Result();
        try {
            result = cajeroDAO.deleteCajero(idCajero);
            if(result.correct){
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = e.getLocalizedMessage();
            result.ex = e;
            return ResponseEntity.internalServerError().body(result);
        }
    }

}
