
async function cargarHistorial() {

    const estudianteId =
        obtenerEstudianteId();

    if (!estudianteId) {
        return;
    }

    try {

        const respuesta = await fetch(
            `${API_URL}/consultas/${estudianteId}/historial`
        );

        if (!respuesta.ok) {
            throw new Error(
                "No se pudo cargar el historial"
            );
        }

        const consultas =
            await respuesta.json();

        console.log(
            "Historial:",
            consultas
        );

        mostrarHistorial(consultas);

    } catch (error) {

        console.error(
            "Error cargando historial:",
            error
        );

    }
}


function mostrarHistorial(consultas) {

    const contenedor =
        document.getElementById(
            "listaHistorial"
        );

    contenedor.innerHTML = "";


    if (consultas.length === 0) {

        contenedor.innerHTML = `
            <p>
                Todavía no has realizado consultas.
            </p>
        `;

        return;
    }


    consultas.forEach(consulta => {

        const elemento =
            document.createElement(
                "article"
            );

        elemento.classList.add(
            "historial-item"
        );


        elemento.innerHTML = `

            <div class="historial-item-header">

                <span class="historial-fecha">
                    ${consulta.fecha}
                </span>

                <span class="historial-estado">
                    ${consulta.estado}
                </span>

            </div>


            <h3>
                ${consulta.pregunta}
            </h3>


            <p class="historial-respuesta">

                ${
                    consulta.recomendacion?.respuesta
                    ?? "Sin respuesta"
                }

            </p>

        `;


        contenedor.appendChild(
            elemento
        );

    });

}
cargarHistorial();