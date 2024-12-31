package utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ruolo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private HashMap<String,Dipendente> dipendenti;

    public Ruolo(String nome){
        this.nome=nome;
        dipendenti=new HashMap<>();
    }

    public String getNome() {
        return nome;
    }
    public void  addDipendente(Dipendente d) {
        if(dipendenti.containsKey(d.getEmail()) && dipendenti.get(d.getEmail()).equals(d))
            throw new IllegalArgumentException("Dipendente già assegnato al ruolo");
        dipendenti.put(d.getEmail(), d);
    }

    public void removeDipendente(Dipendente d){
        if(!dipendenti.containsKey(d.getEmail()))
            throw new IllegalArgumentException("Dipendente non presente");
        dipendenti.remove(d.getEmail());
    }

    public HashMap<String, Dipendente> getDipendenti() {
        return dipendenti;
    }
    public Dipendente getDipendente(String email){
        return dipendenti.get(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ruolo ruolo)) return false;
        return Objects.equals(nome, ruolo.nome) && Objects.equals(dipendenti, ruolo.dipendenti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dipendenti);
    }

    @Override
    public String toString() {
        return "Ruolo{" +
                "nome='" + nome + '\'' +
                ", dipendenti=" + dipendenti +
                '}';
    }

    public void setState(Ruolo vecchio) {
        this.dipendenti=vecchio.getDipendenti();
    }
    public Ruolo deepCopy() {
        Ruolo copy = new Ruolo(this.nome);
        for (Map.Entry<String, Dipendente> entry : this.dipendenti.entrySet()) {
            copy.dipendenti.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return copy;
    }
}
