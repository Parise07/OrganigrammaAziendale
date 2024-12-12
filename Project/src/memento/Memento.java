package memento;

public class Memento {
    private final File file;
    public Memento(File file) {
        this.file = file;
    }
    public File getFile() {
        return file;
    }
}
