package com.risosuit.CajeroService.Restcontroller;

import com.risosuit.CajeroService.ML.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController {
    
    public ResponseEntity auth(){
        Result result = new Result();
        try {
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
