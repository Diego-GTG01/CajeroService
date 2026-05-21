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

CREATE TABLE Cajero(
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

// relaciones

CREATE TABLE CUENTA(
    idCuenta number generated always as identity,
    idUsuario number,
    idBanco number, 
    NumCuenta number,
    saldo number,
    
    FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario),
    FOREIGN KEY (idBanco) REFERENCES Banco (idBanco)

);

CREATE TABLE TARJETA(
    idTarjeta number generated always as identity,
    idUsuario number, 
    idRango number, 
    idBanco number, 
    NumTarjeta varchar(200),
    pin varchar(200),
    fechaVencimiento DATE,
    STATUS number,
    primary key(idTarjeta),
    FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario),
    FOREIGN KEY (idRango) REFERENCES Rango (idRango),
    FOREIGN KEY (idBanco) REFERENCES Banco (idBanco)  
);

CREATE TABLE DetalleCajero(
    idDetalleCajero number generated always as identity,
    idcajero number,
    idDenominacion number,
    cantidadDisponible number
);

CREATE TABLE TRANSACCION(
    idTransaccion number generated always as identity,
    idTarjeta number,
    idCajero number,
    monto number,
    fecha DATE, 
    estado number, 
    primary key(idTransaccion),
    
    FOREIGN KEY (idTarjeta) REFERENCES Tarjeta (idTarjeta),
    FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero)
);

CREATE TABLE DETALLETRANSACCION(
    idDetalleTransaccion number generated always as identity,
    idTransaccion number,
    idDenominacion number,
    CantidadEntregada number,
    primary key (idDetalleTransaccion),
    FOREIGN KEY (idTransaccion) REFERENCES Transaccion (idTransaccion),
    FOREIGN KEY (idDenominacion) REFERENCES Denominacion (idDenominacion)

);


//datos de prueba


-- Usuarios
INSERT INTO USUARIO (nombre, apellidoPaterno, apellidoMaterno, celuar, telefono, email) 
VALUES ('Carlos', 'Mendoza', 'Ruiz', '5551234567', '5559876543', 'carlos@email.com');
INSERT INTO USUARIO (nombre, apellidoPaterno, apellidoMaterno, celuar, telefono, email) 
VALUES ('Ana', 'Gomez', 'Lopez', '5557654321', '5551112233', 'ana@email.com');

-- Rangos de Retiro
INSERT INTO RANGO (nombre, minRetiro, maxRetiro) VALUES ('Estandar', 100, 5000);
INSERT INTO RANGO (nombre, minRetiro, maxRetiro) VALUES ('Premium', 100, 15000);

-- Denominaciones de Billetes

INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 0.5);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 1);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 2);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 5);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 10);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 20);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 50);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 200);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 500);
INSERT INTO DENOMINACION (tipo, denominacion) VALUES ('Billete', 1000);

-- Cajeros
INSERT INTO CAJERO (ubicacion, estado) VALUES ('Centro Comercial Plaza', 'Activo');
INSERT INTO CAJERO (ubicacion, estado) VALUES ('Sucursal Avenida Central', 'Activo');
INSERT INTO CAJERO (ubicacion, estado) VALUES ('Sucursal Calle Reforma', 'Activo');

-- Bancos
INSERT INTO BANCO (nombre) VALUES ('Banco Alfa');
INSERT INTO BANCO (nombre) VALUES ('Banco Beta');

-- Cuentas (Carlos en Banco Alfa, Ana en Banco Beta)
INSERT INTO CUENTA (idUsuario, idBanco, NumCuenta, saldo) VALUES (1, 1, 123456789, 25000);
INSERT INTO CUENTA (idUsuario, idBanco, NumCuenta, saldo) VALUES (2, 2, 987654321, 8500);

UPDATE CUENTA SET saldo = 50000 WHERE NumCuenta = 123456789;

-- Tarjetas (Ligadas a los usuarios, rangos y bancos correspondientes)
INSERT INTO TARJETA (idUsuario, idRango, idBanco, NumTarjeta, pin, fechaVencimiento, STATUS) 
VALUES (1, 2, 1, '1234567812345678', '1234', TO_DATE('2030-12-31', 'YYYY-MM-DD'), 1); -- Activa
INSERT INTO TARJETA (idUsuario, idRango, idBanco, NumTarjeta, pin, fechaVencimiento, STATUS) 
VALUES (2, 1, 2, '8765432187654321', '4321', TO_DATE('2029-05-15', 'YYYY-MM-DD'), 1); -- Activa

-- Detalle del Cajero (Billetes cargados en el Cajero 1)
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (1, 1, 500); -- 500 billetes de 100
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (1, 2, 300); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (1, 3, 200); -- 200 billetes de 500
-- Detalle del Cajero (Billetes cargados en el Cajero 2)
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 17, 2); -- 500 billetes de 100
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 3, 5); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 2, 10); -- 200 billetes de 500
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 1, 20); -- 500 billetes de 100
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 10, 30); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 9, 40); -- 200 billetes de 500
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 8, 50); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 7, 100); -- 200 billetes de 500
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 6, 200); -- 500 billetes de 100
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 5, 300); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (2, 4, 100); -- 200 billetes de 500
-- Detalle del Cajero (Billetes cargados en el Cajero 3)
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 17, 2); -- 500 billetes de 100
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 3, 5); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 2, 10); -- 200 billetes de 500
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 1, 20); -- 500 billetes de 100
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 10, 30); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 9, 40); -- 200 billetes de 500
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 8, 50); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 7, 100); -- 200 billetes de 500
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 6, 200); -- 500 billetes de 100
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 5, 300); -- 300 billetes de 200
INSERT INTO DETALLECAJERO (idcajero, idDenominacion, cantidadDisponible) VALUES (3, 4, 100); -- 200 billetes de 500

-- Transacciones (Carlos retira 700 pesos en el Cajero 1 el d�a de hoy)
INSERT INTO TRANSACCION (idTarjeta, idCajero, monto, fecha, estado) 
VALUES (1, 1, 700, SYSDATE, 1); -- Estado 1 = Exitoso

-- Detalle de la Transacci�n (C�mo se entregaron los 700 pesos: 1 de 500 y 1 de 200)
INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada) VALUES (1, 3, 1); -- 1 billete de 500
INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada) VALUES (1, 2, 1); -- 1 billete de 200


//consultas de prueba 
//Saldo disponible del usuario
SELECT 
    U.nombre || ' ' || U.apellidoPaterno AS Cliente,
    B.nombre AS Banco,
    C.NumCuenta,
    C.saldo AS Saldo_Disponible
FROM CUENTA C
JOIN USUARIO U ON C.idUsuario = U.idUsuario
JOIN BANCO B ON C.idBanco = B.idBanco;

//dinero total de cada cajero
SELECT 
    C.idCajero,
    C.ubicacion AS Cajero,
    SUM(D.denominacion * DC.cantidadDisponible) AS Dinero_Total_Disponible
FROM DetalleCajero DC
JOIN CAJERO C ON DC.idcajero = C.idCajero
JOIN DENOMINACION D ON DC.idDenominacion = D.idDenominacion
GROUP BY C.idCajero, C.ubicacion
ORDER BY C.idCajero;

//transaccion dinero retirado
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


//3
SELECT 
    DT.idTransaccion AS Folio_Transaccion,
    D.denominacion AS Billete_De,
    DT.CantidadEntregada AS Cantidad,
    (D.denominacion * DT.CantidadEntregada) AS Subtotal
FROM DETALLETRANSACCION DT
JOIN DENOMINACION D ON DT.idDenominacion = D.idDenominacion
WHERE DT.idTransaccion = 2;

//total billetes 

SELECT 
    C.ubicacion AS Cajero,
    D.denominacion AS Denominacion_Billete,
    DC.cantidadDisponible AS Billetes_Restantes,
    (D.denominacion * DC.cantidadDisponible) AS Dinero_Total_Disponible_Mxn
FROM DETALLECAJERO DC
JOIN CAJERO C ON DC.idcajero = C.idCajero   
JOIN DENOMINACION D ON DC.idDenominacion = D.idDenominacion
ORDER BY C.ubicacion, D.denominacion DESC;

//stored procedure
CREATE OR REPLACE PROCEDURE RetiroSP (
    p_idTarjeta   IN NUMBER,
    p_idCajero    IN NUMBER,
    p_monto       IN NUMBER,
    p_resultado   OUT SYS_REFCURSOR
) AS
    v_idUsuario       NUMBER;
    v_saldoActual     NUMBER;
    v_minRetiro       NUMBER;
    v_maxRetiro       NUMBER;
    v_idTransaccion   NUMBER;
    v_montoRestante   NUMBER;
    v_piezasEntregar  NUMBER;
    
    CURSOR c_denominaciones IS
        SELECT dc.idDenominacion, d.denominacion, dc.cantidadDisponible
        FROM DetalleCajero dc
        JOIN DENOMINACION d ON dc.idDenominacion = d.idDenominacion
        WHERE dc.idcajero = p_idCajero AND dc.cantidadDisponible > 0
        ORDER BY d.denominacion DESC;

    EXC_FONDOS_INSUFICIENTES EXCEPTION;
    EXC_LIMITES_RANGO        EXCEPTION;
    EXC_SIN_EFECTIVO_CAJERO  EXCEPTION;
BEGIN
    -- Obtener datos de la tarjeta
    SELECT t.idUsuario, r.minRetiro, r.maxRetiro
    INTO v_idUsuario, v_minRetiro, v_maxRetiro
    FROM TARJETA t
    JOIN RANGO r ON t.idRango = r.idRango
    WHERE t.idTarjeta = p_idTarjeta AND t.STATUS = 1;

    -- Verificar límites de la tarjeta 
    IF p_monto <= v_minRetiro OR p_monto >= v_maxRetiro THEN
        RAISE EXC_LIMITES_RANGO;
    END IF;

    --Verificar saldo del usuario
    SELECT saldo INTO v_saldoActual
    FROM CUENTA
    WHERE idUsuario = v_idUsuario;

    IF v_saldoActual < p_monto THEN
        RAISE EXC_FONDOS_INSUFICIENTES;
    END IF;

    --Registrar la transacción inicial
    INSERT INTO TRANSACCION (idTarjeta, idCajero, monto, fecha, estado)
    VALUES (p_idTarjeta, p_idCajero, p_monto, SYSDATE, 0)
    RETURNING idTransaccion INTO v_idTransaccion;

    -- ALGORITMO DE DESGLOSE DE EFECTIVO
    v_montoRestante := p_monto;
    
    FOR rec IN c_denominaciones LOOP
        IF v_montoRestante >= rec.denominacion THEN
            v_piezasEntregar := TRUNC(v_montoRestante / rec.denominacion);
            
            IF v_piezasEntregar > rec.cantidadDisponible THEN
                v_piezasEntregar := rec.cantidadDisponible;
            END IF;
            
            IF v_piezasEntregar > 0 THEN
                INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada)
                VALUES (v_idTransaccion, rec.idDenominacion, v_piezasEntregar);
                
                UPDATE DetalleCajero
                SET cantidadDisponible = cantidadDisponible - v_piezasEntregar
                WHERE idcajero = p_idCajero AND idDenominacion = rec.idDenominacion;
                
                v_montoRestante := v_montoRestante - (v_piezasEntregar * rec.denominacion);
            END IF;
        END IF;
    END LOOP;

    -- VALIDACIÓN FINAL DE SALDO DEL CAJERO 
    IF v_montoRestante > 0 THEN
        RAISE EXC_SIN_EFECTIVO_CAJERO;
    END IF;

    -- Actualizar saldo
    UPDATE CUENTA
    SET saldo = saldo - p_monto
    WHERE idUsuario = v_idUsuario;

    -- Actualizar estado de transacción
    UPDATE TRANSACCION SET estado = 1 WHERE idTransaccion = v_idTransaccion;

    -- DEVOLVER LA TABLA DE RESULTADOS
    OPEN p_resultado FOR
        SELECT 
            T.idTransaccion AS Folio,
            U.nombre || ' ' || U.apellidoPaterno || ' ' || U.apellidoMaterno AS Usuario,
            TJ.NumTarjeta AS Tarjeta_Usada,
            CASE T.estado 
                WHEN 1 THEN 'EXITOSO' 
                ELSE 'PENDIENTE/ERROR' 
            END AS Status_Transaccion,
            DT.CantidadEntregada AS Cantidad,
            D.denominacion AS Denominacion,
            D.tipo AS Tipo,
            (D.denominacion * DT.CantidadEntregada) AS Subtotal
        FROM TRANSACCION T
        JOIN TARJETA TJ ON T.idTarjeta = TJ.idTarjeta
        JOIN USUARIO U ON TJ.idUsuario = U.idUsuario
        JOIN DETALLETRANSACCION DT ON T.idTransaccion = DT.idTransaccion
        JOIN DENOMINACION D ON DT.idDenominacion = D.idDenominacion
        WHERE T.idTransaccion = v_idTransaccion;

    COMMIT;

EXCEPTION
    WHEN EXC_LIMITES_RANGO THEN
        ROLLBACK;
        raise_application_error(-20001, 'Error: Monto fuera de límites (' || v_minRetiro || ' - ' || v_maxRetiro || ').');
        
    WHEN EXC_FONDOS_INSUFICIENTES THEN
        ROLLBACK;
        raise_application_error(-20002, 'Error: Saldo insuficiente del usuario.');
        
    WHEN EXC_SIN_EFECTIVO_CAJERO THEN
        ROLLBACK; 
        raise_application_error(-20003, 'Error: El cajero no cuenta con el efectivo suficiente.');
        
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        raise_application_error(-20004, 'Error: Datos no encontrados. Verifica IDs.');
        
    WHEN OTHERS THEN
        ROLLBACK;
        raise_application_error(-20099, 'Error inesperado: ' || SQLERRM);
END;



VARIABLE cursor REFCURSOR;
CALL RetiroSP(p_idTarjeta => 2, p_idCajero => 2, p_monto => 12550, p_resultado => :cursor);
PRINT cursor;


//crear cajero con dinero
CREATE OR REPLACE PROCEDURE PR_CREAR_CAJERO (
    p_ubicacion IN VARCHAR2,
    p_estado    IN VARCHAR2
)
AS
    v_idCajero NUMBER;
BEGIN

    -- Crear cajero
    INSERT INTO CAJERO (ubicacion, estado)
    VALUES (p_ubicacion, p_estado)
    RETURNING idcajero INTO v_idCajero;

    -- Asignar efectivo inicial = 12,550

    -- 2 billetes de 1000 = 2000
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 17, 2);

    -- 5 billetes de 500 = 2500
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 3, 5);

    -- 10 billetes de 200 = 2000
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 2, 10);

    -- 20 billetes de 100 = 2000
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 1, 20);

    -- 30 billetes de 50 = 1500
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 10, 30);

    -- 40 billetes de 20 = 800
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 9, 40);

    -- 50 monedas de 10 = 500
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 8, 50);

    -- 100 monedas de 5 = 500
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 7, 100);

    -- 200 monedas de 2 = 400
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 6, 200);

    -- 300 monedas de 1 = 300
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 5, 300);

    -- 100 monedas de 0.5 = 50
    INSERT INTO DETALLECAJERO 
    (idcajero, idDenominacion, cantidadDisponible)
    VALUES (v_idCajero, 4, 100);

    COMMIT;

    DBMS_OUTPUT.PUT_LINE(
        'Cajero creado correctamente con ID: ' || v_idCajero
    );

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(
            -20001,
            'Error al crear cajero: ' || SQLERRM
        );
END;

BEGIN
    PR_CREAR_CAJERO(
        'Sucursal Avenida Central',
        'Activo'
    );
END;

