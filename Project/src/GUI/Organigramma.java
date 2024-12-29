package GUI;

import DB.DipendentiDB;
import DB.RuoloDB;
import DB.UnitaDB;
import composite.Unita;
import memento.Originator;
import observer.ObserverConcreto;
import utils.Dipendente;
import utils.Ruolo;
import java.io.*;


public class Organigramma implements Serializable {
    private ObserverConcreto observer;
    private  UnitaDB unita=UnitaDB.getInstance();
    private RuoloDB ruoli=RuoloDB.getInstance();
    private DipendentiDB dipendenti=DipendentiDB.getInstance();
    private Originator file= new Originator();
    private String filePath;



    public Organigramma(String filePath){
        this.filePath = filePath;
        caricaOrganigramma();
    }

    public void setFilePath(String file){
        if(filePath==null)
            this.filePath=file;
    }

    public Organigramma(Unita u) {//costruttore per carica
        observer= new ObserverConcreto(this,file);
        unita.setFile(file);
        ruoli.setFile(file);
        dipendenti.setFile(file);
        registraObserver();
        unita.setRadice(u);
    }

    public Unita getRadice(){
        return unita.getRadice();
    }


    public void aggiungiDipendente(Dipendente d,Unita u, Ruolo r){
        u.addDipendente(d,r);
        dipendenti.add(d);
    }
    public void aggiungiRuolo(Ruolo r,Unita u){
        u.addRuolo(r);
        ruoli.add(r);
    }
    public void aggiungiUnita(Unita u,String padre){
        unita.add(u,padre);
    }
    public String[] getNomiUnita(){
        return unita.getAll();
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
        registraObserver();
        ObjectOutputStream oo;
        try{
            oo=new ObjectOutputStream(new FileOutputStream(filePath));
            System.out.println("Salvataggio: " + file);
            oo.writeObject(file);
            System.out.println("Organigramma salvato correttamente.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void caricaOrganigramma() {
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(filePath))) {
            file.setStato((Originator) oi.readObject());
            observer= new ObserverConcreto(this,file);
            registraObserver();
            System.out.println("Caricamento: " + file);
            unita.setFile(file);
            ruoli.setFile(file);
            dipendenti.setFile(file);
            unita.carica();
            ruoli.carica();
            dipendenti.carica();

            System.out.println("Organigramma caricato correttamente.");
        } catch (FileNotFoundException e) {
            System.err.println("File non trovato: " + filePath);
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Classe non trovata durante la deserializzazione: " + e.getMessage());
        }
    }

    private void registraObserver() {
        unita.attach(observer);
        dipendenti.attach(observer);
        ruoli.attach(observer);
    }

    public UnitaDB getUnitaDb(){
        return unita;
    }
    public Unita getUnita(String nome){
        return unita.get(nome);
    }

    public void redo(){
        file.redo();
        unita.carica();
        ruoli.carica();
        dipendenti.carica();
    }
    public void undo(){
        file.undo();
        unita.carica();
        ruoli.carica();
        dipendenti.carica();
    }
    public void modificaU(Unita u, String nuova){
        unita.modifica(u,nuova);
    }
    public void modificaD(Dipendente vecchio, Dipendente nuovo, Ruolo r, Unita u){
            if(!vecchio.getEmail().equals(nuovo.getEmail())){
                this.removeDipendente(u,r,vecchio);
                this.aggiungiDipendente(nuovo,u,r);
            }else{
                this.aggiungiDipendente(nuovo,u,r);
            }
    }
    public void modificaR(Ruolo vecchio, Ruolo nuovo,Unita u){
        if(!vecchio.getNome().equals(nuovo.getNome())){
            this.removeRuolo(u,vecchio);
            this.aggiungiRuolo(nuovo,u);
            nuovo.setState(vecchio);
        }
    }
}
