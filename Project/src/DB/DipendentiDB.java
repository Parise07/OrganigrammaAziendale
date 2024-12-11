package DB;

import observer.Observer;
import utils.Dipendente;

import java.util.HashMap;
import java.util.LinkedList;

public class DipendentiDB implements DB<Dipendente>{
    private static DipendentiDB dipendentiDB;
    private HashMap<String,Dipendente>dipendenti=new HashMap<>();
    private LinkedList<Observer> osservatori = new LinkedList<>();
    private File file;
    private DipendentiDB(){}

    public static DipendentiDB getInstance(){ //pattern Singleton
        if(dipendentiDB==null){
            dipendentiDB=new DipendentiDB();
        }
        return dipendentiDB;
    }


    @Override
    public void add(Dipendente dipendente) {
        if(dipendenti.containsKey(dipendente.getEmail())){
            throw new IllegalArgumentException("Dipendente già presente nel DB");
        }
        dipendenti.put(dipendente.getEmail(),dipendente);
        notifica();
    }

    @Override
    public void modifica(Dipendente dipendente) {
        if(!dipendenti.containsKey(dipendente.getEmail())){
            throw new IllegalArgumentException("Dipendente non presente nel DB. IMPOSSIBILE modificare");
        }
        dipendenti.put(dipendente.getEmail(),dipendente);
        notifica();
    }

    @Override
    public Dipendente get(String nome) {// deve essere un email
        return dipendenti.get(nome);
    }

    @Override
    public void salva() {
        file.setDipendneti(dipendenti,osservatori);
    }

    @Override
    public void carica() {
        this.osservatori=file.getObserverDipendenti();
        this.dipendenti=file.getMapDipendenti();
    }

    @Override
    public void remove(Dipendente dipendente) {
        if(!dipendenti.containsKey(dipendente.getEmail())){
            throw new IllegalArgumentException("Dipendente non presente nel DB");
        }
        dipendenti.remove(dipendente.getEmail());
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
