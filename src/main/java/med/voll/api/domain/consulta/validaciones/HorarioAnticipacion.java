package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarCounsulta;
import med.voll.api.infra.errores.ValidacionExistencia;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class HorarioAnticipacion implements ValidadorDeConsultas{
    public void validar(DatosAgendarCounsulta datosAgendarCounsulta){
        var ahora = LocalDateTime.now();
        var horaConsulta = datosAgendarCounsulta.fecha();
        var diferencia30Minutos = Duration.between(ahora, horaConsulta).toMinutes()<30;
        if(diferencia30Minutos){
            throw new ValidacionExistencia("Las consultas deben de tener al menos 30 minutos de anticipacion");
        }




    }
}
