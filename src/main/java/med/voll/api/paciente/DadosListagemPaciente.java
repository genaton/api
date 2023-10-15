package med.voll.api.paciente;

import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.Medico;

public record DadosListagemPaciente(Long id, String nome, String cpf, String telefone, String email, Endereco endereco) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getEmail(), paciente.getTelefone(), paciente.getEndereco());
    }

}
