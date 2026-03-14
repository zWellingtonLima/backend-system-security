package com.group1.gestao_seguranca.enums;

public enum PisoEnum implements LabeledEnum {
    PRIMEIRO("Primeiro"),
    SEGUNDO("Segundo"),
    TERCEIRO("Terceiro");

    private final String label;

    PisoEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
