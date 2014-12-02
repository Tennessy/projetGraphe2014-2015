import java.util.ArrayList;

/**
 * Created by amelie on 30/10/14.
 */
public class Sommet {
    public int numero;
    int posx;
    int posy;
    ArrayList<Integer> voisins;
    private boolean active;

    public Sommet(){
        this.numero = -1;
        this.posx = 0;
        this.posy = 0;
        this.voisins = new ArrayList<Integer>();
        this.active=true;
    }

    public Sommet(int num, ArrayList<Integer> voisins){
        this.numero = num;
        this.posx=0;
        this.posy=0;
        this.voisins=voisins;
        this.active=true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public void setVoisins(ArrayList<Integer> voisins) {
        this.voisins = voisins;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public ArrayList<Integer> getVoisins() {
        return voisins;
    }
}
