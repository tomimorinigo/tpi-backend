DROP TABLE IF EXISTS Posiciones;
CREATE TABLE Posiciones
(
    ID          INTEGER                               NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ID_VEHICULO INTEGER                               NOT NULL,
    FECHA_HORA  TIMESTAMP                             NOT NULL,
    LATITUD     REAL                                  NOT NULL,
    LONGITUD    REAL                                  NOT NULL
);

DROP TABLE IF EXISTS Notificaciones_Incidencias;
CREATE TABLE Notificaciones_Incidencias
(
    ID          INTEGER                            AUTO_INCREMENT   NOT NULL PRIMARY KEY,
    ID_VEHICULO INTEGER                               NOT NULL,
    ID_INTERESADO INTEGER                             NOT NULL,
    ID_PRUEBA   INTEGER                               NOT NULL,
    ID_EMPLEADO INTEGER                              NOT NULL,
    TIPO_INCIDENTE VARCHAR(50)                       NOT NULL, -- Ej.: 'Fuera de radio', 'Zona peligrosa'
    FECHA_HORA  TIMESTAMP                             NOT NULL
);

DROP TABLE IF EXISTS Notificaciones_Promociones;
CREATE TABLE Notificaciones_Promociones
(
    ID          INTEGER                               NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ID_VEHICULO INTEGER                               NOT NULL,
    PORCENTAJE_OFERTA REAL                            NOT NULL,
    FECHA_HORA  TIMESTAMP                             NOT NULL,
    FECHA_FIN   TIMESTAMP                             NOT NULL
);

DROP TABLE IF EXISTS Destinatarios_Promociones;
CREATE TABLE Destinatarios_Promociones
(
    ID          INTEGER                               NOT NULL PRIMARY KEY AUTO_INCREMENT,
    NOTIFICACION_ID INTEGER                            NOT NULL,
    EMAIL     VARCHAR(150)                          NOT NULL,
    FOREIGN KEY (NOTIFICACION_ID) REFERENCES Notificaciones_Promociones (ID)
);

SELECT * FROM Posiciones;
SELECT * FROM Notificaciones_Incidencias;
SELECT * FROM Notificaciones_Promociones;
SELECT * FROM Destinatarios_Promociones;