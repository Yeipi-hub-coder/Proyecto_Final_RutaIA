INSERT INTO curso
(nombre, descripcion, categoria, nivel, duracion, estado)
VALUES

-- PROGRAMACIÓN
(
    'Fundamentos de Programación',
    'Curso introductorio sobre variables, tipos de datos, operadores, condicionales, ciclos, funciones y estructuras básicas de programación.',
    'PROGRAMACION',
    'BASICO',
    40,
    'ACTIVO'
),
(
    'Programación Orientada a Objetos',
    'Aprende los conceptos de clases, objetos, herencia, encapsulamiento, polimorfismo y abstracción aplicados al desarrollo de software.',
    'PROGRAMACION',
    'INTERMEDIO',
    50,
    'ACTIVO'
),
(
    'Estructuras de Datos y Algoritmos',
    'Estudio de estructuras de datos, algoritmos de búsqueda y ordenamiento, complejidad computacional y resolución eficiente de problemas.',
    'PROGRAMACION',
    'INTERMEDIO',
    60,
    'ACTIVO'
),
(
    'Programación Avanzada',
    'Curso enfocado en patrones de diseño, programación genérica, manejo avanzado de errores y técnicas para construir aplicaciones robustas.',
    'PROGRAMACION',
    'AVANZADO',
    70,
    'ACTIVO'
),

-- DESARROLLO WEB
(
    'Desarrollo Web con HTML y CSS',
    'Aprende a construir páginas web utilizando HTML5, CSS3, estructuras semánticas, formularios y diseño adaptable.',
    'DESARROLLO WEB',
    'BASICO',
    35,
    'INACTIVO'
),
(
    'JavaScript para Desarrollo Web',
    'Curso sobre fundamentos y aplicaciones de JavaScript para crear páginas web interactivas y dinámicas.',
    'DESARROLLO WEB',
    'INTERMEDIO',
    50,
    'ACTIVO'
),
(
    'Desarrollo Frontend con React',
    'Aprende a desarrollar interfaces modernas utilizando componentes, propiedades, estados, eventos y rutas con React.',
    'DESARROLLO WEB',
    'INTERMEDIO',
    60,
    'ACTIVO'
),
(
    'Desarrollo Web Full Stack',
    'Curso integral para construir aplicaciones web completas utilizando tecnologías frontend, backend, APIs y bases de datos.',
    'DESARROLLO WEB',
    'AVANZADO',
    90,
    'ACTIVO'
),

-- BACKEND
(
    'Desarrollo Backend con Node.js',
    'Introducción al desarrollo de servidores y APIs utilizando Node.js, manejo de peticiones HTTP, módulos y servicios backend.',
    'BACKEND',
    'INTERMEDIO',
    55,
    'ACTIVO'
),
(
    'Construcción de APIs REST',
    'Aprende a diseñar y desarrollar APIs REST, gestionar rutas, métodos HTTP, respuestas, validaciones y códigos de estado.',
    'BACKEND',
    'INTERMEDIO',
    45,
    'ACTIVO'
),
(
    'Arquitectura de Software Backend',
    'Estudio de arquitecturas para aplicaciones backend, separación de responsabilidades, capas, servicios y principios de diseño.',
    'BACKEND',
    'AVANZADO',
    65,
    'ACTIVO'
),

-- BASES DE DATOS
(
    'Fundamentos de Bases de Datos',
    'Introducción al almacenamiento de información, tablas, relaciones, claves primarias, claves foráneas y consultas básicas.',
    'BASES DE DATOS',
    'BASICO',
    35,
    'ACTIVO'
),
(
    'SQL y Bases de Datos Relacionales',
    'Aprende a utilizar SQL para consultar, insertar, actualizar y eliminar información en bases de datos relacionales.',
    'BASES DE DATOS',
    'INTERMEDIO',
    50,
    'INACTIVO'
),
(
    'Diseño y Optimización de Bases de Datos',
    'Curso sobre normalización, índices, relaciones, optimización de consultas y diseño eficiente de bases de datos.',
    'BASES DE DATOS',
    'AVANZADO',
    65,
    'ACTIVO'
),

-- DESARROLLO MOVIL
(
    'Introducción al Desarrollo de Aplicaciones Móviles',
    'Conoce los fundamentos del desarrollo de aplicaciones móviles, interfaces, navegación, almacenamiento y consumo de servicios.',
    'DESARROLLO MOVIL',
    'BASICO',
    40,
    'ACTIVO'
),
(
    'Desarrollo de Apps con Flutter',
    'Aprende a crear aplicaciones móviles multiplataforma utilizando Flutter, widgets, navegación y gestión de estado.',
    'DESARROLLO MOVIL',
    'INTERMEDIO',
    60,
    'INACTIVO'
),
(
    'Desarrollo Avanzado de Aplicaciones Móviles',
    'Curso enfocado en arquitectura móvil, optimización, integración con APIs y manejo avanzado del estado de las aplicaciones.',
    'DESARROLLO MOVIL',
    'AVANZADO',
    70,
    'ACTIVO'
),

-- INTELIGENCIA ARTIFICIAL
(
    'Introducción a la Inteligencia Artificial',
    'Conoce los conceptos fundamentales de inteligencia artificial, aprendizaje automático, datos, modelos y aplicaciones.',
    'INTELIGENCIA ARTIFICIAL',
    'BASICO',
    40,
    'ACTIVO'
),
(
    'Fundamentos de Machine Learning',
    'Introducción al aprendizaje supervisado y no supervisado, preparación de datos, entrenamiento y evaluación de modelos.',
    'INTELIGENCIA ARTIFICIAL',
    'INTERMEDIO',
    65,
    'ACTIVO'
),
(
    'Desarrollo de Aplicaciones con IA',
    'Aprende a integrar modelos de inteligencia artificial en aplicaciones de software mediante servicios y APIs.',
    'INTELIGENCIA ARTIFICIAL',
    'AVANZADO',
    70,
    'ACTIVO'
),

-- DEVOPS Y CLOUD
(
    'Fundamentos de DevOps',
    'Introducción a integración continua, entrega continua, automatización, control de versiones y colaboración entre equipos.',
    'DEVOPS',
    'BASICO',
    40,
    'ACTIVO'
),
(
    'Docker y Contenedores',
    'Aprende los fundamentos de Docker, creación de imágenes, contenedores, redes y almacenamiento para aplicaciones.',
    'DEVOPS',
    'INTERMEDIO',
    50,
    'ACTIVO'
),
(
    'Integración y Despliegue Continuo',
    'Curso sobre construcción de pipelines, automatización de pruebas y despliegue de aplicaciones mediante prácticas de CI/CD.',
    'DEVOPS',
    'AVANZADO',
    60,
    'ACTIVO'
),

-- INGENIERIA DE SOFTWARE
(
    'Fundamentos de Ingeniería de Software',
    'Estudio del ciclo de vida del software, levantamiento de requisitos, documentación, metodologías y buenas prácticas.',
    'INGENIERIA DE SOFTWARE',
    'BASICO',
    35,
    'ACTIVO'
),
(
    'Metodologías Ágiles',
    'Aprende conceptos y prácticas de metodologías ágiles para organizar proyectos, gestionar tareas y trabajar en equipos de desarrollo.',
    'INGENIERIA DE SOFTWARE',
    'INTERMEDIO',
    40,
    'ACTIVO'
),
(
    'Patrones de Diseño de Software',
    'Estudio de patrones de diseño para resolver problemas recurrentes en la construcción y organización de aplicaciones.',
    'INGENIERIA DE SOFTWARE',
    'AVANZADO',
    55,
    'ACTIVO'
);

INSERT INTO estudiante
(nombre, correo, nivel_experiencia, area_interes)
VALUES
(
    'Mateo Sánchez',
    'mateo.sanchez@gmail.com',
    'PRINCIPIANTE',
    'BACKEND'
),
(
    'Isabella Vargas',
    'isabella.vargas@gmail.com',
    'INTERMEDIO',
    'DESARROLLO WEB'
),
(
    'Nicolás Rojas',
    'nicolas.rojas@gmail.com',
    'AVANZADO',
    'INTELIGENCIA ARTIFICIAL'
),
(
    'Gabriela Mendoza',
    'gabriela.mendoza@gmail.com',
    'INTERMEDIO',
    'BASES DE DATOS'
),
(
    'Samuel Cárdenas',
    'samuel.cardenas@gmail.com',
    'PRINCIPIANTE',
    'PROGRAMACION'
),
(
    'Manuela Ortiz',
    'manuela.ortiz@gmail.com',
    'INTERMEDIO',
    'DESARROLLO MOVIL'
),
(
    'David Jiménez',
    'david.jimenez@gmail.com',
    'AVANZADO',
    'DEVOPS'
),
(
    'Natalia Guerrero',
    'natalia.guerrero@gmail.com',
    'PRINCIPIANTE',
    'INGENIERIA DE SOFTWARE'
),
(
    'Alejandro Restrepo',
    'alejandro.restrepo@gmail.com',
    'INTERMEDIO',
    'PROGRAMACION'
),
(
    'Sara López',
    'sara.lopez@gmail.com',
    'AVANZADO',
    'DESARROLLO WEB'
),
(
    'Tomás Ramírez',
    'tomas.ramirez@gmail.com',
    'PRINCIPIANTE',
    'BASES DE DATOS'
),
(
    'Juliana Pérez',
    'juliana.perez@gmail.com',
    'INTERMEDIO',
    'BACKEND'
),
(
    'Felipe Martínez',
    'felipe.martinez@gmail.com',
    'AVANZADO',
    'INTELIGENCIA ARTIFICIAL'
),
(
    'Ana Rodríguez',
    'ana.rodriguez@gmail.com',
    'PRINCIPIANTE',
    'DESARROLLO MOVIL'
),
(
    'Miguel Castro',
    'miguel.castro@gmail.com',
    'INTERMEDIO',
    'DEVOPS'
),
(
    'Paula Torres',
    'paula.torres@gmail.com',
    'AVANZADO',
    'BASES DE DATOS'
),
(
    'Santiago Herrera',
    'santiago.herrera@gmail.com',
    'PRINCIPIANTE',
    'PROGRAMACION'
),
(
    'Daniela Moreno',
    'daniela.moreno@gmail.com',
    'INTERMEDIO',
    'INGENIERIA DE SOFTWARE'
),
(
    'Jorge González',
    'jorge.gonzalez@gmail.com',
    'AVANZADO',
    'BACKEND'
),
(
    'Mariana Silva',
    'mariana.silva@gmail.com',
    'INTERMEDIO',
    'INTELIGENCIA ARTIFICIAL'
);