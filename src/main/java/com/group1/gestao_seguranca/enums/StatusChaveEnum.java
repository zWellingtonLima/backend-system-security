package com.group1.gestao_seguranca.enums;

public enum StatusChaveEnum implements LabeledEnum {
    DISPONIVEL("Disponível"),
    EMPRESTADA("Emprestada"),
    PERDIDA("Perdida"),
    MANUTENCAO("Manutenção");

    private final String label;

    StatusChaveEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
