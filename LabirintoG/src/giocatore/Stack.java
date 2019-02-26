/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giocatore;

import java.util.ArrayList;

/**
 *
 * @author riboni.andrea
 */
public class Stack {

    private ArrayList<Posizione> movements;

    public Stack() {
        movements = new ArrayList<>();
    }

    public boolean empty() {
        return movements.isEmpty();
    }

    public void add(Posizione p) {
        movements.add(p);
    }

    public Posizione get() {
        if (!empty()) {
            Posizione mov = movements.get(movements.size() - 1);
            movements.remove(mov);
            return mov;
        }
        return null;
    }

    public int size() {
        return movements.size();
    }
    
    public String toString(){
        String out = "";
        for(Posizione p : movements){
            out += p.getX()+";"+p.getY()+" # ";
        }
        return out;
    }
    
    public void clear(){
        Posizione p = movements.get(0);
        movements.clear();
        movements.add(p);
    }
}
