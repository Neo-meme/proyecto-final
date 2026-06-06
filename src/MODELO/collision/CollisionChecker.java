package src.MODELO.collision;

import src.MODELO.entity.Entity;
import src.Games.GamePanel;

import java.awt.Rectangle;

public class CollisionChecker{
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    //Creamos un metodo para la colision entre personajes 
    public boolean checkEntityCollision(Entity pacman, Entity fantasma){
        // Calcular la posicion real del rectangle del pacman en el mapa
        int pacmanX = pacman.x + pacman.hitBox.x;
        int pacmanY = pacman.y + pacman.hitBox.y;
        Rectangle rectPacman = new Rectangle(pacmanX, pacmanY, pacman.hitBox.width, pacman.hitBox.height);

        //calculamos la posicion real del rectangle del fantasma en el mapa
        int fantasmaX = fantasma.x + fantasma.hitBox.x;
        int fantasmaY = fantasma.y + fantasma.hitBox.y;
        Rectangle rectFantasma = new Rectangle(fantasmaX, fantasmaY, fantasma.hitBox.width, fantasma.hitBox.height);

        //Devolvemos true si se tocan o false si no se tocan para activar el estado de juego 1
        return rectPacman.intersects(rectFantasma);
    }




    public void checkTile(Entity entity){
        // ── Coordenadas absolutas del hitbox en píxeles ───────────
        int entityLeftX   = entity.x + entity.hitBox.x;
        int entityRightX  = entity.x + entity.hitBox.x + entity.hitBox.width;
        int entityTopY    = entity.y + entity.hitBox.y;
        int entityBottomY = entity.y + entity.hitBox.y + entity.hitBox.height;

        // ── Convertir píxeles a número de columna/fila del mapa ───
        int entityLeftCol   = entityLeftX   / gp.tileSize;
        int entityRightCol  = entityRightX  / gp.tileSize;
        int entityTopRow    = entityTopY    / gp.tileSize;
        int entityBottomRow = entityBottomY / gp.tileSize;

        // ── Guard: si está fuera del mapa no verificar colision ───────
        if (entityLeftCol < 0 || entityRightCol >= gp.maxScreenCol ||
            entityTopRow  < 0 || entityBottomRow >= gp.maxScreenRow) {
            return;
        }

        // ── Tiles que el hitbox toca según la dirección ───────────
        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tileSet[tileNum1].collision ||
                    gp.tileM.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                }   
            }
            case "down" -> {
                entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                 if (gp.tileM.tileSet[tileNum1].collision ||
                    gp.tileM.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                
            }
            case "left" -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;

                if(entityLeftCol < 0){
                    return;
                }

                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tileSet[tileNum1].collision ||
                    gp.tileM.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;

                if(entityRightCol >= gp.maxScreenCol){
                    return;
                }
                
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tileSet[tileNum1].collision ||
                    gp.tileM.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }
}