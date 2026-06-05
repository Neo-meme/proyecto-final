package src.VISTA;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.CONTROLADOR.controller.ControladorJuego;

import java.awt.*;

public class VistaSeleccionPersonaje extends JPanel {

    public VistaSeleccionPersonaje(ControladorJuego controlador){

        // Configuración principal
        this.setPreferredSize(new Dimension(896,768));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        // ---------------- TITULO ----------------

        JLabel titulo = new JLabel(
                "ESCOGE TU PACMAN",
                JLabel.CENTER
        );

        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 40)
        );

        titulo.setForeground(Color.WHITE);

        titulo.setBorder(
                new EmptyBorder(40,0,30,0)
        );

        this.add(titulo, BorderLayout.NORTH);

        // ---------------- PANEL CENTRAL ----------------

        JPanel panelBotones = new JPanel();

        panelBotones.setBackground(Color.BLACK);

        panelBotones.setLayout(
                new BoxLayout(
                        panelBotones,
                        BoxLayout.Y_AXIS
                )
        );

        // ---------- PACMAN CLASICO ----------

        JPanel panelClasico = new JPanel();
        panelClasico.setBackground(Color.BLACK);
        panelClasico.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pacmanClasico = new JLabel("◖");
        pacmanClasico.setForeground(Color.YELLOW);
        pacmanClasico.setFont(new Font("SansSerif", Font.BOLD, 32));

        JButton clasico = crearBoton("PACMAN CLÁSICO");
        clasico.setActionCommand("CLASICO");
        clasico.addActionListener(controlador);

        panelClasico.add(pacmanClasico);
        panelClasico.add(clasico);

        JLabel descClasico = crearDescripcion(
                "Equilibrio entre vidas y velocidad"
        );

        // ---------- PACMAN TANQUE ----------

        JPanel panelTanque = new JPanel();
        panelTanque.setBackground(Color.BLACK);
        panelTanque.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pacmanTanque = new JLabel("◖");
        pacmanTanque.setForeground(Color.GREEN);
        pacmanTanque.setFont(new Font("SansSerif", Font.BOLD, 32));

        JButton tanque = crearBoton("PACMAN TANQUE");
        tanque.setActionCommand("TANQUE");
        tanque.addActionListener(controlador);

        panelTanque.add(pacmanTanque);
        panelTanque.add(tanque);

        JLabel descTanque = crearDescripcion(
                "Más vidas pero mucho más lento"
        );

        // ---------- PACMAN VELOZ ----------

        JPanel panelVeloz = new JPanel();
        panelVeloz.setBackground(Color.BLACK);
        panelVeloz.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pacmanVeloz = new JLabel("◖");
        pacmanVeloz.setForeground(Color.CYAN);
        pacmanVeloz.setFont(new Font("SansSerif", Font.BOLD, 32));

        JButton veloz = crearBoton("PACMAN VELOZ");
        veloz.setActionCommand("VELOZ");
        veloz.addActionListener(controlador);

        panelVeloz.add(pacmanVeloz);
        panelVeloz.add(veloz);

        JLabel descVeloz = crearDescripcion(
                "Muy rápido pero solo una vida"
        );

        // ---------- AGREGAR COMPONENTES ----------

        panelBotones.add(panelClasico);
        panelBotones.add(Box.createRigidArea(new Dimension(0,8)));
        panelBotones.add(descClasico);

        panelBotones.add(Box.createRigidArea(new Dimension(0,25)));

        panelBotones.add(panelTanque);
        panelBotones.add(Box.createRigidArea(new Dimension(0,8)));
        panelBotones.add(descTanque);

        panelBotones.add(Box.createRigidArea(new Dimension(0,25)));

        panelBotones.add(panelVeloz);
        panelBotones.add(Box.createRigidArea(new Dimension(0,8)));
        panelBotones.add(descVeloz);

        this.add(panelBotones, BorderLayout.CENTER);

        // ---------------- BOTON VOLVER ----------------

        JButton volver = new JButton("Volver");

        volver.setActionCommand("Volver");

        volver.addActionListener(controlador);

        volver.setFont(
                new Font("SansSerif", Font.BOLD, 18)
        );

        JPanel panelInferior = new JPanel();

        panelInferior.setBackground(Color.BLACK);

        panelInferior.add(volver);

        this.add(panelInferior, BorderLayout.SOUTH);
    }

    // ------------------------------------------------
    // BOTONES
    // ------------------------------------------------

    private JButton crearBoton(String texto){

        JButton boton = new JButton(texto);

        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        boton.setFont(
                new Font("SansSerif", Font.BOLD, 22)
        );

        boton.setForeground(Color.LIGHT_GRAY);

        boton.setBackground(Color.BLACK);

        boton.setFocusPainted(false);

        boton.setPreferredSize(
                new Dimension(500,60)
        );

        boton.setMaximumSize(
                new Dimension(500,60)
        );

        boton.setBorder(
                BorderFactory.createLineBorder(
                        Color.DARK_GRAY,
                        3
                )
        );

        return boton;
    }

    // ------------------------------------------------
    // DESCRIPCIONES
    // ------------------------------------------------

    private JLabel crearDescripcion(String texto){

        JLabel label = new JLabel(texto);

        label.setForeground(Color.GRAY);

        label.setFont(
                new Font("SansSerif", Font.PLAIN, 16)
        );

        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }
}