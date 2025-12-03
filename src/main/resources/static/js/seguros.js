console.log("SCRIPT CARGADO CORRECTAMENTE v3");

let idPolizaEditando = null;

document.addEventListener('DOMContentLoaded', () => {
    cargarTiposPoliza();
    cargarPolizas();

    const formulario = document.getElementById("crearPolizaForm");
    formulario.addEventListener("submit", function (event) {
        event.preventDefault();
        crearPoliza();
    });

    const tbody = document.getElementById("polizasBody");
    tbody.addEventListener("click", function (event) {
        const target = event.target;

        if (target.classList.contains("btn-editar")) {
            const id = target.getAttribute("data-id");
            editarPoliza(id);
        }

        if (target.classList.contains("btn-eliminar")) {
            const id = target.getAttribute("data-id");
            eliminarPoliza(id);
        }
    });
});


function cargarTiposPoliza() {
    fetch("/tipopolizas")
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById("tipoPoliza");
            select.innerHTML = '<option value="" disabled selected>Seleccione un tipo...</option>';

            data.forEach(tipo => {
                const option = document.createElement("option");
                option.value = tipo.id;
                option.textContent = `${tipo.nombre} - $${tipo.preciobase}`;
                select.appendChild(option);
            });
        })
        .catch(error => console.error("Error cargando tipos:", error));
}

function cargarPolizas() {
    fetch("/polizas/todos")
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById("polizasBody");
            tbody.innerHTML = "";

            if (data.length === 0) {
                document.getElementById("alertNoPolizas").style.display = "block";
            } else {
                document.getElementById("alertNoPolizas").style.display = "none";
            }

            data.forEach(poliza => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
            <td>${poliza.id}</td>
            <td>${poliza.fechaInicio}</td>
            <td>${poliza.fechaFin}</td>
            <td>${poliza.cliente ? poliza.cliente.nombre : 'N/A'}</td>
            <td>${poliza.tipoPoliza ? poliza.tipoPoliza.nombre : 'N/A'}</td>
            <td>${poliza.bienAsegurado ? poliza.bienAsegurado.descripcion : 'N/A'}</td>
            <td>
                <button class="btn btn-danger btn-sm btn-eliminar" data-id="${poliza.id}">Eliminar</button>
                <button class="btn btn-warning btn-sm btn-editar" data-id="${poliza.id}">Editar</button>
            </td>
            `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error("Error cargando polizas:", error));
}

function eliminarPoliza(id) {
    if (confirm("¿Estas seguro de eliminar esta póliza?")) {
        axios.delete(`/polizas/eliminar/${id}`)
            .then(response => {
                if (response.data) {
                    alert("PolizaEliminada");
                    cargarPolizas();
                } else {
                    alert("No se pudo eliminar la poliza");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Ocurrio un error al intentar eliminar la poliza");
            })
    }
}

function crearPoliza() {
    const clienteInput = document.getElementById("idCliente").value;
    const bienInput = document.getElementById("idBienAsegurado").value;
    const valorBien = document.getElementById("valorBien").value;

    const nuevaPoliza = {
        fechaInicio: document.getElementById("FechaInicio").value,
        fechaFin: document.getElementById("FechaFin").value,
        idTipoPoliza: document.getElementById("tipoPoliza").value,
        valorBien: valorBien
    };


    if (!isNaN(clienteInput) && clienteInput.trim() !== "") {
        nuevaPoliza.idCliente = clienteInput;
    } else {
        nuevaPoliza.nombreCliente = clienteInput;
    }


    if (!isNaN(bienInput) && bienInput.trim() !== "") {
        nuevaPoliza.idBienAsegurado = bienInput;
    } else {
        nuevaPoliza.descripcionBien = bienInput;
    }

    if (idPolizaEditando) {

        axios.put(`/polizas/editar/${idPolizaEditando}`, nuevaPoliza)
            .then(response => {
                alert("¡Póliza Actualizada!");
                limpiarFormulario();
                cargarPolizas();
            })
            .catch(error => {
                console.error(error);
                if (error.response && error.response.data) {
                    alert("Detalle del error: " + JSON.stringify(error.response.data));
                } else {
                    alert("Error desconocido: " + error.message);
                }
            });
    } else {

        axios.post("/polizas/guardar", nuevaPoliza)
            .then(response => {
                alert("¡Póliza Creada!");
                limpiarFormulario();
                cargarPolizas();
            })
            .catch(error => {
                console.error(error);
                if (error.response && error.response.data) {
                    alert("Detalle del error: " + JSON.stringify(error.response.data));
                } else {
                    alert("Error desconocido: " + error.message);
                }
            });
    }
}

function limpiarFormulario() {
    document.getElementById("crearPolizaForm").reset();
    idPolizaEditando = null;
    document.querySelector("button[type='submit']").textContent = "Guardar Póliza";
}


function editarPoliza(id) {
    console.log("Editando poliza: " + id);
    alert("Cargando datos de la póliza " + id + "...");
    fetch(`/polizas/${id}`)
        .then(response => response.json())
        .then(poliza => {
            document.getElementById("FechaInicio").value = poliza.fechaInicio;
            document.getElementById("FechaFin").value = poliza.fechaFin;

            document.getElementById("idCliente").value = poliza.cliente ? poliza.cliente.nombre : '';
            document.getElementById("tipoPoliza").value = poliza.tipoPoliza ? poliza.tipoPoliza.id : '';
            document.getElementById("idBienAsegurado").value = poliza.bienAsegurado ? poliza.bienAsegurado.descripcion : '';
            document.getElementById("valorBien").value = poliza.bienAsegurado ? poliza.bienAsegurado.valor : '';

            idPolizaEditando = id;
            document.querySelector("button[type='submit']").textContent = "Actualizar Póliza";
        })
        .catch(error => console.error("Error al cargar para editar la poliza", error))
}