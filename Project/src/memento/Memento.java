package memento;

import composite.Unita;
import utils.Dipendente;
import utils.Ruolo;

import java.io.Serializable;
import java.util.HashMap;

public class Memento implements Serializable {
    private HashMap<String, Unita> unitaMap;
    private HashMap<String, Ruolo>ruoloMap;
    private HashMap<String, Dipendente>dipendentiMap;
    private Unita radice;
    private Caretaker c;

    public Memento(Originator o, Caretaker c){
        this.unitaMap=o.getUnitaMap();
        this.radice=o.getRadice();
        this.ruoloMap=o.getRuoloMap();
        this.dipendentiMap=o.getDipendentiMap();
        this.c=c;
        c.salva(this);
    }

    public HashMap<String, Unita> getUnitaMap() {
        return unitaMap;
    }

    public void setUnitaMap(HashMap<String, Unita> unitaMap) {
        this.unitaMap = unitaMap;
    }

    public HashMap<String, Dipendente> getDipendentiMap() {
        return dipendentiMap;
    }

    public void setDipendentiMap(HashMap<String, Dipendente> dipendentiMap) {
        this.dipendentiMap = dipendentiMap;
    }

    public HashMap<String, Ruolo> getRuoloMap() {
        return ruoloMap;
    }

    public void setRuoloMap(HashMap<String, Ruolo> ruoloMap) {
        this.ruoloMap = ruoloMap;
    }

    public Unita getRadice() {
        return radice;
    }

    public void setRadice(Unita radice) {
        this.radice = radice;
    }
}
