import java.io.FileNotFoundException;

/**
 * Created by ten on 01/12/14.
 */
public class test {

    public static Integer[] color;

    public static void main (String [] args){
        Sommet[] sl = null;
        try {
            sl = IOGraphe.read("test.graphe");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        color =  main.coloration(sl, sl);

        verif(color, sl);

    }

    public static void verif(Integer[] color, Sommet[] sl){
        for(Sommet s : sl){
            for(int i : s.getVoisins()) {
                if (color[i] == color[s.getNumero()]){

                    System.out.println(s.getNumero() + "(" + color[s.getNumero()] + ") | " + i + "(" + color[i] + ")");

                }
            }
        }
        System.out.println("All good");
    }
}
