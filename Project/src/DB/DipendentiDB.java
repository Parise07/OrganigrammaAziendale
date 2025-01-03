package DB;

import memento.Originator;
import observer.Observer;
import utils.Dipendente;

import java.util.HashMap;
import java.util.Map;

public class DipendentiDB implements DB<Dipendente>{
    private static final long serialVersionUID = 1L;
    private static DipendentiDB dipendentiDB;
    private HashMap<String,Dipendente>dipendenti=new HashMap<>();
    private Observer osservatori;
    private Originator file;
    private DipendentiDB(){}

    public static DipendentiDB getInstance(){ //pattern Singleton
        if(dipendentiDB==null){
            dipendentiDB=new DipendentiDB();
        }
        return dipendentiDB;
    }



    @Override
    public void add(Dipendente dipendente) {
        dipendenti.put(dipendente.getEmail(),dipendente);
        notifica();
    }

    @Override
    public Dipendente get(String nome) {// deve essere un email
        return dipendenti.get(nome);
    }

    @Override
    public void salva() {
        HashMap<String, Dipendente> deepCopiedDipendenti = new HashMap<>();
        for (Map.Entry<String, Dipendente> entry : dipendenti.entrySet()) {
            deepCopiedDipendenti.put(entry.getKey(), entry.getValue().deepCopy());
        }
        file.setDipendentiMap(deepCopiedDipendenti);
    }

    @Override
    public void carica() {
        HashMap<String, Dipendente> loadedDipendenti = file.getDipendentiMap();
        dipendenti = new HashMap<>();
        for (Map.Entry<String, Dipendente> entry : loadedDipendenti.entrySet()) {
            dipendenti.put(entry.getKey(), entry.getValue().deepCopy());
        }
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
        osservatori=o;
    }

    @Override
    public void detach(Observer o) {
        osservatori=null;
    }

    @Override
    public void notifica() {
            osservatori.aggiorna();
    }

    public void setFile(Originator o){
        this.file=o;
    }
}
