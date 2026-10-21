import items.materials.Material;
import items.materials.MaterialManager;

import javax.swing.*;
import java.awt.*;


public class Main {

    public static final String LANGUAGE = "en";
    public String language() {
        return LANGUAGE;
    }

    public static final JFrame frame = new JFrame("Shopping Aplication");

    public static void main(String[] args) {
//        Sets up the window
        setup();

//        Updates variables
        update();

//        Draws the screen
        draw();

    }

    private static void update() {

    }

    private static void draw() {

    }

    private static void setup() {

        classInitializations();

        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu materials = new JMenu("Materials");

        for(Material m : MaterialManager.materials.getValueList().toArray(new Material[0])) {
            JMenuItem material = new JMenuItem(m.getName());

            materials.add(material);
        }

        menuBar.add(materials);
        frame.setJMenuBar(menuBar);



        frame.setVisible(true);
    }

    private static void classInitializations() {
        MaterialManager.init();
    }
}