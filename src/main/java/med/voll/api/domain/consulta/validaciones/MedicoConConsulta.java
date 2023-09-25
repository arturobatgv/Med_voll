package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.ConsultaRepositorio;
import med.voll.api.domain.consulta.DatosAgendarCounsulta;
import med.voll.api.infra.errores.ValidacionExistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepositorio consultaRepositorio;
    public void validar(DatosAgendarCounsulta datosAgendarCounsulta){
        if (datosAgendarCounsulta.idMedico()==null){
            return;
        }
        var medicoConConsulta = consultaRepositorio.existsByMedicoIdAndFecha(datosAgendarCounsulta.idMedico(),datosAgendarCounsulta.fecha());

        if (medicoConConsulta){
            throw new ValidacionExistencia("Este médico ya tiene una consulta en ese horario");
        }


    }
}
