package DB;

import memento.File;
import observer.Observer;
import utils.Ruolo;

import java.util.HashMap;
import java.util.LinkedList;

public class RuoloDB implements DB<Ruolo>{
    private static RuoloDB ruoloDB;
    private HashMap<String,Ruolo>ruoli= new HashMap<>();
    private LinkedList<Observer> osservatori= new LinkedList<>();
    private File file;
    private RuoloDB(){}

    public static RuoloDB getInstance(){ //pattern Singleton
        if(ruoloDB==null){
            ruoloDB=new RuoloDB();
        }
        return ruoloDB;
    }

    @Override
    public void add(Ruolo ruolo) {
        ruoli.put(ruolo.getNome(),ruolo);
        notifica();
    }


    @Override
    public Ruolo get(String nome) {
        return ruoli.get(nome);
    }


    @Override
    public void salva() {
        file.setRuolo(ruoli,osservatori);
    }

    @Override
    public void carica() {
        this.osservatori=file.getObserverDipendenti();
        this.ruoli=file.getMapRuoli();
    }

    @Override
    public void remove(Ruolo ruolo) {
        if(!ruoli.containsKey(ruolo.getNome())){
            throw new IllegalArgumentException("Ruolo non presente nel DB");
        }
        ruoli.remove(ruolo.getNome());
        notifica();
    }

    @Override
    public void attach(Observer o) {
        osservatori.add(o);
    }

    @Override
    public void detach(Observer o) {
        osservatori.remove(o);
    }

    @Override
    public void notifica() {
        for(Observer o:osservatori){
            o.aggiorna();
        }
    }
}
