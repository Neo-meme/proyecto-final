package VISTA;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import CONTROLADOR.controller.ControladorMenu;

public class VistaClasificaciones extends JPanel {

    public VistaClasificaciones(ControladorMenu controlador) {
        this.setPreferredSize(new Dimension(896, 768));
        this.setBackground(new Color(30, 20, 50));
        this.setLayout(new BorderLayout());

        // ── Título ────────────────────────────────────────────────
        JLabel titulo = new JLabel("SCORES", JLabel.CENTER);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 40));
        titulo.setForeground(new Color(255, 220, 50));
        titulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        this.add(titulo, BorderLayout.NORTH);

        // ── Panel de scores ───────────────────────────────────────
        JPanel panelScores = new JPanel();
        panelScores.setBackground(new Color(30, 20, 50));
        panelScores.setLayout(new BoxLayout(panelScores, BoxLayout.Y_AXIS));
        panelScores.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));

        try (BufferedReader br = new BufferedReader(new FileReader("clasificaciones.txt"))) {
            String linea;
            int posicion = 1;
            while ((linea = br.readLine()) != null && posicion <= 10) {
                String[] datos = linea.split(";");
                // formato MM:SS
                int seg  = Integer.parseInt(datos[1].trim());
                int min  = seg / 60;
                int segs = seg % 60;
                String tiempo = String.format("%02d:%02d", min, segs);

                String texto = String.format("#%d   Puntos: %s   Tiempo: %s", posicion, datos[0].trim(), tiempo);

                JLabel scoreLabel = new JLabel(texto);
                scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 22));
                scoreLabel.setForeground(posicion == 1
                    ? new Color(255, 220, 50)   // oro para el primero
                    : Color.WHITE);
                scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                scoreLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
                panelScores.add(scoreLabel);

                // Separador
                JSeparator sep = new JSeparator();
                sep.setForeground(new Color(80, 60, 120));
                sep.setMaximumSize(new Dimension(700, 1));
                panelScores.add(sep);

                posicion++;
            }
        } catch (Exception e) {
            JLabel vacio = new JLabel("No hay scores aún");
            vacio.setFont(new Font("Monospaced", Font.BOLD, 24));
            vacio.setForeground(Color.GRAY);
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelScores.add(vacio);
        }

        JScrollPane scroll = new JScrollPane(panelScores);
        scroll.setBackground(new Color(30, 20, 50));
        scroll.setBorder(BorderFactory.createLineBorder(
            new Color(255, 180, 0), 3));
        this.add(scroll, BorderLayout.CENTER);

        // ── Botón volver ──────────────────────────────────────────
        JButton volver = new JButton("Volver");      
        volver.setActionCommand("Volver"); 
        volver.setFont(new Font("Monospaced", Font.BOLD, 20));
        volver.setBackground(new Color(255, 180, 0));
        volver.setForeground(new Color(30, 20, 50));
        volver.setFocusPainted(false);
        volver.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        volver.addActionListener(controlador);

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(30, 20, 50));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));
        panelBoton.add(volver);
        this.add(panelBoton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Borde decorativo estilo Pac-Man
        g2.setColor(new Color(255, 180, 0));
        g2.setStroke(new BasicStroke(6));
        g2.drawRect(15, 15, getWidth() - 30, getHeight() - 30);

        g2.setColor(new Color(80, 60, 120));
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(25, 25, getWidth() - 50, getHeight() - 50);
    }
}
