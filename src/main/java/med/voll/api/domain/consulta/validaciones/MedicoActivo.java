package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarCounsulta;
import med.voll.api.domain.medico.MedicoRepositorio;
import med.voll.api.infra.errores.ValidacionExistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepositorio medicoRepositorio;
    public void validar(DatosAgendarCounsulta datosAgendarCounsulta){
        if(datosAgendarCounsulta.idMedico()== null){
            return;
        }
        var medicoActivo = medicoRepositorio.findActivoById(datosAgendarCounsulta.idMedico());

        if (!medicoActivo){
            throw new ValidacionExistencia("No se puede permitir agendar citas con medicos inactivos en el sistema");

        }


    }
}
