package io.github.aguirresabino.deliverycaseiro.model.enums;

public enum StatusPedidoEnum {
    PEDIDO_EM_PREPARO("0"), PEDIDO_SAIU_PARA_ENTREGA("1"), PEDIDO_ENTREGUE("3");

    private String value;

    StatusPedidoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
