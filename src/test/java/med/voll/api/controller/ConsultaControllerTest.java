package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.DatosAgendarCounsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester <DatosAgendarCounsulta> agendarConsultaJacksonTester;
    @Autowired
    private JacksonTester <DatosDetalleConsulta> datosDetalleConsultaJacksonTester;

    @MockBean
    private AgendaConsultaService agendaConsultaService;
    @Test
    @DisplayName("Al agendar valores erroneos o invalidos retornara estado http 400")
    @WithMockUser
    void agendar() throws Exception{
        //given and when
        var response = mockMvc.perform(post("/consulta")).andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Al agendar valores valores correctos retornara estado http 200")
    @WithMockUser
    void agendar2() throws Exception{
        //given
        var fecha = LocalDateTime.now().plusHours(11);
        var especialidad = Especialidad.GINECOLOGIA;
        var datos = new DatosDetalleConsulta(null, 10l,19l, fecha);
        //when
        Mockito.when(agendaConsultaService.agendar(any())).thenReturn(datos);

        var response = mockMvc.perform(post("/consulta").
                        contentType(MediaType.APPLICATION_JSON).
                        content(agendarConsultaJacksonTester.write(new DatosAgendarCounsulta( 10l,19l, fecha,  especialidad)).getJson())).
                andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = datosDetalleConsultaJacksonTester.write(datos).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

}