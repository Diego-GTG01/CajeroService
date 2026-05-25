-- TABLAS PRINCIPALES
CREATE TABLE USUARIO(
    idUsuario number generated always as identity,
    nombre varchar(30),
    apellidoPaterno varchar(30),
    apellidoMaterno varchar(30),
    celuar varchar(20),
    telefono varchar(20),
    email varchar(25),
    primary key(idUsuario)
);

CREATE TABLE RANGO(
    idRango number generated always as identity,
    nombre varchar(50),
    minRetiro number,
    maxRetiro number,
    primary key(idRango)
);

CREATE TABLE DENOMINACION(
    idDenominacion number generated always as identity,
    tipo varchar(30),
    denominacion number,
    primary key(idDenominacion)
);

CREATE TABLE CAJERO(
    idCajero number generated always as identity,
    ubicacion varchar(50),
    estado varchar(50),
    primary key(idCajero)
);

CREATE TABLE BANCO(
    idBanco number generated always as identity,
    nombre varchar(50),
    primary key(idBanco)
);

-- RELACIONES
CREATE TABLE CUENTA(
    idCuenta number generated always as identity,
    idUsuario number,
    idBanco number, 
    NumCuenta number,
    saldo number,
    FOREIGN KEY (idUsuario) REFERENCES USUARIO (idUsuario),
    FOREIGN KEY (idBanco) REFERENCES BANCO (idBanco)
);

CREATE TABLE TARJETA(
    idTarjeta number generated always as identity,
    idUsuario number, 
    idRango number, 
    idBanco number, 
    NumTarjeta varchar(200),
    pin varchar(200),
    fechaVencimiento DATE,
    status_tarjeta number,
    primary key(idTarjeta),
    FOREIGN KEY (idUsuario) REFERENCES USUARIO (idUsuario),
    FOREIGN KEY (idRango) REFERENCES RANGO (idRango),
    FOREIGN KEY (idBanco) REFERENCES BANCO (idBanco)  
);

CREATE TABLE DETALLECAJERO(
    idDetalleCajero number generated always as identity,
    idcajero number,
    idDenominacion number,
    cantidadDisponible number,
    FOREIGN KEY (idcajero) REFERENCES CAJERO (idCajero),
    FOREIGN KEY (idDenominacion) REFERENCES DENOMINACION (idDenominacion)
);

CREATE TABLE TRANSACCION(
    idTransaccion number generated always as identity,
    idTarjeta number,
    idCajero number,
    monto number,
    fecha DATE, 
    estado number, 
    primary key(idTransaccion),
    FOREIGN KEY (idTarjeta) REFERENCES TARJETA (idTarjeta),
    FOREIGN KEY (idCajero) REFERENCES CAJERO (idCajero)
);

CREATE TABLE DETALLETRANSACCION(
    idDetalleTransaccion number generated always as identity,
    idTransaccion number,
    idDenominacion number,
    CantidadEntregada number,
    primary key (idDetalleTransaccion),
    FOREIGN KEY (idTransaccion) REFERENCES TRANSACCION (idTransaccion),
    FOREIGN KEY (idDenominacion) REFERENCES DENOMINACION (idDenominacion)
);

-- DATOS DE PRUEBA

-- Usuarios
INSERT INTO USUARIO (nombre, apellidoPaterno, apellidoMaterno, celuar, telefono, email) 
VALUES ('Carlos', 'Mendoza', 'Ruiz', '5551234567', '5559876543', 'carlos@email.com');
INSERT INTO USUARIO (nombre, apellidoPaterno, apellidoMaterno, celuar, telefono, email) 
VALUES ('Ana', 'Gomez', 'Lopez', '5557654321', '5551112233', 'ana@email.com');

-- Rangos de Retiro
INSERT INTO RANGO (nombre, minRetiro, maxRetiro) VALUES ('Estandar', 100, 5000);
INSERT INTO RANGO (nombre, minRetiro, maxRetiro) VALUES ('Premium', 100, 15000);

-- Denominaciones de Billetes
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 1000);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 500);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 200);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 100);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Moneda', 50);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Moneda', 20);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Moneda', 10);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Moneda', 5);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Moneda', 2);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Moneda', 1);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Moneda', 0.5);

-- Cajeros
INSERT INTO CAJERO (ubicacion, estado) VALUES ('Centro Comercial Plaza', 'Activo');
INSERT INTO CAJERO (ubicacion, estado) VALUES ('Sucursal Avenida Central', 'Activo');
INSERT INTO CAJERO (ubicacion, estado) VALUES ('Sucursal Calle Reforma', 'Activo');

-- Bancos
INSERT INTO BANCO (nombre) VALUES ('Banco Alfa');
INSERT INTO BANCO (nombre) VALUES ('Banco Beta');

-- Cuentas
INSERT INTO CUENTA (idUsuario, idBanco, NumCuenta, saldo) VALUES (1, 1, 123456789, 25000);
INSERT INTO CUENTA (idUsuario, idBanco, NumCuenta, saldo) VALUES (2, 2, 987654321, 8500);

UPDATE CUENTA SET saldo = 50000 WHERE NumCuenta = 123456789;

-- Tarjetas
INSERT INTO TARJETA (idUsuario, idRango, idBanco, NumTarjeta, pin, fechaVencimiento, status_tarjeta) 
VALUES (1, 2, 1, '1234567812345678', '1234', TO_DATE('2030-12-31', 'YYYY-MM-DD'), 1);
INSERT INTO TARJETA (idUsuario, idRango, idBanco, NumTarjeta, pin, fechaVencimiento, status_tarjeta) 
VALUES (2, 1, 2, '8765432187654321', '4321', TO_DATE('2029-05-15', 'YYYY-MM-DD'), 1);

-- Detalle del Cajero (usando IDs correctos)
-- Primero, obtener los IDs de las denominaciones
DECLARE
    v_id_1000 NUMBER;
    v_id_500 NUMBER;
    v_id_200 NUMBER;
    v_id_100 NUMBER;
    v_id_50 NUMBER;
    v_id_20 NUMBER;
    v_id_10 NUMBER;
    v_id_5 NUMBER;
    v_id_2 NUMBER;
    v_id_1 NUMBER;
    v_id_05 NUMBER;
BEGIN
    SELECT idDenominacion INTO v_id_1000 FROM DENOMINACION WHERE denominacion = 1000;
    SELECT idDenominacion INTO v_id_500 FROM DENOMINACION WHERE denominacion = 500;
    SELECT idDenominacion INTO v_id_200 FROM DENOMINACION WHERE denominacion = 200;
    SELECT idDenominacion INTO v_id_100 FROM DENOMINACION WHERE denominacion = 100;
    SELECT idDenominacion INTO v_id_50 FROM DENOMINACION WHERE denominacion = 50;
    SELECT idDenominacion INTO v_id_20 FROM DENOMINACION WHERE denominacion = 20;
    SELECT idDenominacion INTO v_id_10 FROM DENOMINACION WHERE denominacion = 10;
    SELECT idDenominacion INTO v_id_5 FROM DENOMINACION WHERE denominacion = 5;
    SELECT idDenominacion INTO v_id_2 FROM DENOMINACION WHERE denominacion = 2;
    SELECT idDenominacion INTO v_id_1 FROM DENOMINACION WHERE denominacion = 1;
    SELECT idDenominacion INTO v_id_05 FROM DENOMINACION WHERE denominacion = 0.5;
    
    -- Cajero 1
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (1, v_id_100, 500);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (1, v_id_200, 300);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (1, v_id_500, 200);
    
    -- Cajero 2
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_1000, 2);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_500, 5);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_200, 10);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_100, 20);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_50, 30);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_20, 40);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_10, 50);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_5, 100);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_2, 200);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_1, 300);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, v_id_05, 100);
    
    -- Cajero 3
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_1000, 2);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_500, 5);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_200, 10);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_100, 20);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_50, 30);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_20, 40);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_10, 50);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_5, 100);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_2, 200);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_1, 300);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, v_id_05, 100);
    
    COMMIT;
END;
/

-- Transacciones
INSERT INTO TRANSACCION (idTarjeta, idCajero, monto, fecha, estado) 
VALUES (1, 1, 700, SYSDATE, 1);

-- Detalle de la Transacción
DECLARE
    v_id_500 NUMBER;
    v_id_200 NUMBER;
BEGIN
    SELECT idDenominacion INTO v_id_500 FROM DENOMINACION WHERE denominacion = 500;
    SELECT idDenominacion INTO v_id_200 FROM DENOMINACION WHERE denominacion = 200;
    
    INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada) VALUES (1, v_id_500, 1);
    INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada) VALUES (1, v_id_200, 1);
    
    COMMIT;
END;
/

-- CONSULTAS DE PRUEBA

-- Saldo disponible del usuario
SELECT 
    U.nombre || ' ' || U.apellidoPaterno AS Cliente,
    B.nombre AS Banco,
    C.NumCuenta,
    C.saldo AS Saldo_Disponible
FROM CUENTA C
JOIN USUARIO U ON C.idUsuario = U.idUsuario
JOIN BANCO B ON C.idBanco = B.idBanco;

-- Dinero total de cada cajero
SELECT 
    C.idCajero,
    C.ubicacion AS Cajero,
    SUM(D.denominacion * DC.cantidadDisponible) AS Dinero_Total_Disponible
FROM DETALLECAJERO DC
JOIN CAJERO C ON DC.idcajero = C.idCajero
JOIN DENOMINACION D ON DC.idDenominacion = D.idDenominacion
GROUP BY C.idCajero, C.ubicacion
ORDER BY C.idCajero;

-- Transacciones realizadas
SELECT 
    T.idTransaccion AS Folio,
    U.nombre || ' ' || U.apellidoPaterno AS Cliente,
    TJ.NumTarjeta AS Tarjeta_Usada,
    B.nombre AS Banco_Tarjeta,
    C.ubicacion AS Cajero_Ubicacion,
    T.monto AS Monto_Retirado,
    TO_CHAR(T.fecha, 'DD/MM/YYYY HH24:MI') AS Fecha_Hora
FROM TRANSACCION T
JOIN TARJETA TJ ON T.idTarjeta = TJ.idTarjeta
JOIN USUARIO U ON TJ.idUsuario = U.idUsuario
JOIN BANCO B ON TJ.idBanco = B.idBanco
JOIN CAJERO C ON T.idCajero = C.idCajero;

-- Detalle de transacción específica
SELECT 
    DT.idTransaccion AS Folio_Transaccion,
    D.denominacion AS Denominacion,
    DT.CantidadEntregada AS Cantidad,
    (D.denominacion * DT.CantidadEntregada) AS Subtotal
FROM DETALLETRANSACCION DT
JOIN DENOMINACION D ON DT.idDenominacion = D.idDenominacion
WHERE DT.idTransaccion = 1;

-- Total billetes por cajero
SELECT 
    C.ubicacion AS Cajero,
    D.denominacion AS Denominacion,
    DC.cantidadDisponible AS Billetes_Restantes,
    (D.denominacion * DC.cantidadDisponible) AS Dinero_Total_Disponible_Mxn
FROM DETALLECAJERO DC
JOIN CAJERO C ON DC.idcajero = C.idCajero   
JOIN DENOMINACION D ON DC.idDenominacion = D.idDenominacion
ORDER BY C.ubicacion, D.denominacion DESC;

-- PROCEDIMIENTO ALMACENADO PARA RETIROS
CREATE OR REPLACE PROCEDURE RetiroSP (
    p_NumTarjeta  IN VARCHAR2,
    p_idCajero    IN NUMBER,
    p_monto       IN NUMBER,
    p_resultado   OUT SYS_REFCURSOR
) AS
    v_idTarjeta       NUMBER;
    v_idUsuario       NUMBER;
    v_saldoActual     NUMBER;
    v_minRetiro       NUMBER;
    v_maxRetiro       NUMBER;
    v_idTransaccion   NUMBER;
    v_montoRestante   NUMBER;
    v_piezasEntregar  NUMBER;
    v_statusTarjeta   NUMBER;
    
    CURSOR c_denominaciones IS
        SELECT dc.idDenominacion, d.denominacion, dc.cantidadDisponible
        FROM DETALLECAJERO dc
        JOIN DENOMINACION d ON dc.idDenominacion = d.idDenominacion
        WHERE dc.idcajero = p_idCajero AND dc.cantidadDisponible > 0
        ORDER BY d.denominacion DESC;

    EXC_FONDOS_INSUFICIENTES EXCEPTION;
    EXC_LIMITES_RANGO        EXCEPTION;
    EXC_SIN_EFECTIVO_CAJERO  EXCEPTION;
    EXC_TARJETA_INACTIVA     EXCEPTION;
    EXC_TARJETA_NO_EXISTE    EXCEPTION;
    
    PRAGMA EXCEPTION_INIT(EXC_FONDOS_INSUFICIENTES, -20002);
    PRAGMA EXCEPTION_INIT(EXC_LIMITES_RANGO, -20001);
    PRAGMA EXCEPTION_INIT(EXC_SIN_EFECTIVO_CAJERO, -20003);
    PRAGMA EXCEPTION_INIT(EXC_TARJETA_INACTIVA, -20005);
    PRAGMA EXCEPTION_INIT(EXC_TARJETA_NO_EXISTE, -20004);
BEGIN
    -- Obtener datos de la tarjeta usando el número de tarjeta
    BEGIN
        SELECT t.idTarjeta, t.idUsuario, t.status_tarjeta, r.minRetiro, r.maxRetiro
        INTO v_idTarjeta, v_idUsuario, v_statusTarjeta, v_minRetiro, v_maxRetiro
        FROM TARJETA t
        JOIN RANGO r ON t.idRango = r.idRango
        WHERE t.NumTarjeta = p_NumTarjeta;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE EXC_TARJETA_NO_EXISTE;
    END;
    
    -- Verificar que la tarjeta esté activa
    IF v_statusTarjeta != 1 THEN
        RAISE EXC_TARJETA_INACTIVA;
    END IF;

    -- Verificar límites de la tarjeta
    IF p_monto < v_minRetiro OR p_monto > v_maxRetiro THEN
        RAISE EXC_LIMITES_RANGO;
    END IF;

    -- Verificar saldo del usuario
    SELECT saldo INTO v_saldoActual
    FROM CUENTA
    WHERE idUsuario = v_idUsuario;

    IF v_saldoActual < p_monto THEN
        RAISE EXC_FONDOS_INSUFICIENTES;
    END IF;

    -- Registrar la transacción inicial
    INSERT INTO TRANSACCION (idTarjeta, idCajero, monto, fecha, estado)
    VALUES (v_idTarjeta, p_idCajero, p_monto, SYSDATE, 0)
    RETURNING idTransaccion INTO v_idTransaccion;

    -- ALGORITMO DE DESGLOSE DE EFECTIVO
    v_montoRestante := p_monto;
    
    FOR rec IN c_denominaciones LOOP
        IF v_montoRestante >= rec.denominacion THEN
            v_piezasEntregar := FLOOR(v_montoRestante / rec.denominacion);
            
            IF v_piezasEntregar > rec.cantidadDisponible THEN
                v_piezasEntregar := rec.cantidadDisponible;
            END IF;
            
            IF v_piezasEntregar > 0 THEN
                INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada)
                VALUES (v_idTransaccion, rec.idDenominacion, v_piezasEntregar);
                
                UPDATE DETALLECAJERO
                SET cantidadDisponible = cantidadDisponible - v_piezasEntregar
                WHERE idcajero = p_idCajero AND idDenominacion = rec.idDenominacion;
                
                v_montoRestante := ROUND(v_montoRestante - (v_piezasEntregar * rec.denominacion), 2);
            END IF;
        END IF;
        
        EXIT WHEN v_montoRestante = 0;
    END LOOP;

    -- VALIDACIÓN FINAL DE SALDO DEL CAJERO
    IF v_montoRestante > 0 THEN
        RAISE EXC_SIN_EFECTIVO_CAJERO;
    END IF;

    -- Actualizar saldo de la cuenta
    UPDATE CUENTA
    SET saldo = saldo - p_monto
    WHERE idUsuario = v_idUsuario;

    -- Actualizar estado de transacción a exitoso
    UPDATE TRANSACCION SET estado = 1 WHERE idTransaccion = v_idTransaccion;

    COMMIT;

    -- DEVOLVER LA TABLA DE RESULTADOS
    OPEN p_resultado FOR
        SELECT 
            T.idTransaccion AS Folio,
            U.nombre || ' ' || U.apellidoPaterno || ' ' || U.apellidoMaterno AS Usuario,
            CU.NumCuenta AS NUMEROCUENTA,
            TJ.NumTarjeta AS TARJETAUSADA,
            CASE T.estado 
                WHEN 1 THEN 'EXITOSO' 
                ELSE 'PENDIENTE/ERROR' 
            END AS StatusTransaccion,
            DT.CantidadEntregada AS Cantidad,
            D.denominacion AS Denominacion,
            D.tipo AS Tipo,
            (D.denominacion * DT.CantidadEntregada) AS Subtotal
        FROM TRANSACCION T
        JOIN TARJETA TJ ON T.idTarjeta = TJ.idTarjeta
        JOIN USUARIO U ON TJ.idUsuario = U.idUsuario
        JOIN CUENTA CU ON U.idUsuario = CU.idUsuario
        JOIN DETALLETRANSACCION DT ON T.idTransaccion = DT.idTransaccion
        JOIN DENOMINACION D ON DT.idDenominacion = D.idDenominacion
        WHERE T.idTransaccion = v_idTransaccion;

EXCEPTION
    WHEN EXC_TARJETA_NO_EXISTE THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'Error: Tarjeta no encontrada.');
        
    WHEN EXC_TARJETA_INACTIVA THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20005, 'Error: Tarjeta inactiva.');
        
    WHEN EXC_LIMITES_RANGO THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20001, 'Error: Monto fuera de límites (' || v_minRetiro || ' - ' || v_maxRetiro || ').');
        
    WHEN EXC_FONDOS_INSUFICIENTES THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20002, 'Error: Saldo insuficiente del usuario.');
        
    WHEN EXC_SIN_EFECTIVO_CAJERO THEN
        ROLLBACK; 
        RAISE_APPLICATION_ERROR(-20003, 'Error: El cajero no cuenta con el efectivo suficiente para entregar el monto exacto.');
        
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20099, 'Error inesperado: ' || SQLERRM);
END RetiroSP;
/
-- PROCEDIMIENTO PARA CREAR CAJERO CON DINERO
CREATE OR REPLACE PROCEDURE PR_CREAR_CAJERO (
    p_ubicacion IN VARCHAR2,
    p_estado    IN VARCHAR2
)
AS
    v_idCajero NUMBER;
    v_id_1000 NUMBER;
    v_id_500 NUMBER;
    v_id_200 NUMBER;
    v_id_100 NUMBER;
    v_id_50 NUMBER;
    v_id_20 NUMBER;
    v_id_10 NUMBER;
    v_id_5 NUMBER;
    v_id_2 NUMBER;
    v_id_1 NUMBER;
    v_id_05 NUMBER;
BEGIN
    -- Obtener IDs de denominaciones
    SELECT idDenominacion INTO v_id_1000 FROM DENOMINACION WHERE denominacion = 1000;
    SELECT idDenominacion INTO v_id_500 FROM DENOMINACION WHERE denominacion = 500;
    SELECT idDenominacion INTO v_id_200 FROM DENOMINACION WHERE denominacion = 200;
    SELECT idDenominacion INTO v_id_100 FROM DENOMINACION WHERE denominacion = 100;
    SELECT idDenominacion INTO v_id_50 FROM DENOMINACION WHERE denominacion = 50;
    SELECT idDenominacion INTO v_id_20 FROM DENOMINACION WHERE denominacion = 20;
    SELECT idDenominacion INTO v_id_10 FROM DENOMINACION WHERE denominacion = 10;
    SELECT idDenominacion INTO v_id_5 FROM DENOMINACION WHERE denominacion = 5;
    SELECT idDenominacion INTO v_id_2 FROM DENOMINACION WHERE denominacion = 2;
    SELECT idDenominacion INTO v_id_1 FROM DENOMINACION WHERE denominacion = 1;
    SELECT idDenominacion INTO v_id_05 FROM DENOMINACION WHERE denominacion = 0.5;

    -- Crear cajero
    INSERT INTO CAJERO (ubicacion, estado)
    VALUES (p_ubicacion, p_estado)
    RETURNING idCajero INTO v_idCajero;

    -- Asignar efectivo inicial
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_1000, 2);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_500, 5);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_200, 10);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_100, 20);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_50, 30);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_20, 40);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_10, 50);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_5, 100);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_2, 200);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_1, 300);
    INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (v_idCajero, v_id_05, 100);

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Cajero creado correctamente con ID: ' || v_idCajero || ' y efectivo total de 12,550 pesos');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20001, 'Error al crear cajero: ' || SQLERRM);
END PR_CREAR_CAJERO;
/


-- EJEMPLO DE USO DEL PROCEDIMIENTO RetiroSP
-- Variable para el cursor
VARIABLE cursor_resultado REFCURSOR;

-- Ejecutar retiro (ejemplo: tarjeta 2, cajero 2, monto 12550)
BEGIN
    RetiroSP(
        p_idTarjeta => 2, 
        p_idCajero => 2, 
        p_monto => 12550, 
        p_resultado => :cursor_resultado
    );
END;
/

-- Mostrar resultados
PRINT cursor_resultado;

-- EJEMPLO DE CREACIÓN DE CAJERO
BEGIN
    PR_CREAR_CAJERO(
        p_ubicacion => 'Sucursal Nueva',
        p_estado => 'Activo'
    );
END;
/




-- Procedimiento para obtener usuario completo por ID
CREATE OR REPLACE PROCEDURE SP_OBTENER_USUARIO_COMPLETO (
    p_idUsuario IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT 
            -- Datos del Usuario
            U.idUsuario,
            U.nombre,
            U.apellidoPaterno,
            U.apellidoMaterno,
            U.celuar AS celular,
            U.telefono,
            U.email,
            -- Datos de la Cuenta
            C.idCuenta,
            C.NumCuenta,
            C.saldo,
            -- Datos del Banco (de la cuenta)
            B.idBanco AS idBancoCuenta,
            B.nombre AS nombreBancoCuenta,
            -- Datos de la Tarjeta
            T.idTarjeta,
            T.NumTarjeta,
            T.pin,
            T.fechaVencimiento,
            T.status_tarjeta AS status,
            -- Datos del Banco (de la tarjeta)
            TB.idBanco AS idBancoTarjeta,
            TB.nombre AS nombreBancoTarjeta,
            -- Datos del Rango
            R.idRango,
            R.nombre AS nombreRango,
            R.minRetiro,
            R.maxRetiro
        FROM USUARIO U
        LEFT JOIN CUENTA C ON U.idUsuario = C.idUsuario
        LEFT JOIN BANCO B ON C.idBanco = B.idBanco
        LEFT JOIN TARJETA T ON U.idUsuario = T.idUsuario
        LEFT JOIN BANCO TB ON T.idBanco = TB.idBanco
        LEFT JOIN RANGO R ON T.idRango = R.idRango
        WHERE U.idUsuario = p_idUsuario;
END SP_OBTENER_USUARIO_COMPLETO;
/

-- Procedimiento para obtener usuario por número de tarjeta
CREATE OR REPLACE PROCEDURE SP_OBTENER_USUARIO_POR_TARJETA (
    p_numTarjeta IN VARCHAR2,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_cursor FOR
        SELECT 
            -- Datos del Usuario
            U.idUsuario,
            U.nombre,
            U.apellidoPaterno,
            U.apellidoMaterno,
            U.celuar AS celular,
            U.telefono,
            U.email,
            -- Datos de la Cuenta
            C.idCuenta,
            C.NumCuenta,
            C.saldo,
            -- Datos del Banco (de la cuenta)
            B.idBanco AS idBancoCuenta,
            B.nombre AS nombreBancoCuenta,
            -- Datos de la Tarjeta
            T.idTarjeta,
            T.NumTarjeta,
            T.pin,
            T.fechaVencimiento,
            T.status_tarjeta AS status,
            -- Datos del Banco (de la tarjeta)
            TB.idBanco AS idBancoTarjeta,
            TB.nombre AS nombreBancoTarjeta,
            -- Datos del Rango
            R.idRango,
            R.nombre AS nombreRango,
            R.minRetiro,
            R.maxRetiro
        FROM TARJETA T
        INNER JOIN USUARIO U ON T.idUsuario = U.idUsuario
        LEFT JOIN CUENTA C ON U.idUsuario = C.idUsuario
        LEFT JOIN BANCO B ON C.idBanco = B.idBanco
        LEFT JOIN BANCO TB ON T.idBanco = TB.idBanco
        LEFT JOIN RANGO R ON T.idRango = R.idRango
        WHERE T.NumTarjeta = p_numTarjeta;
END SP_OBTENER_USUARIO_POR_TARJETA;
/