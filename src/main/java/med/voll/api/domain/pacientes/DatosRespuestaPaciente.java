package med.voll.api.domain.pacientes;

import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;

public record DatosRespuestaPaciente(Long id, String nombre, String email, String telefono,
                                     String documento, DatosDireccion direccion) {
}
