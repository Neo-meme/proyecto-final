package src.CONTROLADOR.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

import src.VISTA.MenuPrincipal;
import src.VISTA.PanelPrincipal;
import src.VISTA.VistaJuego;

public class ControladorJuego implements ActionListener{
        private MenuPrincipal ventanaPrincipal ;
        private VistaJuego vistaJuego ;

        public ControladorJuego(MenuPrincipal ventanaPrincipal){
                this.ventanaPrincipal = ventanaPrincipal;
        }

        public void iniciarPartida(){
            //Instanciamos la vista del laberinto 
            this.vistaJuego = new VistaJuego(this);

            //Le ordenamos a la ventana principal el cambio de pantalla a la vista del juego
            ventanaPrincipal.CambiarPantalla(vistaJuego);
            // Esperar a que Swing termine de renderizar antes de ajustar y arrancar
            SwingUtilities.invokeLater(() -> {
                vistaJuego.iniciarJuego(); // arranca el juego después de ajustar el tamaño para evitar problemas de renderizado
            });
        }

            @Override
        public void actionPerformed(ActionEvent e) {
            // e.getActionCommand() obtiene exactamente el texto del botón que fue presionado
            
            
            String botonPulsado = e.getActionCommand();
            switch (botonPulsado) {
                case "Volver":
                        ControladorMenu nuevoControladorMenu = new ControladorMenu();
                        PanelPrincipal panelPrincipal = new PanelPrincipal(nuevoControladorMenu);
                        nuevoControladorMenu.setVistaPrincipal(ventanaPrincipal);
                        ventanaPrincipal.CambiarPantalla(panelPrincipal);
                    break;
            }
        }
}