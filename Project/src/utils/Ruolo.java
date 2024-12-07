package utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import Exception.RuoloException;

public class Ruolo implements Serializable {
    private String nome;
    private HashMap<String,Dipendente> dipendenti;

    public Ruolo(String nome){
        this.nome=nome;
        dipendenti=new HashMap<>();
    }

    public String getNome() {
        return nome;
    }
    public void  addDipendente(Dipendente d) throws Exception.RuoloException.RuoloAssegnatoException {
        if(dipendenti.containsKey(d.getEmail()))
            throw new RuoloException.RuoloAssegnatoException(d);
        dipendenti.put(d.getEmail(), d);
    }

    public void removeDipendente(Dipendente d)throws Exception.RuoloException.RuoloNonAssegnatoException {
        if(!dipendenti.containsKey(d.getEmail()))
            throw new RuoloException.RuoloNonAssegnatoException(d);
        dipendenti.remove(d.getEmail());
    }

    public HashMap<String, Dipendente> getDipendenti() {
        return dipendenti;
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
}
