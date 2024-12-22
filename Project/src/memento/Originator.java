package memento;

import composite.Unita;
import utils.Dipendente;
import utils.Ruolo;

import java.io.Serializable;
import java.util.HashMap;

public class Originator implements Serializable {
    private HashMap<String, Unita> unitaMap=new HashMap<>();
    private HashMap<String, Ruolo>ruoloMap=new HashMap<>();
    private HashMap<String, Dipendente>dipendentiMap=new HashMap<>();
    private String radice;
    private final Caretaker c=new Caretaker();


    public void setStato(Originator o){
        this.unitaMap=o.getUnitaMap();
        this.radice=o.getRadice();
        this.ruoloMap=o.getRuoloMap();
        this.dipendentiMap=o.getDipendentiMap();
        save();
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
    public void save(){
       c.salva( new Memento(this));
    }
    public void restore(){
        Memento memento = c.ripristina();
        if (memento != null) {
            this.unitaMap =new HashMap<>( memento.getUnitaMap());
            this.radice = memento.getRadice();
            this.ruoloMap =new HashMap<>(memento.getRuoloMap()) ;
            this.dipendentiMap =new HashMap<>(memento.getDipendentiMap()) ;
        }
    }
    public void redo(){
            Memento memento = c.redo();
            if (memento != null) {
                this.unitaMap = memento.getUnitaMap();
                this.radice = memento.getRadice();
                this.ruoloMap = memento.getRuoloMap();
                this.dipendentiMap = memento.getDipendentiMap();
            }
    }
}
