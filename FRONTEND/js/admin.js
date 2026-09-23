async function cargarEstadisticas() {

    try {

        const respuestaEstudiantes = await fetch("http://localhost:8080/api/estudiantes");
        const estudiantes = await respuestaEstudiantes.json();

        const respuestaCursos = await fetch("http://localhost:8080/api/cursos");
        const cursos = await respuestaCursos.json();

        const respuestaConsultas = await fetch("http://localhost:8080/api/consultas");
        const consultas = await respuestaConsultas.json();


        document.getElementById("totalEstudiantes").textContent =
            estudiantes.length;

        document.getElementById("totalCursos").textContent =
            cursos.length;

        document.getElementById("totalConsultas").textContent =
            consultas.length;

    } catch (error) {

        console.error("Error cargando estadísticas:", error);

    }

}

cargarEstadisticas();


cargarEstadisticas();