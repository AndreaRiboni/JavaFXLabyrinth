/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirintog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import giocatore.Player;
import giocatore.Posizione;
import java.util.Vector;
import javafx.scene.Group;

/**
 *
 * @author riboni.andrea
 */
public class Labirinto {

    private byte[][] labyrinth;
    public final static byte PATH = 0, WALL = 1, START = 2, END = 3, OFFLIMIT = -1;
    private final static String DELIM = " x ";
    private int ROWS, COLUMNS;
    private Player player;
    private Posizione start, end;
    private boolean StartSetted = false, EndSetted = false;
    private Renderer render;

    public Labirinto(int rows, int columns, int x, int y) {
        ROWS = rows;
        COLUMNS = columns;
        if (ROWS * COLUMNS <= 0) {
            System.out.println("Dimensioni non valide. Sarà impostata la dimensione di default.");
            ROWS = COLUMNS = 5;
        } else {
            labyrinth = new byte[ROWS][COLUMNS];
        }
        smartFill();
        player = new Player(0, 0, this);
        player.setPos(start);
        render = new Renderer(this, x, y, LabirintoG.scene);
    }

    public void reset() {
        smartFill();
        restart();
    }

    public void restart() {
        player.setPos(start);
        player.clearHistory();
        render.update();
    }

    @Deprecated
    private void fill() {
        int cont = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int o = 0; o < COLUMNS; o++) {
                if (++cont % 3 == 0) {
                    setWall(i, o);
                }
            }
        }
        for (int i = 0; i < ROWS; i++) {
            for (int o = 0; o < COLUMNS; o++) {
                if (Math.random() < 0.5) {
                    setPath(i, o);
                }
            }
        }
        setEnd(ROWS - 1, COLUMNS - 1);
    }

    /**
     * Randomized Prim's Algorithm
     */
    public void smartFill() {
        GeneraLab gl = new GeneraLab(this);
    }

    public void display() {
        for (int i = 0; i < ROWS; i++) {
            for (int o = 0; o < COLUMNS; o++) {
                System.out.print("|" + labyrinth[i][o] + "|");
            }
            System.out.println("");
        }
    }

    public void displayWithPlayer() {
        int x = player.getPos().getX();
        int y = player.getPos().getY();
        for (int i = 0; i < ROWS; i++) {
            for (int o = 0; o < COLUMNS; o++) {
                if (i == x && o == y) {
                    System.out.print("|" + getPlayerRepres() + "|");
                } else {
                    System.out.print("|" + labyrinth[i][o] + "|");
                }
            }
            System.out.println("");
        }
    }

    public void setWall(int r, int c) {
        labyrinth[r][c] = WALL;
    }

    public void setPath(int r, int c) {
        labyrinth[r][c] = PATH;
    }

    public void setStart(int r, int c) {
        labyrinth[r][c] = START;
        start = new Posizione(r, c, (byte) 0);
        StartSetted = true;
    }

    public void setEnd(int r, int c) {
        labyrinth[r][c] = END;
        end = new Posizione(r, c, (byte) 0);
        EndSetted = true;
    }

    public byte getCell(int r, int c) {
        return labyrinth[r][c];
    }

    public void save(File path) {
        try {
            System.out.println(path.getPath() + ".ribolab");
            BufferedWriter bw = new BufferedWriter(new FileWriter(path.getPath() + ".ribolab", true));
            for (int i = 0; i < ROWS; i++) {
                for (int o = 0; o < COLUMNS; o++) {
                    bw.append(labyrinth[i][o] + DELIM);
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Errore");
        }
    }

    /**
     * da rigestire le dimensioni
     * 
     * @param path
     * @throws IOException 
     */
    public void getFromSave(File path) throws IOException {
        if (path.getName().endsWith(".ribolab")) {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String lab = "";
            int riga = 0;
            while ((lab = br.readLine()) != null) {
                String[] celle = lab.split(DELIM);
                for (int i = 0; i < celle.length; i++) {
                    labyrinth[riga][i] = Byte.parseByte(celle[i]);
                }
                riga++;
            }
            br.close();
        }
    }

    private char getPlayerRepres() {
        switch (player.getPos().getLooking()) {
            case Player.UP:
                return '^';
            case Player.DOWN:
                return 'V';
            case Player.DX:
                return '>';
            case Player.SX:
                return '<';
        }
        return 'P';
    }

    private byte askInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean correct;
        String input = "";
        do {
            correct = true;
            try {
                input = br.readLine().toUpperCase();
            } catch (IOException e) {
                correct = false;
            }
            if (input.length() != 1) {
                correct = false;
            } else {
                switch (input) {
                    case "W":
                        return Player.UP;
                    case "A":
                        return Player.SX;
                    case "S":
                        return Player.DOWN;
                    case "D":
                        return Player.DX;
                    default:
                        return (byte) 4;
                }
            }
        } while (!correct);
        return 4;
    }

    private void saveMovements(String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            bw.write(player.getMovements());
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(Labirinto.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void comunica(String s) {
        render.start(s);
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public int getLength() {
        return ROWS * COLUMNS;
    }

    public Posizione getPlayerPos() {
        return player.getPos();
    }

    public Group render() {
        return render.group;
    }

    public Player getPlayer() {
        return player;
    }

    public Posizione getStart() {
        return start;
    }

    public Posizione getEnd() {
        return end;
    }
}
