package src.MODELO.entity;

import src.Games.GamePanel;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

/**
 * CLASE: Fantasma (Enemigos)
 * Hereda de Entity.Implementa el movimiento de los fantasmas y colision de las paredes
 */
public class Fantasma extends Entity {
    
    GamePanel gp;
    private Random random; // Generador para decisiones aleatorias de movimiento
    
    // Cada fantasma tiene su propio color y posicion de nacimiento independiente
    private Color colorFantasma; 
    private int startX;
    private int startY;

    public Fantasma(GamePanel gp, Color colorFantasma, int startX, int startY) {
        this.gp = gp;
        this.random = new Random();
        this.colorFantasma = colorFantasma; 
        this.startX = startX;               
        this.startY = startY;               

        // Hitbox similar al de Pac-Man para que pasen por los mismos pasillos
        int drawSize = (int)(gp.tileSize * 0.5);  
        int offset   = (gp.tileSize - drawSize) / 2; 

        hitBox = new Rectangle(offset, offset, drawSize, drawSize);
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        setDefaultValues();
    }

    @Override
    public void setDefaultValues() {
        x = startX; 
        y = startY; 
        speed = 1; // Mas lentos que Pacman para dar oportunidad al jugador
        direction = "up"; 
    }

    @Override
    public void update() {
        checkTunnel();

        collisionOn = false;
        gp.cManager.checkTile(this); 

        // Si choca contra una pared, escoge una nueva dirección al azar.
        if (collisionOn) {
            cambiarDireccionAleatoria();
        } else {
            // Si el camino esta libre, sigue avanzando en su direccion actual
            switch (direction) {
                case "up"    -> y -= speed;
                case "down"  -> y += speed;
                case "left"  -> x -= speed;
                case "right" -> x += speed;
            }
        }

        // Animación visual de los fantasmas (ojos que se mueven)
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
        
    }

    /**
     * Selecciona una direccion aleatoria (Arriba, Abajo, Izquierda, Derecha).
     */
    private void cambiarDireccionAleatoria() {
        int opcion = random.nextInt(4); // Genera un número del 0 al 3
        switch (opcion) {
            case 0 -> direction = "up";
            case 1 -> direction = "down";
            case 2 -> direction = "left";
            case 3 -> direction = "right";
        }
    }

    // Los fantasmas tambien pueden usar los tuneles laterales
    private void checkTunnel() {
        if (x + gp.tileSize < 0) {
            x = gp.ScreenWidth - gp.tileSize;
        }
        if (x > gp.ScreenWidth) {
            x = -gp.tileSize;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int drawSize = (int)(gp.tileSize * 0.5);
        int offset   = (gp.tileSize - drawSize) / 2;
        int drawX    = x + offset;
        int drawY    = y + offset + gp.hudHeight; // ajustar por espacio del HUD

        //nUEVO: VERIFICA SI SE PUEDEN COMER LOS FANTASMAS
        if (gp.fantasmasVulnerables) {
            g2.setColor(Color.BLUE); // Si están asustados, se pintan de azul
        } else {
            g2.setColor(colorFantasma); // Si no, usan su color normal (Rojo, Rosa, Cyan)
        }

        // Dibuja el cuerpo base del fantasma (mitad ovalo, mitad rectángulo)
        g2.fillOval(drawX, drawY, drawSize, drawSize / 2 + 2);
        g2.fillRect(drawX, drawY + drawSize / 2, drawSize, drawSize / 2);

        // OJOS
        g2.setColor(Color.WHITE);
        int ojoSize = drawSize / 4;
        
        // Nuevo: Ojos cuando esta asustado
        if (gp.fantasmasVulnerables) {
            // Ojos de "asustado" (más pequeños o diferentes)
            g2.setColor(Color.ORANGE); // Un toque clásico es poner detalles naranjas/amarillos
            g2.fillRect(drawX + 4, drawY + 6, ojoSize, ojoSize / 2);
            g2.fillRect(drawX + drawSize - 8, drawY + 6, ojoSize, ojoSize / 2);
        } else {
            // Ojos normales
            if (spriteNum == 1) {
                g2.fillOval(drawX + 3, drawY + 4, ojoSize, ojoSize);
                g2.fillOval(drawX + drawSize - 7, drawY + 4, ojoSize, ojoSize);
            } else {
                g2.fillOval(drawX + 5, drawY + 3, ojoSize, ojoSize);
                g2.fillOval(drawX + drawSize - 9, drawY + 3, ojoSize, ojoSize);
            }
        }
    }
}