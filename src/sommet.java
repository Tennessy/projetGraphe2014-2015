import java.util.ArrayList;

/**
 * Created by amelie on 30/10/14.
 */
public class sommet {
    public int numero;
    int posx;
    int posy;
    ArrayList<Integer> voisins;

    public sommet(int num, ArrayList<Integer> voisins){
        this.numero = num;
        this.posx=0;
        this.posy=0;
        this.voisins=(ArrayList<Integer>)voisins.clone();
    }

    public int getNumero() {
        return numero;
    }
}
