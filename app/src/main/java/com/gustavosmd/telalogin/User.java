package com.gustavosmd.telalogin;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class User implements Parcelable,Comparable  {
    private String login,senha,nome;

    int id;

    public User(String login, String senha, String nome){
    setLogin(login);
    setSenha(senha);
    setNome(nome);
    }
    public User(String login, String senha, String nome, int id){
        setLogin(login);
        setSenha(senha);
        setNome(nome);
        setId(id);
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


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    //Implementação da transferencia do Parceable, para se transportar o User entre atividades.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String teste= login+","+senha+","+nome;
        dest.writeString(teste);
    }

    //Implementação da recuperação do Parceable, para se recuperar o User do transporte entre atividades.
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private User(Parcel in) {
        String teste[] = in.readString().split(",");
        login=teste[0];
        senha=teste[1];
        nome=teste[2];
    }



    //Implementação de Comparable, para reorganizar após a recuperação pela Forma 2 no main
    //Necessário pois o putSetString é um metódo não organizado, logo ele não guarda a ordem dos valores.
    @Override
    public int compareTo(@NonNull Object o) {
        int compare =((User)o).getId();
        return this.id-compare;
    }
}
