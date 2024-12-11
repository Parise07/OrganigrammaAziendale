package GUI;

import DB.DipendentiDB;
import DB.File;
import DB.RuoloDB;
import DB.UnitaDB;
import composite.Unita;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Organigramma {

    private UnitaDB unita;
    private RuoloDB ruoli;
    private DipendentiDB dipendenti;
    private File file;

    public Unita getRadice(){
        return unita.getRadice();
    }


    public void salvaOrganigramma(String pathFile){
        ObjectOutputStream oo;
        try{
            unita.salva();
            ruoli.salva();
            dipendenti.salva();
            oo=new ObjectOutputStream(new FileOutputStream(pathFile));
            oo.writeObject(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void caricaOrganigramma(String pathFile){
        ObjectInputStream oi;
        try{
            oi=new ObjectInputStream(new FileInputStream(pathFile));
            file= new File((File)oi.readObject());
            unita.carica();
            ruoli.carica();
            dipendenti.carica();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
