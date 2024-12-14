package GUI;

import composite.Unita;
import composite.UnitaComposite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OrgangrammaGui extends JFrame {
    /*pagina se voglio caricare o creare un organigramma
    permettere di scegliere il file o la directory di dove lo voglio creare
     per il file tenere in consderazione che deve avere il nome del file sia per
     la creazione che per il caricamento
     qui mi richiamo i metodi di organigramma carica o crea
     memorizzarmi la variabile path file che mi servirà poi nel salvataggio
     dell'organigramma
     */
    public OrgangrammaGui() {
        setTitle("Organigramma");
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton caricaButton= new JButton("Carica");
        JButton creaButton= new JButton("Nuovo");

        JPanel panel= new JPanel();
        panel.add(caricaButton);
        panel.add(creaButton);
        add(panel);

        caricaButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser sceltaFile= new JFileChooser();
                sceltaFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
                sceltaFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("File Organigramma (*.bin)", "bin"));
                int result = sceltaFile.showOpenDialog(OrgangrammaGui.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File fileSelezionato = sceltaFile.getSelectedFile();
                    JOptionPane.showMessageDialog(OrgangrammaGui.this,
                            "File selezionato: " + fileSelezionato.getAbsolutePath());
                    Organigramma o=new Organigramma(fileSelezionato.getAbsolutePath());
                    OrganigrammaPage op=new OrganigrammaPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(OrgangrammaGui.this, "Nessun file selezionato.");
                }
            }
        });

        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apriPopupInserimento();
            }
        });
    }

    private void apriPopupInserimento() {
        // Creazione di un JDialog per l'inserimento
        JDialog dialog = new JDialog(this, "Inserisci Nome Radice", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Layout e componenti del dialog
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel label = new JLabel("Inserisci nome radice:");
        JTextField textField = new JTextField();
        JButton inviaButton = new JButton("Invia");

        panel.add(label);
        panel.add(textField);
        panel.add(inviaButton);

        dialog.add(panel);

        // Listener per il bottone "Invia"
        inviaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String radice = textField.getText();
                if (!radice.isEmpty()) {
                    // Creazione dell'unità e apertura della nuova finestra
                    Unita u = new UnitaComposite(radice, null);
                    new OrganigrammaPage(u);
                    dialog.dispose(); // Chiudi il pop-up
                    dispose(); // Chiudi la finestra principale
                } else {
                    // Mostra un messaggio di errore senza chiudere il dialog
                    JOptionPane.showMessageDialog(dialog, "Il nome radice non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrgangrammaGui frame = new OrgangrammaGui();
            frame.setVisible(true);
        });
    }
}
