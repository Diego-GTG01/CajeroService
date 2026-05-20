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

-- Bancos
INSERT INTO BANCO (nombre) VALUES ('Banco Alfa');
INSERT INTO BANCO (nombre) VALUES ('Banco Beta');




-- Cuentas (Carlos en Banco Alfa, Ana en Banco Beta)
INSERT INTO CUENTA (idUsuario, idBanco, NumCuenta, saldo) VALUES (1, 1, 123456789, 25000);
INSERT INTO CUENTA (idUsuario, idBanco, NumCuenta, saldo) VALUES (2, 2, 987654321, 8500);

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

-- Transacciones (Carlos retira 700 pesos en el Cajero 1 el d�a de hoy)
INSERT INTO TRANSACCION (idTarjeta, idCajero, monto, fecha, estado) 
VALUES (1, 1, 700, SYSDATE, 1); -- Estado 1 = Exitoso

-- Detalle de la Transacci�n (C�mo se entregaron los 700 pesos: 1 de 500 y 1 de 200)
INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada) VALUES (1, 3, 1); -- 1 billete de 500
INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada) VALUES (1, 2, 1); -- 1 billete de 200


//consultas de prueba 
//1
SELECT 
    U.nombre || ' ' || U.apellidoPaterno AS Cliente,
    B.nombre AS Banco,
    C.NumCuenta,
    C.saldo AS Saldo_Disponible
FROM CUENTA C
JOIN USUARIO U ON C.idUsuario = U.idUsuario
JOIN BANCO B ON C.idBanco = B.idBanco;

//2
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

//4

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
CREATE OR REPLACE PROCEDURE PR_REALIZAR_RETIRO (
    p_idTarjeta  IN NUMBER,
    p_idCajero   IN NUMBER,
    p_monto      IN NUMBER
) AS
    v_idUsuario       NUMBER;
    v_saldoActual     NUMBER;
    v_minRetiro       NUMBER;
    v_maxRetiro       NUMBER;
    v_idTransaccion   NUMBER;
    v_montoRestante   NUMBER;
    v_piezasEntregar  NUMBER;
    
    -- Cursor para recorrer las denominaciones disponibles en ESTE cajero ordenadas de MAYOR a MENOR
    CURSOR c_denominaciones IS
        SELECT dc.idDenominacion, d.denominacion, dc.cantidadDisponible
        FROM DetalleCajero dc
        JOIN DENOMINACION d ON dc.idDenominacion = d.idDenominacion
        WHERE dc.idcajero = p_idCajero AND dc.cantidadDisponible > 0
        ORDER BY d.denominacion DESC;

    -- Excepciones personalizadas
    EXC_FONDOS_INSUFICIENTES EXCEPTION;
    EXC_LIMITES_RANGO        EXCEPTION;
    EXC_SIN_EFECTIVO_CAJERO  EXCEPTION;
BEGIN
    -- 1. VALIDACI�N: Obtener datos de la tarjeta y l�mites de retiro del rango
    SELECT t.idUsuario, r.minRetiro, r.maxRetiro
    INTO v_idUsuario, v_minRetiro, v_maxRetiro
    FROM TARJETA t
    JOIN RANGO r ON t.idRango = r.idRango
    WHERE t.idTarjeta = p_idTarjeta AND t.STATUS = 1;

    -- 2. VALIDACI�N: Verificar l�mites permitidos por su Rango
    IF p_monto < v_minRetiro OR p_monto > v_maxRetiro THEN
        RAISE EXC_LIMITES_RANGO;
    END IF;

    -- 3. VALIDACI�N: Verificar saldo en la cuenta del usuario
    SELECT saldo INTO v_saldoActual
    FROM CUENTA
    WHERE idUsuario = v_idUsuario;

    IF v_saldoActual < p_monto THEN
        RAISE EXC_FONDOS_INSUFICIENTES;
    END IF;

    -- 4. PROCESO PRINCIPAL: Registrar la transacci�n inicial (como pendiente/proceso: estado 0)
    INSERT INTO TRANSACCION (idTarjeta, idCajero, monto, fecha, estado)
    VALUES (p_idTarjeta, p_idCajero, p_monto, SYSDATE, 0)
    RETURNING idTransaccion INTO v_idTransaccion;

    -- 5. ALGORITMO DE DESGLOSE DE EFECTIVO
    v_montoRestante := p_monto;
    
    DBMS_OUTPUT.PUT_LINE('--- DESGLOSE DE EFECTIVO ENTREGADO ---');
    
    FOR rec IN c_denominaciones LOOP
        IF v_montoRestante >= rec.denominacion THEN
            -- Calcular cu�ntas piezas te�ricas se necesitan
            v_piezasEntregar := TRUNC(v_montoRestante / rec.denominacion);
            
            -- Si el cajero no tiene suficientes piezas de esta denominaci�n, toma todas las que tenga
            IF v_piezasEntregar > rec.cantidadDisponible THEN
                v_piezasEntregar := rec.cantidadDisponible;
            END IF;
            
            IF v_piezasEntregar > 0 THEN
                -- Insertar en el detalle de la transacci�n
                INSERT INTO DETALLETRANSACCION (idTransaccion, idDenominacion, CantidadEntregada)
                VALUES (v_idTransaccion, rec.idDenominacion, v_piezasEntregar);
                
                -- Actualizar el inventario del cajero autom�tico
                UPDATE DetalleCajero
                SET cantidadDisponible = cantidadDisponible - v_piezasEntregar
                WHERE idcajero = p_idCajero AND idDenominacion = rec.idDenominacion;
                
                DBMS_OUTPUT.PUT_LINE('Entregado: ' || v_piezasEntregar || ' de $' || rec.denominacion);
                
                -- Restar del monto pendiente
                v_montoRestante := v_montoRestante - (v_piezasEntregar * rec.denominacion);
            END IF;
        END IF;
    END LOOP;

    -- 6. VALIDACI�N FINAL: �El cajero pudo completar el monto exacto solicitado?
    IF v_montoRestante > 0 THEN
        -- Si qued� un remanente, significa que el cajero no tiene combinaciones o efectivo suficiente
        RAISE EXC_SIN_EFECTIVO_CAJERO;
    END IF;

    -- 7. FINALIZACI�N EXITOSA: Restar dinero de la cuenta del usuario y confirmar transacci�n
    UPDATE CUENTA
    SET saldo = saldo - p_monto
    WHERE idUsuario = v_idUsuario;

    -- Cambiar estado de transacci�n a 1 (Exitoso)
    UPDATE TRANSACCION SET estado = 1 WHERE idTransaccion = v_idTransaccion;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Retiro finalizado con �xito. Cuenta actualizada.');

EXCEPTION
    WHEN EXC_LIMITES_RANGO THEN
        ROLLBACK;
        raise_application_error(-20001, 'Error: El monto solicitado est� fuera de los l�mites de tu rango (' || v_minRetiro || ' - ' || v_maxRetiro || ').');
        
    WHEN EXC_FONDOS_INSUFICIENTES THEN
        ROLLBACK;
        raise_application_error(-20002, 'Error: Tu cuenta no cuenta con saldo suficiente para este retiro.');
        
    WHEN EXC_SIN_EFECTIVO_CAJERO THEN
        ROLLBACK; -- Deshace los inserts/updates intermedios de billetes y transacciones
        raise_application_error(-20003, 'Error: El cajero no cuenta con las denominaciones o el efectivo suficiente para completar tu retiro.');
        
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        raise_application_error(-20004, 'Error: Datos no encontrados. Verifica los IDs de Tarjeta, Cuenta o Cajero.');
        
    WHEN OTHERS THEN
        ROLLBACK;
        raise_application_error(-20099, 'Error inesperado: ' || SQLERRM);
END;


EXEC PR_REALIZAR_RETIRO(p_idTarjeta => 2, p_idCajero => 1, p_monto => 1300);
