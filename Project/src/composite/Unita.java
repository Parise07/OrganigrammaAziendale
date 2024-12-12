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
    void addRuolo(Ruolo r);
    void removeRuolo(Ruolo r);
    HashMap<String, Ruolo> getRuoli();
    void addDipendente(Dipendente d,Ruolo r);
    void removeDipendente(Dipendente d, Ruolo r) ;
    HashMap<String,Dipendente> getDipendenti(Ruolo r);
}
