package com.risosuit.CajeroService.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risosuit.CajeroService.DAO.CajeroDAOImplementation;
import com.risosuit.CajeroService.ML.Result;

@RestController
@RequestMapping("/cajero")

@CrossOrigin(origins = "*")
public class CajeroRestController {

    @Autowired
    private CajeroDAOImplementation cajeroDAO;

    
    @GetMapping("getAll")
    public ResponseEntity GetAllCajeros(){
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

    

}
