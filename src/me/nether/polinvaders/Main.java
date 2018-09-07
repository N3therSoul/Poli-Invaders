package me.nether.polinvaders;

import me.nether.polinvaders.drawing.Display;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final int WIDTH = 900, HEIGHT = 1000;
    public static final Display DISPLAY = new Display();

    public static final JFrame MAIN_FRAME = new JFrame();

    public static boolean paused = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MAIN_FRAME.setVisible(true);
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    MAIN_FRAME.setTitle("Poli Invaders");
                    MAIN_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    MAIN_FRAME.setResizable(false);
                    MAIN_FRAME.setVisible(true);
                    MAIN_FRAME.setBounds(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2, WIDTH, HEIGHT);

                    MAIN_FRAME.add(Main.DISPLAY);
                    MAIN_FRAME.addKeyListener(Main.DISPLAY);

                    Main.DISPLAY.setVisible(true);
                    Main.DISPLAY.init();
                    Thread gameThread = new Thread(Main.DISPLAY);
                    gameThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


