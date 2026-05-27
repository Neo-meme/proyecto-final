package src.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//keyListener es la interfaz que se encarga de escuchar las teclas que se presionan, se sueltan o se escriben

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed; // para saber si se esta presionando alguna de las teclas

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    // keyPressed se ejecuta cuando se presiona una tecla 
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        
    }

    // keyReleased se ejecuta cuando se suelta una tecla
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        
    }

    /*
        explicacion breve, se usa el booleano como un semaforo para saber si se esta presionando una tecla o no, 
        y asi poder mover al personaje en el juego por ejemplo, si upPressed es true, entonces el personaje se movera hacia arriba, 
        si downPressed es true, entonces el personaje se movera hacia abajo, y asi sucesivamente para las demas teclas.
    */
    
    
}