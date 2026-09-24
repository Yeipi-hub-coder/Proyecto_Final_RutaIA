function obtenerEstudianteId() {

    const estudianteId =
        localStorage.getItem("estudianteId");


    if (!estudianteId) {

        alert(
            "Debes iniciar sesión"
        );

        window.location.href =
            "login.html";

        return null;
    }


    return Number(estudianteId);
}

