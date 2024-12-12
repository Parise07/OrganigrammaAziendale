package memento;

import java.util.Stack;

public class Caretaker {
    private final Stack<Memento>storico= new Stack<>();

    public void salva(Memento memento) {
        storico.push(memento);
        System.out.println("Stato salvato.");
    }

    public Memento ripristina() {
        if (!storico.isEmpty()) {
            System.out.println("Ripristino dello stato...");
            return storico.pop();
        }
        System.out.println("Nessuno stato da ripristinare.");
        return null;
    }
}
