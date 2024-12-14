package GUI;

import DB.DipendentiDB;
import memento.Caretaker;
import memento.File;
import DB.RuoloDB;
import DB.UnitaDB;
import composite.Unita;
import memento.Memento;
import utils.Dipendente;
import utils.Ruolo;
import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Organigramma implements Serializable {
    private  UnitaDB unita=UnitaDB.getInstance();
    private RuoloDB ruoli=RuoloDB.getInstance();
    private DipendentiDB dipendenti=DipendentiDB.getInstance();
    private File file;
    private Caretaker c;
    private String filePath;

    public Organigramma(String filePath){
            this.filePath=filePath;
            caricaOrganigramma();
    }

    public Organigramma(Unita u) {
        unita.setRadice(u);
    }

    public Unita getRadice(){
        return unita.getRadice();
    }

    //TODO stampa dell'organigramma in maniera ricorsiva e modificare aggiungi e modifica mettendoli in una classe separata
    public void stampaOrganigramma(){
        stampaOrganigrammaRicorsivo(this.getRadice(),0);
    }

    private void stampaOrganigrammaRicorsivo(Unita unita, int livello) {
        List<Unita> sottounita=  unita.getSottoUnita();
        if(unita.getSottoUnita()==null){
            return;
        }
        for(Unita u: sottounita)
            stampaOrganigrammaRicorsivo(u,livello);
    }
    /*
    livello non mi serve averlo poichè la stampa dei sottolivell di ogni
    sottunità avviene in contemporanea e stampa organigramma me la rixhiamo nella
    gui ogni volta che faccio la chiamata stamp un unità e la grafico tramite
    grafica unità
     */

    public void aggiungiDipendente(Dipendente d,Unita u, Ruolo r){
        u.addDipendente(d,r);
        dipendenti.add(d);
    }
    public void aggiungiRuolo(Ruolo r,Unita u){
        u.addRuolo(r);
        ruoli.add(r);
    }
    public void aggiungiUnita(Unita u){
        unita.add(u);
    }
    public void removeDipendente(Unita u, Ruolo r, Dipendente d){
        u.removeDipendente(d,r);
        dipendenti.remove(d);
    }

    public void removeRuolo(Unita u, Ruolo r){
        u.removeRuolo(r);
        ruoli.remove(r);
    }
    public void removeUnita(Unita u){
        unita.remove(u);
    }


    public void salvaOrganigramma(){
        ObjectOutputStream oo;
        try{
            oo=new ObjectOutputStream(new FileOutputStream(filePath));
            oo.writeObject(c.ripristina());
            oo.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void caricaOrganigramma(){
        ObjectInputStream oi;
        try{
            oi=new ObjectInputStream(new FileInputStream(filePath));
            file= new File((File)oi.readObject());
            oi.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
