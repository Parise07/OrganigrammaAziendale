package GUI;

import composite.Unita;
import composite.UnitaComposite;
import utils.Dipendente;
import utils.Ruolo;

import javax.swing.*;
import java.awt.*;

import java.io.File;


public class OrganigrammaPage extends JFrame {
    private Organigramma o;


    public OrganigrammaPage(String pathFile){
       o=new Organigramma(pathFile);
       page();
    }

    public OrganigrammaPage(Unita radice){
        o=new Organigramma(radice);
        page();
    }

    public void page(){
        setTitle("Organigramma");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Imposta il layout principale
        setLayout(new BorderLayout());

        // Aggiungi i pannelli
        add(raffiguraOrganigramma(), BorderLayout.CENTER);
        add(bottoni(), BorderLayout.SOUTH);

        // Rendi visibile la finestra
        setVisible(true);
    }
    public JPanel  raffiguraOrganigramma(){
        JPanel organigrammaPanel= new JPanel();
        organigrammaPanel.setBackground(Color.LIGHT_GRAY);
        organigrammaPanel.setLayout(new BorderLayout());
        JLabel organigrammaLabel = new JLabel("Raffigurazione Organigramma", SwingConstants.CENTER);
        organigrammaPanel.add(organigrammaLabel, BorderLayout.CENTER);
        add(organigrammaPanel, BorderLayout.CENTER);
        return organigrammaPanel;

    }
    public JPanel  bottoni(){
        JPanel bottoniPanel = new JPanel(new BorderLayout()); // Layout principale

        // Pannello per i gruppi di tre pulsanti
        JPanel gruppiPanel = new JPanel(new GridLayout(3, 3, 10, 10)); // Griglia 3x3 con spaziatura
        JButton aggiungiU = new JButton("Aggiungi Unita");
        JButton removeU = new JButton("Rimuovi Unita");
        JButton aggiungiR = new JButton("Aggiungi Ruolo");
        JButton modificaR = new JButton("Modifica Ruolo");
        JButton removeR = new JButton("Remove Ruolo");
        JButton aggiungiD = new JButton("Aggiungi Dipendente");
        JButton modificaD = new JButton("Modifica Dipendente");
        JButton removeD = new JButton("Remove Dipendente");
        JButton modificaU = new JButton("Modifica Unita");

        // Aggiungi i pulsanti ai gruppi
        gruppiPanel.add(aggiungiU);
        gruppiPanel.add(aggiungiR);
        gruppiPanel.add(aggiungiD);
        gruppiPanel.add(modificaU);
        gruppiPanel.add(modificaR);
        gruppiPanel.add(modificaD);
        gruppiPanel.add(removeU);
        gruppiPanel.add(removeR);
        gruppiPanel.add(removeD);

        // Pannello per il pulsante "Salva"
        JPanel salvaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton salvaButton = new JButton("Salva");
        salvaPanel.add(salvaButton);

        // Aggiungi i due pannelli al pannello principale
        bottoniPanel.add(gruppiPanel, BorderLayout.CENTER); // Gruppi centrati
        bottoniPanel.add(salvaPanel, BorderLayout.SOUTH);   // Salva in basso a destra
        aggiungiU.addActionListener(e -> apriDialogAggiungiUnita());
        removeU.addActionListener(e -> apriDialogRemoveUnita());
        aggiungiR.addActionListener(e->apriDialogoAggiungiR());
        aggiungiD.addActionListener(e->apriDialogoAggiungiD());
        removeR.addActionListener(e->apriDialogoRemoveR());
        removeD.addActionListener(e->apriDialogoRemoveD());
        salvaButton.addActionListener(e->apriDialogoSalva());
        return bottoniPanel;
    }



    private void apriDialogAggiungiUnita() {
        JDialog aggiungiUnita = new JDialog(this, "Aggiungi Unita", true);
        aggiungiUnita.setSize(300, 200);
        aggiungiUnita.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // Spaziatura tra i componenti

        // Etichetta e menu a tendina
        JLabel labelTendina = new JLabel("Seleziona padre:");
        String[] opzioni = o.getNomiUnita();
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);

        JLabel label = new JLabel("Inserisci nome Unita:");
        JTextField textField = new JTextField();

        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");

        // Aggiungi i componenti al pannello
        panel.add(labelTendina);
        panel.add(menuTendina);
        panel.add(label);
        panel.add(textField);
        panel.add(new JLabel()); // Spaziatore per allineamento
        panel.add(confermaButton);

        // Azione sul bottone di conferma
        confermaButton.addActionListener(e -> {
            String selezione = (String) menuTendina.getSelectedItem();
            String nomeUnita = textField.getText();

            if (selezione != null && nomeUnita != null && !nomeUnita.isEmpty()) {
                Unita u = new UnitaComposite(nomeUnita, o.getUnitaDb().get(selezione));
                o.aggiungiUnita(u, selezione);
                JOptionPane.showMessageDialog(this, "Unità aggiunta: " + u.toString());
                aggiungiUnita.dispose(); // Chiudi il dialogo
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci un nome valido per l'unità.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        aggiungiUnita.add(panel);
        aggiungiUnita.setVisible(true);
    }

    private void apriDialogoAggiungiR() {
        JDialog aggiungiRuolo = new JDialog(this, "Aggiungi Ruolo", true);
        aggiungiRuolo.setSize(300, 200);
        aggiungiRuolo.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // Spaziatura tra i componenti

        // Etichetta e menu a tendina
        JLabel labelTendina = new JLabel("Seleziona padre:");
        String[] opzioni = o.getNomiUnita(); // Recupera i nomi delle unità
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);

        JLabel label = new JLabel("Inserisci nome Ruolo:");
        JTextField textField = new JTextField();

        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");

        // Aggiungi i componenti al pannello
        panel.add(labelTendina);
        panel.add(menuTendina);
        panel.add(label);
        panel.add(textField);
        panel.add(new JLabel()); // Spaziatore per allineamento
        panel.add(confermaButton);

        // Azione sul bottone di conferma
        confermaButton.addActionListener(e -> {
            String selezione = (String) menuTendina.getSelectedItem();
            String nomeRuolo = textField.getText();

            if (selezione != null && nomeRuolo != null && !nomeRuolo.isEmpty()) {
                Ruolo r = new Ruolo(nomeRuolo);
                o.aggiungiRuolo(r, o.getUnitaDb().get(selezione));
                JOptionPane.showMessageDialog(this, "Ruolo aggiunto: " + r.toString());
                aggiungiRuolo.dispose(); // Chiudi il dialogo
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci un nome valido per il ruolo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        aggiungiRuolo.add(panel);
        aggiungiRuolo.setVisible(true);
    }

    private void apriDialogoAggiungiD() {
        JDialog aggiungiDipendente = new JDialog(this, "Aggiungi Dipendente", true);
        aggiungiDipendente.setSize(600, 200);
        aggiungiDipendente.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // Spaziatura tra i componenti

        // Etichetta e menu a tendina per le unità
        JLabel labelTendina = new JLabel("Seleziona Unità:");
        String[] opzioni = o.getNomiUnita(); // Recupera i nomi delle unità
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);

        // Etichetta e menu a tendina per i ruoli
        JLabel labelTendinaR = new JLabel("Seleziona Ruolo:");
        JComboBox<String> menuTendinaR = new JComboBox<>();

        // Aggiorna i ruoli in base all'unità selezionata
        menuTendina.addActionListener(e -> {
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            if (selezioneUnita != null) {
                String[] ruoli = o.getUnita(selezioneUnita).getRuoli().keySet().toArray(new String[0]);
                menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoli));
            }
        });

        // Imposta i ruoli iniziali per l'unità predefinita
        if (opzioni.length > 0) {
            String unitaIniziale = opzioni[0];
            String[] ruoliIniziali = o.getUnita(unitaIniziale).getRuoli().keySet().toArray(new String[0]);
            menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoliIniziali));
        }

        // Altri campi di input
        JLabel label = new JLabel("Inserisci nome Dipendente:");
        JTextField textField = new JTextField();
        JLabel labelC = new JLabel("Inserisci cognome Dipendente:");
        JTextField textFieldC = new JTextField();
        JLabel labelE = new JLabel("Inserisci email Dipendente:");
        JTextField textFieldE = new JTextField();

        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");

        // Aggiungi i componenti al pannello
        panel.add(labelTendina);
        panel.add(menuTendina);
        panel.add(labelTendinaR);
        panel.add(menuTendinaR);
        panel.add(label);
        panel.add(textField);
        panel.add(labelC);
        panel.add(textFieldC);
        panel.add(labelE);
        panel.add(textFieldE);
        panel.add(new JLabel()); // Spaziatore per allineamento
        panel.add(confermaButton);

        // Azione sul bottone di conferma
        confermaButton.addActionListener(e -> {
            String selezione = (String) menuTendina.getSelectedItem();
            String selezioneR = (String) menuTendinaR.getSelectedItem();
            String nomeDipendente = textField.getText();
            String cognome = textFieldC.getText();
            String email = textFieldE.getText();
            if (selezione != null && selezioneR != null && nomeDipendente != null && !nomeDipendente.isEmpty()
                    && cognome != null && !cognome.isEmpty() && email != null && !email.isEmpty()) {
                Dipendente d = new Dipendente(nomeDipendente, cognome, email);
                o.aggiungiDipendente(d, o.getUnitaDb().get(selezione), o.getUnitaDb().get(selezione).getRuolo(selezioneR));
                JOptionPane.showMessageDialog(this, "Dipendente aggiunto: " + d.toString());
                aggiungiDipendente.dispose(); // Chiudi il dialogo
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci tutti i campi richiesti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        aggiungiDipendente.add(panel);
        aggiungiDipendente.setVisible(true);
    }

    private void apriDialogRemoveUnita() {
        JDialog rimuoviUnita = new JDialog(this, "Rimuovi Unita", true);
        rimuoviUnita.setSize(300, 150);
        rimuoviUnita.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Etichetta e menu a tendina
        JLabel labelTendina = new JLabel("Seleziona Unità da rimuovere:");
        String[] opzioni = o.getNomiUnita();
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);

        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");
        confermaButton.addActionListener(e -> {
            String selezione = (String) menuTendina.getSelectedItem();

            if (selezione != null) {
                o.removeUnita(o.getUnitaDb().get(selezione));
                JOptionPane.showMessageDialog(this, "Unità rimossa.");
                rimuoviUnita.dispose(); // Chiudi il dialogo
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un'unità valida.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(labelTendina, BorderLayout.NORTH);
        panel.add(menuTendina, BorderLayout.CENTER);
        panel.add(confermaButton, BorderLayout.SOUTH);

        rimuoviUnita.add(panel);
        rimuoviUnita.setVisible(true);
    }

    private void apriDialogoRemoveR() {
        JDialog rimuoviRuolo = new JDialog(this, "Rimuovi Ruolo", true);
        rimuoviRuolo.setSize(300, 200);
        rimuoviRuolo.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // Spaziatura tra i componenti

        // Etichetta e menu a tendina
        JLabel labelTendina = new JLabel("Seleziona Unita");
        String[] opzioni = o.getNomiUnita(); // Recupera i nomi delle unità
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);

        JLabel labelTendinaR = new JLabel("Seleziona Ruolo:");
        JComboBox<String> menuTendinaR = new JComboBox<>();

        // Aggiorna i ruoli in base all'unità selezionata
        menuTendina.addActionListener(e -> {
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            if (selezioneUnita != null) {
                String[] ruoli = o.getUnita(selezioneUnita).getRuoli().keySet().toArray(new String[0]);
                menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoli));
            }
        });

        // Imposta i ruoli iniziali per l'unità predefinita
        if (opzioni.length > 0) {
            String unitaIniziale = opzioni[0];
            String[] ruoliIniziali = o.getUnita(unitaIniziale).getRuoli().keySet().toArray(new String[0]);
            menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoliIniziali));
        }
        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");

        // Aggiungi i componenti al pannello
        panel.add(labelTendina);
        panel.add(menuTendina);
        panel.add(labelTendinaR);
        panel.add(menuTendinaR);
        panel.add(new JLabel()); // Spaziatore per allineamento
        panel.add(confermaButton);

        // Azione sul bottone di conferma
        confermaButton.addActionListener(e -> {
            String selezione = (String) menuTendina.getSelectedItem();
            String selezioneR = (String) menuTendinaR.getSelectedItem();;

            if (selezione != null && selezioneR != null) {
                o.removeRuolo(o.getUnitaDb().get(selezione),o.getUnitaDb().get(selezione).getRuolo(selezioneR));
                JOptionPane.showMessageDialog(this, "Ruolo rimosso ");
                rimuoviRuolo.dispose(); // Chiudi il dialogo
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci un nome valido per il ruolo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        rimuoviRuolo.add(panel);
        rimuoviRuolo.setVisible(true);
    }

    private void apriDialogoRemoveD() {
        JDialog rimuoviDipendente = new JDialog(this, "Rimuovi Dipendente", true);
        rimuoviDipendente.setSize(400, 250);
        rimuoviDipendente.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // Spaziatura tra i componenti

        // Etichetta e menu a tendina per le unità
        JLabel labelTendina = new JLabel("Seleziona Unità:");
        String[] opzioni = o.getNomiUnita(); // Recupera i nomi delle unità
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);

        // Etichetta e menu a tendina per i ruoli
        JLabel labelTendinaR = new JLabel("Seleziona Ruolo:");
        JComboBox<String> menuTendinaR = new JComboBox<>();

        // Etichetta e menu a tendina per i dipendenti
        JLabel labelTendinaD = new JLabel("Seleziona Dipendente (Email):");
        JComboBox<String> menuTendinaD = new JComboBox<>();

        // Aggiorna i ruoli in base all'unità selezionata
        menuTendina.addActionListener(e -> {
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            if (selezioneUnita != null) {
                String[] ruoli = o.getUnita(selezioneUnita).getRuoli().keySet().toArray(new String[0]);
                menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoli));
                menuTendinaD.setModel(new DefaultComboBoxModel<>()); // Reset dipendenti
            }
        });

        // Aggiorna i dipendenti in base al ruolo selezionato
        menuTendinaR.addActionListener(e -> {
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            String selezioneRuolo = (String) menuTendinaR.getSelectedItem();
            if (selezioneUnita != null && selezioneRuolo != null) {
                String[] dipendenti = o.getUnita(selezioneUnita)
                        .getDipendenti(o.getUnita(selezioneUnita).getRuolo(selezioneRuolo)).keySet().toArray(new String[0]);
                menuTendinaD.setModel(new DefaultComboBoxModel<>(dipendenti));
            }
        });

        // Imposta i ruoli iniziali per l'unità predefinita
        if (opzioni.length > 0) {
            String unitaIniziale = opzioni[0];
            String[] ruoliIniziali = o.getUnita(unitaIniziale).getRuoli().keySet().toArray(new String[0]);
            menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoliIniziali));
            if (ruoliIniziali.length > 0) {
                String ruoloIniziale = ruoliIniziali[0];
                String[] dipendentiIniziali = o.getUnita(unitaIniziale)
                        .getDipendenti(o.getUnita(unitaIniziale).getRuolo(ruoloIniziale)).keySet().toArray(new String[0]);
                menuTendinaD.setModel(new DefaultComboBoxModel<>(dipendentiIniziali));
            }
        }

        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");

        // Aggiungi i componenti al pannello
        panel.add(labelTendina);
        panel.add(menuTendina);
        panel.add(labelTendinaR);
        panel.add(menuTendinaR);
        panel.add(labelTendinaD);
        panel.add(menuTendinaD);
        panel.add(new JLabel()); // Spaziatore per allineamento
        panel.add(confermaButton);

        // Azione sul bottone di conferma
        confermaButton.addActionListener(e -> {
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            String selezioneRuolo = (String) menuTendinaR.getSelectedItem();
            String selezioneDipendente = (String) menuTendinaD.getSelectedItem();

            if (selezioneUnita != null && selezioneRuolo != null && selezioneDipendente != null) {
                String[] dipendenti = o.getUnita(selezioneUnita)
                        .getDipendenti(o.getUnita(selezioneUnita).getRuolo(selezioneRuolo)).keySet().toArray(new String[0]);
                menuTendinaD.setModel(new DefaultComboBoxModel<>(dipendenti));
                String dipendente=(String)menuTendinaD.getSelectedItem();
                if (dipendente != null) {
                    o.removeDipendente( o.getUnitaDb().get(selezioneUnita), o.getUnitaDb().get(selezioneUnita).getRuolo(selezioneRuolo),o.getUnitaDb().get(selezioneUnita).getRuolo(selezioneRuolo).getDipendente(dipendente));
                    JOptionPane.showMessageDialog(this, "Dipendente rimosso: " + dipendente.toString());
                    rimuoviDipendente.dispose(); // Chiudi il dialogo
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona tutti i campi richiesti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        rimuoviDipendente.add(panel);
        rimuoviDipendente.setVisible(true);
    }

    private void apriDialogoSalva() {
        JFileChooser sceltaFile = new JFileChooser();
        sceltaFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = sceltaFile.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File directorySelezionata = sceltaFile.getSelectedFile();

            // Mostra un dialogo per inserire il nome del file
            String nomeFile = JOptionPane.showInputDialog(
                    this,
                    "Inserisci il nome del file (senza estensione):",
                    "Salva File",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (nomeFile != null && !nomeFile.trim().isEmpty()) {
                // Aggiunge l'estensione ".bin" al nome del file
                String percorsoCompleto = directorySelezionata.getAbsolutePath() + File.separator + nomeFile.trim();
                o.setFilePath(percorsoCompleto);
                o.salvaOrganigramma();
                // Procedi con il salvataggio o altre operazioni
                JOptionPane.showMessageDialog(this, "File salvato: " + percorsoCompleto);
                //tornare alla principale
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nome del file non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
