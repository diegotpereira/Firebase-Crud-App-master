package br.java.firebase_crud_app_master;

import android.os.Parcel;
import android.os.Parcelable;

public class CursoRVModal implements  Parcelable{

    // criando variáveis para nossos diferentes campos.
    private String cursoNome;
    private String cursoDescricao;
    private String cursoPreco;
    private String maisAdequadoPara;
    private String cursoImg;
    private String cursoLink;
    private String cursoId;

    public CursoRVModal() {
    }

    public CursoRVModal(String cursoNome, String cursoDescricao, String cursoPreco, String maisAdequadoPara, String cursoImg, String cursoLink, String cursoId) {
        this.cursoNome = cursoNome;
        this.cursoDescricao = cursoDescricao;
        this.cursoPreco = cursoPreco;
        this.maisAdequadoPara = maisAdequadoPara;
        this.cursoImg = cursoImg;
        this.cursoLink = cursoLink;
        this.cursoId = cursoId;
    }

    @Override
    public  int describeContents() {
        return 0;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    public String getCursoDescricao() {
        return cursoDescricao;
    }

    public void setCursoDescricao(String cursoDescricao) {
        this.cursoDescricao = cursoDescricao;
    }

    public String getCursoPreco() {
        return cursoPreco;
    }

    public void setCursoPreco(String cursoPreco) {
        this.cursoPreco = cursoPreco;
    }

    public String getMaisAdequadoPara() {
        return maisAdequadoPara;
    }

    public void setMaisAdequadoPara(String maisAdequadoPara) {
        this.maisAdequadoPara = maisAdequadoPara;
    }

    public String getCursoImg() {
        return cursoImg;
    }

    public void setCursoImg(String cursoImg) {
        this.cursoImg = cursoImg;
    }

    public String getCursoLink() {
        return cursoLink;
    }

    public void setCursoLink(String cursoLink) {
        this.cursoLink = cursoLink;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    protected CursoRVModal(Parcel in) {
        cursoNome = in.readString();
        cursoId = in.readString();
        cursoDescricao = in.readString();
        cursoPreco = in.readString();
        maisAdequadoPara = in.readString();
        cursoImg = in.readString();
        cursoLink = in.readString();
    }

    public static  final Creator<CursoRVModal> CREATOR = new Creator<CursoRVModal>() {
        @Override
        public CursoRVModal createFromParcel(Parcel in) {
            return new CursoRVModal(in);
        }

        @Override
        public CursoRVModal[] newArray(int size) {
            return new CursoRVModal[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cursoNome);
        dest.writeString(cursoId);
        dest.writeString(cursoDescricao);
        dest.writeString(cursoPreco);
        dest.writeString(maisAdequadoPara);
        dest.writeString(cursoImg);
        dest.writeString(cursoLink);
    }
}
