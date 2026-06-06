package src.VISTA;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import src.CONTROLADOR.controller.ControladorMenu;

public class VistaInstrucciones extends JPanel {

    private BufferedImage imagenInstrucciones;

    public VistaInstrucciones(ControladorMenu controlador) {
        this.setPreferredSize(new Dimension(896, 768));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        // Cargar imagen
        try {
            imagenInstrucciones = ImageIO.read(
                getClass().getResourceAsStream("/resources/cosas/instrucciones.png")
            );
        } catch (Exception e) {
            System.out.println("No se pudo cargar instrucciones.png");
        }

        // Panel para la imagen
        JPanel panelImagen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenInstrucciones != null) {
                    g.drawImage(imagenInstrucciones, 0, 0,
                        getWidth(), getHeight(), null);
                }
            }
        };
        panelImagen.setBackground(Color.BLACK);
        this.add(panelImagen, BorderLayout.CENTER);

        // Botón volver
        JButton volver = new JButton("← VOLVER");
        volver.setActionCommand("Volver"); 
        volver.setFont(new Font("Monospaced", Font.BOLD, 18));
        volver.setBackground(new Color(255, 180, 0));
        volver.setForeground(new Color(30, 20, 50));
        volver.setFocusPainted(false);
        volver.addActionListener(controlador);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(Color.BLACK);
        panelBoton.add(volver);
        this.add(panelBoton, BorderLayout.SOUTH);
    }
}