import java.util.Vector;

public class Notenverwaltung {

    private Vector<Fach> faecher;
    private float durchschnitt;

    public Notenverwaltung(){
        faecher = new Vector<>();
    }

    public Fach getFach(int index){
        if (index >= 0 && index <= faecher.size()){
            return faecher.get(index);
        }
        return null;
    }
    public Fach getFachMitName(String bezeichnung){
        for (int i = 0; i < faecher.size(); i++){
            if (faecher.get(i).getBezeichnung().equals(bezeichnung)){
                return faecher.get(i);
            }
        }
        return null;
    }
    public void addFach(Fach fach){
        if (checkFach(fach)){
            faecher.add(fach);
            berechneDurchschnitt();
        }
    }
    public boolean checkFach(Fach fach){
        boolean check = true;
        for (int i = 0; i < faecher.size(); i++){
            if (faecher.get(i).getBezeichnung().equals(fach.getBezeichnung())){
                check = false;
            }
        }
        return check;
    }
    public void removeFach(int index){
        if (index >= 0 && index <= faecher.size()){
            faecher.remove(index);
            berechneDurchschnitt();
        }
    }
    public void removeFach(String bezeichnung){
        for (int i = 0; i < faecher.size(); i++){
            if (faecher.get(i).getBezeichnung().equals(bezeichnung)){
                faecher.remove(i);
                berechneDurchschnitt();
            }
        }
    }
    public void berechneDurchschnitt(){
        durchschnitt = 0;
        int cntr = 0;
        for (int i = 0; i < faecher.size(); i++){
            if (faecher.get(i).getDurchschnitt() != 0.00){
                durchschnitt = durchschnitt + faecher.get(i).getDurchschnitt();
                cntr++;
            }
        }
        if (cntr == 0){
            durchschnitt = 0.00f;
        }
        else{
            durchschnitt = durchschnitt/cntr;
        }
    }
    public int getAnzFaecher(){
        return faecher.size();
    }

    public float getDurchschnitt() {
        return durchschnitt;
    }

    public void setDurchschnitt(float durchschnitt) {
        this.durchschnitt = durchschnitt;
    }
}
