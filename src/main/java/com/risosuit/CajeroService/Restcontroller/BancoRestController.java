package com.risosuit.CajeroService.Restcontroller;

import com.risosuit.CajeroService.DAO.BancoDAOImplementation;
import com.risosuit.CajeroService.ML.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("banco")
@Tag(name = "Banco", description = "Endpoints relacionados con la gestión y administración de bancos")
public class BancoRestController {

    @Autowired
    private BancoDAOImplementation bancoDAO;

    @GetMapping
    @Operation(summary = "Obtener todos los bancos", description = "Obtiene una lista de todos los bancos registrados y disponibles en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de bancos obtenida con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
                    {
                        "correct": true,
                        "message": "Bancos obtenidos exitosamente",
                        "object": null,
                        "objects": [
                            { "idBanco": 1, "nombre": "Banco Central" },
                            { "idBanco": 2, "nombre": "Banco del Norte" }
                        ],
                        "ex": null
                    }
                    """))),
            @ApiResponse(responseCode = "204", description = "No hay contenido. La consulta fue exitosa pero no existen bancos registrados en la base de datos."),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud o fallo en la consulta interna", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
                    {
                        "correct": false,
                        "message": "Error al consultar la base de datos",
                        "object": null,
                        "objects": null,
                        "ex": null
                    }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al procesar la solicitud", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
                    {
                        "correct": false,
                        "message": "Formato de cadena de entrada no válido",
                        "object": null,
                        "objects": null,
                        "ex": {}
                    }
                    """)))
    })
    public ResponseEntity<?> getAll() {
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
            return ResponseEntity.internalServerError().body(result); // Devuelve 500 Internal Server Error
        }
    }
}