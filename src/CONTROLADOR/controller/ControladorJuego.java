package CONTROLADOR.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

import VISTA.MenuPrincipal;
import VISTA.PanelPrincipal;
import VISTA.VistaJuego;
import MODELO.entity.TipoPacman;

public class ControladorJuego implements ActionListener{
        private MenuPrincipal ventanaPrincipal ;
        private VistaJuego vistaJuego ;
        private TipoPacman tipoSeleccionado;

        public ControladorJuego(MenuPrincipal ventanaPrincipal){
                this.ventanaPrincipal = ventanaPrincipal;
                this.tipoSeleccionado = TipoPacman.CLASICO;
        }

        public void iniciarPartida(){
            //Instanciamos la vista del laberinto 
            this.vistaJuego = new VistaJuego(this,tipoSeleccionado);

            //Le ordenamos a la ventana principal el cambio de pantalla a la vista del juego
            ventanaPrincipal.CambiarPantalla(vistaJuego);
            // Esperar a que Swing termine de renderizar antes de ajustar y arrancar
            SwingUtilities.invokeLater(() -> {
                vistaJuego.iniciarJuego(); // arranca el juego después de ajustar el tamaño para evitar problemas de renderizado
            });
        }

        public void setTipoPacman(TipoPacman tipo){
            this.tipoSeleccionado = tipo;
        }

            @Override
        public void actionPerformed(ActionEvent e) {
            // e.getActionCommand() obtiene exactamente el texto del botón que fue presionado
            
            
            String botonPulsado = e.getActionCommand();
            switch (botonPulsado) {
                case "CLASICO":

                    tipoSeleccionado = TipoPacman.CLASICO;
                    iniciarPartida();

                    break;

                case "TANQUE":

                    tipoSeleccionado = TipoPacman.TANQUE;
                    iniciarPartida();

                    break;

                case "VELOZ":

                    tipoSeleccionado = TipoPacman.VELOZ;
                    iniciarPartida();

                    break;
                    
                case "Volver":
                        ControladorMenu nuevoControladorMenu = new ControladorMenu();
                        PanelPrincipal panelPrincipal = new PanelPrincipal(nuevoControladorMenu);
                        nuevoControladorMenu.setVistaPrincipal(ventanaPrincipal);
                        ventanaPrincipal.CambiarPantalla(panelPrincipal);
                    break;
            }
        }
}
