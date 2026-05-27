package com.risosuit.CajeroService.Restcontroller;

import com.risosuit.CajeroService.DAO.TarjetaDAOImplementation;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.Service.JWTService;

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
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private TarjetaDAOImplementation tarjetaDAO;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        try {
            System.out.println(passwordEncoder.encode(request.get("NumTarjeta")));
            System.out.println(passwordEncoder.encode(request.get("pin")));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.get("NumTarjeta"),
                            request.get("pin")
                    )
            );

            String token = jwtService.generateToken(authentication.getName());

            return ResponseEntity.ok(
                    Map.of(
                            "token", token,
                            "numTarjeta", authentication.getName()
                    )
            );

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
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> request) {
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
                    "numTarjeta", numTarjeta
            ));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "error", "Token inválido o expirado"));
        }
    }
}
