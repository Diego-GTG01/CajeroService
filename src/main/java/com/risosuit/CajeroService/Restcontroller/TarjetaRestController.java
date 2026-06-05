
package com.risosuit.CajeroService.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.risosuit.CajeroService.DAO.TarjetaDAOImplementation;
import com.risosuit.CajeroService.DTO.TarjetaDTO;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Tarjeta;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("tarjeta")
@CrossOrigin(origins = "*")
public class TarjetaRestController {

    @Autowired
    private TarjetaDAOImplementation tarjetaDAO;

    @GetMapping
    public ResponseEntity getAllById(@RequestParam("idUsuario") int idUsuario) {
        try {
            Result<Tarjeta> result = new Result<>();
            result = tarjetaDAO.getAllById(idUsuario);
            if (result.correct) {

                if (result.objects.isEmpty()) {
                    result.correct = false;
                    result.message = "No se encontró ninguna tarjeta con el número proporcionado.";
                    result.object = null;
                    return ResponseEntity.status(204).body(result);
                }
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while fetching tarjeta");
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TarjetaDTO tarjeta) {
        System.out.println("LOG IDBANCO RECIBIDA: " + tarjeta.getIdBanco());
        System.out.println("LOG IDUSUARIO RECIBIDA: " + tarjeta.getIdUsuario());
        System.out.println("LOG IDRANGO RECIBIDA: " + tarjeta.getIdRango());
        System.out.println("LOG PIN RECIBIDA: " + tarjeta.getPin());
        try {
            Result result = new Result();
            result = tarjetaDAO.create(tarjeta);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(400).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while creating tarjeta");
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam("idTarjeta") int idTarjeta) {
        try {
            Result result = new Result();
            result = tarjetaDAO.delete(idTarjeta);
            if (result.correct) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(400).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while deleting tarjeta");
        }
    }

}
