package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Prato implements Parcelable, Cloneable {

    private String _id;
    private boolean disponivel;
    private String imagem;
    private String valor;
    private String descricao;
    private String nome;

    public Prato() {
    }

    public Prato(String _id, boolean disponivel, String imagem, String valor, String descricao, String nome) {
        this._id = _id;
        this.disponivel = disponivel;
        this.imagem = imagem;
        this.valor = valor;
        this.descricao = descricao;
        this.nome = nome;
    }

    protected Prato(Parcel in) {
        _id = in.readString();
        disponivel = in.readByte() != 0;
        imagem = in.readString();
        valor = in.readString();
        descricao = in.readString();
        nome = in.readString();
    }

    public static final Creator<Prato> CREATOR = new Creator<Prato>() {
        @Override
        public Prato createFromParcel(Parcel in) {
            return new Prato(in);
        }

        @Override
        public Prato[] newArray(int size) {
            return new Prato[size];
        }
    };

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Integer getValor() {
        return Integer.valueOf(valor);
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prato prato = (Prato) o;
        return disponivel == prato.disponivel &&
                Objects.equals(_id, prato._id) &&
                Objects.equals(imagem, prato.imagem) &&
                Objects.equals(valor, prato.valor) &&
                Objects.equals(descricao, prato.descricao) &&
                Objects.equals(nome, prato.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, disponivel, imagem, valor, descricao, nome);
    }

    @Override
    public String toString() {
        return "Prato{" +
                "_id='" + _id + '\'' +
                ", disponivel=" + disponivel +
                ", imagem='" + imagem + '\'' +
                ", valor='" + valor + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeByte((byte) (disponivel ? 1 : 0));
        dest.writeString(imagem);
        dest.writeString(valor);
        dest.writeString(descricao);
        dest.writeString(nome);
    }
}
