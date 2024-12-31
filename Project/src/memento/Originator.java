package memento;

import composite.Unita;
import utils.Dipendente;
import utils.Ruolo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Originator implements Serializable {
    private HashMap<String, Unita> unitaMap = new HashMap<>();
    private HashMap<String, Ruolo> ruoloMap = new HashMap<>();
    private HashMap<String, Dipendente> dipendentiMap = new HashMap<>();
    private String radice;
    private final Caretaker caretaker = new Caretaker(); // Collegamento al Caretaker

    // Metodo per impostare lo stato
    public void setStato(Originator stato) {
        this.unitaMap = new HashMap<>(stato.getUnitaMap());
        this.ruoloMap = new HashMap<>(stato.getRuoloMap());
        this.dipendentiMap = new HashMap<>(stato.getDipendentiMap());
        this.radice = stato.getRadice();
        save(); // Salva lo stato nel Caretaker
    }

    // Getter e Setter
    public HashMap<String, Unita> getUnitaMap() {
        return unitaMap;
    }

    public void setUnitaMap(HashMap<String, Unita> unitaMap) {
        this.unitaMap = unitaMap;
    }

    public HashMap<String, Dipendente> getDipendentiMap() {
        return dipendentiMap;
    }

    public void setDipendentiMap(HashMap<String, Dipendente> dipendentiMap) {
        this.dipendentiMap = dipendentiMap;
    }

    public HashMap<String, Ruolo> getRuoloMap() {
        return ruoloMap;
    }

    public void setRuoloMap(HashMap<String, Ruolo> ruoloMap) {
        this.ruoloMap = ruoloMap;
    }

    public String getRadice() {
        return radice;
    }

    public void setRadice(String radice) {
        this.radice = radice;
    }

    // Salva lo stato attuale nel Caretaker
    public void save() {
        Memento memento = new OriginatorMemento(); // Crea un nuovo Memento
        caretaker.salva(memento); // Salva il Memento nel Caretaker
    }

    // Ripristina lo stato dal Caretaker (UNDO)
    public void undo() {
        Memento memento = caretaker.ripristina();
        if (memento != null) {
            restore(memento); // Ripristina lo stato dal Memento
        }
    }

    // Ripristina lo stato dal Caretaker (REDO)
    public void redo() {
        Memento memento = caretaker.redo();
        if (memento != null) {
            restore(memento); // Ripristina lo stato dal Memento
        }
    }

    // Metodo per ripristinare lo stato da un Memento
    public void restore(Memento memento) {
        if (!(memento instanceof OriginatorMemento originatorMemento)) {
            throw new IllegalArgumentException("Memento non valido.");
        }
        this.unitaMap = new HashMap<>(originatorMemento.unitaMap);
        this.ruoloMap = new HashMap<>(originatorMemento.ruoloMap);
        this.dipendentiMap = new HashMap<>(originatorMemento.dipendentiMap);
        this.radice = originatorMemento.radice;
    }

    // Classe interna per il Memento
    private class OriginatorMemento implements Serializable, Memento {
        private final HashMap<String, Unita> unitaMap;
        private final HashMap<String, Ruolo> ruoloMap;
        private final HashMap<String, Dipendente> dipendentiMap;
        private final String radice;

        // Salva lo stato dell'Originator
        private OriginatorMemento() {
            this.unitaMap = deepCopyUnitaMap(Originator.this.unitaMap);
            this.ruoloMap = deepCopyRuoloMap(Originator.this.ruoloMap);
            this.dipendentiMap = deepCopyDipendentiMap(Originator.this.dipendentiMap);
            this.radice = Originator.this.radice;
        }

        // Metodo per copiare profondamente la mappa di Unita
        private HashMap<String, Unita> deepCopyUnitaMap(HashMap<String, Unita> original) {
            HashMap<String, Unita> copy = new HashMap<>();
            for (Map.Entry<String, Unita> entry : original.entrySet()) {
                copy.put(entry.getKey(), entry.getValue().deepCopy()); // Assumi che Unita implementi clone()
            }
            return copy;
        }

        // Metodo per copiare profondamente la mappa di Ruolo
        private HashMap<String, Ruolo> deepCopyRuoloMap(HashMap<String, Ruolo> original) {
            HashMap<String, Ruolo> copy = new HashMap<>();
            for (Map.Entry<String, Ruolo> entry : original.entrySet()) {
                copy.put(entry.getKey(), entry.getValue().deepCopy()); // Assumi che Ruolo implementi clone()
            }
            return copy;
        }

        // Metodo per copiare profondamente la mappa di Dipendente
        private HashMap<String, Dipendente> deepCopyDipendentiMap(HashMap<String, Dipendente> original) {
            HashMap<String, Dipendente> copy = new HashMap<>();
            for (Map.Entry<String, Dipendente> entry : original.entrySet()) {
                copy.put(entry.getKey(), entry.getValue().deepCopy()); // Assumi che Dipendente implementi clone()
            }
            return copy;
        }
    }
}
