package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // anotacao necessaria para o Spring carregar a classe
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired // Injeção de dependencias
    private MedicoRepository repository;

//    @PostMapping //verbo de requisicao do metodo
//    public void cadastrar(@RequestBody String json){ // anotacao @RequestBody necessaria para receber o json enviado pelo insomnia. ATENCAO essa anotacao traz TODO o jason
//        System.out.println(json);
//    }
   @PostMapping //verbo de requisicao do metodo
   @Transactional // anotacao para inserir dados na tabela do banco de dados.
     public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){ // anotacao @RequestBody. Para receber as conteudo json fragmentado eh necessario criar classes que contenham metodos com paramentros com nomes identicos (case sensitive) aos ados contidos no json
//        System.out.println(dados);
       repository.save(new Medico(dados));


    }

}
