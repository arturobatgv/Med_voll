package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarCounsulta;
import med.voll.api.domain.pacientes.PacienteRepositorio;
import med.voll.api.infra.errores.ValidacionExistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    public void validar(DatosAgendarCounsulta datosAgendarCounsulta){
        if(datosAgendarCounsulta.idPaciente()== null){
            return;
        }

        var pacienteActivo = pacienteRepositorio.findActivoById(datosAgendarCounsulta.idPaciente());
        if (!pacienteActivo){
            throw new ValidacionExistencia("No se puede permitir agendar citas con pacientes inactivos en el sistema");

        }


    }
}
