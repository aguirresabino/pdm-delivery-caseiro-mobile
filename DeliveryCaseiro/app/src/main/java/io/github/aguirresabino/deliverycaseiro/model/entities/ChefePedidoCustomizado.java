package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class ChefePedidoCustomizado implements Cloneable, Parcelable {

    private String id, nome, imagem, valor;

    public ChefePedidoCustomizado() {
    }

    public ChefePedidoCustomizado(String nome, String imagem, String valor, String id) {
        this.nome = nome;
        this.imagem = imagem;
        this.valor = valor;
        this.id = id;
    }

    protected ChefePedidoCustomizado(Parcel in) {
        id = in.readString();
        nome = in.readString();
        imagem = in.readString();
        valor = in.readString();
    }

    public static final Creator<ChefePedidoCustomizado> CREATOR = new Creator<ChefePedidoCustomizado>() {
        @Override
        public ChefePedidoCustomizado createFromParcel(Parcel in) {
            return new ChefePedidoCustomizado(in);
        }

        @Override
        public ChefePedidoCustomizado[] newArray(int size) {
            return new ChefePedidoCustomizado[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChefePedidoCustomizado that = (ChefePedidoCustomizado) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(imagem, that.imagem) &&
                Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, imagem, valor);
    }

    @Override
    public String toString() {
        return "ChefePedidoCustomizado{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", imagem='" + imagem + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeString(imagem);
        dest.writeString(valor);
    }
}
