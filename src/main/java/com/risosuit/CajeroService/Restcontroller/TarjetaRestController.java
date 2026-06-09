
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("tarjeta")
@CrossOrigin(origins = "*")
@Tag(name = "Tarjetas", description = """
        Endpoints para la administración de tarjetas bancarias.

        Funcionalidades:
        - Consulta de tarjetas por usuario.
        - Registro de nuevas tarjetas.
        - Eliminación de tarjetas existentes.
        """)
@SecurityRequirement(name = "BearerAuth")
public class TarjetaRestController {

    @Autowired
    private TarjetaDAOImplementation tarjetaDAO;

    @GetMapping
    @Operation(summary = "Consultar tarjetas por usuario", description = """
            Obtiene todas las tarjetas asociadas a un usuario.

            Devuelve la información básica de cada tarjeta registrada
            para el usuario indicado.
            """)
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Tarjetas encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
                    {
                      "correct": true,
                      "message": null,
                      "objects": [
                        {
                          "idTarjeta": 1,
                          "numTarjeta": "51617216831767158553",
                          "fechaVencimiento": "2029-12-31",
                          "estatus": true
                        },
                        {
                          "idTarjeta": 2,
                          "numTarjeta": "51617216831767158554",
                          "fechaVencimiento": "2029-12-31",
                          "estatus": true
                        }
                      ]
                    }
                    """))),

            @ApiResponse(responseCode = "204", description = "El usuario no tiene tarjetas asociadas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": false,
                      "message": "No se encontró ninguna tarjeta con el número proporcionado."
                    }
                    """))),

            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity getAllById(
            @Parameter(description = "Identificador del usuario", required = true, example = "1") @RequestParam("idUsuario") int idUsuario) {
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
    @Operation(summary = "Crear nueva tarjeta", description = """
            Genera una nueva tarjeta bancaria asociada a un usuario.

            La tarjeta quedará vinculada a una cuenta existente.
            """)
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Tarjeta creada correctamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": true,
                      "message": "Tarjeta creada correctamente",
                      "object": {
                        "idTarjeta": 15,
                        "numTarjeta": "51617216831767158553"
                      }
                    }
                    """))),

            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "correct": false,
                      "message": "El usuario especificado no existe"
                    }
                    """))),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Datos necesarios para generar una nueva tarjeta", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
            {
                "idUsuario": 81,
                "idBanco": "2",
                "idRango": "2",
                "pin": "1234"
            }
                        """)))
    public ResponseEntity create(@RequestBody TarjetaDTO tarjeta) {

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
    @Operation(summary = "Eliminar tarjeta", description = """
            Elimina una tarjeta bancaria del sistema.

            La tarjeta debe existir previamente.
            """)
    @ApiResponses(value = {

            @ApiResponse(responseCode = "204", description = "Tarjeta eliminada correctamente"),

            @ApiResponse(responseCode = "400", description = "No fue posible eliminar la tarjeta", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                {
                    "correct": false,
                    "ex": null,
                    "message": "No se encontró ninguna tarjeta con el ID proporcionado.",
                    "object": null,
                    "objects": null
                }
                                        """))),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                {
                    "correct": false,
                    "message": "Error occurred while deleting tarjeta",
                    "object": null,
                    "objects": null,
                    "ex": {}
                }
                                        """)))
    })
    public ResponseEntity delete(
            @Parameter(description = "Identificador único de la tarjeta", required = true, example = "15") @RequestParam("idTarjeta") int idTarjeta) {
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
