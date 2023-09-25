package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepositorio;
import med.voll.api.domain.pacientes.Paciente;
import med.voll.api.domain.pacientes.PacienteRepositorio;
import med.voll.api.infra.errores.ValidacionExistencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultaService {
    @Autowired
    private ConsultaRepositorio consultaRepositorio;
    @Autowired
    private MedicoRepositorio medicoRepositorio;
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    List<ValidadorDeConsultas> validadores;
    public void agendar(DatosAgendarCounsulta datosAgendarCounsulta){
        if (!pacienteRepositorio.findById(datosAgendarCounsulta.idPaciente()).isPresent()){
            throw new ValidacionExistencia("El paciente no se encuentra en la base de datos");

        }
        if (datosAgendarCounsulta.idMedico() != null && !medicoRepositorio.existsById(datosAgendarCounsulta.idMedico())){
            throw new ValidacionExistencia("El médico no se encuentra en la base de datos");
        }
        validadores.forEach(v-> v.validar(datosAgendarCounsulta));

        Paciente paciente = pacienteRepositorio.findById(datosAgendarCounsulta.idPaciente()).get();
        Medico medico = escogerMedico(datosAgendarCounsulta);


        Consulta consulta = new Consulta(null, medico,paciente, datosAgendarCounsulta.fecha());
        consultaRepositorio.save(consulta);

    }

    private Medico escogerMedico(DatosAgendarCounsulta datosAgendarCounsulta) {
    if (datosAgendarCounsulta.idMedico()!= null){
        medicoRepositorio.findById(datosAgendarCounsulta.idMedico());
    }
    if(datosAgendarCounsulta.especialidad()== null){
        throw new ValidacionExistencia("Debe seleccionar una especialidad para el médico");
    }
        return medicoRepositorio.seleccionarMedicoConEspecialidadEnfecha(datosAgendarCounsulta.especialidad(),datosAgendarCounsulta.fecha());
    }
}
