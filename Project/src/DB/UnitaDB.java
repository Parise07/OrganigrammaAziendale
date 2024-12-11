package DB;

import composite.Unita;
import observer.Observer;

import java.util.HashMap;
import java.util.LinkedList;



public class UnitaDB implements DB<Unita>{
    private static UnitaDB unitaDB;
    private HashMap<String,Unita> unita= new HashMap<>();
    private LinkedList<Observer>osservatori= new LinkedList<>();
    private Unita radice;
    private File file;

    private UnitaDB(){}

    public static UnitaDB getInstance(){
        if(unitaDB==null){
            unitaDB=new UnitaDB();
        }
        return unitaDB;
    }

    public Unita getRadice(){
        return radice;
    }

    public void setRadice(Unita u){
        if(radice==null)
            this.radice=u;
        throw new IllegalArgumentException("Radice già esistente");
    }


    @Override
    public void add(Unita u) {
        if(unita.containsKey(u.getNome())){
            throw new IllegalArgumentException("Unita già presente nel DB");
        }
        unita.put(u.getNome(),u);
        notifica();
    }

    @Override
    public void modifica(Unita u) {
        if(unita.containsKey(u.getNome())){
            throw new IllegalArgumentException("Unita già presente nel DB");
        }
        unita.put(u.getNome(),u);
        notifica();
    }

    @Override
    public Unita get(String nome) {
        return unita.get(nome);
    }

    @Override
    public void salva() {
        file.setUnita(unita,osservatori,radice);
    }

    @Override
    public void carica() {
        this.osservatori=file.getObserverDipendenti();
        this.unita=file.getMapUnita();
        this.radice=file.getRadice();
    }
    @Override
    public void remove(Unita u) {
        if(!unita.containsKey(u.getNome())){
            throw new IllegalArgumentException("Unita non presente nel DB");
        }
        unita.remove(u.getNome());
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
