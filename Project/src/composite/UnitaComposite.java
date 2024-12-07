package composite;

import utils.Dipendente;
import utils.Ruolo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import Exception.UnitaException;

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


    public void addSottoUnita(Unita u) throws Exception.UnitaException.SottoUnitaEsistenteException {
        if(sottoUnita.contains(u))
            throw new UnitaException.SottoUnitaEsistenteException();
        sottoUnita.add(u);
    }


    public void remoeSottoUnita(Unita u) throws Exception.UnitaException.SottoUnitaInesistenteException {
        if(sottoUnita.contains(u))
            throw new UnitaException.SottoUnitaInesistenteException();
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
    public void addRuolo(Ruolo r) throws Exception.UnitaException.RuoloEsistenteException {
        if(ruoli.containsKey(r.getNome()))
            throw new UnitaException.RuoloEsistenteException();
        ruoli.put(r.getNome(),r);
    }

    @Override
    public void removeRuolo(Ruolo r) throws Exception.UnitaException.RuoloInesistenteException {
        if(!ruoli.containsKey(r.getNome()))
            throw new UnitaException.RuoloInesistenteException();
        ruoli.remove(r.getNome());
    }

    @Override
    public HashMap<String, Ruolo> getRuoli() {
        return  ruoli;
    }

    @Override
    public void addDipendente(Dipendente d, Ruolo r) throws Exception.UnitaException.RuoloEsistenteException, Exception.RuoloException.RuoloAssegnatoException {
        if(ruoli.containsKey(r.getNome()))
            throw new UnitaException.RuoloEsistenteException();
        r.addDipendente(d);
    }

    @Override
    public void removeDipendente(Dipendente d, Ruolo r) throws Exception.UnitaException.RuoloInesistenteException, Exception.RuoloException.RuoloNonAssegnatoException {
        if(!ruoli.containsKey(r.getNome()))
            throw new UnitaException.RuoloInesistenteException();
        r.removeDipendente(d);
    }

    @Override
    public HashMap<String, Dipendente> getDipendenti(Ruolo r) throws Exception.UnitaException.RuoloInesistenteException {
        if(!ruoli.containsKey(r.getNome()))
            throw new UnitaException.RuoloInesistenteException();
        return r.getDipendenti();
    }
}
