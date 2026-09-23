async function cargarCursos() {

    try {

        const respuesta = await fetch(`${API_URL}/cursos`);

        if (!respuesta.ok) {
            throw new Error("Error al obtener los cursos");
        }

        const cursos = await respuesta.json();

        mostrarCursos(cursos);

    } catch (error) {

        console.error("Error:", error);

    }

}


function mostrarCursos(cursos) {

    const contenedor = document.getElementById("listaCursos");

    contenedor.innerHTML = "";

    cursos.forEach(curso => {

        contenedor.innerHTML += `

            <div class="course">

                <div class="course-icon">
                    🫆
                </div>

                <div>

                    <div class="course-name">
                        ${curso.nombre}
                    </div>


                </div>

            </div>

        `;

    });

}


cargarCursos();

