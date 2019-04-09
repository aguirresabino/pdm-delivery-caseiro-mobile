package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Pedido implements Parcelable, Cloneable {

    @SerializedName("id")
    private String _id;
    private String idUsuario;
    private String idFornecedor;
    private List<ItemPedido> itens;
    private String valor;
    private String status;
    private boolean pedidoCustomizado;
    private Endereco endereco;
    private String imagem;
    // Estratégia adotada temporariamente, enquanto utilizar o json server
    private List<ChefePedidoCustomizado> chefesDoPedidoCustomizado;

    public Pedido() {
    }

    public Pedido(String _id, String idUsuario, String idFornecedor, List<ItemPedido> itens, String valor, String status, boolean pedidoCustomizado, Endereco endereco, String imagem, List<ChefePedidoCustomizado> chefesDoPedidoCustomizado) {
        this._id = _id;
        this.idUsuario = idUsuario;
        this.idFornecedor = idFornecedor;
        this.itens = itens;
        this.valor = valor;
        this.status = status;
        this.pedidoCustomizado = pedidoCustomizado;
        this.endereco = endereco;
        this.imagem = imagem;
        this.chefesDoPedidoCustomizado = chefesDoPedidoCustomizado;
    }

    protected Pedido(Parcel in) {
        _id = in.readString();
        idUsuario = in.readString();
        idFornecedor = in.readString();
        itens = in.createTypedArrayList(ItemPedido.CREATOR);
        valor = in.readString();
        status = in.readString();
        pedidoCustomizado = in.readByte() != 0;
        endereco = in.readParcelable(Endereco.class.getClassLoader());
        imagem = in.readString();
        chefesDoPedidoCustomizado = in.createTypedArrayList(ChefePedidoCustomizado.CREATOR);
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(String idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPedidoCustomizado() {
        return pedidoCustomizado;
    }

    public void setPedidoCustomizado(boolean pedidoCustomizado) {
        this.pedidoCustomizado = pedidoCustomizado;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<ChefePedidoCustomizado> getChefesDoPedidoCustomizado() {
        return chefesDoPedidoCustomizado;
    }

    public void setChefesDoPedidoCustomizado(List<ChefePedidoCustomizado> chefesDoPedidoCustomizado) {
        this.chefesDoPedidoCustomizado = chefesDoPedidoCustomizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return pedidoCustomizado == pedido.pedidoCustomizado &&
                Objects.equals(_id, pedido._id) &&
                Objects.equals(idUsuario, pedido.idUsuario) &&
                Objects.equals(idFornecedor, pedido.idFornecedor) &&
                Objects.equals(itens, pedido.itens) &&
                Objects.equals(valor, pedido.valor) &&
                Objects.equals(status, pedido.status) &&
                Objects.equals(endereco, pedido.endereco) &&
                Objects.equals(imagem, pedido.imagem) &&
                Objects.equals(chefesDoPedidoCustomizado, pedido.chefesDoPedidoCustomizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, idUsuario, idFornecedor, itens, valor, status, pedidoCustomizado, endereco, imagem, chefesDoPedidoCustomizado);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "_id='" + _id + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", idFornecedor='" + idFornecedor + '\'' +
                ", itens=" + itens +
                ", valor='" + valor + '\'' +
                ", status='" + status + '\'' +
                ", pedidoCustomizado=" + pedidoCustomizado +
                ", endereco=" + endereco +
                ", imagem='" + imagem + '\'' +
                ", chefesDoPedidoCustomizado=" + chefesDoPedidoCustomizado +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(idUsuario);
        dest.writeString(idFornecedor);
        dest.writeTypedList(itens);
        dest.writeString(valor);
        dest.writeString(status);
        dest.writeByte((byte) (pedidoCustomizado ? 1 : 0));
        dest.writeParcelable(endereco, flags);
        dest.writeString(imagem);
        dest.writeTypedList(chefesDoPedidoCustomizado);
    }
}