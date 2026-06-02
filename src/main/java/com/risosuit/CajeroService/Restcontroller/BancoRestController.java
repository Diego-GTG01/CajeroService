package com.risosuit.CajeroService.Restcontroller;

import com.risosuit.CajeroService.DAO.BancoDAOImplementation;
import com.risosuit.CajeroService.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("banco")
public class BancoRestController {

    @Autowired
    private BancoDAOImplementation bancoDAO;

    @GetMapping
    public ResponseEntity getAll() {
        Result result = new Result();
        try {
            result = bancoDAO.getAllBanco();
            if (result.correct) {
                if (result.objects == null || result.objects.isEmpty()) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(result);
            } else {
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
