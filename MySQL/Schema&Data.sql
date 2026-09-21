DROP DATABASE	 IF EXISTS RutaIA;
CREATE DATABASE RutaIA;
USE RutaIA;

CREATE TABLE estudiante (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    nivel_experiencia ENUM('PRINCIPIANTE', 'INTERMEDIO', 'AVANZADO') NOT NULL,
    area_interes VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE curso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    nivel ENUM('BASICO', 'INTERMEDIO', 'AVANZADO') NOT NULL,
    duracion INT NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    CHECK (duracion > 0),
    INDEX idx_categoria (categoria),
    INDEX idx_nivel (nivel),
    INDEX idx_estado (estado)
) ENGINE=InnoDB;

CREATE TABLE consulta (
    id INT PRIMARY KEY AUTO_INCREMENT,
    estudiante_id INT NOT NULL,
    pregunta VARCHAR(500) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('PENDIENTE', 'RESPONDIDA', 'SIN_RESULTADOS', 'ERROR') NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (estudiante_id) REFERENCES estudiante(id),
    INDEX idx_estudiante (estudiante_id)
) ENGINE=InnoDB;

CREATE TABLE recomendacion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    consulta_id INT NOT NULL,
    respuesta_generada TEXT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado_final ENUM('RESPONDIDA', 'SIN_RESULTADOS', 'ERROR') NOT NULL,
    FOREIGN KEY (consulta_id) REFERENCES consulta(id),
    INDEX idx_consulta (consulta_id)
) ENGINE=InnoDB;

CREATE TABLE fuente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    recomendacion_id INT NOT NULL,
    curso_id INT NOT NULL,
    similitud DECIMAL(5,4) NOT NULL,
    FOREIGN KEY (recomendacion_id) REFERENCES recomendacion(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id),
    INDEX idx_recomendacion (recomendacion_id)
) ENGINE=InnoDB;

CREATE TABLE calificacion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    recomendacion_id INT NOT NULL UNIQUE,
    puntuacion INT NOT NULL,
    comentario VARCHAR(500),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    CHECK (puntuacion BETWEEN 1 AND 5),
    FOREIGN KEY (recomendacion_id) REFERENCES recomendacion(id)
) ENGINE=InnoDB;