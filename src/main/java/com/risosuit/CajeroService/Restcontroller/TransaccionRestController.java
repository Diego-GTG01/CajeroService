package com.risosuit.CajeroService.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risosuit.CajeroService.DAO.RetiroDAOImplementation;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Transaccion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/Cajero")
@CrossOrigin(origins = "*")
@Tag(name = "Transacciones", description = "Endpoints relacionados con la gestión y administración de transacciones")
@SecurityRequirement(name = "BearerAuth")
public class TransaccionRestController {

  @Autowired
  private RetiroDAOImplementation retiroDAO;

  @GetMapping
  @Operation(hidden = true)
  public ResponseEntity<?> demo() {
    Result result = new Result();
    try {
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      result.correct = false;
      result.ex = e;
      result.message = e.getLocalizedMessage();
      return ResponseEntity.internalServerError().body(result);
    }
  }

  @PostMapping("/transact")
  @Operation(summary = "Procesar una transacción de retiro", description = """
      Realiza una operación de retiro de efectivo desde una tarjeta bancaria.

      Validaciones realizadas:
      - Existencia de la tarjeta.
      - Disponibilidad de saldo.
      - Existencia del cajero receptor.
      - Disponibilidad de efectivo en el cajero.
      - Reglas de negocio del banco emisor.

      Cuando la operación es exitosa, se devuelve el desglose de billetes
      y monedas entregados junto con el folio de la transacción.
      """)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transacción procesada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = {
          @ExampleObject(name = "Retiro Exitoso", value = """
              {
                "correct": true,
                "message": "Retiro autorizado con éxito.",
                "object": null,
                "objects": [
                  {
                    "folio": 89,
                    "usuario": "Diego Gómez Tagle González",
                    "NumCuenta": "82384018",
                    "NumTarjeta": "****8553",
                    "Status": "EXITOSO",
                    "tipo": "Billete",
                    "denominacion": "1000",
                    "cantidad": 1,
                    "subtotal": 1000
                  },
                  {
                    "folio": 89,
                    "usuario": "Diego Gómez Tagle González",
                    "NumCuenta": "82384018",
                    "NumTarjeta": "****8553",
                    "Status": "EXITOSO",
                    "tipo": "Billete",
                    "denominacion": "200",
                    "cantidad": 1,
                    "subtotal": 200
                  },
                  {
                    "folio": 89,
                    "usuario": "Diego Gómez Tagle González",
                    "NumCuenta": "82384018",
                    "NumTarjeta": "****8553",
                    "Status": "EXITOSO",
                    "tipo": "Moneda",
                    "denominacion": "50",
                    "cantidad": 1,
                    "subtotal": 50
                  }
                ],
                "ex": null
              }
              """),
          @ExampleObject(name = "Fondos insuficientes", value = """
              {
                "correct": false,
                "message": "Fondos insuficientes en la cuenta.",
                "object": null,
                "objects": null,
                "ex": null
              }
              """)
      })),
      @ApiResponse(responseCode = "500", description = "Error interno en el servidor o fallo crítico al procesar el retiro", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
          {
              "correct": false,
              "message": "Fallo de conexión con el switch transaccional",
              "object": null,
              "objects": null,
              "ex": { }
          }
          """)))
  })
  public ResponseEntity<?> transact(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Datos necesarios para realizar un retiro de efectivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transaccion.class), examples = @ExampleObject(value = """
                              {
              "cajero": {
                  "idCajero": 1,
                  "total": 0
              },
              "tarjeta": {
                  "idTarjeta": 0,
                  "NumTarjeta": "51617216831767158553"
              },
              "monto": 500
          }
                              """))) @RequestBody Transaccion transaccion) {
    Result result = new Result();
    try {
      result = retiroDAO.retirar(
          transaccion.getTarjeta(),
          transaccion.getCajero().getIdCajero(),
          transaccion.getMonto());
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      result.correct = false;
      result.ex = e;
      result.message = e.getLocalizedMessage();
      return ResponseEntity.internalServerError().body(result);
    }
  }
}