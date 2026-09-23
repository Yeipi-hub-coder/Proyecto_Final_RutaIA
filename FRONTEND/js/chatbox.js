const botonEnviar = document.getElementById("btnEnviar");
const inputPregunta = document.getElementById("pregunta");
const mensajesChat = document.getElementById("mensajesChat");


botonEnviar.addEventListener("click", enviarConsulta);


async function enviarConsulta() {

    const pregunta = inputPregunta.value.trim();

    if (pregunta === "") {
        return;
    }


    mostrarMensajeUsuario(pregunta);

    inputPregunta.value = "";


    const consulta = {
        estudianteId: 1,
        pregunta: pregunta,
        nivelExperiencia: "PRINCIPIANTE",
        areaInteres: "Programación"
    };


    try {

        const respuesta = await fetch(`${API_URL}/consultas`, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(consulta)

        });


        if (!respuesta.ok) {
            throw new Error("Error al realizar la consulta");
        }


        const resultado = await respuesta.json();

        console.log(resultado);

        mostrarMensajeIA(resultado.respuesta);


    } catch (error) {

        console.error(error);

        mostrarMensajeIA(
            "Ocurrió un error al procesar tu consulta."
        );

    }

}

function mostrarMensajeUsuario(texto) {

    const mensaje = document.createElement("div");

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

    const mensaje = document.createElement("div");

    mensaje.classList.add(
        "mensaje",
        "mensaje-ia"
    );

    mensaje.textContent = texto;

    mensajesChat.appendChild(mensaje);

    mensajesChat.scrollTop =
        mensajesChat.scrollHeight;
}