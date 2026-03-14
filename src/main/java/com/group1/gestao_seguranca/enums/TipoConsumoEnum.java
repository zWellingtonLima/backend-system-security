package com.group1.gestao_seguranca.enums;

public enum TipoConsumoEnum implements LabeledEnum {

    AGUA("Água"), ELETRICIDADE("Eletricidade"), GAS("Gás");

    private final String label;

    TipoConsumoEnum(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
