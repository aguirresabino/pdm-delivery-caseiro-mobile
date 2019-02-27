package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Chefe implements Cloneable, Parcelable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("endereco")
    @Expose
    private Endereco endereco;
    @SerializedName("imagem")
    @Expose
    private String imagem;
    @SerializedName("telefone")
    @Expose
    private String telefone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("senha")
    @Expose
    private String senha;
    @SerializedName("horaFinal")
    @Expose
    private String horaFinal;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;

    public Chefe() {
    }

    public Chefe(String _id, Endereco endereco, String imagem, String telefone, String email, String nome, String senha, String horaFinal, String horaInicio) {
        this._id = _id;
        this.endereco = endereco;
        this.imagem = imagem;
        this.telefone = telefone;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.horaFinal = horaFinal;
        this.horaInicio = horaInicio;
    }

    protected Chefe(Parcel in) {
        _id = in.readString();
        endereco = in.readParcelable(Endereco.class.getClassLoader());
        imagem = in.readString();
        telefone = in.readString();
        email = in.readString();
        nome = in.readString();
        senha = in.readString();
        horaFinal = in.readString();
        horaInicio = in.readString();
    }

    public static final Creator<Chefe> CREATOR = new Creator<Chefe>() {
        @Override
        public Chefe createFromParcel(Parcel in) {
            return new Chefe(in);
        }

        @Override
        public Chefe[] newArray(int size) {
            return new Chefe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeParcelable(endereco, flags);
        dest.writeString(imagem);
        dest.writeString(telefone);
        dest.writeString(email);
        dest.writeString(nome);
        dest.writeString(senha);
        dest.writeString(horaFinal);
        dest.writeString(horaInicio);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chefe chefe = (Chefe) o;
        return Objects.equals(_id, chefe._id) &&
                Objects.equals(endereco, chefe.endereco) &&
                Objects.equals(imagem, chefe.imagem) &&
                Objects.equals(telefone, chefe.telefone) &&
                Objects.equals(email, chefe.email) &&
                Objects.equals(nome, chefe.nome) &&
                Objects.equals(senha, chefe.senha) &&
                Objects.equals(horaFinal, chefe.horaFinal) &&
                Objects.equals(horaInicio, chefe.horaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, endereco, imagem, telefone, email, nome, senha, horaFinal, horaInicio);
    }

    @Override
    public String toString() {
        return "Chefe{" +
                "_id='" + _id + '\'' +
                ", endereco=" + endereco +
                ", imagem='" + imagem + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", horaFinal='" + horaFinal + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                '}';
    }
}
