package src.menu;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import src.input.KeyHandler;

public class GamePanel extends JPanel implements Runnable {

    //--------------------- configuracion de pantalla ---------------------

    final int originalTileSize =16;     // 16*16 tamaño base del tile en pixeles 
    final int scale =3;                // factor de escala

    final int tileSize = originalTileSize * scale; // 48*48 px por tile
    final int maxScreenCol = 16; 
    final int maxScreenRow = 12;
    final int ScreenWidth = tileSize * maxScreenCol; // 768 pixeles 
    final int ScreenHeight = tileSize * maxScreenRow; // 576 pixeles

    // --------------------- fin de configuracion de pantalla ---------------------

    //----------- configuracion del loop -----------
    final int FPS = 60;
    Thread gameThread;
    //----------- fin de configuracion del loop -----------

    // ------------- input de movimiento -------------
        KeyHandler keyH = new KeyHandler();
    // ------------- fin de input de movimiento -------------


    //---------------------- constructor ---------------------
    public GamePanel(){
        this.setPreferredSize (new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
    }
    //---------------------- fin de constructor ---------------------


    //-------------- arranque del hilo del juego --------------
    public void startGameThread(){
        gameThread = new Thread (this);
        gameThread.start();
    }
    //-------------- fin de arranque del hilo del juego --------------

    //--------------  Game loop (se ejecuta en el hilo) --------------
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 0.0166666667 segundos por frame
        double nextDrawTime = System.nanoTime() + drawInterval;
    
        while (gameThread != null){
            update();           // 1. actualizar lógica
            repaint();          // 2. redibujar pantalla
        
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // convertir a milisegundos
    
                if (remainingTime < 0) {
                    remainingTime = 0; // si el tiempo restante es negativo, no espera
                }
    
                Thread.sleep((long) remainingTime);
    
                nextDrawTime += drawInterval; // programar el próximo frame
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
     }
    //--------------  fin de Game loop (se ejecuta en el hilo) --------------

    // ── Lógica del juego (se llenará después) ─────────────────────
    public void update() {
        // aquí irá: mover Pac-Man, detectar colisiones, etc.
    }

    // ── Renderizado ───────────────────────────────────────────────
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // aquí irá: dibujar mapa, Pac-Man, HUD, etc.

        g2.dispose(); // libera recursos del objeto gráfico
    }
}

