package com.group1.gestao_seguranca.dto.movimentacoes;

import com.group1.gestao_seguranca.entities.Movimentacoes;
import com.group1.gestao_seguranca.enums.TipoEntrada;
import com.group1.gestao_seguranca.enums.TipoVisitanteEnum;

import java.time.LocalDateTime;
import java.util.List;

public class MovimentacaoResponseDTO {

    private int id_movimentacao;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private String observacoes;
    private boolean ativa; // hora_saida == null
    private String setorDestino;

    // Quem entrou
    private TipoEntrada tipoPessoa;       // "FUNCIONARIO" ou "VISITANTE"
    private int idPessoa;
    private String nomePessoa;

    // Só preenchido para visitantes
    private TipoVisitanteEnum tipoVisita;
    private String nomeResponsavel;

    // Pendências (usado apenas na resposta da saída)
    private String aviso;
    private List<EntregaPendenteDTO> entregasPendentes;

    MovimentacaoResponseDTO() {
    }

    // Para listagem
    public static MovimentacaoResponseDTO from(Movimentacoes m) {
        MovimentacaoResponseDTO dto = new MovimentacaoResponseDTO();
        dto.id_movimentacao = m.getId();
        dto.horaEntrada = m.getHoraEntrada();
        dto.horaSaida = m.getHoraSaida();
        dto.observacoes = m.getObservacoes();
        dto.ativa = m.getHoraSaida() == null;
        dto.setorDestino = m.getSetorDestino();

        if (m.getFuncionario() != null) {
            dto.tipoPessoa = TipoEntrada.FUNCIONARIO;
            dto.idPessoa = m.getFuncionario().getId();
            dto.nomePessoa = m.getFuncionario().getNomeFuncionario();
        } else if (m.getVisitante() != null) {
            dto.tipoPessoa = TipoEntrada.VISITANTE;
            dto.idPessoa = m.getVisitante().getId();
            dto.nomePessoa = m.getVisitante().getNomeVisitante();
            dto.tipoVisita = m.getTipoVisitante();

            if (m.getFuncionarioResponsavel() != null) {
                dto.nomeResponsavel = m.getFuncionarioResponsavel().getNomeFuncionario();
            }
        }

        return dto;
    }

    // Para saída sem pendências
    public MovimentacaoResponseDTO(Movimentacoes m) {
        this(m, null, null);
    }

    // Para saída com pendências
    public MovimentacaoResponseDTO(Movimentacoes m, String aviso, List<EntregaPendenteDTO> pendentes) {
        MovimentacaoResponseDTO base = from(m);
        this.id_movimentacao = base.id_movimentacao;
        this.horaEntrada = base.horaEntrada;
        this.horaSaida = base.horaSaida;
        this.observacoes = base.observacoes;
        this.ativa = base.ativa;
        this.tipoPessoa = base.tipoPessoa;
        this.idPessoa = base.idPessoa;
        this.nomePessoa = base.nomePessoa;
        this.tipoVisita = base.tipoVisita;
        this.nomeResponsavel = base.nomeResponsavel;
        this.aviso = aviso;
        this.entregasPendentes = pendentes;
    }

    public void setEntregasPendentes(List<EntregaPendenteDTO> entregasPendentes) {
        this.entregasPendentes = entregasPendentes;
    }

    // getters
    public int getId() {
        return id_movimentacao;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public int getId_movimentacao() {
        return id_movimentacao;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public TipoEntrada getTipoPessoa() {
        return tipoPessoa;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public TipoVisitanteEnum getTipoVisita() {
        return tipoVisita;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public String getAviso() {
        return aviso;
    }

    public List<EntregaPendenteDTO> getEntregasPendentes() {
        return entregasPendentes;
    }
}