
package com.risosuit.CajeroService.Restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risosuit.CajeroService.ML.Result;

@RestController
@RequestMapping("/transact")
public class TransaccionRestController {

    @GetMapping
    public ResponseEntity demo() {
        Result result = new Result();
        try {
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.correct = false;
            result.ex = e;
            result.message= e.getLocalizedMessage();
            return ResponseEntity.internalServerError().body(result);
        }

    }

}
