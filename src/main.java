import graphe.IOGraphe;
import graphe.Sommet;
import view.Fenetre;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by amelie on 30/10/14.
 */
public class main {

    public static void main (String [] args){
        traitementArgs(args);
    }


    public static Integer[] coloration(Sommet[] sommets, Sommet[] sommetsInit, int nbSommetActif){
        int x = 0;
        Integer[] sommetColor= new Integer[sommets.length];

        //Initialisation des couleurs des sommets à vide
        if(nbSommetActif == 1){
            for(int i=0; i<sommets.length; i++) {
                sommetColor[i] = -1;
            }
        }

        //Recherche d'un sommet ayant au plus 5 voisins
        boolean trouve = false;
        Iterator<Integer> it;
        while(x<sommets.length && !trouve){
            Sommet xs = sommets[x];
            if(xs.isActive()){
                int nbVoisin = 0;
                ArrayList<Integer> voisins = xs.getVoisins();
                it = voisins.iterator();

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

        //Suppression du sommet choisis du graphe
        sommets[x].setActive(false);

        //Appel recurcif
        if(nbSommetActif>1)
            sommetColor = coloration(sommets, sommetsInit, nbSommetActif-1);

        sommets[x].setActive(true);

        //Recherche d'une couleur pour le sommet
        int couleur = rechCouleur(sommets[x].getVoisins(), sommetColor);

        //Attribution de la couleur trouvée ( si elle existe )
        if(couleur != -1){
            sommetColor[x]=couleur;
            sommets[x].setActive(true);
        }

        // Si pas de couleur trouvée, on cherche une composante connexe
        else{
            sommetColor = inverserCouleur(sommets[x], sommetsInit, sommetColor);
            sommetColor[x] = rechCouleur(sommets[x].getVoisins(), sommetColor);
            sommets[x].setActive(true);
        }

        return sommetColor;
    }

    //Renvois la plus petite couleur attribuable à un sommet ( par rapport à la couleur de ses voisins )
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

    //Recherche d'une composante connexe et inversion des couleurs afin de liberer une couleur pour le sommet x
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

        //Parcours du graphe
        while (!parcours.isEmpty()) {
            boucle = false;
            int sActu = parcours.get(0);

            //Si on trouve un voisin d'une des deux couleurs, on l'ajoute à la composante connexe
            for (int sVoisin : sommets[sActu].getVoisins()) {
                if (sVoisin != x.getNumero() && (sommets[sVoisin].isActive()) && (sommetColor[sVoisin] == couleurPremier || sommetColor[sVoisin] == couleurSecond) && visite[sVoisin] == false && (sVoisin != premier.getNumero()) && !compConnexe.contains(sVoisin)) {
                    parcours.add(sVoisin);
                    compConnexe.add(sVoisin);
                    visite[sVoisin]=true;
                    //Si le sommets ramène au sommet d'origine, on l'ajoute à la liste sommetBoucle afin de chercher une autre composante connexe
                    if (voisinsX.contains(sVoisin) || sommetsBoucle.contains(sVoisin)) {
                        boucle = true;
                        sommetsBoucle.add(second.getNumero());
                    }
                }

            }
            parcours.remove(0);
            //Recherche d'un nouveau depart pour la composante connèxe en cas de retour sur le sommet d'origine
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

        //Si la compossante connexe ne contient qu'un seul sommet, on change ça couleur
        if(compConnexe.size() == 1){
            sommetColor[compConnexe.get(0)] = (sommetColor[compConnexe.get(0)]+1) % 5;
        }

        //Sinon, on inverse les couleurs de chaque sommets de la composante connexe
        else{
            for (int sommet : compConnexe) {

                if (sommetColor[sommet] == couleurPremier) {
                    sommetColor[sommet] = couleurSecond;
                } else {
                    sommetColor[sommet] = couleurPremier;
                }

            }
        }


        return sommetColor;
    }

    public static void traitementArgs(String[] args){
        Sommet[] sl = null;
        boolean fileNotFound = false;
        if(args.length == 1){
            try {
                sl = IOGraphe.read(args[0]);
            } catch (FileNotFoundException e) {
                fileNotFound = true;
            }
        }
        else if(args.length == 2){
            try {
                sl = IOGraphe.read(args[0], args[1]);
            } catch (FileNotFoundException e) {
                fileNotFound = true;
            }
        }
        else{
            System.out.println("Utilisation : java -jar 5coloration.jar fichierGraphe [fichierPosition]");
            return;
        }

        if(fileNotFound){
            System.out.println("Erreur : Chemin de fichier invalide");
            return;
        }
        else{
            Integer[] color =  coloration(sl, sl, sl.length);
            for(int i=0; i<sl.length; i++){
                System.out.println(i + " : " + color[i]);
            }

            Fenetre fen;
            if(args.length == 2)
                fen = new Fenetre(sl, color);
        }
    }
}
