package view;

import javax.swing.*;
import Graphe.*;

import java.awt.*;

/**
 * Created by ten on 03/12/14.
 */
public class Fenetre extends JFrame {
    Canvas c;
    JScrollPane jsp;

    public Fenetre(Sommet[] sommets, Integer[] colors){
        super();

        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.c = new Canvas(sommets, colors);
        this.jsp = new JScrollPane(c);
        jsp.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        this.getContentPane().add(jsp, BorderLayout.CENTER);
        this.add(jsp, BorderLayout.CENTER);
        this.setVisible(true);
    }

}
