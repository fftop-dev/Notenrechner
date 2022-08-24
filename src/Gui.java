import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsAdapter;

public class Gui extends JFrame {
    Notenverwaltung notenverwaltung = new Notenverwaltung();
    JFrame errorFrame;

    JPanel mainPanel = new JPanel(new BorderLayout());
    JLabel topLabel = new JLabel(" Notenverwaltung");
    JLabel bottomText = new JLabel(" Semesterschnitt: --");
    JPanel panelLeft = new JPanel(new GridLayout());
    JPanel panelRight = new JPanel(new GridLayout());
    JPanel panelBottom = new JPanel((new GridLayout(0,4)));
    JPanel panelBottomFach = new JPanel(new GridLayout(0,4));
    JPanel panelBottomNote = new JPanel(new GridLayout(0,4));
    JPanel midPanel = new JPanel(new GridLayout(1,2));
    JPanel topPanel = new JPanel(new GridLayout(0,4));
    JButton fachhinzufuegenButton = new JButton("Fach hinzufügen");
    JButton noteHinzufuegenButton = new JButton("Note hinzufügen");
    JPanel tablePanel2;

    //Tabelle faecher vorbereiten
    JFrame fachHinzufuegenFenster;
    JTextField fachbezeichnung;
    String ausgewaeltesFach;

    JTable tabelleFaecher;
    String[] tabelleFaecherKopf = new String[]{"Fach", "Schnitt"};
    String[][] inhaltFach = null;
    DefaultTableModel tableModleFaecher = new DefaultTableModel(inhaltFach, tabelleFaecherKopf){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };

    //Tabelle Note vorbereiten
    JFrame noteHinzufuegen;
    JTextField notenbezeichnung;
    JTextField notenwert;
    JTextField notengewichtung;
    String ausgewaelteNote;

    JTable tabelleNoten;
    String[] tabelleNotenKopf = new String[]{"Beschreibung", "Wert", "Gewichtung"};
    String[][]inhaltNote = null;
    DefaultTableModel tableModleNoten = new DefaultTableModel(inhaltNote, tabelleNotenKopf){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };

    public Gui(){
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        midPanel.add(panelLeft, BorderLayout.WEST);
        midPanel.add(panelRight, BorderLayout.EAST);
        mainPanel.add(midPanel, BorderLayout.CENTER);
        mainPanel.add(panelBottom, BorderLayout.SOUTH);
        panelLeft.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelRight.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        updateSemesterSchnitt();
        
        //Tabelle faecher erstellen
        tabelleFaecher = new JTable(inhaltFach, tabelleFaecherKopf);
        JPanel tablePanel = new JPanel(new BorderLayout());
        JTableHeader header = tabelleFaecher.getTableHeader();
        JScrollPane scroll1 = new JScrollPane(tablePanel);
        tabelleFaecher.setModel(tableModleFaecher);
        tabelleFaecher.setShowGrid(false);
        tabelleFaecher.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabelleFaecher.setFont(new Font("sunserif", Font.BOLD, 16));
        tabelleFaecher.getSelectionModel().addListSelectionListener(new fachSelected());
        header.setResizingAllowed(false);
        tablePanel.add(header, BorderLayout.NORTH);
        tablePanel.add(tabelleFaecher, BorderLayout.CENTER);
        tablePanel.add(fachhinzufuegenButton, BorderLayout.SOUTH);
        header.setReorderingAllowed(false);
        header.setFont(new Font("sunserif", Font.BOLD, 16));

        //Tabelle Noten
        tabelleNoten = new JTable(inhaltNote, tabelleNotenKopf);
        tablePanel2 = new JPanel(new BorderLayout());
        JTableHeader header2 = tabelleNoten.getTableHeader();
        JScrollPane scroll2 = new JScrollPane(tablePanel2);
        tabelleNoten.setModel(tableModleNoten);
        tabelleNoten.setShowGrid(false);
        tabelleNoten.getColumnModel().getColumn(0).setPreferredWidth(175);
        tabelleNoten.setFont(new Font("sunserif", Font.BOLD, 16));
        tabelleNoten.getSelectionModel().addListSelectionListener(new noteSelected());
        header2.setResizingAllowed(false);
        tablePanel2.add(header2, BorderLayout.NORTH);
        tablePanel2.add(tabelleNoten, BorderLayout.CENTER);
        tablePanel2.add(noteHinzufuegenButton, BorderLayout.SOUTH);
        header2.setReorderingAllowed(false);
        header2.setFont(new Font("sunserif", Font.BOLD, 16));
        tablePanel2.setVisible(false);

        createBotomSpaceFach();
        createBottomSpaceNote();

        panelLeft.add(scroll1, BorderLayout.CENTER);
        panelRight.add(scroll2, BorderLayout.CENTER);

        topLabel.setFont(new Font("sunserif", Font.BOLD, 16));
        bottomText.setFont(new Font("sunserif", Font.BOLD, 16));

        fillSpace(topPanel, topLabel);
        fillSpace(panelBottom, bottomText);

        setTitle("Notenrechner v.1.00");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);

        fachhinzufuegenButton.addActionListener(new buttonsMainFrame());
        noteHinzufuegenButton.addActionListener(new buttonsMainFrame());
    }
    class buttonsMainFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] auswahl = new String[]{"Fach hinzufügen", "Fach umbenennen", "Fach löschen", "Note hinzufügen", "Note bearbeiten", "Note löschen", "zurück"};
            JButton button = (JButton) e.getSource();
            if (button.getText().equals(auswahl[0]) || button.getText().equals(auswahl[1])){
                if (fachHinzufuegenFenster == null){
                    fachHinzufuegen(button.getText());
                }
            }
            else if (button.getText().equals(auswahl[3]) || button.getText().equals(auswahl[4])){
                if (noteHinzufuegen == null){
                    noteHinzufuegen(button.getText());
                }
            }
            else if (button.getText().equals(auswahl[6])){
                panelBottom.setVisible(true);
                mainPanel.add(panelBottom, BorderLayout.SOUTH);
                panelBottomFach.setVisible(false);
                panelBottomNote.setVisible(false);
            }
            else if (button.getText().equals(auswahl[2])){
                notenverwaltung.removeFach(ausgewaeltesFach);
                fillTableFach();
                tabelleFaecher.getColumnModel().getColumn(0).setPreferredWidth(200);
                notenverwaltung.berechneDurchschnitt();
                updateSemesterSchnitt();
                tableModleNoten.setDataVector(null, new String[]{"Bezeichnung", "Wert", "Gewichtung"});
                if (notenverwaltung.getAnzFaecher() == 0){
                    noteHinzufuegenButton.setEnabled(false);
                }
            }
            else if (button.getText().equals(auswahl[5])){
                notenverwaltung.getFachMitName(ausgewaeltesFach).removeNote(ausgewaelteNote);
                fillTableNote(ausgewaeltesFach);
                tabelleNoten.getColumnModel().getColumn(0).setPreferredWidth(175);
                updateSemesterSchnitt();
            }
        }
    }
    class buttonsSideFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] auswahl = new String[]{"Fach hinzufügen", "Note hinzufügen", "Fach umbenennen", "Note bearbeiten", "Abbrechen", "Note löschen", "Ok"};
            JButton button = (JButton) e.getSource();
            if (button.getText().equals(auswahl[0])) {
                if (checkFach(fachbezeichnung.getText())){
                    Fach fach = new Fach(fachbezeichnung.getText());
                    notenverwaltung.addFach(fach);
                    fillTableFach();
                    tabelleFaecher.getColumnModel().getColumn(0).setPreferredWidth(200);
                    resetFachFenster();
                    noteHinzufuegenButton.setEnabled(true);
                }
                else{
                    resetFachFenster();
                    errorFenster();
                }
            }
            else if (button.getText().equals(auswahl[4])) {
                resetFachFenster();
                resetNotenFenster();
            }
            else if (button.getText().equals(auswahl[1])) {
                if (checkNote(notenbezeichnung.getText(), Float.parseFloat(notenwert.getText()), Integer.parseInt(notengewichtung.getText()))){
                    Note note = new Note(notenbezeichnung.getText());
                    note.setNotenwert(Float.parseFloat(notenwert.getText()));
                    note.setGewichtung(Integer.parseInt(notengewichtung.getText()));
                    notenverwaltung.getFachMitName(ausgewaeltesFach).addNote(note);
                    fillTableNote(ausgewaeltesFach);
                    tabelleNoten.getColumnModel().getColumn(0).setPreferredWidth(175);
                    resetNotenFenster();
                    fillTableFach();
                    tabelleFaecher.getColumnModel().getColumn(0).setPreferredWidth(200);
                    notenverwaltung.berechneDurchschnitt();
                    updateSemesterSchnitt();
                }
                else{
                    resetNotenFenster();
                    errorFenster();
                }

            }
            else if (button.getText().equals(auswahl[2])){
                if (checkFach(fachbezeichnung.getText())){
                    notenverwaltung.getFachMitName(ausgewaeltesFach).setBezeichnung(fachbezeichnung.getText());
                    ausgewaeltesFach = fachbezeichnung.getText();
                    fillTableFach();
                    tabelleFaecher.getColumnModel().getColumn(0).setPreferredWidth(200);
                    resetFachFenster();
                }
                else{
                    resetFachFenster();
                    errorFenster();
                }
            }
            else if (button.getText().equals(auswahl[3])){
                if (checkNote(notenbezeichnung.getText(), Float.parseFloat(notenwert.getText()), Integer.parseInt(notengewichtung.getText()))){
                    notenverwaltung.getFachMitName(ausgewaeltesFach).getnote(ausgewaelteNote).setNotenwert(Float.parseFloat(notenwert.getText()));
                    notenverwaltung.getFachMitName(ausgewaeltesFach).getnote(ausgewaelteNote).setGewichtung(Integer.parseInt(notengewichtung.getText()));
                    notenverwaltung.getFachMitName(ausgewaeltesFach).getnote(ausgewaelteNote).setBezeichnung(notenbezeichnung.getText());
                    fillTableNote(ausgewaeltesFach);
                    tabelleNoten.getColumnModel().getColumn(0).setPreferredWidth(175);
                    resetNotenFenster();
                    fillTableFach();
                    tabelleFaecher.getColumnModel().getColumn(0).setPreferredWidth(200);
                    notenverwaltung.berechneDurchschnitt();
                    updateSemesterSchnitt();
                }
                else{
                    resetNotenFenster();
                    errorFenster();
                }
            }
            else if (button.getText().equals(auswahl[6])){
                resetErrorFenster();
            }
        }
    }
    class fachSelected implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            tablePanel2.setVisible(true);
            try{
                ausgewaeltesFach = tabelleFaecher.getValueAt(tabelleFaecher.getSelectedRow(), 0).toString();
            }
            catch (ArrayIndexOutOfBoundsException ignored){}
            fillTableNote(ausgewaeltesFach);
            panelBottom.setVisible(false);
            panelBottomNote.setVisible(false);
            mainPanel.add(panelBottomFach, BorderLayout.SOUTH);
            panelBottomFach.setVisible(true);
            tabelleNoten.getColumnModel().getColumn(0).setPreferredWidth(175);
        }
    }
    class noteSelected implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            try{
                ausgewaelteNote = tabelleNoten.getValueAt(tabelleNoten.getSelectedRow(), 0).toString();
            }
            catch (ArrayIndexOutOfBoundsException ignored){}
            panelBottom.setVisible(false);
            panelBottomFach.setVisible(false);
            mainPanel.add(panelBottomNote, BorderLayout.SOUTH);
            panelBottomNote.setVisible(true);
        }
    }
    public void fillSpace(JPanel ground, JLabel mainContent){
        for (int i = 0; i < 8 ; i++){
            if (i == 4){
                ground.add(mainContent);
            }
            JLabel placeholder = new JLabel();
            ground.add(placeholder);
        }
    }
    public void fillTableFach(){
        String[][] inhaltFach = new String[notenverwaltung.getAnzFaecher()][2];
        for (int i = 0; i < notenverwaltung.getAnzFaecher(); i++){
            String[] faecherInfos = new String[]{notenverwaltung.getFach(i).getBezeichnung(), "" + String.format("%.2f",notenverwaltung.getFach(i).getDurchschnitt())};
            inhaltFach[i] = faecherInfos;
        }
        tableModleFaecher.setDataVector(inhaltFach, new String[]{"Fach", "Schnitt"});
    }
    public void fillTableNote(String bezeichnung){
        if (notenverwaltung.getFachMitName(bezeichnung) != null){
            String[][] inhaltNote = new String[notenverwaltung.getFachMitName(bezeichnung).getAnzahlNoten()][3];
            for (int i = 0; i < notenverwaltung.getFachMitName(bezeichnung).getAnzahlNoten(); i++){
                String[] noteInfos = new String[]{notenverwaltung.getFachMitName(bezeichnung).getnote(i).getBezeichnung(), Float.toString(notenverwaltung.getFachMitName(bezeichnung).getnote(i).getNotenwert()), Integer.toString(notenverwaltung.getFachMitName(bezeichnung).getnote(i).getGewichtung())};
                inhaltNote[i] = noteInfos;
            }
            tableModleNoten.setDataVector(inhaltNote, new String[]{"Bezeichnung", "Wert", "Gewichtung"});
        }
    }
    public void fachHinzufuegen(String text){
        fachHinzufuegenFenster = new JFrame();
        fachbezeichnung = new JTextField("");
        fachHinzufuegenFenster.setTitle(text);
        fachHinzufuegenFenster.setFont(new Font("sunserif", Font.BOLD, 16));
        fachHinzufuegenFenster.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel bezeichnung = new JLabel("  Bezeichnung  ");
        JLabel placeHolder = new JLabel(" ");
        JLabel placeHolder2 = new JLabel(" ");
        JButton abbrechen = new JButton("Abbrechen");
        JButton hinzufuegen = new JButton(text);

        JPanel panelFach1 = new JPanel(new BorderLayout());
        JPanel panelFach2 = new JPanel(new BorderLayout());
        JPanel panelFach3 = new JPanel(new BorderLayout());
        JPanel panelButtons = new JPanel(new GridLayout(1,2));
        panelFach1.add(panelFach2, BorderLayout.NORTH);
        panelFach1.add(panelFach3, BorderLayout.SOUTH);
        fachHinzufuegenFenster.add(panelFach1);

        panelFach2.add(placeHolder, BorderLayout.NORTH);
        panelFach2.add(bezeichnung, BorderLayout.WEST);
        panelFach2.add(fachbezeichnung, BorderLayout.CENTER);
        panelFach2.add(placeHolder2, BorderLayout.EAST);
        panelButtons.add(abbrechen);
        panelButtons.add(hinzufuegen);
        panelFach3.add(panelButtons, BorderLayout.EAST);

        fachHinzufuegenFenster.setSize(350,125);
        fachHinzufuegenFenster.setResizable(false);
        fachHinzufuegenFenster.setVisible(true);

        abbrechen.addActionListener(new buttonsSideFrame());
        hinzufuegen.addActionListener(new buttonsSideFrame());
    }
    public void noteHinzufuegen(String text){
        noteHinzufuegen = new JFrame();
        notenbezeichnung = new JTextField("");
        notenwert = new JTextField("");
        notengewichtung = new JTextField("");

        noteHinzufuegen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        noteHinzufuegen.setTitle(text);
        noteHinzufuegen.setFont(new Font("sunserif", Font.BOLD, 16));

        JLabel bezeichnung2 = new JLabel(" Bezeichnung ");
        JLabel wert = new JLabel(" Notenwert ");
        JLabel gewichtung = new JLabel(" Gewichtung(%) ");
        JButton hinzufuegen = new JButton(text);
        JButton abbrechen = new JButton("Abbrechen");

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        JPanel wertePanel = new JPanel(new GridLayout(4,2));
        JLabel placeHolder1 = new JLabel("");

        wertePanel.add(bezeichnung2);
        wertePanel.add(notenbezeichnung);
        wertePanel.add(wert);
        wertePanel.add(notenwert);
        wertePanel.add(gewichtung);
        wertePanel.add(notengewichtung);
        wertePanel.add(placeHolder1);

        buttonPanel.add(abbrechen);
        buttonPanel.add(hinzufuegen);

        mainPanel.add(wertePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        noteHinzufuegen.add(mainPanel);

        noteHinzufuegen.setSize(320,145);
        noteHinzufuegen.setResizable(false);
        noteHinzufuegen.setVisible(true);

        abbrechen.addActionListener(new buttonsSideFrame());
        hinzufuegen.addActionListener(new buttonsSideFrame());

    }
    public void resetNotenFenster(){
        if (noteHinzufuegen != null){
            noteHinzufuegen.dispose();
        }
        noteHinzufuegen = null;
        notenbezeichnung = null;
        notenwert = null;
        notengewichtung = null;
    }
    public void resetFachFenster(){
        if (fachHinzufuegenFenster != null){
            fachHinzufuegenFenster.dispose();
        }
        fachHinzufuegenFenster = null;
        fachbezeichnung = null;
    }
    public void resetErrorFenster(){
        if (errorFrame != null){
            errorFrame.dispose();
        }
    }
    public void createBotomSpaceFach(){

        JButton umbenennen = new JButton("Fach umbenennen");
        JButton loeschen = new JButton("Fach löschen");
        JButton zurueck = new JButton("zurück");
        JLabel placeHolder1 = new JLabel("");
        JLabel placeHolder2 = new JLabel("");
        JLabel placeHolder3 = new JLabel("");
        JLabel placeHolder4 = new JLabel("");
        JLabel placeHolder5 = new JLabel("");
        JLabel placeHolder6 = new JLabel("");

        panelBottomFach.add(placeHolder1);
        panelBottomFach.add(placeHolder2);
        panelBottomFach.add(placeHolder3);
        panelBottomFach.add(placeHolder4);
        panelBottomFach.add(umbenennen);
        panelBottomFach.add(loeschen);
        panelBottomFach.add(placeHolder5);
        panelBottomFach.add(zurueck);
        panelBottomFach.add(placeHolder6);

        umbenennen.addActionListener(new buttonsMainFrame());
        loeschen.addActionListener(new buttonsMainFrame());
        zurueck.addActionListener(new buttonsMainFrame());

        panelBottomFach.setVisible(false);

    }
    public void createBottomSpaceNote(){

        JButton bearbeiten = new JButton("Note bearbeiten");
        JButton loeschen = new JButton("Note löschen");
        JButton zurueck = new JButton("zurück");
        JLabel placeHolder1 = new JLabel("");
        JLabel placeHolder2 = new JLabel("");
        JLabel placeHolder3 = new JLabel("");
        JLabel placeHolder4 = new JLabel("");
        JLabel placeHolder5 = new JLabel("");
        JLabel placeHolder6 = new JLabel("");

        panelBottomNote.add(placeHolder1);
        panelBottomNote.add(placeHolder2);
        panelBottomNote.add(placeHolder3);
        panelBottomNote.add(placeHolder4);
        panelBottomNote.add(bearbeiten);
        panelBottomNote.add(loeschen);
        panelBottomNote.add(placeHolder5);
        panelBottomNote.add(zurueck);
        panelBottomNote.add(placeHolder6);

        bearbeiten.addActionListener(new buttonsMainFrame());
        loeschen.addActionListener(new buttonsMainFrame());
        zurueck.addActionListener(new buttonsMainFrame());

        panelBottomNote.setVisible(false);

    }
    public void updateSemesterSchnitt(){
        notenverwaltung.berechneDurchschnitt();
        bottomText.setText("Semesterschnitt: " + String.format("%.2f",notenverwaltung.getDurchschnitt()));
        fillTableFach();
    }
    public boolean checkFach(String bezeichnung){
        boolean check = true;
        for (int i = 0; i < notenverwaltung.getAnzFaecher(); i++){
            if (bezeichnung.equals(notenverwaltung.getFach(i).getBezeichnung())){
                check = false;
            }
        }
        return check;
    }
    public boolean checkNote(String bezeichnung, float notenWert, int gewichtung){
        boolean check = true;
        for (int i = 0; i < notenverwaltung.getFachMitName(ausgewaeltesFach).getAnzahlNoten(); i++){
            if ((bezeichnung.equals(notenverwaltung.getFachMitName(ausgewaeltesFach).getnote(i).getBezeichnung()))){
                check = false;
            }
        }
        if((notenWert < 1 || notenWert > 6) || (gewichtung < 1 || gewichtung > 10000)){
            check = false;
        }
        return check;
    }
    public void errorFenster(){

        errorFrame = new JFrame();
        errorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        errorFrame.setTitle("Fehler");
        errorFrame.setFont(new Font("sunserif", Font.BOLD, 16));

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel errorText = new JLabel("Ungültige Eingabe");
        JButton okButton = new JButton("Ok");

        JPanel panel = new JPanel(new GridLayout(2,2));
        JLabel placeHolder1 = new JLabel("");
        JLabel placeHolder2 = new JLabel("");

        mainPanel.add(panel, BorderLayout.CENTER);
        panel.add(errorText);
        panel.add(placeHolder1);
        panel.add(placeHolder2);
        panel.add(okButton);

        errorFrame.add(mainPanel);

        errorFrame.setSize(350,90);
        errorFrame.setResizable(false);
        errorFrame.setVisible(true);

        okButton.addActionListener(new buttonsSideFrame());
    }
}
