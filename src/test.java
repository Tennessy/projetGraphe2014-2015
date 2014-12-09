import graphe.IOGraphe;
import graphe.Sommet;

import java.io.FileNotFoundException;

/**
 * Created by ten on 09/12/14.
 */
public class test {

    public static void main(String[] args){
        appelGraphe("expl10.graphe");
        appelGraphe("expl100.graphe");
        appelGraphe("expl1000.graphe");
        appelGraphe("expl10000.graphe");
    }

    public static void appelGraphe(String path){
        Sommet[] sl = null;
        long startTime = System.nanoTime();
        try {
            sl = IOGraphe.read(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Integer[] color =  main.coloration(sl, sl, sl.length);
        long endTime = System.nanoTime();
        System.out.println(sl.length + " Sommets | Executed in : " + (endTime - startTime)/1000000 + "ms");
        verif(sl, color);
    }

    public static void verif(Sommet[] sl, Integer[] color){
        boolean erreur = false;
        for(int i=0; i<sl.length; i++){
            for(int s : sl[i].getVoisins()){
                if(color[i] == color[s]){
                    erreur = true;
                }
            }
        }
        if(erreur){
            System.out.println("Erreur");
        }
        else
            System.out.println("All good");
    }
}
