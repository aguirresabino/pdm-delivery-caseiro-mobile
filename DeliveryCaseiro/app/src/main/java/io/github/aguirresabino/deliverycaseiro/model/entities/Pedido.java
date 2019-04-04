package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

public class Pedido implements Parcelable, Cloneable {
    private String _id;
    private String idUsuario;
    private String idFornecedor;
    private List<ItemPedido> itens;
    private String valor;
    private boolean status;
    private boolean entregar;
    private Endereco endereco;

    public Pedido() {
    }

    public Pedido(String _id, String idUsuario, String idFornecedor, List<ItemPedido> itens, String valor, boolean status, boolean entregar, Endereco endereco) {
        this._id = _id;
        this.idUsuario = idUsuario;
        this.idFornecedor = idFornecedor;
        this.itens = itens;
        this.valor = valor;
        this.status = status;
        this.entregar = entregar;
        this.endereco = endereco;
    }


    protected Pedido(Parcel in) {
        _id = in.readString();
        idUsuario = in.readString();
        idFornecedor = in.readString();
        itens = in.createTypedArrayList(ItemPedido.CREATOR);
        valor = in.readString();
        status = in.readByte() != 0;
        entregar = in.readByte() != 0;
        endereco = in.readParcelable(Endereco.class.getClassLoader());
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

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
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

    public Integer getValor() {
        return Integer.valueOf(valor);
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isEntregar() {
        return entregar;
    }

    public void setEntregar(boolean entregar) {
        this.entregar = entregar;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return valor == pedido.valor &&
                status == pedido.status &&
                entregar == pedido.entregar &&
                Objects.equals(_id, pedido._id) &&
                Objects.equals(idUsuario, pedido.idUsuario) &&
                Objects.equals(idFornecedor, pedido.idFornecedor) &&
                Objects.equals(itens, pedido.itens) &&
                Objects.equals(endereco, pedido.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, idUsuario, idFornecedor, itens, valor, status, entregar, endereco);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "_id='" + _id + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", idFornecedor='" + idFornecedor + '\'' +
                ", itens=" + itens +
                ", valor=" + valor +
                ", status=" + status +
                ", entregar=" + entregar +
                ", endereco=" + endereco +
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
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeByte((byte) (entregar ? 1 : 0));
        dest.writeParcelable(endereco, flags);
    }
}
