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
    private String radice;

    public Memento(Originator o){
        this.unitaMap = new HashMap<>(o.getUnitaMap()); // Deep copy se necessario
        this.radice = o.getRadice(); // Copia profonda o serializzazione se complesso
        this.ruoloMap = new HashMap<>(o.getRuoloMap());
        this.dipendentiMap = new HashMap<>(o.getDipendentiMap());

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

    public String getRadice() {
        return radice;
    }

    public void setRadice(String radice) {
        this.radice = radice;
    }
}
