package io.github.aguirresabino.deliverycaseiro.model.enums;

public enum ValuesApplicationEnum {

    URL_BASE_DELIVERY_SERVICE("https://comida-caseira.herokuapp.com/comida-caseira/"),
    MY_TAG("DELIVERY_CASEIRO_DEBUG");

    private String value;

    ValuesApplicationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
