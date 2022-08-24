public class Note {

    private String bezeichnung;
    private float notenwert;
    private int gewichtung;

    public Note(String bezeichnung){
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung(){
        return bezeichnung;
    }
    public float getNotenwert(){
        return notenwert;
    }
    public int getGewichtung(){
        return gewichtung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    public void setNotenwert(float notenwert) {
        if (notenwert >= 1 && notenwert <= 6){
            this.notenwert = notenwert;
        }
        else{
            this.notenwert = 1;
        }
    }
    public void setGewichtung(int gewichtung) {
        if (gewichtung >= 1 && gewichtung <= 10000){
            this.gewichtung = gewichtung;
        }
        else{
            this.gewichtung = 100;
        }
    }
}
