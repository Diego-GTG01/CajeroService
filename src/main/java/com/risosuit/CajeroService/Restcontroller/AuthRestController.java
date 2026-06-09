package com.risosuit.CajeroService.Restcontroller;

import com.risosuit.CajeroService.DAO.TarjetaDAOImplementation;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.Service.JWTService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoints relacionados con autenticación y validación de tokens")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Autenticar tarjeta (Login)", description = """
            Valida el número de tarjeta y el PIN enviados por el cliente.

            Si las credenciales son correctas:
            - Se autentica al usuario.
            - Se genera un token JWT.
            - Se devuelve el token junto con el número de tarjeta.

            El token deberá enviarse en futuras solicitudes protegidas.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = """
                    {
                        "token": "eyJhbGciOiJIUzI1NiJ9...",
                        "numTarjeta": "12345678123456781234"
                    }
                    """))),

            @ApiResponse(responseCode = "401", description = "Número de tarjeta o PIN incorrectos", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = """
                    {
                        "error": "Número de tarjeta o PIN incorrecto"
                    }
                    """))),

            @ApiResponse(responseCode = "400", description = "Error de validación", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = """
                    {
                        "error": "La tarjeta no existe"
                    }
                    """))),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object"), examples = @ExampleObject(value = """
                    {
                        "error": "Ocurrió un error inesperado"
                    }
                    """)))
    })
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo de la petición que contiene el token", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object", requiredProperties = {
                    "NumTarjeta", "pin" }),

                    examples = @ExampleObject(value = """
                                    {
                                        "NumTarjeta": "12345678123456781234",
                                        "pin": "1234"
                            }
                                """))) @RequestBody Map<String, String> request) {

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.get("NumTarjeta"),
                            request.get("pin")));

            String token = jwtService.generateToken(authentication.getName());

            return ResponseEntity.ok(
                    Map.of(
                            "token", token,
                            "numTarjeta", authentication.getName()));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Número de tarjeta o PIN incorrecto"));

        } catch (InternalAuthenticationServiceException e) {
            String mensajeError = "Error en la autenticación";

            if (e.getCause() != null) {
                mensajeError = e.getCause().getMessage();
            } else {
                mensajeError = e.getMessage();
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", mensajeError));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()));
        }
    }

    @PostMapping("/validate-token")
    @Operation(summary = "Validar token JWT", description = "Comprueba si un token sigue siendo válido y no ha expirado. Extrae y devuelve el número de tarjeta asociado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El token es válido.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object", example = """
                        {
                            "valid": true,
                            "numTarjeta": "12345678123456781234"
                        }
                    """))),
            @ApiResponse(responseCode = "403", description = "Token inválido o expirado", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object", example = """
                        {
                            "valid": false,
                            "error": "Token inválido o expirado"
                        }
                    """))),
            @ApiResponse(responseCode = "401", description = "Petición incorrecta. El token no fue proporcionado.", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object", example = """
                        {
                            "error": "Número de tarjeta o PIN incorrecto"
                        }
                    """)))

    })

    public ResponseEntity<?> validateToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cuerpo de la petición que contiene el token", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(type = "object", requiredProperties = {
                    "token" }),

                    examples = @ExampleObject(value = """
                                    {
                                        "NumTarjeta": "12345678123456781234",
                                        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODEyMzQ1Njc4MTIzNCIsImlhdCI6MTc4MDk0NDUxNSwiZXhwIjoxNzgwOTQ4MTE1fQ.hxYuebxxTIdN1znZLa8CWGpXU-J7uBrbqkO9tno_QiY"
                            }
                                """))) @RequestBody Map<String, String> request) {
        String token = request.get("token");

        if (token == null || token.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("valid", false, "error", "El token es requerido"));
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        boolean isValid = jwtService.isTokenValid(token);

        if (isValid) {
            String numTarjeta = jwtService.extractNumTarjeta(token);
            return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "numTarjeta", numTarjeta));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "error", "Token inválido o expirado"));
        }
    }
}