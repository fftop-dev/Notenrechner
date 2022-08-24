import java.util.Vector;

public class Fach {

    private String bezeichnung;
    private Vector<Note> noten;
    private float durchschnitt;

    public Fach(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        noten = new Vector<>();
    }
    public String getBezeichnung() {
        return bezeichnung;
    }
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    public float getDurchschnitt() {
        return durchschnitt;
    }
    public Note getnote(int index){
        if (index >= 0 && index <= noten.size()){
            return noten.get(index);
        }
        return null;
    }
    public Note getnote(String bezeichnung){
        for (int i = 0; i < noten.size(); i++){
            if (noten.get(i).getBezeichnung().equals(bezeichnung)){
                return noten.get(i);
            }
        }
        return null;
    }
    public void addNote(Note note){
        if (checkNote(note)){
            noten.add(note);
            berechneDurchschnitt();
        }
    }
    public void removeNote(int index){
        if (index >= 0 && index <= noten.size()){
            noten.remove(index);
            berechneDurchschnitt();
        }
    }
    public void removeNote(String bezeichnung){
        for (int i = 0; i < noten.size(); i++){
            if (noten.get(i).getBezeichnung().equals(bezeichnung)){
                noten.remove(i);
                berechneDurchschnitt();
            }
        }
    }
    public void berechneDurchschnitt(){
        float durchschnittTemp = 0;
        int gewichtungen = 0;
        for (int i = 0; i < noten.size(); i++){
            durchschnittTemp = durchschnittTemp + (noten.get(i).getGewichtung() * noten.get(i).getNotenwert());
            gewichtungen = gewichtungen + noten.get(i).getGewichtung();
        }
        durchschnitt = durchschnittTemp/gewichtungen;
    }
    public int getAnzahlNoten(){
        return noten.size();
    }
    public boolean checkNote(Note note){
        boolean check = true;
        for (int i = 0; i < noten.size(); i++){
            if (note.getBezeichnung().equals(noten.get(i).getBezeichnung())){
                check = false;
            }
        }
        return check;
    }
}
