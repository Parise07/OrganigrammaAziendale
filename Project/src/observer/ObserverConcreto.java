package observer;

import DB.DipendentiDB;
import DB.RuoloDB;
import DB.UnitaDB;
import GUI.Organigramma;
import memento.File;

public class ObserverConcreto implements Observer{
    private UnitaDB unita;
    private RuoloDB ruoli;
    private DipendentiDB dipendenti;
    private File file;
    private Organigramma o;
    public void aggiorna(){
        unita.salva();
        ruoli.salva();
        dipendenti.salva();
        file.saveToMemento();
        o.stampaOrganigramma();
    }
}
