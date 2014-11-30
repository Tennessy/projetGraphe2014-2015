import java.io.FileNotFoundException;
import java.lang.reflect.Array;
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

        Integer[] color =  coloration(sl, sl);
       /* Integer [] sommetColor = new Integer [6];
        sommetColor[0]=-1;
        sommetColor[1]=0;
        sommetColor[2]=1;
        sommetColor[3]=2;
        sommetColor[4]=3;
        sommetColor[5]=4;


        sommetColor=autreCouleur(sl[0].getVoisins(), sommetColor, sl);
        sommetColor[0] = rechCouleur(sl[0].getVoisins(), sommetColor);*/

        for(int i=0; i<sl.length; i++){
            System.out.println(i + " : " + color[i]);
        }




    }

    public static Integer[] coloration(Sommet[] sommets, Sommet[] sommetsInit){



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

        while((x<sommets.length && sommets[x] == null) || (x<sommets.length && sommets[x]!=null && sommets[x].getVoisins().size()>5))
            x++;


        Sommet[] sommetsTemp = new Sommet[sommets.length];

        for(int i=0; i<sommets.length; i++){
            //System.out.print(sommets[i] + " ");
            sommetsTemp[i] = sommets[i] != null?new Sommet(sommets[i].getNumero(),sommets[i].getVoisins()):null;
            if(sommetsTemp[i]!=null){
                sommetsTemp[i].getVoisins().remove((Object)x);
            }
        }
        //System.out.println();
        sommetsTemp[x] = null;
        //System.out.println("X = " + x);

        if(nbSommet>1)
            sommetColor = coloration(sommetsTemp, sommetsInit);

        int couleur = rechCouleur(sommets[x].getVoisins(), sommetColor);

        if(couleur != -1){
            sommetColor[x]=couleur;
        }
        else{
            ArrayList<Integer> sommetFaits = new ArrayList<Integer>();
            while(sommetColor[x]==-1) {
                sommetColor = inverserCouleur(sommets[x], sommetsInit, sommetColor, sommetFaits);
                sommetColor[x] = rechCouleur(sommets[x].getVoisins(), sommetColor);
            }
        }

        return sommetColor;
    }

    public static int rechCouleur(ArrayList<Integer> voisins,  Integer[] sommetColor) {
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
        return -1;

    }

    public static Integer [] inverserCouleur(Sommet x, Sommet[] sommets, Integer[] sommetColor, ArrayList<Integer> sommetFaits) {
        Sommet premier=null;
        for(int som : x.getVoisins()){
            if (som > x.getNumero() && sommetColor[som]!=-1){
                premier = sommets[som];
            }
        }

        int couleurPremier = sommetColor[premier.getNumero()];
        if(premier.getVoisins().isEmpty()) {
            if(couleurPremier<4){
                sommetColor[premier.getNumero()]++;
            }
            else{
                sommetColor[premier.getNumero()]=0;
            }
            return sommetColor;
        }

        Sommet second = sommets[premier.getVoisins().get(0)];
        int couleurSecond = sommetColor[second.getNumero()];
        for(int i : premier.getVoisins()) {
            if(sommetColor[i]!=-1 && i!=x.getNumero()) {
                second = sommets[i];
                couleurSecond = sommetColor[second.getNumero()];
            }
        }


        boolean[] visite = new boolean[sommets.length];
        for (int i = 0; i < sommets.length; i++) {
            visite[i] = false;
        }
        ArrayList<Integer> voisinsX = x.getVoisins();
        ArrayList<Integer> compConnexe = new ArrayList<Integer>();
        ArrayList<Integer> parcours = new ArrayList<Integer>();

        compConnexe.add(premier.getNumero());
        parcours.add(premier.getNumero());
        boolean boucle = false;
        int sommetBoucle = -1;

        while (!parcours.isEmpty()) {
            boucle = false;
            int sActu = parcours.get(0);
            for (int sVoisin : sommets[sActu].getVoisins()) {
                if ((sommetColor[sVoisin] == couleurPremier || sommetColor[sVoisin] == couleurSecond) && visite[sVoisin] == false && (sVoisin != premier.getNumero()) && !compConnexe.contains(sVoisin)) {
                    parcours.add(sVoisin);
                    compConnexe.add(sVoisin);
                    visite[sVoisin]=true;
                    if (voisinsX.contains(sVoisin)) {
                        boucle = true;
                        sommetBoucle = sVoisin;
                    }
                }

            }
            parcours.remove(0);
            if (boucle) {
                parcours.clear();
                compConnexe.clear();
                int newS = -1;
                for (int ns : voisinsX) {
                    if (ns != sommetBoucle && ns != premier.getNumero()) {
                        newS = ns;
                    }
                }
                couleurPremier = sommetColor[newS];
                second = sommets[sommets[sommets[newS].getVoisins().get(0)].getVoisins().get(0)];
                couleurSecond = sommetColor[second.getNumero()];
                parcours.add(newS);
                compConnexe.add(newS);
            }

        }
        for (int sommet : compConnexe) {
            if (sommetColor[sommet] == couleurPremier) {
                sommetColor[sommet] = couleurSecond;
            } else {
                sommetColor[sommet] = couleurPremier;
            }
        }
        return sommetColor;
    }
    /*public static Integer[] autreCouleur(ArrayList<Integer> voisins, Integer[] sommetColor, Sommet[] sommets ){


        for(int i : voisins) {
            boolean [] color = new boolean[5];
            for (int j=0; j<5; j++){
                color[j]=true;
            }
            for(int j : sommets[i].getVoisins()){
                if(sommetColor[j] != -1)
                    color[sommetColor[j]]=false;
            }
            color[sommetColor[i]]=false;
            for(int k =0; k<5; k++) {
                if (color[k] == true) {
                    sommetColor[i] = k;
                    return sommetColor;
                }
            }
        }
        return null;
    }*/
}
