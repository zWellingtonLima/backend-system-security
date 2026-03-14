package com.group1.gestao_seguranca.enums;

public enum TipoVisitaEnum implements LabeledEnum {
    VISITA("Visita"),
    ENTREGA("Entrega"),
    MANUTENCAO("Manutenção"),
    REUNIAO("Reunião"),
    SERVICO("Serviço");

    private final String label;

    TipoVisitaEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}

