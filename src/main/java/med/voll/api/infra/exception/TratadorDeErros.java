package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//classe para personalizar o tratamento de erros
@RestControllerAdvice // anotacao necessaria para o spring ler a classe dentro do projeto
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class) // direciona os erros para o codico 404
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build(); // direciona os erros para o codico 404
    }
    @ExceptionHandler(MethodArgumentNotValidException.class) //direciona os erros para o codico 400
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());

    }

    @ExceptionHandler(ValidacaoException.class) //direciona os erros para o codico 400
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());

    }
// neste caso pode-se criar uma classe DTO interna.
    private record DadosErrosValidacao(String campo, String mensagem){
        public DadosErrosValidacao (FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
// o DTO retornará um json da seguinte maneira: Detalhe, as mensagens retornadas são mensagens padrão do spring. portanto não é necessário escrevê-las.

//    {
//        "campo": "crm",
//            "mensagem": "não deve estar em branco"
//    }
    }
}
