package composite;

import utils.Dipendente;
import utils.Ruolo;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class UnitaFoglia implements Unita{
    private static final long serialVersionUID = 1L;
    private String nome;
    private String padre;
    private HashMap<String,Ruolo>ruoli;

    public UnitaFoglia(String nome,String padre){
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

    @Override
    public void addSottoUnita(Unita u) {
        return;
    }

    @Override
    public Ruolo getRuolo(String r) {
        return ruoli.get(r);
    }
    public void copiaStato(Unita u){
        this.ruoli=u.getRuoli();
    }
}
