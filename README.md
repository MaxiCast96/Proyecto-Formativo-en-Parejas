
# Proyecto Formativo PERJEAS

Bienvenido a la documentación del proyecto formativo PERJEAS. Este proyecto incluye la gestión de pacientes y medicamentos utilizando una base de datos relacional. A continuación, se detallan las estructuras de las tablas y algunos ejemplos de inserciones de datos.

## Base de Datos

### Estructura de Tablas

#### Tabla Pacientes

```sql
CREATE TABLE Pacientes (
    id_paciente VARCHAR(36) PRIMARY KEY,
    nombres VARCHAR2(100) NOT NULL,
    apellidos VARCHAR2(100) NOT NULL,
    edad NUMBER NOT NULL,
    enfermedad VARCHAR2(100) NOT NULL,
    numero_habitacion NUMBER NOT NULL,
    numero_cama NUMBER NOT NULL,
    fecha_nacimiento VARCHAR(20) NOT NULL,
    id_medicamento NUMBER,
    FOREIGN KEY (id_medicamento) REFERENCES Medicamentos(id_medicamento)
);
```

#### Tabla Medicamentos

```sql
CREATE TABLE Medicamentos (
    id_medicamento NUMBER PRIMARY KEY,
    nombre_medicamento VARCHAR2(100) NOT NULL,
    hora_aplicacion VARCHAR2(20) NOT NULL
);
```

### Inserciones de Ejemplo

#### Insertar Pacientes

```sql
INSERT INTO Pacientes (id_paciente, nombres, apellidos, edad, enfermedad, numero_habitacion, numero_cama, fecha_nacimiento, id_medicamento) 
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Juan', 'Perez', 30, 'Diabetes', 101, 1, '1993-05-14', 1);

INSERT INTO Pacientes (id_paciente, nombres, apellidos, edad, enfermedad, numero_habitacion, numero_cama, fecha_nacimiento, id_medicamento) 
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'Maria', 'Lopez', 45, 'Hipertension', 102, 2, '1978-09-22', 2);
```

#### Insertar Medicamentos

```sql
INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (1, 'Insulina', '08:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (2, 'Losartan', '09:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (3, 'Metformina', '07:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (4, 'Atenolol', '08:30 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (5, 'Aspirina', '10:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (6, 'Amoxicilina', '11:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (7, 'Omeprazol', '07:30 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (8, 'Simvastatina', '08:00 PM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (9, 'Lisinopril', '09:00 PM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (10, 'Metoprolol', '06:00 PM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (11, 'Furosemida', '08:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (12, 'Clopidogrel', '09:30 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (13, 'Levotiroxina', '07:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (14, 'Atorvastatina', '09:00 PM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (15, 'Gabapentina', '10:00 AM');
```

### Consultas

#### Consultar Pacientes y Medicamentos

```sql
SELECT 
    Pacientes.id_paciente, 
    Pacientes.nombres, 
    Pacientes.apellidos, 
    Pacientes.edad, 
    Pacientes.enfermedad, 
    Pacientes.numero_habitacion, 
    Pacientes.numero_cama, 
    Pacientes.fecha_nacimiento, 
    Medicamentos.nombre_medicamento, 
    Medicamentos.hora_aplicacion 
FROM Pacientes
INNER JOIN Medicamentos ON Pacientes.id_medicamento = Medicamentos.id_medicamento;
```

#### Consultar Medicamentos

```sql
SELECT * FROM Medicamentos;
```

