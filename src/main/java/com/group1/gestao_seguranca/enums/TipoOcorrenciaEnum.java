package com.group1.gestao_seguranca.enums;

public enum TipoOcorrenciaEnum implements LabeledEnum {
    ACESSO_NAO_AUTORIZADO("Acesso Não Autorizado"),
    AVARIA_EQUIPAMENTO("Avaria / Equipamento Danificado"),
    OBJETO_PERDIDO_ENCONTRADO("Objeto Perdido / Encontrado"),
    INCIDENTE_COM_VISITANTE("Incidente com Visitante"),
    ALARME_DISPARADO("Alarme Disparado"),
    OUTROS("Outros");

    private final String label;

    TipoOcorrenciaEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
