package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.pacientes.DatosRegistroPaciente;
import med.voll.api.domain.pacientes.Paciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositorioTest {

    @Autowired
    private MedicoRepositorio medicoRepositorio;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia retorrnar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnfecha() {
        var proximoLunes10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Jose Dolores","josesito@gmail.com","123456",Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Antonio Delgado", "alola@gmail.com","654321");
        registrarConsulta(medico,paciente,proximoLunes10Am);
        var medicoLibre = medicoRepositorio.seleccionarMedicoConEspecialidadEnfecha(Especialidad.CARDIOLOGIA,proximoLunes10Am);

        Assertions.assertThat(medicoLibre).isNull();
    }


    @Test
    @DisplayName("Deberia retorrnar un medico cuando realice la consulta en la bd para ese horario")
    void seleccionarMedicoConEspecialidadEnfecha2() {
        var proximoLunes10Am = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Jose Dolores","josesito@gmail.com","123456",Especialidad.CARDIOLOGIA);
        var medicoLibre = medicoRepositorio.seleccionarMedicoConEspecialidadEnfecha(Especialidad.CARDIOLOGIA,proximoLunes10Am);
        Assertions.assertThat(medicoLibre).isEqualTo(medico);
    }
    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }


    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }


    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha));
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "61999999999",
                documento,
                datosDireccion()
        );
    }

}