package Exception;

public class UnitaException {
    public static class RuoloEsistenteException extends Exception{
        public RuoloEsistenteException(){
            super("Ruolo gia esistente ");
        }
    }
    public static class RuoloInesistenteException extends Exception{
        public RuoloInesistenteException(){
            super("Ruolo non trovato");
        }
    }

    public static class SottoUnitaEsistenteException extends Exception{
        public SottoUnitaEsistenteException(){
            super("Unita già esistente ");
        }
    }
    public static class SottoUnitaInesistenteException extends Exception{
        public SottoUnitaInesistenteException(){
            super("Unita non esistente ");
        }
    }
}
