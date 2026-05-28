package src.collision;

import src.entity.Entity;
import src.menu.GamePanel;

public class CollisionChecker{
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
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
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tileSet[tileNum1].collision ||
                    gp.tileM.tileSet[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
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