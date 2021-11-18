package EntityObjects;

import java.awt.*;
import java.util.LinkedList;

import Enum.*;
import Handler.*;
import Loader.GraphicsLoader;
import Loader.MusicLoader;
import Super.GameObject;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class Player extends GameObject {

    private float gravity = 0.3f;
    private final int maxSpeed = 10;

    public static BufferedImage[] idleAnimation;
    public static BufferedImage[] walkingAnimation;
    public static BufferedImage[] jumpingAnimation;
    public static BufferedImage[] dieAnimation;

    public static int ballNumber = 10;
    public static int Heart = 3;
    public static int score = 0;

    public boolean god = true;
    public int countGod = 0;

    int CheckLoop = 0;

    public static boolean facingRight;

    private BufferedImage image;

    ObjectHandler objecthandler;
    AnimationHandler animationhandler;
    MusicLoader musicloader;

    public Player(int x, int y, int width, int height, ObjectID id, ObjectHandler objecthandler, Type type, MusicLoader musicloader) {

        super(x, y, width, height, id, type);
        this.objecthandler = objecthandler;
        this.animationhandler = new AnimationHandler();
        this.musicloader = musicloader;

        animationhandler.setAnimation(idleAnimation);
        animationhandler.setDelay(-1);
        facingRight = true;

        try {

            image = ImageIO.read(new File("src\\Graphics\\Heart.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(Graphics g) {

        if (facingRight) {
            g.drawImage(animationhandler.getImage(), x, y, width, height, null);
        } else if (!facingRight) {
            g.drawImage(animationhandler.getImage(), x + width, y, -width, height, null);
        }
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < Heart; i++) {
            g.drawImage(image, x - 800 + i * 67, 25, 64, 64, null);
        }
        g2d.setFont(new Font("New Super Mario Font U", Font.PLAIN, 60));
        g.setColor(new Color(211, 211, 211));
        g.drawString("" + score, x + 480, 70);

        g2d.setFont(new Font("Super Mario 256", Font.PLAIN, 28));
        g.setColor(new Color(255, 0, 0));
        g.drawString("" + ballNumber, x, y - 20);

    }

    @Override
    public void tick(LinkedList<GameObject> objectlist) {

        if (alive) {
            this.x += this.velX;
        }
        this.y += this.velY;

        if (this.y >= 1000) {
            die();
        }

        if (falling || jumping) {
            this.velY += gravity;
            if (!god) {
                countGod++;
                if (countGod == 100) {
                    countGod = 0;
                    god = true;
                }
            }
            if (this.velY >= maxSpeed) {
                this.velY = maxSpeed;
            }
        }

        if (alive) {
            if (velX != 0 && !jumping) { //animation movement
                animationhandler.setAnimation(walkingAnimation);
                animationhandler.setDelay(80);
            } else if (velX == 0 && !jumping) {
                animationhandler.setAnimation(idleAnimation);
                animationhandler.setDelay(-1);
            } else if (jumping) {
                animationhandler.setAnimation(jumpingAnimation);
                animationhandler.setDelay(-1);
            }

            if (velX > 0) {
                facingRight = true;
            } else if (velX < 0) {
                facingRight = false;
            }
            collisions(objectlist);
        }
        animationhandler.tick();

        if (score < 0) {
            score = 0;
        }

    }

    public void collisions(LinkedList<GameObject> objectlist) {

        for (int i = 0; i < objecthandler.objectlist.size(); i++) {
            GameObject tempObject = objecthandler.objectlist.get(i);

            if (tempObject.getId() == ObjectID.BLOCK) {

                if (getBounds().intersects(tempObject.getBounds())) {

                    y = tempObject.getY() - tempObject.getHeight();
                    velY = 0;
                    falling = false;
                    jumping = false;

                } else {
                    falling = true;
                }

                if (getBoundsRight().intersects(tempObject.getBounds())) {

                    x = tempObject.getX() - tempObject.getWidth();
                    velX = 0;
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) {

                    x = tempObject.getX() + tempObject.getWidth();
                    velX = 0;
                }

                if (getBoundsTop().intersects(tempObject.getBounds())) {

                    y = tempObject.getY() + tempObject.getHeight();
                    velY = 0;
                }
            } else if (tempObject.getId() == ObjectID.GOOMBA) {

                if (tempObject.isAlive() && god) {
                    if (getBounds().intersects(tempObject.getBounds())) {

                        tempObject.setAnimation(Goomba.dieAnimation);
                        musicloader.play(MusicLoader.GoombaDie);
                        tempObject.setAlive(false);
                        velY = -5;
                        score += 30;
                        return;
                    }

                    if ((getBoundsRight().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) && (!getBounds().intersects(tempObject.getBounds()))) {
                        Heart--;

                        musicloader.play(MusicLoader.bump);

                        god = false;
                        if (Heart <= 0) {
                            die();
                        }
                        return;
                    }

                }
            } else if (tempObject.getId() == ObjectID.COIN) {

                if (tempObject.isAlive()) {
                    if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())) {
                        musicloader.play(MusicLoader.getCoin);
                        objecthandler.removeObject(tempObject);
                        score += 10;
                    }
                }
            } else if (tempObject.getId() == ObjectID.MUSHROOM) {

                if (tempObject.isAlive()) {
                    if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())) {

                        musicloader.play(MusicLoader.getMushroom);

                        GraphicsLoader graphicloader = new GraphicsLoader();
                        graphicloader.loadSprites(2);
                        if (Heart < 5) {
                            Heart++;
                        }

                        objecthandler.removeObject(tempObject);
                    }

                }
            } else if (tempObject.getId() == ObjectID.STARMAN) {

                if (tempObject.isAlive()) {
                    if (getBounds().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) {
                        musicloader.play(MusicLoader.getMushroom);
                        GraphicsLoader graphicloader = new GraphicsLoader();
                        graphicloader.loadSprites(3);
                        objecthandler.removeObject(tempObject);
                        ballNumber += 10;

                    }

                }
            } else if (tempObject.getId() == ObjectID.PIPE) {
                if (CheckLoop == 0) {
                    if (getBounds().intersects(tempObject.getBounds())) {

                        musicloader.stopLoop();
                        score += 50;
                        musicloader.play(MusicLoader.pipe);
                        mario.Window.game.nextLV++;

                        mario.Window.next_LV(mario.Window.game.nextLV);
                        try {
                            musicloader.play(MusicLoader.theme2);
                            TimeUnit.SECONDS.sleep(4);
                        } catch (Exception e) {
                        }
                        mario.Window.game.nextLV++;
                        mario.Window.next_LV(mario.Window.game.nextLV);

                        CheckLoop = 1;

                    } else {
                        falling = true;
                    }

                    if (getBoundsRight().intersects(tempObject.getBounds())) {

                        x = tempObject.getX() - tempObject.getWidth() + 56;
                        velX = 0;
                    }

                    if (getBoundsLeft().intersects(tempObject.getBounds())) {

                        x = tempObject.getX() + tempObject.getWidth();
                        velX = 0;
                    }

                    if (getBoundsTop().intersects(tempObject.getBounds())) {

                        y = tempObject.getY() + tempObject.getHeight();
                        velY = 0;
                    }

                }
            } else if (tempObject.getId() == ObjectID.TRAMPOLINE) {

                if (getBounds().intersects(tempObject.getBounds())) {

                    y = tempObject.getY() - tempObject.getHeight();
                    velY = -10;
                    falling = false;
                    jumping = false;
                    musicloader.play(MusicLoader.trampoline);

                } else {
                    falling = true;
                }

                if (getBoundsRight().intersects(tempObject.getBounds())) {

                    x = tempObject.getX() - tempObject.getWidth();
                    velX = 0;
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) {

                    x = tempObject.getX() + tempObject.getWidth();
                    velX = 0;
                }

                if (getBoundsTop().intersects(tempObject.getBounds())) {

                    y = tempObject.getY() + tempObject.getHeight();
                    velY = 0;
                }
            } else if (tempObject.getId() == ObjectID.BOO) {

                if (god) {

                    if ((getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) && (!getBounds().intersects(tempObject.getBounds()))) {
                        god = false;
                        Heart--;
                        score -= 30;
                        musicloader.play(MusicLoader.bump);

                        if (Heart <= 0) {
                            die();
                        }
                        return;
                    }

                }
            } else if (tempObject.getId() == ObjectID.PRINCESS) {

                if (CheckLoop == 0) {
                    if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsRight().intersects(tempObject.getBounds())) { //getbounds is coordinates

                        musicloader.stopLoop();
                        score += 150;
                        musicloader.play(MusicLoader.end);
                        mario.Window.game.nextLV++;

                        mario.Window.next_LV(mario.Window.game.nextLV);

                        try {
                            TimeUnit.SECONDS.sleep(7);
                        } catch (Exception e) {
                        }

                        Player.Heart = 3;
                        Player.ballNumber = 10;
                        Player.score = 0;
                        Bowser.bowser_heart = 50;
                        mario.Window.start_menu();
                        CheckLoop = 1;
                    }
                }

            } else if (tempObject.getId() == ObjectID.BOSSFIRE) {

                if (god) {

                    if ((getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) && (!getBounds().intersects(tempObject.getBounds()))) {
                        god = false;
                        Heart--;
                        score -= 10;
                        musicloader.play(MusicLoader.bump);

                        if (Heart <= 0) {
                            die();
                        }
                        return;
                    }

                }
            } else if (tempObject.getId() == ObjectID.BOWSER) {

                if (god) {

                    if ((getBoundsRight().intersects(tempObject.getBounds()) || getBounds().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) && (!getBounds().intersects(tempObject.getBounds()))) {
                        god = false;
                        Heart--;
                        score -= 50;
                        musicloader.play(MusicLoader.bump);

                        if (Heart <= 0) {
                            die();
                        }
                        return;
                    }

                }
            }

        }

    }

    public void die() {

        velY = -8;
        alive = false;
        animationhandler.setAnimation(dieAnimation);
        animationhandler.setDelay(-1);
        musicloader.stopLoop();
        countGod = 99;

        if (god) {

            mario.Window.next_LV(7);
            musicloader.play(MusicLoader.die);
            try {

                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {
            }

            Player.Heart = 3;
            Player.ballNumber = 10;
            Player.score = 0;
            mario.Window.start_menu();
        }
    }

    public Rectangle getBounds() {

        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) ((int) y + height / 2), (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsTop() {

        return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) y, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsRight() {

        return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {

        return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
    }

    @Override
    public void setAnimation(BufferedImage[] animation) {

    }

}
