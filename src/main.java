import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by amelie on 30/10/14.
 */
public class main {

    public static void main (String [] args){
        ArrayList<Sommet> adj;

        Sommet[] sl = null;
        try {
            sl = IOGraphe.read("test.graphe");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Integer[] color =  coloration(sl);

        /*for(int i=0; i<sl.length; i++){
            System.out.println(i + " : " + color[i]);
        }*/
    }

    public static Integer[] coloration(Sommet[] sommets){
        int nbSommet = 0;
        for(int i=0; i<sommets.length; i++){
            if(sommets[i] != null)
                nbSommet++;
        }

        //System.out.println("nbSommet : " + nbSommet);
        /*Integer[] sommetColor= new Integer[nbSommet];
        for(int i=0; i<nbSommet; i++){
            sommetColor[i] = -1;
        }
        if (!adj.isEmpty()){
         ArrayList<Sommet> adj2= (ArrayList <Sommet>) adj.clone();
         adj2.remove(0);
         sommetColor=coloration(adj2, nbSommet);

         sommetColor[adj.get(0).getNumero()] = rechCouleur(adj.get(0).voisins, sommetColor);
        }
        return sommetColor;*/
        int x = 0;
        Integer[] sommetColor= new Integer[sommets.length];

        if(nbSommet == 1)
            for(int i=0; i<sommets.length; i++){
                sommetColor[i] = -1;
            }

        while(x<sommets.length && sommets[x] == null)
            x++;

        Sommet[] sommetsTemp = new Sommet[sommets.length];
        for(int i=0; i<sommets.length; i++){
            //System.out.print(sommets[i] + " ");
            sommetsTemp[i] = sommets[i];
        }
        //System.out.println();
        sommetsTemp[x] = null;
        //System.out.println("X = " + x);

        if(nbSommet>1)
            sommetColor = coloration(sommetsTemp);

        try {
            sommetColor[x] = rechCouleur(sommets[x].getVoisins(), sommetColor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sommetColor;
    }

    public static int rechCouleur(ArrayList<Integer> voisins,  Integer[] sommetColor) throws Exception{
        boolean [] color = new boolean[5];
        for (int j=0; j<5; j++){
            color[j]=true;
        }
        for(int i : voisins){
            if(sommetColor[i] != -1)
                color[sommetColor[i]]=false;
        }
        for(int i =0; i<5; i++) {
            if (color[i] == true) {
                return i;
            }
        }
        throw new Exception("-1");

    }
}
