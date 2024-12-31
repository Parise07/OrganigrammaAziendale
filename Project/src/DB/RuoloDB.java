package DB;

import memento.Originator;
import observer.Observer;
import utils.Ruolo;

import java.util.HashMap;
import java.util.Map;

public class RuoloDB implements DB<Ruolo>{
    private static final long serialVersionUID = 1L;
    private static RuoloDB ruoloDB;
    private HashMap<String,Ruolo>ruoli= new HashMap<>();
    private Observer osservatori;
    private Originator file;
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
        // Crea una copia profonda dei ruoli
        HashMap<String, Ruolo> deepCopiedRuoli = new HashMap<>();
        for (Map.Entry<String, Ruolo> entry : ruoli.entrySet()) {
            deepCopiedRuoli.put(entry.getKey(), entry.getValue().deepCopy());
        }

        // Salva la copia profonda nell'Originator
        file.setRuoloMap(deepCopiedRuoli);
    }

    @Override
    public void carica() {
        HashMap<String, Ruolo> loadedRuoli = file.getRuoloMap();
        this.ruoli = new HashMap<>();
        for (Map.Entry<String, Ruolo> entry : loadedRuoli.entrySet()) {
            this.ruoli.put(entry.getKey(), entry.getValue().deepCopy());
        }
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
