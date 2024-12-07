package composite;

import utils.Dipendente;
import utils.Ruolo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface Unita extends Serializable {
    String getNome();
    List<Unita> getSottoUnita();
    void setPadre(Unita u);
    Unita getPadre();
    void addRuolo(Ruolo r) throws Exception.UnitaException.RuoloEsistenteException;
    void removeRuolo(Ruolo r) throws Exception.UnitaException.RuoloInesistenteException;
    HashMap<String, Ruolo> getRuoli();
    void addDipendente(Dipendente d,Ruolo r) throws Exception.UnitaException.RuoloEsistenteException, Exception.RuoloException.RuoloAssegnatoException;
    void removeDipendente(Dipendente d, Ruolo r) throws Exception.UnitaException.RuoloInesistenteException, Exception.RuoloException.RuoloNonAssegnatoException;
    HashMap<String,Dipendente> getDipendenti(Ruolo r) throws Exception.UnitaException.RuoloInesistenteException;
}
