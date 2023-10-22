package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.domain.paciente.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

//CLASSE CONTROLLER CONTROLA O FLUXO DE REQUISICOES E NAL AS REGRAS DE NEGOCIOS
@RestController
@RequestMapping("consultas")
@SecurityRequirement(name ="bearer-key") // anotacao necessaria para disponibilizar a insercao do token na documentacao do swagger/springdoc. Colocar a anotacao antes da classe quando todos os metodos podem ser usados. Quando se quer liberar apenas um metodo basta colocar a anotacao acima dele e nao acima da classe.
public class ConsultaController {
    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DadosAgendamentoConsulta dados) {
//        System.out.println(dados);
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){

        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var consulta = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

}
