package GUI;

import composite.Unita;
import composite.UnitaComposite;
import utils.Dipendente;
import utils.Ruolo;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


public class OrganigrammaPage extends JFrame {
    private Organigramma o;
    private final mxGraph graph;
    private final Object parent;

    public OrganigrammaPage(String pathFile){
        o=new Organigramma(pathFile);
        graph = new mxGraph();
        parent = graph.getDefaultParent();
        page();
    }

    public OrganigrammaPage(Unita radice){
        o=new Organigramma(radice);
        graph = new mxGraph();
        parent = graph.getDefaultParent();
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
    public JPanel raffiguraOrganigramma() {
        // Creazione del pannello principale
        JPanel organigrammaPanel = new JPanel(new BorderLayout());

        // Disabilita il layout automatico predefinito
        graph.setAutoSizeCells(true);
        graph.getModel().beginUpdate();
        try {
            // Costruzione ricorsiva del grafo
            buildGraph(o.getRadice(), null);
        } finally {
            graph.getModel().endUpdate();
        }

        // Applica il layout gerarchico al grafo
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(parent);

        // Creazione del componente di visualizzazione del grafo
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setBackground(Color.WHITE);

        // Aggiungi il componente di visualizzazione al pannello
        organigrammaPanel.add(graphComponent, BorderLayout.CENTER);

        return organigrammaPanel;
    }

    private void buildGraph(Unita unita, Object parentCell) {
        Object unitCell = graph.insertVertex(parent, null, unita.getNome(), 20, 20, 100, 40);
        if (parentCell != null) {
            graph.insertEdge(parent, null, "", parentCell, unitCell);
        }

        for (Unita subUnit : unita.getSottoUnita()) {
            if(o.getUnita(subUnit.getNome())!=null)
                buildGraph(subUnit, unitCell);
        }

    }
    private void aggiornaGrafico(){
        graph.getModel().beginUpdate();
        try {
            graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
            buildGraph(o.getRadice(), null);
        } finally {
            graph.getModel().endUpdate();
        }
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(parent);
    }

    public JPanel  bottoni(){
        JPanel bottoniPanel = new JPanel(new BorderLayout()); // Layout principale

        // Pannello per i gruppi di tre pulsanti
        JPanel gruppiPanel = new JPanel(new GridLayout(4, 3, 10, 10)); // Griglia 3x3 con spaziatura
        JButton aggiungiU = new JButton("Aggiungi Unita");
        JButton removeU = new JButton("Rimuovi Unita");
        JButton aggiungiR = new JButton("Aggiungi Ruolo");
        JButton modificaR = new JButton("Modifica Ruolo");
        JButton removeR = new JButton("Remove Ruolo");
        JButton aggiungiD = new JButton("Aggiungi Dipendente");
        JButton modificaD = new JButton("Modifica Dipendente");
        JButton removeD = new JButton("Remove Dipendente");
        JButton modificaU = new JButton("Modifica Unita");
        JButton info=new JButton("Info");
        gruppiPanel.setBackground(Color.ORANGE);
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
        gruppiPanel.add(info);

        // Pannello per il pulsante "Salva"
        JPanel salvaPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        salvaPanel.setBackground(Color.ORANGE);
        JButton salvaButton = new JButton("Salva");
        JButton redoButton = new JButton("Redo");
        JButton undoButton = new JButton("Undo");
        redoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                o.redo();
                aggiornaGrafico();
            }
        });
        undoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                o.undo();
                aggiornaGrafico();
            }
        });
        undoButton.setBackground(Color.BLUE);
        redoButton.setBackground(Color.BLUE);
        salvaButton.setBackground(Color.BLUE);
        salvaPanel.add(undoButton);
        salvaPanel.add(redoButton);
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
        info.addActionListener(e->bottoniPerUnita());
        salvaButton.addActionListener(e->apriDialogoSalva());
        modificaU.addActionListener(e->apriDialogoModificaU());
        modificaD.addActionListener(e->apriDialogoModificaD());
        modificaR.addActionListener(e->apriDialogoModificaR());

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
                Unita u = new UnitaComposite(nomeUnita, selezione);
                o.aggiungiUnita(u, selezione);
                aggiornaGrafico();

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
                aggiornaGrafico();
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
            String selezioneR = (String) menuTendinaR.getSelectedItem();

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

    private void bottoniPerUnita() {
        // Creazione della finestra di dialogo
        JDialog infoUnita = new JDialog(this, "Informazioni Unità", true);
        infoUnita.setSize(400, 300);
        infoUnita.setLocationRelativeTo(this);

        // Layout principale
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Sezione per il menu a tendina
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelTendina = new JLabel("Seleziona padre:");
        topPanel.add(labelTendina);

        // Verifica che l'oggetto "o" sia valido
        if (o == null) {
            JOptionPane.showMessageDialog(this, "Errore: Oggetto 'o' non inizializzato.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creazione del menu a tendina
        String[] opzioni = o.getNomiUnita(); // Supponendo che o.getNomiUnita() restituisca un array di stringhe
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);
        topPanel.add(menuTendina);

        // Area testo per mostrare le informazioni
        JTextArea infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);

        // Aggiungere il pannello superiore al layout principale
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottone Chiudi
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton chiudiButton = new JButton("Chiudi");
        buttonPanel.add(chiudiButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Azione per il menu a tendina: aggiornamento delle informazioni
        menuTendina.addActionListener(e -> {
            String selezione = (String) menuTendina.getSelectedItem();
            if (selezione == null) {
                selezione = o.getRadice().getNome();
            }

            Unita unita = o.getUnita(selezione);
            if (unita == null) {
                JOptionPane.showMessageDialog(this, "Unità non trovata!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Costruzione delle informazioni
            StringBuilder informazioniUnita = new StringBuilder();
            for (String r : unita.getRuoli().keySet()) {
                informazioniUnita.append(r).append(":\n");
                Ruolo ruolo = unita.getRuolo(r);
                for (String email : ruolo.getDipendenti().keySet()) {
                    informazioniUnita.append(" - ").append(ruolo.getDipendente(email).toString()).append("\n");
                }
            }

            // Aggiornamento dell'area di testo
            infoTextArea.setText(informazioniUnita.toString());
        });

        // Azione per il bottone Chiudi
        chiudiButton.addActionListener(e -> {
            int risposta = JOptionPane.showConfirmDialog(this,
                    "Sicuro di voler chiudere le informazioni?",
                    "Conferma Chiusura",
                    JOptionPane.YES_NO_OPTION);
            if (risposta == JOptionPane.YES_OPTION) {
                infoUnita.dispose(); // Chiude la finestra
            }
        });

        // Aggiunta del pannello principale alla finestra di dialogo
        infoUnita.add(panel);
        infoUnita.setVisible(true);
    }

    private void apriDialogoModificaU() {
            JDialog aggiungiUnita = new JDialog(this, "Modifica Unita", true);
            aggiungiUnita.setSize(300, 200);
            aggiungiUnita.setLocationRelativeTo(this);

            // Pannello principale del dialogo
            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // Spaziatura tra i componenti

            // Etichetta e menu a tendina
            JLabel labelTendina = new JLabel("Seleziona Unita :");
            String[] opzioni = o.getNomiUnita();
            JComboBox<String> menuTendina = new JComboBox<>(opzioni);

            JLabel label = new JLabel("Inserisci nuovo nome :");
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
                    Unita vecchia= o.getUnita(selezione);
                    o.modificaU(vecchia, nomeUnita);
                    aggiornaGrafico();

                    JOptionPane.showMessageDialog(this, "Unità modificata: ");
                    aggiungiUnita.dispose(); // Chiudi il dialogo
                } else {
                    JOptionPane.showMessageDialog(this, "Inserisci un nome valido per l'unità.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });

            aggiungiUnita.add(panel);
            aggiungiUnita.setVisible(true);

    }
    private void apriDialogoModificaD() {
        JDialog aggiungiDipendente = new JDialog(this, "Modifica Dipendente", true);
        aggiungiDipendente.setSize(600, 300);
        aggiungiDipendente.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // Spaziatura tra i componenti

        // Etichetta e menu a tendina per le unità
        JLabel labelTendina = new JLabel("Seleziona Unita:");
        String[] opzioni = o.getNomiUnita(); // Recupera i nomi delle unità
        JComboBox<String> menuTendina = new JComboBox<>(opzioni);

        // Etichetta e menu a tendina per i ruoli
        JLabel labelTendinaR = new JLabel("Seleziona Ruolo:");
        JComboBox<String> menuTendinaR = new JComboBox<>();

        // Etichetta e menu a tendina per i dipendenti
        JLabel labelTendinaD = new JLabel("Seleziona Dipendente:");
        JComboBox<String> menuTendinaD = new JComboBox<>();

        // Altri campi di input
        JLabel label = new JLabel("Inserisci nome Dipendente:");
        JTextField textField = new JTextField();
        JLabel labelC = new JLabel("Inserisci cognome Dipendente:");
        JTextField textFieldC = new JTextField();
        JLabel labelE = new JLabel("Inserisci email Dipendente:");
        JTextField textFieldE = new JTextField();

        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");

        // Aggiorna i ruoli e i dipendenti in base all'unità selezionata
        menuTendina.addActionListener(e -> {
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            if (selezioneUnita != null) {
                String[] ruoli = o.getUnita(selezioneUnita).getRuoli().keySet().toArray(new String[0]);
                menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoli));

                if (ruoli.length > 0) {
                    String selezioneRuolo = ruoli[0];
                    String[] dipendenti = o.getUnita(selezioneUnita).getRuolo(selezioneRuolo).getDipendenti().keySet().toArray(new String[0]);
                    menuTendinaD.setModel(new DefaultComboBoxModel<>(dipendenti));
                } else {
                    menuTendinaD.setModel(new DefaultComboBoxModel<>(new String[0]));
                }
            }
        });

        // Aggiorna i dipendenti in base al ruolo selezionato
        menuTendinaR.addActionListener(e -> {
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            String selezioneRuolo = (String) menuTendinaR.getSelectedItem();
            if (selezioneUnita != null && selezioneRuolo != null) {
                String[] dipendenti = o.getUnita(selezioneUnita).getRuolo(selezioneRuolo).getDipendenti().keySet().toArray(new String[0]);
                menuTendinaD.setModel(new DefaultComboBoxModel<>(dipendenti));
            }
        });

        // Imposta i ruoli e i dipendenti iniziali per l'unità predefinita
        if (opzioni.length > 0) {
            String unitaIniziale = opzioni[0];
            String[] ruoliIniziali = o.getUnita(unitaIniziale).getRuoli().keySet().toArray(new String[0]);
            menuTendinaR.setModel(new DefaultComboBoxModel<>(ruoliIniziali));

            if (ruoliIniziali.length > 0) {
                String[] dipendentiIniziali = o.getUnita(unitaIniziale).getRuolo(ruoliIniziali[0]).getDipendenti().keySet().toArray(new String[0]);
                menuTendinaD.setModel(new DefaultComboBoxModel<>(dipendentiIniziali));
            }
        }

        // Aggiungi i componenti al pannello
        panel.add(labelTendina);
        panel.add(menuTendina);
        panel.add(labelTendinaR);
        panel.add(menuTendinaR);
        panel.add(labelTendinaD);
        panel.add(menuTendinaD);
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
            String selezioneUnita = (String) menuTendina.getSelectedItem();
            String selezioneRuolo = (String) menuTendinaR.getSelectedItem();
            String selezioneDipendente = (String) menuTendinaD.getSelectedItem();
            String nomeDipendente = textField.getText().trim();
            String cognome = textFieldC.getText().trim();
            String email = textFieldE.getText().trim();

            if (selezioneUnita != null && selezioneRuolo != null && selezioneDipendente != null
                    && !nomeDipendente.isEmpty() && !cognome.isEmpty() && !email.isEmpty()) {

                Dipendente d = new Dipendente(nomeDipendente, cognome, email);
                o.modificaD(o.getUnita(selezioneUnita).getRuolo(selezioneRuolo).getDipendente(selezioneDipendente), d,
                        o.getUnita(selezioneUnita).getRuolo(selezioneRuolo), o.getUnita(selezioneUnita));

                JOptionPane.showMessageDialog(this, "Dipendente modificato: " + d.toString());
                aggiungiDipendente.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci tutti i campi richiesti.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        aggiungiDipendente.add(panel);
        aggiungiDipendente.setVisible(true);
    }

    private void apriDialogoModificaR() {
        JDialog rimuoviRuolo = new JDialog(this, "Modifica Ruolo", true);
        rimuoviRuolo.setSize(300, 200);
        rimuoviRuolo.setLocationRelativeTo(this);

        // Pannello principale del dialogo
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // Spaziatura tra i componenti

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
        JLabel label = new JLabel("Inserisci nome Ruolo:");
        JTextField textField = new JTextField();
        // Bottone di conferma
        JButton confermaButton = new JButton("Conferma");

        // Aggiungi i componenti al pannello
        panel.add(labelTendina);
        panel.add(menuTendina);
        panel.add(labelTendinaR);
        panel.add(menuTendinaR);
        panel.add(label);
        panel.add(textField);
        panel.add(new JLabel()); // Spaziatore per allineamento
        panel.add(confermaButton);

        // Azione sul bottone di conferma
        confermaButton.addActionListener(e -> {
            String selezione = (String) menuTendina.getSelectedItem();
            String selezioneR = (String) menuTendinaR.getSelectedItem();

            if (selezione != null && selezioneR != null) {
                o.modificaR(o.getUnitaDb().get(selezione).getRuolo(selezioneR),new Ruolo(textField.getText()),o.getUnitaDb().get(selezione));
                JOptionPane.showMessageDialog(this, "Ruolo rimosso ");
                rimuoviRuolo.dispose(); // Chiudi il dialogo
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci un nome valido per il ruolo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        rimuoviRuolo.add(panel);
        rimuoviRuolo.setVisible(true);
    }
}
