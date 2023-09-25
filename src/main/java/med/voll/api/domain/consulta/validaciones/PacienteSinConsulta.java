package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.ConsultaRepositorio;
import med.voll.api.domain.consulta.DatosAgendarCounsulta;
import med.voll.api.infra.errores.ValidacionExistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepositorio consultaRepositorio;
    public void validar (DatosAgendarCounsulta datosAgendarCounsulta){
        var primerHorario = datosAgendarCounsulta.fecha().withHour(7);
        var ultimoHorario = datosAgendarCounsulta.fecha().withHour(18);
        var pacienteConsulta = consultaRepositorio.existsByPacienteIdAndFechaBetween(datosAgendarCounsulta.idPaciente(),primerHorario,ultimoHorario);
        if(pacienteConsulta){
            throw new ValidacionExistencia("El paciente ya tiene consulta para ese día");
        }

    }

}
