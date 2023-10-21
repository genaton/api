package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//CLASSES SERVICE PROCESSAM AS REGRAS DE NEGOCIOS E AS VALIDACOES DA APLICACAO
@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores; // o spring permite chamar a interface que é usada em cada validador e fazer uma lista de todas as classes de validadores criadas. Isso evita instanciar uma a uma.
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
       if (!pacienteRepository.existsById(dados.idPaciente())){
           throw new ValidacaoException("Id do paciente informado nao existe");
       }
        if (dados.idMedico()!= null && !medicoRepository.existsById(dados.idMedico())){ // regra de neg diz que nao eh necessario informar o medico para marcar uma consulta.
            throw new ValidacaoException("Id do medico informado nao existe");
        };

        validadores.forEach( v-> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        var medico = escolherMedico(dados);

        if (medico == null){
            throw new ValidacaoException("Nao existe medico disponivel nesta data");

        }

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {// escolha aleatoria do medico
        if (dados.idMedico()!=null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade eh obrigatoria quando o medico nao eh escolhido");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }


    public void cancelar(DadosCancelamentoConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta informado nao existe");
        }
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

}
