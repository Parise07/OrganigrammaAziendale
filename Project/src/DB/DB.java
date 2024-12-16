package DB;

import observer.Observer;
import java.io.Serializable;


public interface DB<E> extends Serializable {
    void add(E e);
    E get(String nome);
    void salva();
    void carica();
    void remove(E e);
    void attach(Observer o);
    void detach(Observer o);
    void notifica();
}
