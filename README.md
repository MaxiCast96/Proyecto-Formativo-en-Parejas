Base de datos:

-- Tabla Pacientes
CREATE TABLE Pacientes (
    id_paciente Varchar(36) PRIMARY KEY,
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

-- Tabla Medicamentos
CREATE TABLE Medicamentos (
    id_medicamento NUMBER PRIMARY KEY,
    nombre_medicamento VARCHAR2(100) NOT NULL,
    hora_aplicacion VARCHAR2(20) NOT NULL
);

-- Insertar pacientes
INSERT INTO Pacientes (id_paciente, nombres, apellidos, edad, enfermedad, numero_habitacion, numero_cama, fecha_nacimiento, id_medicamento) 
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Juan', 'Perez', 30, 'Diabetes', 101, 1, '1993-05-14', 1);

INSERT INTO Pacientes (id_paciente, nombres, apellidos, edad, enfermedad, numero_habitacion, numero_cama, fecha_nacimiento, id_medicamento) 
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'Maria', 'Lopez', 45, 'Hipertension', 102, 2, '1978-09-22', 2);

-- Insertar medicamentos
INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (1, 'Insulina', '08:00 AM');

INSERT INTO Medicamentos (id_medicamento, nombre_medicamento, hora_aplicacion) 
VALUES (2, 'Losartan', '09:00 AM');

-- Selects
Select  Pacientes.id_paciente, Pacientes.nombres, Pacientes.apellidos, Pacientes.edad, Pacientes.enfermedad, Pacientes.numero_habitacion, Pacientes.numero_cama, Pacientes.fecha_nacimiento, Medicamentos.nombre_medicamento, medicamentos.hora_aplicacion from Pacientes
INNER JOIN 
    Medicamentos ON Pacientes.id_medicamento = Medicamentos.id_medicamento;

Select * from Medicamentos;
