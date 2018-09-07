package me.nether.polinvaders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class SomeApp extends JFrame {

    private static class ImagePanel extends JPanel {
        private BufferedImage currentImage;
        public BufferedImage getCurrentImage() {
            return currentImage;
        }
        @Override
        public void paint(Graphics g) {
            Rectangle tempBounds = g.getClipBounds();
            currentImage = new BufferedImage(tempBounds.width, tempBounds.height, BufferedImage.TYPE_INT_ARGB);
            super.paint(g);
            super.paint(currentImage.getGraphics());
        }
    }

    public SomeApp() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800,600);
        int matrixSize = 4;
        setLayout(new BorderLayout());
        add(new JLabel("Wonderful Application"), BorderLayout.NORTH);
        final ImagePanel imgPanel = new ImagePanel();
        imgPanel.setLayout(new GridLayout(matrixSize,matrixSize));
        for(int i=1; i<=matrixSize*matrixSize; i++) {
            imgPanel.add(new JButton("A Button" + i));
        }
        add(imgPanel, BorderLayout.CENTER);
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new AbstractAction("get image") {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SomeApp.this, new ImageIcon(imgPanel.getCurrentImage()));
            }

        }));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        System.setProperty("swing.defaultlaf", UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SomeApp().setVisible(true);
            }
        });
    }
}