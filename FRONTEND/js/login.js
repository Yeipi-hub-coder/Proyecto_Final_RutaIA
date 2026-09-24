const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", async function(event) {

    event.preventDefault();

    const correo = document
        .getElementById("correo")
        .value
        .trim();

    const contrasena = document
        .getElementById("contrasena")
        .value
        .trim();


    // Validamos la contraseña
    if (contrasena !== "1234") {

        alert("Contraseña incorrecta");

        return;
    }


    try {

        // 1. Buscar el ID según el correo
        const respuestaId = await fetch(
            `${API_URL}/estudiantes/id?correo=${encodeURIComponent(correo)}`
        );


        if (!respuestaId.ok) {

            alert("El correo no pertenece a ningún estudiante");

            return;
        }


        const estudianteId =
            await respuestaId.json();


        console.log(
            "ID del estudiante:",
            estudianteId
        );


        // 2. Buscar los datos completos del estudiante según el ID
        const respuestaEstudiante = await fetch(
            `${API_URL}/estudiantes/${estudianteId}`
        );


        if (!respuestaEstudiante.ok) {

            alert("No se pudieron obtener los datos del estudiante");

            return;
        }


        const estudiante =
            await respuestaEstudiante.json();


        console.log(
            "Datos del estudiante:",
            estudiante
        );


        // 3. Guardamos ID, nombre y correo
        localStorage.setItem(
            "estudianteId",
            estudianteId
        );

        localStorage.setItem(
            "nombrelocal",
            estudiante.nombre
        );

        localStorage.setItem(
            "estudianteCorreo",
            correo
        );


        // 4. Entramos al portal
        window.location.href =
            "estudiante.html";


    } catch (error) {

        console.error(
            "Error al iniciar sesión:",
            error
        );

        alert(
            "No se pudo conectar con el servidor"
        );

    }

});