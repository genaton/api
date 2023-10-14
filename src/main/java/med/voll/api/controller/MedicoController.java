package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    // sem paginacao
//    @GetMapping
//    public List<DadosListagemMedico> listar(){
//        return repository.findAll().stream().map(DadosListagemMedico::new).toList();
//    }


    // implementando paginacao para atender a regra de negocios. O codigo abaixo traz a paginacao padrao
//    @GetMapping
//    public Page<DadosListagemMedico> listar(Pageable paginacao){
//        return repository.findAll(paginacao).map(DadosListagemMedico::new);
//    }

    // presonalizando a paginacao para atender as regras de negócios: 10 registros por pag e em ordem alfabetica pelo nome do medico
//    @GetMapping
//    public Page<DadosListagemMedico> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao){
//        return repository.findAll(paginacao).map(DadosListagemMedico::new);
//    }

    // exibindo apenas medicos ativos.  findAllByAtivoTrue é criado no MedicoRepository.
    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
       var medico = repository.getReferenceById(dados.id());
       medico.atualizarInformacoes(dados);

    }
    // metodo para exclusao definitiva (fisica) no banco de dados.
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id){
//       repository.deleteById(id);
//
//    }

    // excluir dados do medico apenas logico (sem exclusao permanete) apenas inativando o cadastro.
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();

    }



}
