package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarCounsulta;
import med.voll.api.infra.errores.ValidacionExistencia;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class HorarioFuncionamiento implements ValidadorDeConsultas{
    public void validar (DatosAgendarCounsulta datosAgendarCounsulta){
        var domingo = DayOfWeek.SUNDAY.equals(datosAgendarCounsulta.fecha().getDayOfWeek());
        var antesHoraApertura = datosAgendarCounsulta.fecha().getHour() < 7;
        var verificarCierre = datosAgendarCounsulta.fecha().getHour() > 19;



        if(domingo||antesHoraApertura||verificarCierre){
            throw new ValidacionExistencia("El horario de atención de la clinica es de lunes a sábado de 7:00 a 19:00 horas");
                }

    }
}
