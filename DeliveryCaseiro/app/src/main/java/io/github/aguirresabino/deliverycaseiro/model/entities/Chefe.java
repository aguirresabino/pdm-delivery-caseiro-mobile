package io.github.aguirresabino.deliverycaseiro.model.entities;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chefe extends Usuario {

    @SerializedName("horaFinal")
    @Expose
    private String horaFinal;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;

    public Chefe() {
        super();
    }

    public Chefe(String id, String nome, String email, String senha, String telefone, String imagem, Endereco endereco, String horaFinal, String horaInicio) {
        super(id, nome, email, senha, telefone, imagem, endereco);
        this.horaFinal = horaFinal;
        this.horaInicio = horaInicio;
    }

    public Chefe(Parcel in) {
        super(in);
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(horaInicio);
        dest.writeString(horaFinal);
    }
}
