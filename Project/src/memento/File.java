package memento;

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
    private LinkedList<Observer>observerUnita;
    private HashMap<String, Ruolo>mapRuoli;
    private LinkedList<Observer>observerRuoli;
    private HashMap<String, Dipendente>mapDipendenti;
    private LinkedList<Observer>observerDipendenti;
    private Unita radice;

    public File(File file){
        mapUnita = file.getMapUnita();
        mapRuoli = file.getMapRuoli();
        mapDipendenti = file.getMapDipendenti();
        observerUnita = file.getObserverUnita();
        observerRuoli = file.getObserverRuoli();
        observerDipendenti = file.getObserverDipendenti();
        radice=file.getRadice();
    }


    public HashMap<String, Unita> getMapUnita() {
        return mapUnita;
    }
    public void setUnita(HashMap<String, Unita> mapUnita,LinkedList<Observer>ObserverUnita,Unita radice) {
        this.mapUnita = mapUnita;
        this.observerUnita = ObserverUnita;
        this.radice=radice;
    }

    public void setRuolo(HashMap<String, Ruolo> mapRuoli,LinkedList<Observer>ObserverRuolo) {
        this.mapRuoli = mapRuoli;
        this.observerRuoli = ObserverRuolo;
    }

    public void setDipendneti(HashMap<String, Dipendente> mapDipendenti,LinkedList<Observer>ObserverDipendenti) {
        this.mapDipendenti = mapDipendenti;
        this.observerDipendenti = ObserverDipendenti;
    }

    public LinkedList<Observer> getObserverUnita() {
        return observerUnita;
    }
    public LinkedList<Observer> getObserverRuoli() {
        return observerRuoli;
    }
    public LinkedList<Observer> getObserverDipendenti() {
        return observerDipendenti;
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
