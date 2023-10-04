package med.voll.api.controller;

import med.voll.api.medico.DadosCadastroMedico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // anotacao necessaria para o Spring carregar a classe
@RequestMapping("/medicos")
public class MedicoController {
//    @PostMapping //verbo de requisicao do metodo
//    public void cadastrar(@RequestBody String json){ // anotacao @RequestBody necessaria para receber o json enviado pelo insomnia. ATENCAO essa anotacao traz TODO o jason
//        System.out.println(json);
//    }
   @PostMapping //verbo de requisicao do metodo
     public void cadastrar(@RequestBody DadosCadastroMedico dados){ // anotacao @RequestBody. Para receber as conteudo json fragmentado eh necessario criar classes que contenham metodos com paramentros com nomes identicos (case sensitive) aos ados contidos no json
        System.out.println(dados);
    }

}
