package DB;

import composite.Unita;
import composite.UnitaComposite;
import memento.Originator;
import observer.Observer;

import java.util.HashMap;


public class UnitaDB implements DB<Unita>{
    private static final long serialVersionUID = 1L;
    private static UnitaDB unitaDB;
    private HashMap<String,Unita> unita= new HashMap<>();
    private Observer osservatori;
    private Unita radice=null;
    private Originator file;

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
        this.add(u);
    }

    public void setFile(Originator o){
        this.file=o;
    }

    public void add(Unita u,String padre) {
        unita.get(padre).addSottoUnita(u);
        unita.put(u.getNome(),u);
        notifica();
    }


    @Override
    public void add(Unita u) {
        unita.put(u.getNome(),u);
        notifica();
    }

    public String[] getAll(){
        return unita.keySet().toArray(new String[0]);
    }

    @Override
    public Unita get(String nome) {
        return unita.get(nome);
    }

    @Override
    public void salva() {
        file.setUnitaMap(unita);
        file.setRadice(radice);
    }

    @Override
    public void carica() {
        this.unita=file.getUnitaMap();
        this.radice=file.getRadice();
    }
    @Override
    public void remove(Unita u) {
        if(!unita.containsKey(u.getNome())){
            throw new IllegalArgumentException("Unita non presente nel DB");
        }
        Unita padre= unita.get(u.getPadre());
        if(!u.getSottoUnita().isEmpty()){
            UnitaComposite padre1=(UnitaComposite)padre;
            for(Unita i: u.getSottoUnita()){
                padre1.addSottoUnita(i);
            }
        }
        padre.getSottoUnita().remove(u);
        unita.remove(u.getNome());
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
}
