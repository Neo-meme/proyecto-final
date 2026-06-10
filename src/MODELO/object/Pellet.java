package MODELO.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pellet {

    // ── Posicion en el mapa ──────────────────────────
    public int x, y;
    private int hudOffset;
    // ── Estado ────────────────────────────────────────────────────
    public boolean visible = true; // false cuando Pac-Man lo consume

    // ── Tamaño visual ─────────────────────────────────────────────
    public int size;

    // ── poderes pa el pacman  ─────────────────────────────────────────────

    public boolean isPower;   // pellet naranja — comer fantasmas
    public boolean isCereza;  // cereza — cronokinesis



    // ── Constructor ───────────────────────────────────────────────
    public Pellet(int col, int row, int tileSize, int hudHeight, boolean isPower) {
        this.isPower  = isPower;
        this.isCereza  = false;
        this.size     = isPower ? tileSize / 2 : tileSize / 6; // grande o pequeño

        //posicionamos el pellet en el centro del tile
        this.x        = col * tileSize + (tileSize - size) / 2;
        this.y        = row * tileSize + (tileSize - size) / 2;
        this.hudOffset = hudHeight;
    }

    // Constructor especial para la cereza
    public Pellet(int pixelX, int pixelY, int tileSize, int hudHeight) {
        this.isCereza  = true;
        this.isPower   = false;
        this.size      = tileSize / 2;
        this.x         = pixelX;
        this.y         = pixelY;
        this.hudOffset = hudHeight;
        this.visible   = true;
    }

    
    
    // ── Dibujo ────────────────────────────────────────────────────
    public void draw(Graphics2D g2) {

        if (!visible) return;

        if (isCereza) {
            dibujarCereza(g2);
        } else if (isPower) {
            // Pellet naranja — poder comer fantasmas
            g2.setColor(new Color(255, 140, 0));
            g2.fillOval(x, y + hudOffset, size, size);
        } else {
            // Pellet normal blanco
            g2.setColor(Color.WHITE);

            g2.fillOval(x, y + hudOffset, size, size);
        }
    }

    private void dibujarCereza(Graphics2D g2) {
        int fruitSize = size;
        int drawX = x;
        int drawY = y + hudOffset;

        // Tallos verdes
        g2.setColor(new Color(34, 139, 34));
        g2.drawLine(drawX + fruitSize/2, drawY + fruitSize/2,
                    drawX + fruitSize,   drawY - 4);
        g2.drawLine(drawX + fruitSize,   drawY - 4,
                    drawX + fruitSize + 8, drawY + fruitSize/2);

        // Cerezas rojas
        g2.setColor(Color.RED);
        g2.fillOval(drawX - 2,    drawY + 2, fruitSize, fruitSize);
        g2.fillOval(drawX + 8,    drawY + 2, fruitSize, fruitSize);

        // Brillo
        g2.setColor(Color.WHITE);
        g2.fillOval(drawX,        drawY + 5, 4, 4);
        g2.fillOval(drawX + 10,   drawY + 5, 4, 4);
    }
}
