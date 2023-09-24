package med.voll.api.controller.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consulta")
public class ConsultaController {


    @Autowired
    private AgendaConsutaService agendaConsutaService;
    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    @PostMapping
    @Transactional
    public ResponseEntity <DatosDetalleConsulta>agendar(@RequestBody @Valid DatosAgendarCounsulta datosAgendarCounsulta){

        agendaConsutaService.agendar(datosAgendarCounsulta);
        return ResponseEntity.ok(new DatosDetalleConsulta(null,null,null,null));
    }
}
