package utils;

import java.io.Serializable;

public class Dipendente implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String cognome;
    private String email;

    public Dipendente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }
    public String getEmail() {
        return email;
    }
    public String toString(){
        return this.getNome()+" "+this.getCognome()+" "+this.getEmail();
    }
    public Dipendente deepCopy() {
        return new Dipendente(this.nome, this.cognome,this.email);
    }
}
