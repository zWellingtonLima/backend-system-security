package com.group1.gestao_seguranca.enums;

public enum TipoChaveEnum implements LabeledEnum {

    CHAVE("Chave"),
    MOLHO("Molho");
    private final String label;

    TipoChaveEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
