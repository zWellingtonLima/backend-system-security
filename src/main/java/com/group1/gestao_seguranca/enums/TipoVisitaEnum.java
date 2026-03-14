package com.group1.gestao_seguranca.enums;

public enum StatusMolhoEnum implements LabeledEnum {
    DISPONIVEL("Disponível"),
    PERDIDA("Perdida"),
    EM_USO("Em uso");

    private final String label;

    StatusMolhoEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}

