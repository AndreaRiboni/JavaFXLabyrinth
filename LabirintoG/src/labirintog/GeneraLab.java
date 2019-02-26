/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirintog;

import giocatore.Posizione;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 *
 * @author Utente
 */
public class GeneraLab {

    private static Labirinto lab;
    private static final boolean WALL = false;
    private static final boolean PASSAGE = true;

    private final boolean map[][];

    public GeneraLab(Labirinto lab) {
        this.map = new boolean[lab.getRows()][lab.getColumns()];

        LinkedList<int[]> celle = new LinkedList<>();
        int x = (int) (Math.random() * lab.getColumns());
        int y = (int) (Math.random() * lab.getRows());
        celle.add(new int[]{x, y, x, y});

        while (!celle.isEmpty()) {
            final int[] f = celle.remove((int) (Math.random() * celle.size()));
            x = f[2];
            y = f[3];
            if (map[x][y] == WALL) {
                map[f[0]][f[1]] = map[x][y] = PASSAGE;
                if (x >= 2 && map[x - 2][y] == WALL) {
                    celle.add(new int[]{x - 1, y, x - 2, y});
                }
                if (y >= 2 && map[x][y - 2] == WALL) {
                    celle.add(new int[]{x, y - 1, x, y - 2});
                }
                if (x < lab.getColumns() - 2 && map[x + 2][y] == WALL) {
                    celle.add(new int[]{x + 1, y, x + 2, y});
                }
                if (y < lab.getRows() - 2 && map[x][y + 2] == WALL) {
                    celle.add(new int[]{x, y + 1, x, y + 2});
                }
            }
        }
        
        for(int i = 0; i < lab.getRows(); i++){
            for(int o = 0; o < lab.getColumns(); o++){
                if(map[i][o]) lab.setPath(i, o);
                else lab.setWall(i, o);
            }
        }
        
        boolean yeah = true;
        for(int i = 0; i < lab.getRows() && yeah; i++){
            for(int o = 0; o < lab.getColumns() && yeah; o++){
                if(lab.getCell(i, o) == Labirinto.PATH){
                    lab.setStart(i, o);
                    yeah = false;
                }
            }
        }
        for(int i = lab.getRows()-1; i >=0 && !yeah; i--){
            for(int o = lab.getColumns() - 1; o >= 0 && !yeah; o--){
                if(lab.getCell(i, o) == Labirinto.PATH){
                    lab.setEnd(i, o);
                    yeah = true;
                }
            }
        }
    }
}
