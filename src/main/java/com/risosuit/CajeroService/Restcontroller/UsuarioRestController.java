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
import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Cuenta;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioRestController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAO;

    @GetMapping("/id/{id}")
    public ResponseEntity<Result> getUsuarioById(@PathVariable int id) {
        Result result = new Result();
        try {
            Result<UsuarioCompletoDTO> daoResult = usuarioDAO.getUsuarioById(id);

            if (daoResult.correct) {
                result.correct = true;
                result.object = daoResult.object;
                result.message = daoResult.message;
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = daoResult.message;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener usuario: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/usuariotarjeta/{numTarjeta}")
    public ResponseEntity<Result> getUsuarioByTarjeta(@PathVariable String numTarjeta) {
        Result result = new Result();
        try {
            Result<UsuarioCompletoDTO> daoResult = usuarioDAO.getUsuarioByTarjeta(numTarjeta);

            if (daoResult.correct) {
                result.correct = true;
                result.object = daoResult.object;
                result.message = daoResult.message;
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = daoResult.message;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener usuario: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<Result> getUsuarioByEmail(@RequestParam String email) {
        Result result = new Result();
        try {
            Result<Usuario> daoResult = usuarioDAO.getUsuarioByEmail(email);

            if (daoResult.correct) {
                result.correct = true;
                result.object = daoResult.object;
                result.message = daoResult.message;
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = daoResult.message;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener usuario: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

        @PostMapping("create")
        public ResponseEntity<Result> createUsuario(@RequestBody CreateClienteRequest request) {
            Result result = new Result();
            Usuario usuario = (Usuario) request.getUsuario();
            Cuenta cuenta  = (Cuenta) request.getCuenta();
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

    @GetMapping("/tarjeta/{numTarjeta}")
    public ResponseEntity<Result> getTarjetaByNumero(@PathVariable String numTarjeta) {
        Result result = new Result();
        try {
            Result<Tarjeta> daoResult = usuarioDAO.getTarjetaByNumero(numTarjeta);

            if (daoResult.correct) {
                result.correct = true;
                result.object = daoResult.object;
                result.message = daoResult.message;
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = daoResult.message;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener tarjeta: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping("/validar-acceso")
    public ResponseEntity<Result> validarAcceso(@RequestBody Map<String, String> request) {
        Result result = new Result();
        try {
            String numTarjeta = request.get("numTarjeta");
            String pin = request.get("pin");

            if (numTarjeta == null || pin == null) {
                result.correct = false;
                result.message = "Número de tarjeta y PIN son requeridos";
                return ResponseEntity.badRequest().body(result);
            }

            Result<UsuarioCompletoDTO> daoResult = usuarioDAO.getUsuarioByTarjeta(numTarjeta);

            if (daoResult.correct && daoResult.object != null) {
                UsuarioCompletoDTO usuario = daoResult.object;

                if (usuario.getStatus() != 1) {
                    result.correct = false;
                    result.message = "Tarjeta inactiva";
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
                }

                if (usuario.getPin().equals(pin)) {
                    result.correct = true;
                    result.object = true;
                    result.message = "Acceso válido";
                    return ResponseEntity.ok(result);
                } else {
                    result.correct = false;
                    result.object = false;
                    result.message = "PIN incorrecto";
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
                }
            } else {
                result.correct = false;
                result.message = "Tarjeta no encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al validar acceso: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/tarjeta/activa/{numTarjeta}")
    public ResponseEntity<Result> isTarjetaActiva(@PathVariable String numTarjeta) {
        Result result = new Result();
        try {
            Result<Tarjeta> daoResult = usuarioDAO.getTarjetaByNumero(numTarjeta);

            if (daoResult.correct && daoResult.object != null) {
                boolean activa = daoResult.object.getStatus() == 1;
                result.correct = true;
                result.object = activa;
                result.message = activa ? "Tarjeta activa" : "Tarjeta inactiva";
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.object = false;
                result.message = "Tarjeta no encontrada";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al verificar tarjeta: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/cuenta/usuario/{idUsuario}")
    public ResponseEntity<Result> getCuentaByUsuarioId(@PathVariable int idUsuario) {
        Result result = new Result();
        try {
            Result<Cuenta> daoResult = usuarioDAO.getCuentaByUsuarioId(idUsuario);

            if (daoResult.correct) {
                result.correct = true;
                result.object = daoResult.object;
                result.message = daoResult.message;
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = daoResult.message;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener cuenta: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/saldo/{numTarjeta}")
    public ResponseEntity<Result> getSaldoByTarjeta(@PathVariable String numTarjeta) {
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

    @GetMapping("/rango/{numTarjeta}")
    public ResponseEntity<Result> getRangoByTarjeta(@PathVariable String numTarjeta) {
        Result result = new Result();
        try {
            Result<UsuarioCompletoDTO> daoResult = usuarioDAO.getUsuarioByTarjeta(numTarjeta);

            if (daoResult.correct && daoResult.object != null) {
                UsuarioCompletoDTO usuario = daoResult.object;

                Map<String, Object> rangoInfo = new HashMap<>();
                rangoInfo.put("nombreRango", usuario.getNombreRango());
                rangoInfo.put("minRetiro", usuario.getMinRetiro());
                rangoInfo.put("maxRetiro", usuario.getMaxRetiro());
                rangoInfo.put("numTarjeta", usuario.getNumTarjeta());
                rangoInfo.put("nombreUsuario", usuario.getNombreCompleto());

                result.correct = true;
                result.object = rangoInfo;
                result.message = "Información de rango obtenida exitosamente";
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = "Usuario no encontrado con tarjeta: " + numTarjeta;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener información del rango: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/datos-completos/{numTarjeta}")
    public ResponseEntity<Result> getDatosCompletos(@PathVariable String numTarjeta) {
        Result result = new Result();
        try {
            Result<UsuarioCompletoDTO> daoResult = usuarioDAO.getUsuarioByTarjeta(numTarjeta);

            if (daoResult.correct) {
                result.correct = true;
                result.object = daoResult.object;
                result.message = "Datos completos del usuario obtenidos exitosamente";
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = daoResult.message;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener datos completos: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/resumen-cuenta/{numTarjeta}")
    public ResponseEntity<Result> getResumenCuenta(@PathVariable String numTarjeta) {
        Result result = new Result();
        try {
            Result<UsuarioCompletoDTO> daoResult = usuarioDAO.getUsuarioByTarjeta(numTarjeta);

            if (daoResult.correct && daoResult.object != null) {
                UsuarioCompletoDTO usuario = daoResult.object;

                Map<String, Object> resumen = new HashMap<>();

                Map<String, Object> infoUsuario = new HashMap<>();
                infoUsuario.put("nombre", usuario.getNombre());
                infoUsuario.put("apellidoPaterno", usuario.getApellidoPaterno());
                infoUsuario.put("apellidoMaterno", usuario.getApellidoMaterno());
                infoUsuario.put("email", usuario.getEmail());
                infoUsuario.put("telefono", usuario.getTelefono());
                infoUsuario.put("celular", usuario.getCelular());

                Map<String, Object> infoCuenta = new HashMap<>();
                infoCuenta.put("numCuenta", usuario.getNumCuenta());
                infoCuenta.put("saldo", usuario.getSaldo());
                infoCuenta.put("banco", usuario.getNombreBancoCuenta());

                Map<String, Object> infoTarjeta = new HashMap<>();
                infoTarjeta.put("numTarjeta", usuario.getNumTarjeta());
                infoTarjeta.put("fechaVencimiento", usuario.getFechaVencimiento());
                infoTarjeta.put("status", usuario.getStatus() == 1 ? "Activa" : "Inactiva");
                infoTarjeta.put("rango", usuario.getNombreRango());
                infoTarjeta.put("limiteMinimo", usuario.getMinRetiro());
                infoTarjeta.put("limiteMaximo", usuario.getMaxRetiro());

                resumen.put("usuario", infoUsuario);
                resumen.put("cuenta", infoCuenta);
                resumen.put("tarjeta", infoTarjeta);

                result.correct = true;
                result.object = resumen;
                result.message = "Resumen de cuenta obtenido exitosamente";
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.message = "No se encontró información para la tarjeta: " + numTarjeta;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.correct = false;
            result.message = "Error al obtener resumen de cuenta: " + e.getMessage();
            result.ex = e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
