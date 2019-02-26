/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirintog;

import giocatore.Player;
import giocatore.Posizione;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import labirintog.Labirinto;

/**
 *
 * @author Utente
 */
public class Renderer {

    private Labirinto labyrinth;
    private Rectangle[][] grid;
    private static final Color WALL = Color.BLACK, PATH = Color.DARKGREEN, START = Color.DARKGOLDENROD, END = Color.BLUEVIOLET, PLAYER = Color.INDIANRED;
    private Polygon player;
    public Group group;
    private Scene scene;
    public static final String UP = "W", DOWN = "S", DX = "D", SX = "A";
    private static ImagePattern STARTEND, REALWALL, REALPATH, FINE;
    private final int NUMWALL = 3, NUMPATH = 2;
    private int x, y;
    
    public Renderer(Labirinto l, int x, int y, Scene s) {
        this.x = x;
        this.y = y;
        labyrinth = l;
        group = new Group();
        grid = new Rectangle[labyrinth.getRows()][labyrinth.getColumns()];
        scene = s;
        graphics();
        update();
    }

    private void graphics() {
        try {
            STARTEND = new ImagePattern(new Image(new FileInputStream("startend.png")));
            FINE = new ImagePattern(new Image(new FileInputStream("fine.jpg")));
            REALWALL = new ImagePattern(new Image(new FileInputStream("wall"+(int)(Math.random()*NUMWALL+1)+".png")));
            REALPATH = new ImagePattern(new Image(new FileInputStream("path"+(int)(Math.random()*NUMPATH+1)+".png")));
        } catch (FileNotFoundException ex) {
            System.out.println("Image error");
        }
    }

    public void update(){
        setLabyrinth();
        setPlayer();
        updatePlayer();
    }
    
    private void setLabyrinth() {
        float Xdim = x / labyrinth.getColumns(), Ydim = y / labyrinth.getRows();
        
        for (int i = 0; i < labyrinth.getRows(); i++) {
            for (int o = 0; o < labyrinth.getColumns(); o++) {
                grid[i][o] = new Rectangle(i * Ydim, o * Xdim, Ydim, Xdim);
                switch (labyrinth.getCell(i, o)) {
                    case Labirinto.PATH:
                        grid[i][o].setFill(REALPATH);
                        break;
                    case Labirinto.START:
                    case Labirinto.END:
                        grid[i][o].setFill(STARTEND);
                        break;
                    case Labirinto.WALL:
                        grid[i][o].setFill(REALWALL);
                        break;
                }
                grid[i][o].setStroke(Color.TRANSPARENT);
                group.getChildren().add(grid[i][o]);
            }
        }
    }

    private void setPlayer() {
        player = new Polygon();
        double dim = grid[0][0].getWidth() < grid[0][0].getHeight() ? grid[0][0].getWidth() / 2 : grid[0][0].getHeight() / 2;
        player.getPoints().addAll(new Double[]{
            dim / 2, 0.0,
            dim, dim,
            dim / 2, dim / 4 * 3,
            0.0, dim});
        player.setFill(PLAYER);
        player.setStroke(Color.BLACK);
        group.getChildren().add(player);
        updatePlayer();
    }

    public void updatePlayer() {
        Posizione pos = labyrinth.getPlayerPos();
        player.setTranslateX(pos.getX() * grid[0][0].getWidth() + grid[0][0].getWidth() / 4);
        player.setTranslateY(pos.getY() * grid[0][0].getHeight() + grid[0][0].getHeight() / 4);
        float angle = 0;
        switch (labyrinth.getPlayer().getPos().getLooking()) {
            case Player.DOWN:
                angle = 180;
                break;
            case Player.UP:
                angle = 0;
                break;
            case Player.DX:
                angle = 90;
                break;
            case Player.SX:
                angle = 270;
                break;
        }
        player.setRotate(angle);

//        if (pos.is(labyrinth.getEnd())) {
//            for (int i = 0; i < labyrinth.getRows(); i++) {
//                for (int o = 0; o < labyrinth.getColumns(); o++) {
//                    labyrinth.setPath(i, o);
//                    FadeTransition ft = new FadeTransition(Duration.millis(1000), grid[i][o]);
//                    ft.setFromValue(0.3);
//                    ft.setToValue(1);
//                    ft.setAutoReverse(true);
//                    ft.play();
//                    grid[i][o].setFill(FINE);
//                }
//            }
//        }
    }

    public void start(String s) {
        switch (s) {
            case UP:
                labyrinth.getPlayer().setPos(giocatore.Player.UP);
                break;
            case DOWN:
                labyrinth.getPlayer().setPos(giocatore.Player.DOWN);
                break;
            case DX:
                labyrinth.getPlayer().setPos(giocatore.Player.DX);
                break;
            case SX:
                labyrinth.getPlayer().setPos(giocatore.Player.SX);
                break;
            default:
                labyrinth.getPlayer().setPos((byte) -1);
                break;
        }
        updatePlayer();
    }

}
