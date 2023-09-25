package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medico")
@SecurityRequirement(name = "bearer-key")

public class MedicoController {
	
	@Autowired
	private MedicoRepositorio medicoRepositorio;

	@PostMapping
	public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
		Medico medico = medicoRepositorio.save(new Medico(datosRegistroMedico));
		DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
				medico.getTelefono(), medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
				medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
				medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
		//URI url = "http://localhost8080/medicos/" + medico.getId();
		URI url = uriComponentsBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaMedico);
	}

	/*
	*Para enilistar todo de la entidad lista Médico
	* @GetMapping
	public List<Medico> listadoMedicos(){
		return medicoRepositorio.findAll();
	}
	*Este sirve para enlistar unos datos en específico, que se encuentra en el record de
	* Datos Listado Médico
	* @GetMapping
	public List<DatosListadoMedico> listadoMedicos(){

		return medicoRepositorio.findAll().stream().map(DatosListadoMedico::new).toList();
	}
	* */

	//Sirve para paginar
	@GetMapping
	public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size=2, sort = "nombre") Pageable paginacion){
		//return medicoRepositorio.findAll(paginacion).map(DatosListadoMedico::new);
		return ResponseEntity.ok(medicoRepositorio.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
	}
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
		Medico medico = medicoRepositorio.getReferenceById(datosActualizarMedico.id());
		medico.actualizarDatos(datosActualizarMedico);
		return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
				medico.getTelefono(), medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
				medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
				medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
	}

	/* Delete en base de datos
	@DeleteMapping("{id}")
	@Transactional
	public void eliminarMedico(@PathVariable Long id){
		Medico medico = medicoRepositorio.getReferenceById(id);
		medicoRepositorio.delete(medico);
	}*/

	//Delete Lógico
	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity eliminarMedico(@PathVariable Long id){
		Medico medico = medicoRepositorio.getReferenceById(id);
		medico.desactivarMedico();
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{id}")
	public ResponseEntity <DatosRespuestaMedico> consultarMedicoEspcifico(@PathVariable Long id){
		Medico medico = medicoRepositorio.getReferenceById(id);
		DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
				medico.getTelefono(), medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
				medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
				medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
		return ResponseEntity.ok(datosRespuestaMedico);
	}

	}

