package composite;

import utils.Dipendente;
import utils.Ruolo;
import java.util.HashMap;
import java.util.List;


public class UnitaFoglia implements Unita{
    private String nome;
    private Unita padre;
    private HashMap<String,Ruolo>ruoli;

    public UnitaFoglia(String nome,Unita padre){
        this.nome=nome;
        this.padre=padre;
        ruoli=new HashMap<>();

    }
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public List<Unita> getSottoUnita() {
        return null;
    }

    @Override
    public void setPadre(Unita u) {
        this.padre=u;
    }

    @Override
    public Unita getPadre() {
        return padre;
    }

    @Override
    public void addRuolo(Ruolo r){
        ruoli.put(r.getNome(),r);
    }

    @Override
    public void removeRuolo(Ruolo r){
        if(!ruoli.containsKey(r.getNome()))
            throw new IllegalArgumentException("Ruolo non trovato");
        ruoli.remove(r.getNome());
    }

    @Override
    public HashMap<String, Ruolo> getRuoli() {
        return  ruoli;
    }

    @Override
    public void addDipendente(Dipendente d, Ruolo r) {
        r.addDipendente(d);
    }

    @Override
    public void removeDipendente(Dipendente d, Ruolo r) {
        if(!ruoli.containsKey(r.getNome()))
            throw new IllegalArgumentException("ruolo non trovato");
        r.removeDipendente(d);
    }

    @Override
    public HashMap<String, Dipendente> getDipendenti(Ruolo r){
        if(!ruoli.containsKey(r.getNome()))
            throw new IllegalArgumentException("ruolo non trovato");
        return r.getDipendenti();
    }
}
