package src.VISTA;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import src.CONTROLADOR.controller.ControladorMenu;

public class VistaClasificaciones extends JPanel {

    public VistaClasificaciones(ControladorMenu controlador){

        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();

        try(BufferedReader br =
            new BufferedReader(
                new FileReader("clasificaciones.txt"))){

            String linea;

            while((linea = br.readLine()) != null){

                String[] datos = linea.split(";");

                area.append(
                    "Puntos: " + datos[0]
                    + " | Tiempo: "
                    + datos[1]
                    + " segundos\n"
                );
            }

        }catch(Exception e){
            area.setText("No hay clasificaciones.");
        }

        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton volver =
            new JButton("Volver");

        volver.addActionListener(controlador);

        add(volver, BorderLayout.SOUTH);
    }
}