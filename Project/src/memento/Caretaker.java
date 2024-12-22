package memento;

import java.io.Serializable;
import java.util.LinkedList;

public class Caretaker implements Serializable {
    private final LinkedList<Memento> storico = new LinkedList<>();
    private final LinkedList<Memento> redoList = new LinkedList<>();

    // Salva uno stato nel Caretaker
    public void salva(Memento memento) {
        storico.addFirst(memento); // Inserisce in fondo alla coda
        redoList.clear(); // Dopo un nuovo salvataggio, lo stack di redo viene svuotato
        System.out.println("Stato salvato.");
    }

    // Ripristina il primo stato salvato (Undo)
    public Memento ripristina() {
        if (!storico.isEmpty()) {
            Memento primo = storico.removeFirst(); // Rimuove il primo elemento salvato (FIFO)
            redoList.addFirst(primo); // Lo sposta nella coda di redo
            Memento secondo =storico.getFirst();
            redoList.addFirst(secondo);
            System.out.println("Ripristino dello stato...");
            return secondo;
        }
        System.out.println("Nessuno stato da ripristinare.");
        return null;
    }

    // Ripristina lo stato successivo (Redo)
    public Memento redo() {
        if (!redoList.isEmpty()) {
            Memento primo = redoList.removeFirst(); // Rimuove il primo elemento salvato (FIFO)
            storico.addFirst(primo); // Lo sposta nella coda di redo
            Memento secondo =redoList.getFirst();
            storico.addFirst(secondo);
            System.out.println("Ripristino dello stato...");
            return secondo;
        }
        System.out.println("Nessuno stato da ripristinare.");
        return null;
    }
}
