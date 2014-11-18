import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ten on 18/11/14.
 */
public class IOGraphe {

    public static Sommet[] read(String path) throws FileNotFoundException {
        Sommet[] as = null;
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int nLine = 0;
        int nbSommet = 0;
        int currentSommet = 0;
        try {
            while((line = br.readLine()) != null){
                nLine++;
                if(nLine == 1){
                    nbSommet = Integer.parseInt(line);
                    as = new Sommet[nbSommet];
                }
                else{
                    String[] splits = line.split(":");
                    currentSommet = Integer.parseInt(splits[0]);
                    ArrayList<Integer> sl = parseLine(splits[1]);
                    as[currentSommet] = new Sommet(currentSommet, sl);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return as;
    }

    private static ArrayList<Integer> parseLine(String line){
        ArrayList<Integer> a = new ArrayList<Integer>();
        String[] splits = line.substring(2, line.length()-1).split(",");
        for(String s : splits){
            s = s.replaceAll("\\s","");
            a.add(Integer.parseInt(s));
        }
        return a;
    }

}
