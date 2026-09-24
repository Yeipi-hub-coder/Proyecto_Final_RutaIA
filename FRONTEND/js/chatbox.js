const botonEnviar = document.getElementById("btnEnviar");
const inputPregunta = document.getElementById("pregunta");
const mensajesChat = document.getElementById("mensajesChat");


botonEnviar.addEventListener("click", enviarConsulta);


async function enviarConsulta() {

    const pregunta = inputPregunta.value.trim();

    if (pregunta === "") {
        return;
    }


    // Obtenemos el estudiante que inició sesión
    const estudianteId =
        Number(localStorage.getItem("estudianteId"));


    // Si no existe sesión
    if (!estudianteId) {

        alert("Debes iniciar sesión");

        window.location.href = "login.html";

        return;
    }


    mostrarMensajeUsuario(pregunta);

    inputPregunta.value = "";


    // La consulta queda asociada al estudiante real
    const consulta = {

        estudianteId: estudianteId,

        pregunta: pregunta,

        nivelExperiencia: "PRINCIPIANTE",

        areaInteres: "Programación"
    };


    try {

        const respuesta = await fetch(
            `${API_URL}/consultas`,
            {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(consulta)

            }
        );


        if (!respuesta.ok) {

            throw new Error(
                "Error al realizar la consulta"
            );

        }


        const resultado =
            await respuesta.json();


        console.log(
            "Consulta guardada:",
            resultado
        );


        console.log(
            "ID de consulta:",
            resultado.id
        );


        mostrarMensajeIA(
            resultado.recomendacion.respuesta
        );


    } catch (error) {

        console.error(error);

        mostrarMensajeIA(
            "Ocurrió un error al procesar tu consulta."
        );

    }

}



function mostrarMensajeUsuario(texto) {

    const mensaje =
        document.createElement("div");

    mensaje.classList.add(
        "mensaje",
        "mensaje-usuario"
    );

    mensaje.textContent = texto;

    mensajesChat.appendChild(mensaje);

    mensajesChat.scrollTop =
        mensajesChat.scrollHeight;
}



function mostrarMensajeIA(texto) {

    const mensaje =
        document.createElement("div");

    mensaje.classList.add(
        "mensaje",
        "mensaje-ia"
    );

    mensaje.textContent = texto;

    mensajesChat.appendChild(mensaje);

    mensajesChat.scrollTop =
        mensajesChat.scrollHeight;
}