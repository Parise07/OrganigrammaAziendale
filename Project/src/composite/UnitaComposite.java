package composite;

import utils.Dipendente;
import utils.Ruolo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UnitaComposite implements Unita {
    private static final long serialVersionUID = 1L;
    private String nome;
    private String padre;
    private HashMap<String,Ruolo>ruoli;
    List<String> sottoUnita;

    public UnitaComposite(String nome,String padre){
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
    public List<String> getSottoUnita() {
        return sottoUnita;
    }

    public void addSottoUnita(String u){
        sottoUnita.add(u);
    }

    public void removeSottoUnita(String u){
        if(!sottoUnita.contains(u))
            throw new IllegalArgumentException("Sotto unità non presente");
        sottoUnita.remove(u);
    }

    @Override
    public void setPadre(String u) {
        this.padre=u;
    }

    @Override
    public String getPadre() {
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

    public void copiaStato(Unita u){
        this.sottoUnita=u.getSottoUnita();
        this.ruoli=u.getRuoli();
    }

    public UnitaComposite deepCopy() {
        UnitaComposite copy = new UnitaComposite(this.nome, this.padre);

        for (Map.Entry<String, Ruolo> entry : this.ruoli.entrySet()) {
            copy.ruoli.put(entry.getKey(), entry.getValue().deepCopy()); // Assumi che Ruolo abbia un metodo deepCopy()
        }

        // Copia delle sotto-unità (lista di stringhe)
        copy.sottoUnita = new LinkedList<>(this.sottoUnita);

        return copy;
    }
}
