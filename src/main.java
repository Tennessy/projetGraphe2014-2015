import java.util.ArrayList;

/**
 * Created by amelie on 30/10/14.
 */
public class main {

    public static void main (String [] args){
        ArrayList<sommet> adj;
    }

    public ArrayList<Integer> coloration(ArrayList <sommet> adj, int nbSommet){
        ArrayList<Integer> sommetColor= new ArrayList<Integer>();
        for(int i=0; i<nbSommet; i++){
            sommetColor.add(i, -1);
        }
        if (!adj.isEmpty()){
         ArrayList<sommet> adj2= (ArrayList <sommet>) adj.clone();
         adj2.remove(0);
         sommetColor=(ArrayList<Integer>) coloration(adj2, nbSommet).clone();

         sommetColor.get(adj.get(0).getNumero()) = rechCouleur(adj.get(0).voisins, sommetColor);
        }
        return sommetColor;
    }

    public int rechCouleur(ArrayList<Integer> voisins,  ArrayList<Integer> sommetColor){
        boolean [] color = new boolean[5];
        for (int j=0; j<5; j++){
            color[j]=true;
        }
        for(int i : voisins){
            if(sommetColor.get(i) != -1)
                color[sommetColor.get(i)]=false;
            }
        for(int i =0; i<5; i++){
            if(color[i]=true){
                return i;
            }
        }
        return -1;
    }
}
