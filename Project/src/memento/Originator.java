package memento;

import composite.Unita;
import utils.Dipendente;
import utils.Ruolo;

import java.io.Serializable;
import java.util.HashMap;

public class Originator implements Serializable {
    private HashMap<String, Unita> unitaMap;
    private HashMap<String, Ruolo>ruoloMap;
    private HashMap<String, Dipendente>dipendentiMap;
    private Unita radice;
    private Caretaker c=new Caretaker();


    public void setStato(Originator o){
        this.unitaMap=o.getUnitaMap();
        this.radice=o.getRadice();
        this.ruoloMap=o.getRuoloMap();
        this.dipendentiMap=o.getDipendentiMap();
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
    public void save(){
        new Memento(this,c);
    }
    public void restore(){
        Memento m=c.ripristina();
        this.unitaMap=m.getUnitaMap();
        this.radice=m.getRadice();
        this.ruoloMap=m.getRuoloMap();
        this.dipendentiMap=m.getDipendentiMap();
    }
    public void redo(){
        Memento m=c.redo();
        this.unitaMap=m.getUnitaMap();
        this.radice=m.getRadice();
        this.ruoloMap=m.getRuoloMap();
        this.dipendentiMap=m.getDipendentiMap();
    }
}
