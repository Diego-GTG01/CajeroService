package com.risosuit.CajeroService.Restcontroller;

import com.risosuit.CajeroService.DAO.RangoDAOImplementation;
import com.risosuit.CajeroService.ML.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rango")
@Tag(name = "Rangos", description = "Endpoints relacionados con la gestión y administración de rangos")

public class RangoRestController {

    @Autowired
    private RangoDAOImplementation rangoDAO;

    @GetMapping
    @Operation(
        summary = "Obtener todos los rangos", 
        description = "Obtiene una lista de todos los rangos configurados en el sistema (ej. límites transaccionales, comisiones, etc.)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Lista de rangos obtenida con éxito", 
                content = @Content(
                    mediaType = "application/json", 
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = """
                            {
                                "correct": true,
                                "message": "Rangos obtenidos exitosamente",
                                "object": null,
                                "objects": [
                                    { "idRango": 1, "descripcion": "Rango Mínimo", "limiteInferior": 100, "limiteSuperior": 5000 },
                                    { "idRango": 2, "descripcion": "Rango Máximo", "limiteInferior": 5001, "limiteSuperior": 20000 }
                                ],
                                "ex": null
                            }
                            """)
                )
            ),
            @ApiResponse(
                responseCode = "204", 
                description = "No hay contenido. La consulta fue exitosa pero no existen rangos registrados en la base de datos."
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Error en la solicitud o fallo en la consulta interna de rangos", 
                content = @Content(
                    mediaType = "application/json", 
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = """
                            {
                                "correct": false,
                                "message": "Error al consultar los rangos en la base de datos",
                                "object": null,
                                "objects": null,
                                "ex": null
                            }
                            """)
                )
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Error interno del servidor al procesar la solicitud", 
                content = @Content(
                    mediaType = "application/json", 
                    schema = @Schema(implementation = Result.class),
                    examples = @ExampleObject(value = """
                            {
                                "correct": false,
                                "message": "Error de conexión con el servidor de datos",
                                "object": null,
                                "objects": null,
                                "ex": {}
                            }
                            """)
                )
            )
    })
    public ResponseEntity<?> getAll() { 
        Result result = new Result();
        try {
            result = rangoDAO.getAllRango();
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
            return ResponseEntity.internalServerError().body(result); // 500 Internal Server Error
        }
    }
}