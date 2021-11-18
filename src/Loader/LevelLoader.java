package Loader;

import EntityObjects.*;
import Enum.*;
import GameObjects.*;
import Handler.ObjectHandler;

import java.io.*;
import java.util.Scanner;

public class LevelLoader {

    private int nr;
    private int row;

    Scanner scanner;
    File file;
    ObjectHandler objecthandler;
    MusicLoader musicloader;

    public LevelLoader(ObjectHandler objecthandler, MusicLoader musicloader) {
        this.objecthandler = objecthandler;
        this.musicloader = musicloader;
        this.nr = -1;
        this.row = 0;
    }

    public void load(String path) {

        file = new File(path); // create object using path

        try {
            scanner = new Scanner(file);
            row = 0;
            nr = -1;

            while (scanner.hasNextInt()) { //check object scanner file load is int (if in file is int. return true)
                int object = scanner.nextInt();
                nr++;
                if (nr == 100) { //is width tileset
                    nr = 0;
                    row++;
                }
                if (object == 0) {
                    //not obj
                } else if (object == 1) {//block it              //position x,y,width,height
                    objecthandler.addObject(new Ground(nr * 56, row * 56, 56, 56, ObjectID.BLOCK, Type.OBJECT));//new Object Block(call) and next call addObject 
                } else if (object == 2) {//player              //sizeobject
                    objecthandler.addObject(new Player(nr * 56, row * 56, 56, 56, ObjectID.PLAYER, objecthandler, Type.ENTITY, musicloader));
                } else if (object == 3) {//goomba
                    objecthandler.addObject(new Goomba(nr * 56, row * 56, 56, 56, ObjectID.GOOMBA, objecthandler, Type.ENTITY));
                } else if (object == 4) {//coin
                    objecthandler.addObject(new Coin(nr * 56, row * 56, 56, 56, ObjectID.COIN, Type.OBJECT));
                } else if (object == 5) {//Mushroom
                    objecthandler.addObject(new Mushroom(nr * 56, row * 56, 56, 56, ObjectID.MUSHROOM, objecthandler, Type.ENTITY));
                } else if (object == 6) {//starman
                    objecthandler.addObject(new Starman(nr * 56, row * 56, 56, 56, ObjectID.STARMAN, Type.OBJECT));
                } else if (object == 7) {//pipe              
                    objecthandler.addObject(new Pipe(nr * 56, row * 56, 56 * 2, 56, ObjectID.PIPE, Type.OBJECT));//new Object Block(call) and next call addObject 
                } else if (object == 8) {//Trampoline         
                    objecthandler.addObject(new Trampoline(nr * 56, row * 56, 56, 56, ObjectID.TRAMPOLINE, Type.OBJECT));//new Object Block(call) and next call addObject 
                } else if (object == 9) {//boo
                    objecthandler.addObject(new Boo(nr * 56, row * 56, 56, 56, ObjectID.BOO, objecthandler, Type.ENTITY));
                } else if (object == 10) {//bowser
                    objecthandler.addObject(new Bowser(nr * 56, row * 56, 56 * 4, 56 * 4, ObjectID.BOWSER, objecthandler, Type.ENTITY, musicloader));
                } else if (object == 11) {//princess
                    objecthandler.addObject(new Princess(nr * 56, row * 56, 56, 56, ObjectID.PRINCESS, Type.OBJECT));
                }
            }
        } catch (FileNotFoundException e) {}
    }
}
