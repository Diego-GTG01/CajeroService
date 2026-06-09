package com.risosuit.CajeroService.Restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.risosuit.CajeroService.DAO.UsuarioDAOImplementation;
import com.risosuit.CajeroService.DTO.CreateClienteRequest;
import com.risosuit.CajeroService.DTO.UsuarioCompletoDTO;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Cuenta;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = """
    Endpoints para la administración de usuarios bancarios.

    Funcionalidades:
    - Registro de nuevos clientes.
    - Creación de cuentas bancarias.
    - Emisión de tarjetas.
    - Consulta de saldo mediante número de tarjeta.
    """)

public class UsuarioRestController {

  @Autowired
  private UsuarioDAOImplementation usuarioDAO;

  @PostMapping("create")
  @Operation(summary = "Registrar nuevo cliente", description = """
      Registra un nuevo cliente dentro del sistema.

      El proceso incluye:
      - Creación del usuario.
      - Creación de la cuenta bancaria.
      - Creación y asociación de una tarjeta.

      Todas las operaciones se realizan de forma transaccional.
      """)
  @ApiResponses(value = {

      @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
          {
            "correct": true,
            "message": "Cliente registrado correctamente",
            "object": {
              "usuario": {
                "nombre": "Diego",
                "apellidoPaterno": "Gómez",
                "apellidoMaterno": "Tagle"
              },
              "cuenta": {
                "numCuenta": "82384018",
                "saldo": 5000
              },
              "tarjeta": {
                "numTarjeta": "51617216831767158553"
              }
            },
            "ex": null
          }
          """))),

      @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
          {
            "correct": false,
            "message": "La información proporcionada es inválida",
            "object": null,
            "ex": null
          }
          """))),

      @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
          {
            "correct": false,
            "message": "Error al crear usuario",
            "object": null,
            "ex": {}
          }
          """)))
  })
  @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "Información del usuario, cuenta y tarjeta a registrar", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
            {
          "usuario": {
              "apellidoMaterno": "Toriz",
              "apellidoPaterno": "Garcia ",
              "celular": "1122334455",
              "email": "diegogomeztg1234@outlook.com",
              "idUsuario": 0,
              "nombre": "Gabriel ",
              "telefono": "9988776655"
          },
          "cuenta": {
              "saldo": 5000,
              "banco": {
                  "idBanco": 1,
                  "nombre": ""
              },
              "NumCuenta": 0
          },
          "tarjeta": {
              "idTarjeta": 0,
              "pin": "1234",
              "banco": {
                  "idBanco": 1,
                  "nombre": "Banco Alfa"
              },
              "rango": {
                  "idRango": 1,
                  "maxRetiro": 5000,
                  "minRetiro": 50,
                  "nombre": "Estandar"
              }
          }
      }
            """)))

  public ResponseEntity<Result> createUsuario(@RequestBody CreateClienteRequest request) {
    Result result = new Result();
    Usuario usuario = (Usuario) request.getUsuario();
    Cuenta cuenta = (Cuenta) request.getCuenta();
    Tarjeta tarjeta = (Tarjeta) request.getTarjeta();

    CreateClienteRequest clienteRequest = new CreateClienteRequest(usuario, cuenta, tarjeta);

    try {
      Result<CreateClienteRequest> daoResult = usuarioDAO.createUsuario(clienteRequest);
      if (daoResult.correct) {
        result.correct = true;
        result.object = daoResult.object;
        result.message = daoResult.message;
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
      } else {
        result.correct = false;
        result.message = daoResult.message;
        return ResponseEntity.badRequest().body(result);
      }
    } catch (Exception e) {
      result.correct = false;
      result.message = "Error al crear usuario: " + e.getMessage();
      result.ex = e;
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
  }

  @GetMapping("/saldo/{numTarjeta}")
  @Operation(summary = "Consultar saldo por tarjeta", description = """
      Obtiene la información financiera asociada a una tarjeta.

      Información devuelta:
      - Usuario.
      - Cuenta.
      - Banco.
      - Rango.
      - Saldo actual.
      """)
  @ApiResponses(value = {

      @ApiResponse(responseCode = "200", description = "Saldo consultado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class), examples = @ExampleObject(value = """
          {
            "correct": true,
            "message": "Saldo consultado exitosamente",
            "object": {
              "idUsuario": 1,
              "numTarjeta": "51617216831767158553",
              "numCuenta": "82384018",
              "saldo": 3850.00,
              "nombreUsuario": "Diego Gómez Tagle González",
              "banco": "Banorte",
              "rango": "Clásica"
            },
            "ex": null
          }
          """))),

      @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
          {
            "correct": false,
            "message": "Usuario no encontrado con tarjeta: 51617216831767158553",
            "object": null,
            "ex": null
          }
          """))),

      @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
          {
            "correct": false,
            "message": "Error al consultar saldo",
            "object": null,
            "ex": {}
          }
          """)))
  })
  @SecurityRequirement(name = "BearerAuth")
  public ResponseEntity<Result> getSaldoByTarjeta(
      @Parameter(description = "Número de tarjeta del cliente", example = "51617216831767158553", required = true) @PathVariable String numTarjeta) {

    Result result = new Result();
    try {
      Result<UsuarioCompletoDTO> daoResult = usuarioDAO.getUsuarioByTarjeta(numTarjeta);

      if (daoResult.correct && daoResult.object != null) {
        UsuarioCompletoDTO usuario = daoResult.object;

        Map<String, Object> saldoInfo = new HashMap<>();
        saldoInfo.put("idUsuario", usuario.getIdUsuario());
        saldoInfo.put("numTarjeta", usuario.getNumTarjeta());
        saldoInfo.put("numCuenta", usuario.getNumCuenta());
        saldoInfo.put("saldo", usuario.getSaldo());
        saldoInfo.put("nombreUsuario", usuario.getNombreCompleto());
        saldoInfo.put("banco", usuario.getNombreBancoCuenta());
        saldoInfo.put("rango", usuario.getNombreRango());

        result.correct = true;
        result.object = saldoInfo;
        result.message = "Saldo consultado exitosamente";
        return ResponseEntity.ok(result);
      } else {
        result.correct = false;
        result.message = "Usuario no encontrado con tarjeta: " + numTarjeta;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
      }
    } catch (Exception e) {
      result.correct = false;
      result.message = "Error al consultar saldo: " + e.getMessage();
      result.ex = e;
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
  }

}
