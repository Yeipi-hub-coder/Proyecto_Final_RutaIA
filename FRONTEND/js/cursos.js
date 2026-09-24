const listaCursos =
    document.getElementById("listaTodosCursos");

const buscador =
    document.getElementById("buscarCurso");


let cursosGuardados = [];


async function cargarCursos() {

    try {

        const respuesta =
            await fetch(`${API_URL}/cursos`);


        if (!respuesta.ok) {

            throw new Error(
                "No se pudieron obtener los cursos"
            );

        }


        cursosGuardados =
            await respuesta.json();


        mostrarCursos(cursosGuardados);


    } catch (error) {

        console.error(
            "Error cargando cursos:",
            error
        );

    }

}


function mostrarCursos(cursos) {

    listaCursos.innerHTML = "";


    cursos.forEach(curso => {

        const iniciales =
            curso.nombre
                ? curso.nombre
                    .substring(0, 2)
                    .toUpperCase()
                : "CU";


        const tarjeta =
            document.createElement("article");


        tarjeta.classList.add(
            "curso-card"
        );


        tarjeta.innerHTML = `

            <div class="curso-card-superior">

                <div class="curso-iniciales">
                    ${iniciales}
                </div>

                <span class="curso-categoria">
                    ${curso.categoria ?? "Curso"}
                </span>

            </div>


            <div class="curso-card-contenido">

                <h2>
                    ${curso.nombre}
                </h2>

                <p>
                    ${curso.descripcion ?? "Sin descripción disponible."}
                </p>

            </div>


            <div class="curso-card-footer">

                <button>
                    Ver curso
                </button>

            </div>

        `;


        listaCursos.appendChild(tarjeta);

    });

}


/* =========================================
   BUSCADOR
========================================= */

buscador.addEventListener(
    "input",
    function() {

        const texto =
            buscador.value
                .toLowerCase()
                .trim();


        const cursosFiltrados =
            cursosGuardados.filter(curso => {

                const nombre =
                    curso.nombre
                        ?.toLowerCase()
                        ?? "";

                const descripcion =
                    curso.descripcion
                        ?.toLowerCase()
                        ?? "";

                return (
                    nombre.includes(texto)
                    ||
                    descripcion.includes(texto)
                );

            });


        mostrarCursos(cursosFiltrados);

    }
);


cargarCursos();