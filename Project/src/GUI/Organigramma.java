package GUI;

import DB.DipendentiDB;
import memento.File;
import DB.RuoloDB;
import DB.UnitaDB;
import composite.Unita;
import utils.Dipendente;
import utils.Ruolo;
import java.io.*;


public class Organigramma implements Serializable {
    private  UnitaDB unita=UnitaDB.getInstance();
    private RuoloDB ruoli=RuoloDB.getInstance();
    private DipendentiDB dipendenti=DipendentiDB.getInstance();
    private File file;

    public Organigramma(Unita u) {
        unita.setRadice(u);
    }

    public Unita getRadice(){
        return unita.getRadice();
    }

    //TODO nei metodi aggiungi e remove c'è un problema di logica
    //TODO per la modifica modificare i metodi add dei singoli non lanciare eccezione
    // ma usare put come anche aggiornamento essendo che lo permette
    //TODO stampa dell'organigramma in maniera ricorsiva e modificare aggiungi e modifica mettendoli in una classe separata


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


    public void salvaOrganigramma(String pathFile){
        ObjectOutputStream oo;
        try{
            unita.salva();
            ruoli.salva();
            dipendenti.salva();
            oo=new ObjectOutputStream(new FileOutputStream(pathFile));
            oo.writeObject(file);
            oo.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void caricaOrganigramma(String pathFile){
        ObjectInputStream oi;
        try{
            oi=new ObjectInputStream(new FileInputStream(pathFile));
            file= new File((File)oi.readObject());
            oi.close();
            unita.carica();
            ruoli.carica();
            dipendenti.carica();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
