package Loader;

import EntityObjects.*;
import GameObjects.*;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GraphicsLoader {

    int Level = 1; //Level player

    public void load() {
        this.loadGraphics(); //call loadGraphics
        this.loadSprites(Level); //call loadSprites
    }

    public void loadGraphics() {
        //declare image path in some class
        try {
            Ground.img = ImageIO.read(new File("src/Graphics/Block.png"));
            Pipe.img = ImageIO.read(new File("src/Sprites/Pipe.png"));
            Trampoline.img = ImageIO.read(new File("src/Sprites/Trampoline.png"));
            Princess.img = ImageIO.read(new File("src/Sprites/Princess.png"));
        } catch (Exception e) {}
    }

    public void loadSprites(int Level) {//declare path image of some animation sprite

        try {
            Player.idleAnimation = new BufferedImage[1];
            Player.jumpingAnimation = new BufferedImage[1];
            Player.dieAnimation = new BufferedImage[1];
            Player.walkingAnimation = new BufferedImage[3];

            if (Level == 1) {
                Player.idleAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level1_idle.png"));
                Player.jumpingAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level1_jump.png"));
                Player.dieAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level1_die.png"));

                BufferedImage playerwalking = ImageIO.read(new File("src/Sprites/Mario_Level1_walk.png"));
                for (int i = 0; i < 3; i++) { //num of loop is num of picture
                    Player.walkingAnimation[i] = playerwalking.getSubimage(i * 64, 0, 64, 64);
                }
            } else if (Level == 2) {
                Player.idleAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level2_idle.png"));
                Player.jumpingAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level2_jump.png"));
                Player.dieAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level2_die.png"));

                BufferedImage playerwalking = ImageIO.read(new File("src/Sprites/Mario_Level2_walk.png"));
                for (int i = 0; i < 3; i++) { //num of loop is num of picture
                    Player.walkingAnimation[i] = playerwalking.getSubimage(i * 64, 0, 64, 64);
                }
            } else if (Level == 3) {
                Player.idleAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level3_idle.png"));
                Player.jumpingAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level3_jump.png"));
                Player.dieAnimation[0] = ImageIO.read(new File("src/Sprites/Mario_Level3_die.png"));

                BufferedImage playerwalking = ImageIO.read(new File("src/Sprites/Mario_Level3_walk.png"));
                for (int i = 0; i < 3; i++) { //num of loop is num of picture
                    Player.walkingAnimation[i] = playerwalking.getSubimage(i * 64, 0, 64, 64);
                }
            }

            Goomba.walkingAnimation = new BufferedImage[2];
            BufferedImage goombawalking = ImageIO.read(new File("src/Sprites/GoombaWalkingSprite.png"));

            for (int i = 0; i < 2; i++) {
                Goomba.walkingAnimation[i] = goombawalking.getSubimage(i * 64, 0, 64, 64);
            }

            Goomba.dieAnimation = new BufferedImage[1];
            Goomba.dieAnimation[0] = ImageIO.read(new File("src/Sprites/GoombaDieSprite.png"));

            Coin.coinAnimation = new BufferedImage[4];
            BufferedImage coinsprite = ImageIO.read(new File("src/Sprites/CoinSprite.png"));

            for (int i = 0; i < 4; i++) {
                Coin.coinAnimation[i] = coinsprite.getSubimage(i * 16, 0, 16, 16);
            }

            Mushroom.walkingAnimation = new BufferedImage[1];
            BufferedImage mushroomwalking = ImageIO.read(new File("src/Sprites/1upMushroom.png"));

            for (int i = 0; i < 1; i++) {
                Mushroom.walkingAnimation[i] = mushroomwalking.getSubimage(i * 16, 0, 16, 16);
            }

            Starman.starmanAnimation = new BufferedImage[1];
            BufferedImage starmansprite = ImageIO.read(new File("src/Sprites/Starman.png"));
            Starman.starmanAnimation[0] = starmansprite.getSubimage(0, 0, 769, 769);

            Fireball.fireballAnimation = new BufferedImage[4];
            BufferedImage fireball = ImageIO.read(new File("src/Sprites/fireball.png"));

            for (int i = 0; i < 4; i++) {
                Fireball.fireballAnimation[i] = fireball.getSubimage(i * 16, 0, 16, 16);
            }

            Boo.booAnimation = new BufferedImage[1];
            Boo.booAnimation[0] = ImageIO.read(new File("src/Sprites/boo.png"));

            Bowser.bowserAnimation = new BufferedImage[4];
            BufferedImage bowsersprite = ImageIO.read(new File("src/Sprites/Bowser.png"));

            for (int i = 0; i < 4; i++) {
                Bowser.bowserAnimation[i] = bowsersprite.getSubimage(i * 32, 0, 32, 32);
            }

            BossFire.bossfireAnimation = new BufferedImage[1];
            BossFire.bossfireAnimation[0] = ImageIO.read(new File("src/Sprites/bossActack.png"));

            Bowser.bowserDieAnimation = new BufferedImage[1];
            Bowser.bowserDieAnimation[0] = ImageIO.read(new File("src/Sprites/BowserDie.png"));

        } catch (Exception e) {}
    }
}
