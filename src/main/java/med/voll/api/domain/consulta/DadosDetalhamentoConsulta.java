package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
//ATENCAO O MÉTIDO ABAIXO RETORNARA 403 quando colocarmos o motivo de cancelamento em uma consulta que nao foi cancelada pois retornara um valor nulo (null) isso devera ser tratado em erros de validacao
//public record DadosDetalhamentoConsulta(Long id,  Long idPaciente, Long idMedico, LocalDateTime data, String MotivoCancelamento) {
//    public DadosDetalhamentoConsulta(Consulta consulta) {
//        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData(), consulta.getMotivoCancelamento().toString());
//    }

    public record DadosDetalhamentoConsulta(Long id,  Long idPaciente, Long idMedico, LocalDateTime data) {
        public DadosDetalhamentoConsulta(Consulta consulta) {
            this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData());
        }
}
