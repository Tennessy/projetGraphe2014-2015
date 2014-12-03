import Graphe.IOGraphe;
import Graphe.Sommet;
import view.Fenetre;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by amelie on 30/10/14.
 */
public class main {

    public static void main (String [] args){
        ArrayList<Sommet> adj;

        Sommet[] sl = null;
        try {
            sl = IOGraphe.read("test.graphe", "test.coords");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Integer[] color =  coloration(sl, sl, sl.length);
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

        Fenetre fen = new Fenetre(sl, color);




    }

    public static Integer[] coloration(Sommet[] sommets, Sommet[] sommetsInit, int nbSommetActif){

        //System.out.println("nbSommet : " + nbSommet);
        /*Integer[] sommetColor= new Integer[nbSommet];
        for(int i=0; i<nbSommet; i++){
            sommetColor[i] = -1;
        }
        if (!adj.isEmpty()){
         ArrayList<Graphe.Sommet> adj2= (ArrayList <Graphe.Sommet>) adj.clone();
         adj2.remove(0);
         sommetColor=coloration(adj2, nbSommet);

         sommetColor[adj.get(0).getNumero()] = rechCouleur(adj.get(0).voisins, sommetColor);
        }
        return sommetColor;*/
        int x = 0;
        Integer[] sommetColor= new Integer[sommets.length];

        if(nbSommetActif == 1){
            for(int i=0; i<sommets.length; i++) {
                sommetColor[i] = -1;

            }

        }

        boolean trouve = false;
        Iterator<Integer> it;
        while(x<sommets.length && !trouve){
            Sommet xs = sommets[x];
            if(xs.isActive()){
                int nbVoisin = 0;
                ArrayList<Integer> voisins = xs.getVoisins();
                it = voisins.iterator();
                /*for(int sv : voisins){
                    Graphe.Sommet v = sommets[sv];
                    if(v.isActive())
                        nbVoisin++;
                }*/

                while(it.hasNext()){
                    Sommet v = sommets[it.next()];
                    if(v.isActive())
                        nbVoisin++;
                }

                if(nbVoisin>5)
                    x++;
                else{
                    trouve = true;
                }
            }
            else{
                x++;
            }
        }


       /* Graphe.Sommet[] sommetsTemp = new Graphe.Sommet[sommets.length];

        for(int i=0; i<sommets.length; i++){
            //System.out.print(sommets[i] + " ");
            sommetsTemp[i] = sommets[i] != null?new Graphe.Sommet(sommets[i].getNumero(),sommets[i].getVoisins()):null;
            if(sommetsTemp[i]!=null){
                sommetsTemp[i].getVoisins().remove((Object)x);
            }
        }
        System.out.println();
        sommetsTemp[x] = null;*/


        sommets[x].setActive(false);
        if(nbSommetActif>1)
            sommetColor = coloration(sommets, sommetsInit, nbSommetActif-1);

        if(x == 299){
            System.out.println("299! " + sommets[796].isActive());
        }

        if(x == 796)
            System.out.println("796!");
        sommets[x].setActive(true);
        int couleur = rechCouleur(sommets[x].getVoisins(), sommetColor);


        if(couleur != -1){
            sommetColor[x]=couleur;
            sommets[x].setActive(true);
        }

        else{
            sommetColor = inverserCouleur(sommets[x], sommetsInit, sommetColor);
            sommetColor[x] = rechCouleur(sommets[x].getVoisins(), sommetColor);
            sommets[x].setActive(true);
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

    public static Integer [] inverserCouleur(Sommet x, Sommet[] sommets, Integer[] sommetColor) {
        Sommet premier=null;
        for(int som : x.getVoisins()){
            if (som != x.getNumero() && sommetColor[som]!=-1 && sommets[som].isActive()){
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
            if(sommetColor[i]!=-1 && i!=x.getNumero() && sommets[i].isActive()) {
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
        ArrayList<Integer> sommetsBoucle = new ArrayList<Integer>();

        while (!parcours.isEmpty()) {
            boucle = false;
            int sActu = parcours.get(0);
            for (int sVoisin : sommets[sActu].getVoisins()) {
                if (sVoisin != x.getNumero() && (sommets[sVoisin].isActive()) && (sommetColor[sVoisin] == couleurPremier || sommetColor[sVoisin] == couleurSecond) && visite[sVoisin] == false && (sVoisin != premier.getNumero()) && !compConnexe.contains(sVoisin)) {
                    parcours.add(sVoisin);
                    compConnexe.add(sVoisin);
                    visite[sVoisin]=true;
                    if (voisinsX.contains(sVoisin) || sommetsBoucle.contains(sVoisin)) {
                        boucle = true;
                        //sommetsBoucle.add(sVoisin);
                        sommetsBoucle.add(second.getNumero());
                    }
                }

            }
            parcours.remove(0);
            if (boucle) {
                parcours.clear();
                compConnexe.clear();
                int newS = -1;
                for(int ns : premier.getVoisins()){
                    if (!sommetsBoucle.contains(ns) && sommets[ns].isActive() && ns != x.getNumero() && sommetColor[ns] != couleurSecond) {
                        newS = ns;
                    }
                }


                if(newS == -1){
                    sommetsBoucle.add(premier.getNumero());
                    for (int ns : voisinsX) {
                        if (!sommetsBoucle.contains(ns) && ns != premier.getNumero() && sommets[ns].isActive() && ns != x.getNumero()) {
                            newS = ns;
                        }
                    }
                    premier = sommets[newS];
                    couleurPremier = sommetColor[newS];
                    int i=0;
                    while(i<premier.getVoisins().size() && sommets[premier.getVoisins().get(i)].isActive() && premier.getVoisins().get(i) != x.getNumero()){
                        second = sommets[premier.getVoisins().get(i)];
                        i++;
                    }

                    couleurSecond = sommetColor[second.getNumero()];

                }
                else{
                    second = sommets[newS];
                    couleurSecond = sommetColor[second.getNumero()];
                }
                parcours.add(premier.getNumero());
                compConnexe.add(premier.getNumero());
                for(int voisVisit =0; voisVisit < visite.length; voisVisit++){
                    visite[voisVisit] = false;
                }

            }

        }

        System.out.println("++++++++ " + x.getNumero() + " ++++++++");
        if(compConnexe.size() == 1){
            System.out.print("Unique : ");
            System.out.print(compConnexe.get(0) + " Ancienne couleur : " + sommetColor[compConnexe.get(0)]);
            sommetColor[compConnexe.get(0)] = (sommetColor[compConnexe.get(0)]+1) % 5;

            System.out.print(" Nouvelle couleur : " + sommetColor[compConnexe.get(0)]);
            System.out.print(" | ");
            for(int sv : sommets[compConnexe.get(0)].getVoisins()){
                if(sommets[sv].isActive())
                    System.out.print(" " + sv);
            }
            System.out.println();
        }
        else{
            for (int sommet : compConnexe) {

                System.out.print(sommet + " Ancienne couleur : " + sommetColor[sommet]);
                if (sommetColor[sommet] == couleurPremier) {
                    sommetColor[sommet] = couleurSecond;
                    System.out.print(" Nouvelle couleur : " + couleurSecond);
                } else {
                    sommetColor[sommet] = couleurPremier;
                    System.out.print(" Nouvelle couleur : " + couleurPremier);
                }
                System.out.println();

            }
        }

        System.out.println("++++++++++++++++++++++");

        return sommetColor;
    }
    /*public static Integer[] autreCouleur(ArrayList<Integer> voisins, Integer[] sommetColor, Graphe.Sommet[] sommets ){


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
