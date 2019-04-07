package io.github.aguirresabino.deliverycaseiro.model.enums;

public enum ValuesApplicationEnum {

//    URL_BASE_DELIVERY_SERVICE("https://comida-caseira.herokuapp.com/comida-caseira/"),
    URL_BASE_DELIVERY_SERVICE("http://10.0.3.2:3000/"),
//    URL_BASE_DELIVERY_SERVICE("http://192.168.0.102:3000/"),
    MY_TAG("DELIVERY_CASEIRO_DEBUG");

    private String value;

    ValuesApplicationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
