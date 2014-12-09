package view;

import graphe.Sommet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by ten on 03/12/14.
 */
public class Canvas extends JPanel{
    private Integer[] colors;
    private Sommet[] sommets;
    public static Color COLOR[] = {Color.RED, Color.BLUE, Color.ORANGE, Color.BLACK, Color.GREEN};
    private double maxX;
    private double maxY;

    public Canvas(Sommet[] sommets, Integer[] colors){
        this.colors = colors;
        this.sommets = sommets;
    }


    //Affichage des sommets par rapport aux position du fichier passé en paramètre du programme
    @Override
    public void paintComponent(Graphics g) {
        int sizeX = (this.getWidth()-40);
        int sizeY = (this.getHeight()-40);
        int propX = Math.max(sizeX / this.sommets.length, 1);
        int propY = Math.max(sizeY / this.sommets.length, 1);
        this.setPreferredSize(new Dimension(propX*this.sommets.length + 40, propY*this.sommets.length + 40));


        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        for(Sommet s : sommets){
            g2d.setColor(COLOR[colors[s.getNumero()]]);
            Ellipse2D.Double circle = new Ellipse2D.Double((s.getPosx()*propX), (s.getPosy()*propY), 10, 10);
            g2d.drawString(String.valueOf(s.getNumero()), (s.getPosx()*propX), (s.getPosy()*propY));

            g2d.fill(circle);
            g2d.setColor(Color.BLACK);
            for(Integer i : s.getVoisins()){
                g2d.drawLine((s.getPosx()*propX) + 10/2,(s.getPosy()*propY)+ 10/2,(sommets[i].getPosx()*propX)+ 10/2,(sommets[i].getPosy()*propY)+ 10/2);
            }
        }
    }


}
