package src.MODELO.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pellet {

    // ── Posición en el mapa (en píxeles) ──────────────────────────
    public int x, y;
    private int hudOffset;
    // ── Estado ────────────────────────────────────────────────────
    public boolean visible = true; // false cuando Pac-Man lo consume

    // ── Tamaño visual ─────────────────────────────────────────────
    public int size;

    // ── poderes pa el pacman  ─────────────────────────────────────────────
    public boolean isPower; // true = power pellet (punto grande)

    // ── Constructor ───────────────────────────────────────────────
    public Pellet(int col, int row, int tileSize, int hudHeight, boolean isPower) {
        this.isPower  = isPower;
        this.size     = isPower ? tileSize / 2 : tileSize / 6; // grande o pequeño
        this.x        = col * tileSize + (tileSize - size) / 2;
        this.y        = row * tileSize + (tileSize - size) / 2;
        this.hudOffset = hudHeight;
    }

    
    
    // ── Dibujo ────────────────────────────────────────────────────
    public void draw(Graphics2D g2) {
        if (visible) {
            g2.setColor(isPower ? Color.ORANGE : Color.WHITE); // naranja para power pellets
            g2.fillOval(x, y+ hudOffset, size, size);
        }
    }
}