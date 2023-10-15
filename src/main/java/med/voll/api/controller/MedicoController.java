package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
     public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){ // anotacao @RequestBody. Para receber as conteudo json fragmentado eh necessario criar classes que contenham metodos com paramentros com nomes identicos (case sensitive) aos ados contidos no json
//        System.out.println(dados);
       var medico = new Medico(dados);
       repository.save(medico);
       var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
       return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));// 201 created

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
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
       var medico = repository.getReferenceById(dados.id());
       medico.atualizarInformacoes(dados);
       return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

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
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build(); // retorna o código http 204.

    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
   }



}
