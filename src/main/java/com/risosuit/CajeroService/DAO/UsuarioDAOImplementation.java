package com.risosuit.CajeroService.DAO;

import com.risosuit.CajeroService.DTO.CreateClienteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.risosuit.CajeroService.DTO.UsuarioCompletoDTO;
import com.risosuit.CajeroService.ML.Result;
import com.risosuit.CajeroService.ML.Usuario;
import com.risosuit.CajeroService.ML.Tarjeta;
import com.risosuit.CajeroService.ML.Cuenta;
import com.risosuit.CajeroService.ML.Banco;
import com.risosuit.CajeroService.ML.Rango;

import oracle.jdbc.OracleTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Repository
public class  UsuarioDAOImplementation implements IUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result<UsuarioCompletoDTO> getUsuarioByTarjeta(String numTarjeta) {
        Result<UsuarioCompletoDTO> result = new Result<>();
        try {
            jdbcTemplate.execute("{CALL SP_OBTENER_USUARIO_POR_TARJETA(?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setString(1, numTarjeta);
                        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        UsuarioCompletoDTO usuario = null;

                        if (resultSet.next()) {
                            usuario = new UsuarioCompletoDTO();

                            usuario.setIdUsuario(resultSet.getInt("idUsuario"));
                            usuario.setNombre(resultSet.getString("nombre"));
                            usuario.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                            usuario.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                            usuario.setCelular(resultSet.getString("celular"));
                            usuario.setTelefono(resultSet.getString("telefono"));
                            usuario.setEmail(resultSet.getString("email"));

                            usuario.setIdCuenta(resultSet.getInt("idCuenta"));
                            usuario.setNumCuenta(resultSet.getLong("NumCuenta"));
                            usuario.setSaldo(resultSet.getFloat("saldo"));
                            usuario.setIdBancoCuenta(resultSet.getInt("idBancoCuenta"));
                            usuario.setNombreBancoCuenta(resultSet.getString("nombreBancoCuenta"));

                            usuario.setIdTarjeta(resultSet.getInt("idTarjeta"));
                            usuario.setNumTarjeta(resultSet.getString("NumTarjeta"));
                            usuario.setPin(resultSet.getString("pin"));
                            usuario.setFechaVencimiento(resultSet.getDate("fechaVencimiento"));
                            usuario.setStatus(resultSet.getInt("status"));
                            usuario.setIdBancoTarjeta(resultSet.getInt("idBancoTarjeta"));
                            usuario.setNombreBancoTarjeta(resultSet.getString("nombreBancoTarjeta"));

                            usuario.setIdRango(resultSet.getInt("idRango"));
                            usuario.setNombreRango(resultSet.getString("nombreRango"));
                            usuario.setMinRetiro(resultSet.getInt("minRetiro"));
                            usuario.setMaxRetiro(resultSet.getInt("maxRetiro"));
                        }

                        resultSet.close();

                        if (usuario != null) {
                            result.correct = true;
                            result.object = usuario;
                            result.message = "Usuario encontrado exitosamente";
                        } else {
                            result.correct = false;
                            result.message = "No se encontró usuario con la tarjeta: " + numTarjeta;
                        }

                        return true;
                    });

        } catch (DataAccessException e) {
            result.correct = false;
            result.ex = e;
            result.message = "Error al buscar usuario por tarjeta: " + e.getMessage();
        } catch (Exception e) {
            result.correct = false;
            result.ex = e;
            result.message = "Error inesperado: " + e.getMessage();
        }

        return result;
    }

    @Override
    public Result<UsuarioCompletoDTO> getUsuarioById(int id) {
        Result<UsuarioCompletoDTO> result = new Result<>();
        try {
            jdbcTemplate.execute("{CALL SP_OBTENER_USUARIO_COMPLETO(?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setInt(1, id);
                        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        UsuarioCompletoDTO usuario = null;

                        if (resultSet.next()) {
                            usuario = new UsuarioCompletoDTO();

                            usuario.setIdUsuario(resultSet.getInt("idUsuario"));
                            usuario.setNombre(resultSet.getString("nombre"));
                            usuario.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                            usuario.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                            usuario.setCelular(resultSet.getString("celular"));
                            usuario.setTelefono(resultSet.getString("telefono"));
                            usuario.setEmail(resultSet.getString("email"));

                            usuario.setIdCuenta(resultSet.getInt("idCuenta"));
                            usuario.setNumCuenta(resultSet.getLong("NumCuenta"));
                            usuario.setSaldo(resultSet.getFloat("saldo"));
                            usuario.setIdBancoCuenta(resultSet.getInt("idBancoCuenta"));
                            usuario.setNombreBancoCuenta(resultSet.getString("nombreBancoCuenta"));

                            usuario.setIdTarjeta(resultSet.getInt("idTarjeta"));
                            usuario.setNumTarjeta(resultSet.getString("NumTarjeta"));
                            usuario.setPin(resultSet.getString("pin"));
                            usuario.setFechaVencimiento(resultSet.getDate("fechaVencimiento"));
                            usuario.setStatus(resultSet.getInt("status"));
                            usuario.setIdBancoTarjeta(resultSet.getInt("idBancoTarjeta"));
                            usuario.setNombreBancoTarjeta(resultSet.getString("nombreBancoTarjeta"));

                            usuario.setIdRango(resultSet.getInt("idRango"));
                            usuario.setNombreRango(resultSet.getString("nombreRango"));
                            usuario.setMinRetiro(resultSet.getInt("minRetiro"));
                            usuario.setMaxRetiro(resultSet.getInt("maxRetiro"));
                        }

                        resultSet.close();

                        if (usuario != null) {
                            result.correct = true;
                            result.object = usuario;
                            result.message = "Usuario encontrado exitosamente";
                        } else {
                            result.correct = false;
                            result.message = "No se encontró usuario con ID: " + id;
                        }

                        return true;
                    });

        } catch (DataAccessException e) {
            result.correct = false;
            result.ex = e;
            result.message = "Error al buscar usuario por ID: " + e.getMessage();
        } catch (Exception e) {
            result.correct = false;
            result.ex = e;
            result.message = "Error inesperado: " + e.getMessage();
        }

        return result;
    }

    @Override
    public Result<Usuario> getUsuarioByEmail(String email) {
        Result<Usuario> result = new Result<>();
        try {
            String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";
            Usuario usuario = jdbcTemplate.queryForObject(sql,
                    new Object[] { email },
                    new RowMapper<Usuario>() {
                        @Override
                        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Usuario u = new Usuario();
                            u.setIdUsuario(rs.getInt("idUsuario"));
                            u.setNombre(rs.getString("nombre"));
                            u.setApellidoPaterno(rs.getString("apellidoPaterno"));
                            u.setApellidoMaterno(rs.getString("apellidoMaterno"));
                            u.setCelular(rs.getString("celuar"));
                            u.setTelefono(rs.getString("telefono"));
                            u.setEmail(rs.getString("email"));
                            return u;
                        }
                    });
            if (usuario != null) {
                result.correct = true;
                result.object = usuario;
                result.message = "Usuario encontrado exitosamente";

            } else {

                result.correct = false;
                result.object = usuario;
                result.message = "Usuario No encontrado exitosamente";
            }

        } catch (EmptyResultDataAccessException e) {
            result.correct = false;
            result.message = "No se encontró usuario con email: " + email;
        } catch (DataAccessException e) {
            result.correct = false;
            result.ex = e;
            result.message = "Error al buscar usuario por email: " + e.getMessage();
        }
        return result;
    }

    @Override
    public Result<CreateClienteRequest> createUsuario(CreateClienteRequest request) {
        Result<CreateClienteRequest> result = new Result<>();
        try {
            Usuario usuario = (Usuario) request.getUsuario();
            Tarjeta tarjeta = (Tarjeta) request.getTarjeta();
            Cuenta cuenta = (Cuenta) request.getCuenta();
            Rango rango = tarjeta.getRango();
            Banco banco = tarjeta.getBanco();

            Result<Usuario> existe = getUsuarioByEmail(usuario.getEmail());
            if (existe.correct) {
                result.correct = false;
                result.message = "Ya existe un usuario con el email: " + usuario.getEmail();
                return result;
            }

            jdbcTemplate.execute("{CALL AddUserSP(?,?,?,?,?,?,?,?,?,?,?)}",
                    (CallableStatementCallback<Boolean>) callablestatement -> {
                        callablestatement.setString(1, usuario.getNombre());
                        callablestatement.setString(2, usuario.getApellidoPaterno());
                        callablestatement.setString(3, usuario.getApellidoMaterno());
                        callablestatement.setString(4, usuario.getCelular());
                        callablestatement.setString(5, usuario.getTelefono());
                        callablestatement.setString(6, usuario.getEmail());
                        callablestatement.setInt(7, banco.getIdBanco());
                        callablestatement.setInt(8, rango.getIdRango());
                        callablestatement.setDouble(9, cuenta.getSaldo());
                        callablestatement.setString(10, tarjeta.getPin());
                        callablestatement.registerOutParameter(11, OracleTypes.CURSOR);
                        callablestatement.execute();
                        ResultSet resultset = (ResultSet) callablestatement.getObject(11);

                        if (resultset.next()) {
                            Usuario usuarioNuevo = new Usuario();
                            Cuenta cuentaNueva = new Cuenta();
                            Tarjeta tarjetaNueva = new Tarjeta();
                            usuarioNuevo.setIdUsuario(resultset.getInt("idUsuario"));
                            usuarioNuevo.setNombre(resultset.getString("nombre"));
                            usuarioNuevo.setApellidoMaterno(resultset.getString("apellidoMaterno"));
                            usuarioNuevo.setApellidoPaterno(resultset.getString("apellidoPaterno"));
                            usuarioNuevo.setCelular(resultset.getString("celuar"));
                            usuarioNuevo.setCelular(resultset.getString("telefono"));
                            usuarioNuevo.setEmail(resultset.getString("email"));
                            cuentaNueva.setIdCuenta(resultset.getInt("idCuenta"));
                            cuentaNueva.setNumCuenta(resultset.getLong("NumCuenta"));
                            cuentaNueva.setSaldo(resultset.getFloat("saldo"));
                            cuentaNueva.setBanco(new Banco(
                                    resultset.getInt("idBanco"),
                                    resultset.getString("nombreBanco")));
                            tarjetaNueva.setIdTarjeta(resultset.getInt("idTarjeta"));
                            tarjetaNueva.setNumTarjeta(resultset.getString("NumTarjeta"));
                            tarjetaNueva.setFechaVencimiento(resultset.getDate("fechaVencimiento"));
                            tarjetaNueva.setStatus(resultset.getInt("STATUS_TARJETA"));
                            tarjetaNueva.setRango(new Rango(
                                    resultset.getInt("idRango"),
                                    resultset.getString("nombreRango"),
                                    resultset.getInt("minRetiro"),
                                    resultset.getInt("maxRetiro")));
                            result.object = new CreateClienteRequest(usuarioNuevo, cuentaNueva, tarjetaNueva);
                            result.correct = true;
                            result.message = "Cliente creado exitosamente.";
                        }

                        return true;
                    });

        } catch (DataAccessException e) {
            result.correct = false;
            result.message = e.getLocalizedMessage();
            result.ex = e;
            result.ex = e;
            result.message = "Error al crear usuario: " + e.getMessage();
        }
        return result;
    }

    @Override
    public Result<Tarjeta> getTarjetaByNumero(String numTarjeta) {
        Result<Tarjeta> result = new Result<>();
        try {
            String sql = "SELECT T.*, R.idRango, R.nombre as nombreRango, R.minRetiro, R.maxRetiro, "
                    + "B.idBanco, B.nombre as nombreBanco "
                    + "FROM TARJETA T "
                    + "LEFT JOIN RANGO R ON T.idRango = R.idRango "
                    + "LEFT JOIN BANCO B ON T.idBanco = B.idBanco "
                    + "WHERE T.NumTarjeta = ?";

            Tarjeta tarjeta = jdbcTemplate.queryForObject(sql,
                    new Object[] { numTarjeta },
                    new RowMapper<Tarjeta>() {
                        @Override
                        public Tarjeta mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Tarjeta t = new Tarjeta();
                            t.setIdTarjeta(rs.getInt("idTarjeta"));
                            t.setNumTarjeta(rs.getString("NumTarjeta"));
                            t.setPin(rs.getString("pin"));
                            t.setFechaVencimiento(rs.getDate("fechaVencimiento"));
                            t.setStatus(rs.getInt("status_tarjeta"));

                            Rango rango = new Rango();
                            rango.setIdRango(rs.getInt("idRango"));
                            rango.setNombre(rs.getString("nombreRango"));
                            rango.setMinRetiro(rs.getInt("minRetiro"));
                            rango.setMaxRetiro(rs.getInt("maxRetiro"));
                            t.setRango(rango);

                            Banco banco = new Banco();
                            banco.setIdBanco(rs.getInt("idBanco"));
                            banco.setNombre(rs.getString("nombreBanco"));
                            t.setBanco(banco);

                            return t;
                        }
                    });

            result.correct = true;
            result.object = tarjeta;
            result.message = "Tarjeta encontrada exitosamente";

        } catch (EmptyResultDataAccessException e) {
            result.correct = false;
            result.message = "No se encontró tarjeta: " + numTarjeta;
        } catch (DataAccessException e) {
            result.correct = false;
            result.ex = e;
            result.message = "Error al buscar tarjeta: " + e.getMessage();
        }
        return result;
    }

    @Override
    public Result<Cuenta> getCuentaByUsuarioId(int idUsuario) {
        Result<Cuenta> result = new Result<>();
        try {
            String sql = "SELECT C.*, B.idBanco, B.nombre as nombreBanco FROM CUENTA C "
                    + "LEFT JOIN BANCO B ON C.idBanco = B.idBanco "
                    + "WHERE C.idUsuario = ?";

            Cuenta cuenta = jdbcTemplate.queryForObject(sql,
                    new Object[] { idUsuario },
                    new RowMapper<Cuenta>() {
                        @Override
                        public Cuenta mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Cuenta c = new Cuenta();
                            c.setIdCuenta(rs.getInt("idCuenta"));
                            c.setNumCuenta(rs.getLong("NumCuenta"));
                            c.setSaldo(rs.getFloat("saldo"));

                            Banco banco = new Banco();
                            banco.setIdBanco(rs.getInt("idBanco"));
                            banco.setNombre(rs.getString("nombreBanco"));
                            c.setBanco(banco);

                            return c;
                        }
                    });

            result.correct = true;
            result.object = cuenta;
            result.message = "Cuenta encontrada exitosamente";

        } catch (EmptyResultDataAccessException e) {
            result.correct = false;
            result.message = "No se encontró cuenta para el usuario ID: " + idUsuario;
        } catch (DataAccessException e) {
            result.correct = false;
            result.ex = e;
            result.message = "Error al buscar cuenta: " + e.getMessage();
        }
        return result;
    }

    public Result<UsuarioCompletoDTO> getDatosCompletosUsuario(String numTarjeta) {
        Result<UsuarioCompletoDTO> result = new Result<>();
        try {
            jdbcTemplate.execute("{CALL SP_OBTENER_USUARIO_POR_TARJETA(?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setString(1, numTarjeta);
                        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        ArrayList<UsuarioCompletoDTO> usuarios = new ArrayList<>();

                        while (resultSet.next()) {
                            UsuarioCompletoDTO usuario = new UsuarioCompletoDTO();

                            usuario.setIdUsuario(resultSet.getInt("idUsuario"));
                            usuario.setNombre(resultSet.getString("nombre"));
                            usuario.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                            usuario.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                            usuario.setCelular(resultSet.getString("celular"));
                            usuario.setTelefono(resultSet.getString("telefono"));
                            usuario.setEmail(resultSet.getString("email"));
                            usuario.setIdCuenta(resultSet.getInt("idCuenta"));
                            usuario.setNumCuenta(resultSet.getLong("NumCuenta"));
                            usuario.setSaldo(resultSet.getFloat("saldo"));
                            usuario.setIdBancoCuenta(resultSet.getInt("idBancoCuenta"));
                            usuario.setNombreBancoCuenta(resultSet.getString("nombreBancoCuenta"));
                            usuario.setIdTarjeta(resultSet.getInt("idTarjeta"));
                            usuario.setNumTarjeta(resultSet.getString("NumTarjeta"));
                            usuario.setPin(resultSet.getString("pin"));
                            usuario.setFechaVencimiento(resultSet.getDate("fechaVencimiento"));
                            usuario.setStatus(resultSet.getInt("status"));
                            usuario.setIdBancoTarjeta(resultSet.getInt("idBancoTarjeta"));
                            usuario.setNombreBancoTarjeta(resultSet.getString("nombreBancoTarjeta"));
                            usuario.setIdRango(resultSet.getInt("idRango"));
                            usuario.setNombreRango(resultSet.getString("nombreRango"));
                            usuario.setMinRetiro(resultSet.getInt("minRetiro"));
                            usuario.setMaxRetiro(resultSet.getInt("maxRetiro"));

                            usuarios.add(usuario);
                        }

                        resultSet.close();

                        if (!usuarios.isEmpty()) {
                            result.correct = true;
                            result.object = usuarios.get(0);
                            result.objects = new ArrayList<>(usuarios);
                            result.message = "Usuario encontrado exitosamente";
                        } else {
                            result.correct = false;
                            result.message = "No se encontró usuario con la tarjeta: " + numTarjeta;
                        }

                        return true;
                    });

        } catch (DataAccessException dae) {
            result.correct = false;
            result.ex = dae;

            String fullMessage = dae.getMessage() != null ? dae.getMessage() : "";

            if (fullMessage.contains("20001") || fullMessage.contains("20002")
                    || fullMessage.contains("20003") || fullMessage.contains("20004")
                    || fullMessage.contains("20099")) {

                try {
                    int oraIndex = fullMessage.indexOf("ORA-200");
                    if (oraIndex != -1) {
                        String oraLine = fullMessage.substring(oraIndex);
                        if (oraLine.contains("\n")) {
                            oraLine = oraLine.split("\n")[0];
                        }
                        String cleanMessage = oraLine.replaceAll("ORA-\\d+:\\s*(Error:\\s*|Error inesperado:\\s*)?", "")
                                .trim();
                        result.message = cleanMessage;
                    } else {
                        result.message = "Error en la transacción";
                    }
                } catch (Exception e) {
                    result.message = "Error de validación en el cajero automático.";
                }
            } else {
                result.message = "Error crítico en la base de datos: " + fullMessage;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.ex = ex;
            result.message = "Error al obtener datos del usuario: " + ex.getMessage();
        }

        return result;
    }

    @Override
    public Result<Usuario> createUsuario(String email, String password, String NumTarjeta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
