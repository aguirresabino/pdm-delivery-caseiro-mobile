package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class ItemPedido implements Parcelable, Cloneable {
    private String _id;
    private String nome;
    private String descricao;
    private String valor;
    private Integer quantidade;

    public ItemPedido() {
    }

    public ItemPedido(String _id, String nome, String descricao, String valor, Integer quantidade) {
        this._id = _id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    protected ItemPedido(Parcel in) {
        _id = in.readString();
        nome = in.readString();
        descricao = in.readString();
        if (in.readByte() == 0) {
            quantidade = null;
        } else {
            quantidade = in.readInt();
        }
    }

    public static final Creator<ItemPedido> CREATOR = new Creator<ItemPedido>() {
        @Override
        public ItemPedido createFromParcel(Parcel in) {
            return new ItemPedido(in);
        }

        @Override
        public ItemPedido[] newArray(int size) {
            return new ItemPedido[size];
        }
    };

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getValor() {
        return Integer.valueOf(valor);
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(_id, that._id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(descricao, that.descricao) &&
                Objects.equals(valor, that.valor) &&
                Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, nome, descricao, valor, quantidade);
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "_id='" + _id + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(nome);
        dest.writeString(descricao);
        if (quantidade == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantidade);
        }
    }
}
