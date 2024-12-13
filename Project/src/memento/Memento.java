package memento;

import composite.Unita;
import observer.Observer;
import utils.Dipendente;
import utils.Ruolo;

import java.util.HashMap;
import java.util.LinkedList;

public class Memento {
    private final HashMap<String, Unita> mapUnita;
    private final LinkedList<Observer> observerUnita;
    private final HashMap<String, Ruolo> mapRuoli;
    private final LinkedList<Observer> observerRuoli;
    private final HashMap<String, Dipendente> mapDipendenti;
    private final LinkedList<Observer> observerDipendenti;
    private final Unita radice;
    private Caretaker c= new Caretaker();

    public Memento(File file) {
        this.mapUnita = new HashMap<>(file.getMapUnita());
        this.observerUnita = new LinkedList<>(file.getObserverUnita());
        this.mapRuoli = new HashMap<>(file.getMapRuoli());
        this.observerRuoli = new LinkedList<>(file.getObserverRuoli());
        this.mapDipendenti = new HashMap<>(file.getMapDipendenti());
        this.observerDipendenti = new LinkedList<>(file.getObserverDipendenti());
        this.radice = file.getRadice();
        c.salva(this);
    }

    public HashMap<String, Unita> getMapUnita() {
        return mapUnita;
    }

    public LinkedList<Observer> getObserverUnita() {
        return observerUnita;
    }

    public HashMap<String, Ruolo> getMapRuoli() {
        return mapRuoli;
    }

    public LinkedList<Observer> getObserverRuoli() {
        return observerRuoli;
    }

    public HashMap<String, Dipendente> getMapDipendenti() {
        return mapDipendenti;
    }

    public LinkedList<Observer> getObserverDipendenti() {
        return observerDipendenti;
    }

    public Unita getRadice() {
        return radice;
    }
}

