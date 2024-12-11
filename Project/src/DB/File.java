package DB;

import composite.Unita;
import observer.Observer;
import utils.Dipendente;
import utils.Ruolo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class File implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap<String, Unita>mapUnita;
    private LinkedList<Observer>ObserverUnita;
    private HashMap<String, Ruolo>mapRuoli;
    private LinkedList<Observer>ObserverRuoli;
    private HashMap<String, Dipendente>mapDipendenti;
    private LinkedList<Observer>ObserverDipendenti;
    private Unita radice;

    public File(File file){
        mapUnita = file.getMapUnita();
        mapRuoli = file.getMapRuoli();
        mapDipendenti = file.getMapDipendenti();
        ObserverUnita = file.getObserverUnita();
        ObserverRuoli = file.getObserverRuoli();
        ObserverDipendenti = file.getObserverDipendenti();
        radice=file.getRadice();
    }


    public HashMap<String, Unita> getMapUnita() {
        return mapUnita;
    }
    public void setUnita(HashMap<String, Unita> mapUnita,LinkedList<Observer>ObserverUnita,Unita radice) {
        this.mapUnita = mapUnita;
        this.ObserverUnita = ObserverUnita;
        this.radice=radice;
    }

    public void setRuolo(HashMap<String, Ruolo> mapRuoli,LinkedList<Observer>ObserverRuolo) {
        this.mapRuoli = mapRuoli;
        this.ObserverRuoli = ObserverRuolo;
    }

    public void setDipendneti(HashMap<String, Dipendente> mapDipendenti,LinkedList<Observer>ObserverDipendenti) {
        this.mapDipendenti = mapDipendenti;
        this.ObserverDipendenti = ObserverDipendenti;
    }

    public LinkedList<Observer> getObserverUnita() {
        return ObserverUnita;
    }
    public LinkedList<Observer> getObserverRuoli() {
        return ObserverRuoli;
    }
    public LinkedList<Observer> getObserverDipendenti() {
        return ObserverDipendenti;
    }
    public HashMap<String, Ruolo> getMapRuoli() {
        return mapRuoli;
    }
    public HashMap<String, Dipendente> getMapDipendenti() {
        return mapDipendenti;
    }
    public Unita getRadice() {
        return radice;
    }
}
