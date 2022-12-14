package Game;

import Entity.Entity;

/**
 * This class is for checking Collision.
 * @author Ryan
 * @author Jason
 */
public class CollisionChecker {

    GamePanel gp;

    /**
     * This method is constructor of CollisionChecker class.
     * @param gp GamePanel object.
     */
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * This method is for checking player collided with tile.
     * @param e Entity object.
     */
    public void checkTile(Entity e) {
        //get top left coords of entity hitbox
        int leftX = e.worldX + e.hitBox.x;
        int topY = e.worldY + e.hitBox.y;

        //get bottom right coords of entity hitbox
        int rightX = e.worldX + e.hitBox.x + e.hitBox.width;
        int bottomY = e.worldY + e.hitBox.y + e.hitBox.height;

        //get block to the left
        int leftCol = leftX/gp.tileSize;
        int rightCol = rightX/gp.tileSize;
        int topRow = topY/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileA, tileB;
        switch (e.direction) {
            case "up" -> {
                //"predict" which tile player will be in the next 60 frames when UP key is pressed
                topRow = (topY - e.speed) / gp.tileSize;

                /*
                    if the tiles above player's top left OR top right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[rightCol][topRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
            case "down" -> {
                //"predict" which tile player will be in the next 60 frames when DOWN key is pressed
                bottomRow = (bottomY + e.speed) / gp.tileSize;

                /*
                    if the tiles below player's bottom left OR bottom right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][bottomRow];
                tileB = gp.tileManager.mapTileNum[rightCol][bottomRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
            case "left" -> {
                //"predict" which tile player will be in the next 60 frames when LEFT key is pressed
                leftCol = (leftX - e.speed) / gp.tileSize;

                /*
                    if the tiles to the left of player's top left OR bottom left hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[leftCol][topRow];
                tileB = gp.tileManager.mapTileNum[leftCol][bottomRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
            case "right" -> {
                //"predict" which tile player will be in the next 60 frames when RIGHT key is pressed
                rightCol = (rightX + e.speed) / gp.tileSize;

                /*
                    if the tiles to the right of player's top right OR bottom right hitbox coords
                    has collision set to on, player's collision var is set to true
                */
                tileA = gp.tileManager.mapTileNum[rightCol][topRow];
                tileB = gp.tileManager.mapTileNum[rightCol][bottomRow];
                if (gp.tileManager.tile[tileA].collision ||
                        gp.tileManager.tile[tileB].collision) {
                    e.collisionDetected = true;
                }
            }
        }
    }

    /**
     * This method is for checking player collided with Spaceship.
     * @param entity Entity object.
     * @param player Whether players collide with Spaceship.
     * @return If index is 999, character haven't collided with any object.
     */
    public int checkSpaceshipPart(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i  < gp.spaceshipPart.length; i++) {
            if(gp.spaceshipPart[i] != null) {

                // Get entity's solid area position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's solid area position
                gp.spaceshipPart[i].hitBox.x = gp.spaceshipPart[i].objectX + gp.spaceshipPart[i].hitBox.x;
                gp.spaceshipPart[i].hitBox.y = gp.spaceshipPart[i].objectY + gp.spaceshipPart[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gp.spaceshipPart[i].hitBox)) {
                    if (gp.spaceshipPart[i].collision) {
                        entity.collisionDetected = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                gp.spaceshipPart[i].hitBox.x = gp.spaceshipPart[i].hitBoxDefaultX;
                gp.spaceshipPart[i].hitBox.y = gp.spaceshipPart[i].hitBoxDefaultY;
            }
        }
        return index;
    }

    /**
     * This method is for checking player collided with Diamond.
     * @param entity Entity object.
     * @param player Whether players collide with Diamond.
     * @return If index is 999, character haven't collided with any object.
     */
    public int checkDiamond(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i  < gp.diamond.size(); i++) {
            if(gp.diamond.get(i) != null) {

                // Get entity's solid area position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's solid area position
                gp.diamond.get(i).hitBox.x = gp.diamond.get(i).objectX + gp.diamond.get(i).hitBox.x;
                gp.diamond.get(i).hitBox.y = gp.diamond.get(i).objectY + gp.diamond.get(i).hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gp.diamond.get(i).hitBox)) {
                    if (gp.diamond.get(i).collision) {
                        entity.collisionDetected = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                gp.diamond.get(i).hitBox.x = gp.diamond.get(i).hitBoxDefaultX;
                gp.diamond.get(i).hitBox.y = gp.diamond.get(i).hitBoxDefaultY;
            }
        }
        return index;
    }

    /**
     * This method is for checking player collided with Blackhole.
     * @param entity Entity object.
     * @param player Whether players collide with Blackhole.
     * @return If index is 999, character haven't collided with any object.
     */
    public int checkBlackhole(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i  < gp.blackhole.length; i++) {
            if(gp.blackhole[i] != null) {

                // Get entity's solid area position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's solid area position
                gp.blackhole[i].hitBox.x = gp.blackhole[i].objectX + gp.blackhole[i].hitBox.x;
                gp.blackhole[i].hitBox.y = gp.blackhole[i].objectY + gp.blackhole[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gp.blackhole[i].hitBox)) {
                    if (gp.blackhole[i].collision) {
                        entity.collisionDetected = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                gp.blackhole[i].hitBox.x = gp.blackhole[i].hitBoxDefaultX;
                gp.blackhole[i].hitBox.y = gp.blackhole[i].hitBoxDefaultY;
            }
        }
        return index;
    }

    /**
     * This method is for checking player collided with door.
     * @param entity Entity object.
     * @param player Whether players collide with door.
     * @return If index is 999, character haven't collided with any object.
     */
    public int checkWinningDoor(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.openedDoor.length; i++) {
            if (gp.openedDoor[i] != null) {

                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                gp.openedDoor[i].hitBox.x = gp.openedDoor[i].objectX + gp.openedDoor[i].hitBox.x;
                gp.openedDoor[i].hitBox.y = gp.openedDoor[i].objectY + gp.openedDoor[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(gp.openedDoor[i].hitBox)) {
                    if (gp.openedDoor[i].collision) {
                        entity.collisionDetected = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                gp.openedDoor[i].hitBox.x = gp.openedDoor[i].hitBoxDefaultX;
                gp.openedDoor[i].hitBox.y = gp.openedDoor[i].hitBoxDefaultY;
            }
        }
        return index;
    }

    /**
     * This method is for Entity collision.
     * @param entity Entity object.
     * @param target Entity array.
     * @return If index is 999, character haven't collided with any object.
     */
    // Entity collision
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i  < target.length; i++) {
            if(target[i] != null) {

                // Get entity's solid area position
                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                // Get object's solid area position
                target[i].hitBox.x = target[i].worldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].worldY + target[i].hitBox.y;

                switch (entity.direction) {
                    case "up" -> entity.hitBox.y -= entity.speed;
                    case "down" -> entity.hitBox.y += entity.speed;
                    case "left" -> entity.hitBox.x -= entity.speed;
                    case "right" -> entity.hitBox.x += entity.speed;
                }
                if (entity.hitBox.intersects(target[i].hitBox)) {
                    if (target[i] != entity) {
                        entity.collisionDetected = true;
                        index = i;
                    }
                }
                entity.hitBox.x = entity.solidAreaDefaultX;
                entity.hitBox.y = entity.solidAreaDefaultY;
                target[i].hitBox.x = target[i].solidAreaDefaultX;
                target[i].hitBox.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * This method is for checking Player collision.
     * @param entity Entity object.
     * @return Whether to collide.
     */
    public boolean checkPlayer(Entity entity) {
        boolean collidedPlayer = false;
        // Get entity's solid area position
        entity.hitBox.x = entity.worldX + entity.hitBox.x;
        entity.hitBox.y = entity.worldY + entity.hitBox.y;

        // Get object's solid area position
        gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;

        switch (entity.direction) {
            case "up" -> entity.hitBox.y -= entity.speed;
            case "down" -> entity.hitBox.y += entity.speed;
            case "left" -> entity.hitBox.x -= entity.speed;
            case "right" -> entity.hitBox.x += entity.speed;
        }
        if (entity.hitBox.intersects(gp.player.hitBox)) {
            if (gp.player != entity) {
                collidedPlayer = true;
                entity.collisionDetected = true;
            }
        }
        entity.hitBox.x = entity.solidAreaDefaultX;
        entity.hitBox.y = entity.solidAreaDefaultY;
        gp.player.hitBox.x = gp.player.solidAreaDefaultX;
        gp.player.hitBox.y = gp.player.solidAreaDefaultY;
        return collidedPlayer;
    }

}
