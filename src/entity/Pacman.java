package src.entity;

import src.input.KeyHandler;
import src.Games.GamePanel;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class Pacman extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    // Constructor
    public Pacman(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // hitbox proporcional al tamaño visual (50% del tile)
        int drawSize = (int)(gp.tileSize * 0.5);  // 24px
        int offset   = (gp.tileSize - drawSize) / 2; // 12px de margen

        hitBox = new Rectangle(offset, offset, drawSize, drawSize);
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        setDefaultValues();
    }

    // ----- posicion y velocidad iniciales -----
    @Override
    public void setDefaultValues() {
        x = gp.tileSize * 18; // Posición inicial en el centro del mapa
        y = gp.tileSize * 11;
        speed = 3; // Velocidad de movimiento
        direction = "arriba"; // Dirección inicial
    }

    //---- logica de movimiento y animación -----
    @Override
    public void update() {
        // 1. Leer input del teclado
        if (keyH.upPressed)    direction = "up";
        if (keyH.downPressed)  direction = "down";
        if (keyH.leftPressed)  direction = "left";
        if (keyH.rightPressed) direction = "right";
        // 2. Verificar colisión con paredes
        collisionOn = false;
        gp.cManager.checkTile(this); // verifica colisión y actualiza collisionOn

        //3 moverse solo si no hay colisión
        if (!collisionOn) {
            switch (direction) {
                case "up"    -> y -= speed;
                case "down"  -> y += speed;
                case "left"  -> x -= speed;
                case "right" -> x += speed;
            }
        }

        // 4. Animación: alternar sprite cada 10 frames
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }

    }

    // bibujo del personaje en pantalla
    @Override
    public void draw(Graphics2D g2) {

        // ── Tamaño visual de Pac-Man (70% del tile) ───────────────────
        int drawSize = (int)(gp.tileSize * 0.5);
        int offset   = (gp.tileSize - drawSize) / 2;
        int drawX    = x + offset;
        int drawY    = y + offset;


        // Temporalmente dibujamos un círculo amarillo
        g2.setColor(Color.YELLOW);

        // Animación simple: boca abierta vs cerrada
        if (spriteNum == 1) {
            // Boca abierta — arco de 300 grados
            g2.fillArc(drawX, drawY, drawSize, drawSize, getStartAngle(), 300);
        } else {
            // Boca cerrada — círculo completo
            g2.fillOval(drawX, drawY, drawSize, drawSize);
        }
    }

    // ── Ángulo de inicio según dirección (para la boca) ───────────
    private int getStartAngle() {
        return switch (direction) {
            case "right" -> 30;
            case "left"  -> 210;
            case "up"    -> 120;
            case "down"  -> 300;
            default      -> 30;
        };
    }


}