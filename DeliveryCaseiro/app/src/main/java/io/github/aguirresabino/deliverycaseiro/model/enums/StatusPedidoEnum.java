package io.github.aguirresabino.deliverycaseiro.model.enums;

public enum StatusPedidoEnum {
    PEDIDO_EM_PREPARO("Em preparo"),
    PEDIDO_SAIU_PARA_ENTREGA("Saiu para entrega"),
    PEDIDO_ENTREGUE("Entregue"),
    PEDIDO_POSTADO("Pedido Postado");

    private String value;

    StatusPedidoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
