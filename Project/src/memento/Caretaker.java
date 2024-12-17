package memento;

import java.io.Serializable;
import java.util.Stack;

public class Caretaker implements Serializable {
    private final Stack<Memento> storico = new Stack<>();
    private final Stack<Memento> redoStack = new Stack<>();

    // Salva uno stato nel Caretaker
    public void salva(Memento memento) {
        storico.push(memento);// Puliamo lo stack di redo ogni volta che salviamo un nuovo stato
        redoStack.clear();
        System.out.println("Stato salvato.");
    }

    // Ripristina l'ultimo stato (Undo)
    public Memento ripristina() {
        if (!storico.isEmpty()) {
            Memento ultimo = storico.pop();
            redoStack.push(ultimo); // Spostiamo nello stack di redo
            System.out.println("Ripristino dello stato...");
            return ultimo;
        }
        System.out.println("Nessuno stato da ripristinare.");
        return null;
    }

    // Ripristina lo stato successivo (Redo)
    public Memento redo() {
        if (!redoStack.isEmpty()) {
            Memento redoMemento = redoStack.pop();
            storico.push(redoMemento); // Spostiamo nello stack di undo
            System.out.println("Ripristino redo dello stato...");
            return redoMemento;
        }
        System.out.println("Nessuno stato da rifare.");
        return null;
    }

    // Controlla se ci sono stati da ripristinare
    public boolean hasUndo() {
        return !storico.isEmpty();
    }

    // Controlla se ci sono stati da rifare
    public boolean hasRedo() {
        return !redoStack.isEmpty();
    }
}
