package Graphe;

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

    public static Sommet[] read(String pathSommet, String pathPos) throws FileNotFoundException {
        Sommet[] sommets = IOGraphe.read(pathSommet);
        BufferedReader br = new BufferedReader(new FileReader(pathPos));
        String line;
        int nLine = 0;
        int currentSommet = 0;
        try {
            while((line = br.readLine()) != null){
                nLine++;

                if(nLine != 1){
                    String[] splits = line.split(":");
                    currentSommet = Integer.parseInt(splits[0]);
                    int[] coord = parseLinePos(splits[1]);
                    sommets[currentSommet].setPosition(coord[0], coord[1]);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sommets;
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

    private static int[] parseLinePos(String line){
        int[] a = new int[2];
        String[] splits = line.substring(2, line.length()-1).split(",");
        System.out.println("Splits : " + splits[0] + " " + splits[1]);
        int i=0;
        for(String s : splits){
            s = s.replaceAll("\\s","");
            a[i] = Integer.parseInt(s);
            i++;
        }
        return a;
    }

}
