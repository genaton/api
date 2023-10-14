package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;
//usando as anotacoes do bean validation
public record DadosCadastroMedico(
      //  @NotNull // atributo nome nao pode ser nulo
        @NotBlank // atribuot nome nao pode ser nulo nem vazio e deve ser usado em strings
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone, // alterou as regras de negocios exigindo o telefone do medico
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull // atributo nao pode ser nulo como eh enum o campo tem preechimento obrigatorio
        Especialidade especialidade,
        @NotNull
        @Valid // DadosEndereco eh uma outra classe DTO e esta anotacao eh para validar esta classe
        DadosEndereco endereco ) {
}
