package composite;

import utils.Dipendente;
import utils.Ruolo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UnitaComposite implements Unita {
    private String nome;
    private Unita padre;
    private HashMap<String,Ruolo>ruoli;
    private List<Unita> sottoUnita;

    public UnitaComposite(String nome,Unita padre){
        this.nome=nome;
        this.padre=padre;
        ruoli=new HashMap<>();
        sottoUnita=new LinkedList<>();
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public List<Unita> getSottoUnita() {
        return sottoUnita;
    }
    public void addSottoUnita(Unita u){
        sottoUnita.add(u);
    }

    public void removeSottoUnita(Unita u){
        if(!sottoUnita.contains(u))
            throw new IllegalArgumentException("Sotto unità non presente");
        sottoUnita.remove(u);
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
    public void removeRuolo(Ruolo r) {
        if(!ruoli.containsKey(r.getNome()))
            throw new IllegalArgumentException("Impossibile rimuovere il ruolo");
        ruoli.remove(r.getNome());
    }

    @Override
    public HashMap<String, Ruolo> getRuoli() {
        return  ruoli;
    }

    public Ruolo getRuolo(String r){
        return ruoli.get(r);
    }

    @Override
    public void addDipendente(Dipendente d, Ruolo r) {
        if(!ruoli.containsKey(r.getNome()))
            throw new IllegalArgumentException("Impossibile aggiungere il dipendente ruolo non trovato");
        r.addDipendente(d);
    }

    @Override
    public void removeDipendente(Dipendente d, Ruolo r) {
        if(!ruoli.containsKey(r.getNome()))
            throw new IllegalArgumentException("Ruolo non trovato impossibile rimuover dipendente");
        r.removeDipendente(d);
    }

    @Override
    public HashMap<String, Dipendente> getDipendenti(Ruolo r) {
        if(!ruoli.containsKey(r.getNome()))
            throw new IllegalArgumentException("Ruolo non Trovato");
        return r.getDipendenti();
    }
}
