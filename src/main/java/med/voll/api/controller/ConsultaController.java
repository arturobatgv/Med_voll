package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consulta")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {


    @Autowired
    private AgendaConsultaService agendaConsultaService;
    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    @PostMapping
    @Transactional
    public ResponseEntity <DatosDetalleConsulta>agendar(@RequestBody @Valid DatosAgendarCounsulta datosAgendarCounsulta){

        var response = agendaConsultaService.agendar(datosAgendarCounsulta);
        return ResponseEntity.ok(response);
    }
}
