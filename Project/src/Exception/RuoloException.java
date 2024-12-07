package Exception;

import utils.Dipendente;

public class RuoloException {
    public static class RuoloAssegnatoException extends Exception{
        public RuoloAssegnatoException(Dipendente d){
            super("Ruolo già assegnato al dipendente "+d.toString());
        }
    }
    public static class RuoloNonAssegnatoException extends Exception{
        public RuoloNonAssegnatoException(Dipendente d){
            super("Ruolo non assegnato al dipendente al dipendente "+d.toString());
        }
    }
}
