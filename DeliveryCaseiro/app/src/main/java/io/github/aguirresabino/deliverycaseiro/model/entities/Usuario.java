package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario implements Cloneable, Parcelable {

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

    public Usuario() {
    }

    public Usuario(String id, String nome, String email, String senha, String telefone, String imagem, Endereco endereco) {
        this._id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.imagem = imagem;
        this.endereco = endereco;
    }

    protected Usuario(Parcel in) {
        _id = in.readString();
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
        telefone = in.readString();
        imagem = in.readString();
        endereco = in.readParcelable(Endereco.class.getClassLoader());
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + _id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", telefone='" + telefone + '\'' +
                ", imagem='" + imagem + '\'' +
                ", endereco=" + endereco +
                '}';
    }

    public Usuario getClone() {
        try {
            return (Usuario) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return this;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeString(telefone);
        dest.writeString(imagem);
        dest.writeParcelable(endereco, flags);
    }
}
