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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cajero")
@CrossOrigin(origins = "*")
@Tag(name = "Cajero", description = """
        Endpoints para la administración de cajeros automáticos.

        Funcionalidades:
        - Consultar cajeros registrados.
        - Registrar nuevos cajeros.
        - Eliminar cajeros existentes.

        Los cajeros son utilizados para realizar operaciones de retiro
        y control de disponibilidad de efectivo.
        """)
public class CajeroRestController {

    @Autowired
    private CajeroDAOImplementation cajeroDAO;

    @GetMapping("getAll")
    @Operation(summary = "Obtener todos los cajeros", description = """
            Recupera la lista completa de cajeros registrados en el sistema.

            Devuelve información como:
            - Identificador del cajero.
            - Ubicación.
            - Estado del cajero.
            """)
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
                    {
                      "correct": true,
                      "message": null,
                      "objects": [
                        {
                          "idCajero": 1,
                          "ubicacion": "Sucursal Centro"
                        },
                        {
                          "idCajero": 2,
                          "ubicacion": "Sucursal Norte"
                        }
                      ]
                    }
                    """))),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": false,
                      "message": "Error al obtener cajeros"
                    }
                    """)))
    })

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
    @Operation(summary = "Registrar un nuevo cajero", description = """
            Registra un nuevo cajero automático en el sistema.

            Se requiere únicamente la ubicación del cajero.
            """)
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Cajero registrado correctamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": true,
                      "message": "Cajero registrado correctamente"
                    }
                    """))),

            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": false,
                      "message": "La ubicación es requerida"
                    }
                    """))),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Información del cajero a registrar", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
            {
              "ubicacion": "Sucursal Centro"
            }
            """)))

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
    @Operation(summary = "Eliminar cajero", description = """
            Elimina un cajero del sistema mediante su identificador.

            El cajero debe existir previamente para poder ser eliminado.
            """)
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Cajero eliminado correctamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": true,
                      "message": "Cajero eliminado correctamente"
                    }
                    """))),

            @ApiResponse(responseCode = "400", description = "No fue posible eliminar el cajero", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": false,
                      "message": "El cajero no existe"
                    }
                    """))),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity delete(
            @Parameter(description = "Identificador único del cajero", required = true, example = "1") @PathVariable("idCajero") int idCajero) {
        Result result = new Result();
        try {
            result = cajeroDAO.deleteCajero(idCajero);
            if (result.correct) {
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
