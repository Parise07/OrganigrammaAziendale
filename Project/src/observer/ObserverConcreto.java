package observer;

import DB.DipendentiDB;
import DB.RuoloDB;
import DB.UnitaDB;
import GUI.Organigramma;
import memento.Originator;

public class ObserverConcreto implements Observer{
    private UnitaDB unita=UnitaDB.getInstance();
    private RuoloDB ruoli=RuoloDB.getInstance();
    private DipendentiDB dipendenti=DipendentiDB.getInstance();
    private Originator file;
    private Organigramma o;

    public ObserverConcreto(Organigramma o,Originator file){
        this.file=file;
        this.o=o;
    }

    public void aggiorna(){
        unita.salva();
        ruoli.salva();
        dipendenti.salva();
        file.save();
        o.stampaOrganigramma();
    }
}
