/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giocatore;

import labirintog.Labirinto;

/**
 *
 * @author riboni.andrea
 */
public class Player {

    private Posizione pos;
    public static final byte UP = 0, DOWN = 1, DX = 2, SX = 3;
    private Stack movements;
    private Labirinto lab;

    public Player(int px, int py, Labirinto lab) {
        pos = new Posizione(px, py, UP);
        movements = new Stack();
        movements.add(pos);
        this.lab = lab;
    }

    public Posizione getPos() {
        return pos;
    }

    public void clearHistory(){
        movements.clear();
    }
    
    private boolean canMove(Posizione pos) {
        try {
            return lab.getCell(pos.getX(), pos.getY()) != lab.WALL;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("movimento non valido");
            return false;
        }
    }

    public byte getAround(byte dir) {
        try {
            switch (dir) {
                case UP:
                    return lab.getCell(pos.getX(), pos.getY() - 1);
                case DOWN:
                    return lab.getCell(pos.getX(), pos.getY() + 1);
                case DX:
                    return lab.getCell(pos.getX() + 1, pos.getY());
                case SX:
                    return lab.getCell(pos.getX() - 1, pos.getY());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return lab.OFFLIMIT;
        }
        return lab.OFFLIMIT;
    }

    public void setPos(Posizione p){
        pos = p;
    }
    
    public void setPos(byte dir) {
        Posizione desired = null;
        switch (dir) {
            case UP:
                desired = new Posizione(pos.getX(), pos.getY() - 1, UP);
                break;
            case DOWN:
                desired = new Posizione(pos.getX(), pos.getY() + 1, DOWN);
                break;
            case DX:
                desired = new Posizione(pos.getX() + 1, pos.getY(), DX);
                break;
            case SX:
                desired = new Posizione(pos.getX() - 1, pos.getY(), SX);
                break;
            default: //undo
                desired = movements.get();
                break;
        }
        if (canMove(desired)) {
            if (dir >= UP && dir <= SX) {
                movements.add(pos);
            }
            pos = desired;
        }
    }

    public String getMovements() {
        return movements.toString();
    }
}
