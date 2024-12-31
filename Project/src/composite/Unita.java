package composite;

import utils.Dipendente;
import utils.Ruolo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface Unita extends Serializable {

    String getNome();
    List<String> getSottoUnita();
    void setPadre(String u);
    String getPadre();
    void addRuolo(Ruolo r);
    void removeRuolo(Ruolo r);
    HashMap<String, Ruolo> getRuoli();
    void addDipendente(Dipendente d,Ruolo r);
    void removeDipendente(Dipendente d, Ruolo r) ;
    HashMap<String,Dipendente> getDipendenti(Ruolo r);
    void copiaStato(Unita u);
    void addSottoUnita(String u);
    Ruolo getRuolo(String r);
    UnitaComposite deepCopy();
}
