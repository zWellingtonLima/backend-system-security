package com.group1.gestao_seguranca.enums;

public enum EstadoOcorrenciaEnum implements LabeledEnum {
    PENDENTE("Pendente"),
    EM_ANALISE("Em Análise"),
    RESOLVIDA("Resolvida"),
    CANCELADA("Cancelada");

    private final String label;

    EstadoOcorrenciaEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}