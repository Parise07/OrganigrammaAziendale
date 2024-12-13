package memento;

import DB.DipendentiDB;
import DB.RuoloDB;
import DB.UnitaDB;
import composite.Unita;
import observer.Observer;
import utils.Dipendente;
import utils.Ruolo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class File implements Serializable {
    private static final long serialVersionUID = 1L;
    private UnitaDB unita;
    private RuoloDB ruoli;
    private DipendentiDB dipendenti;
    private HashMap<String, Unita> mapUnita;
    private LinkedList<Observer> observerUnita;
    private HashMap<String, Ruolo> mapRuoli;
    private LinkedList<Observer> observerRuoli;
    private HashMap<String, Dipendente> mapDipendenti;
    private LinkedList<Observer> observerDipendenti;
    private Unita radice;

    // Costruttore di copia
    public File(File file) {
        this.mapUnita = new HashMap<>(file.getMapUnita());
        this.observerUnita = new LinkedList<>(file.getObserverUnita());
        this.mapRuoli = new HashMap<>(file.getMapRuoli());
        this.observerRuoli = new LinkedList<>(file.getObserverRuoli());
        this.mapDipendenti = new HashMap<>(file.getMapDipendenti());
        this.observerDipendenti = new LinkedList<>(file.getObserverDipendenti());
        this.radice = file.getRadice();
        this.saveToMemento();// Assumendo che sia immutabile o clonabile
    }

    // Getter e Setter
    public HashMap<String, Unita> getMapUnita() {
        return mapUnita;
    }

    public void setUnita(HashMap<String, Unita> mapUnita, LinkedList<Observer> observerUnita, Unita radice) {
        this.mapUnita = mapUnita;
        this.observerUnita = observerUnita;
        this.radice = radice;
    }

    public void setRuolo(HashMap<String, Ruolo> mapRuoli, LinkedList<Observer> observerRuoli) {
        this.mapRuoli = mapRuoli;
        this.observerRuoli = observerRuoli;
    }

    public void setDipendenti(HashMap<String, Dipendente> mapDipendenti, LinkedList<Observer> observerDipendenti) {
        this.mapDipendenti = mapDipendenti;
        this.observerDipendenti = observerDipendenti;
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

    // Salva lo stato in un Memento
    public Memento saveToMemento() {
        return new Memento(this);
    }

    // Ripristina lo stato da un Memento
    public void restoreStateFromMemento(Memento memento) {//questa inserire nella undo
        this.mapUnita = new HashMap<>(memento.getMapUnita());
        this.observerUnita = new LinkedList<>(memento.getObserverUnita());
        this.mapRuoli = new HashMap<>(memento.getMapRuoli());
        this.observerRuoli = new LinkedList<>(memento.getObserverRuoli());
        this.mapDipendenti = new HashMap<>(memento.getMapDipendenti());
        this.observerDipendenti = new LinkedList<>(memento.getObserverDipendenti());
        this.radice = memento.getRadice();
        unita.carica();
        dipendenti.carica();
        ruoli.carica();
    }
}
