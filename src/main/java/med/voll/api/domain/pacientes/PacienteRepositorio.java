package med.voll.api.domain.pacientes;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {

    Page<Paciente> findByActivoTrue(Pageable page);

    @Query("""
            select p.activo from Paciente p where p.id =: idPaciente
            """)
    Boolean findActivoById(Long idPaciente);
}
