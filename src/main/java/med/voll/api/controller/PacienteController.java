package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/paciente")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    private PacienteRepositorio pacienteRepositorio;
    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@Valid @RequestBody DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder ){
        Paciente paciente = pacienteRepositorio.save(new Paciente(datosRegistroPaciente));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(),
                paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(), paciente.getDocumento(), new DatosDireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getNumero(),paciente.getDireccion().getComplemento()));

        URI url = uriComponentsBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listarPaciente(
            @PageableDefault(size = 2, sort = "nombre") Pageable paginacion){
             return ResponseEntity.ok(pacienteRepositorio.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(
            @RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepositorio.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(),
                paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(),
                paciente.getTelefono(), new DatosDireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(),paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getNumero(),paciente.getDireccion().getComplemento())));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepositorio.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity <DatosRespuestaPaciente> consultarEspecificoPaciente
            (@PathVariable Long id){
        Paciente paciente = pacienteRepositorio.getReferenceById(id);
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(),
                paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),paciente.getDireccion().getNumero(),paciente.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosRespuestaPaciente);

    }


}
