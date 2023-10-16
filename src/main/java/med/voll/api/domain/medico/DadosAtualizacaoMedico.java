package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

// regra de negocio: podem ser atualizados apenas os seguintes dados Telefone, Nome e Endereco.
public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String telefone,
        String nome,
        @Valid DadosEndereco endereco) {
}
