/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giocatore;

/**
 *
 * @author riboni.andrea
 */
public class Posizione {
    private int x, y;
    private byte look;
    
    public Posizione(int x, int y, byte look) {
        this.x = x;
        this.y = y;
        this.look = look;
    }
    
    public Posizione(int x, int y) {
        this.x = x;
        this.y = y;
        look = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setLooking(byte b){
        look = b;
    }
    
    public byte getLooking(){
        return look;
    }
    
    public boolean is(Posizione pos){
        return getX()==pos.getX() && getY()==pos.getY();
    }
    
    public String toString(){
        return "x: "+x+"; y: "+y;
    }
    
}
