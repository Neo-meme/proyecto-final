package src.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pellet {

    // ── Posición en el mapa (en píxeles) ──────────────────────────
    public int x, y;

    // ── Estado ────────────────────────────────────────────────────
    public boolean visible = true; // false cuando Pac-Man lo consume

    // ── Tamaño visual ─────────────────────────────────────────────
    public int size;

    // ── Constructor ───────────────────────────────────────────────
    public Pellet(int col, int row, int tileSize) {
        this.size = tileSize / 6;                          // pellet pequeño
        this.x = col * tileSize + (tileSize - size) / 2;  // centrado en el tile
        this.y = row * tileSize + (tileSize - size) / 2;
    }

    // ── Dibujo ────────────────────────────────────────────────────
    public void draw(Graphics2D g2) {
        if (visible) {
            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, size, size);
        }
    }
}