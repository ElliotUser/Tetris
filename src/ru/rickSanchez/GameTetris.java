package ru.rickSanchez;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameTetris {

    final String TITLE_OF_PROGRAM = "Tetris";
    final int BLOKE_SIZE = 25;
    final int ARC_RADIUS = 6;
    final int FIELD_WIDTH = 10;// in block
    final int FIELD_HEIGHT = 18;
    final int START_LOCATION = 180;
    final int FIELD_DX = 7;
    final int FIELD_DY = 26;
    final int LEFT = 37;
    final int RIGHT = 38;
    final int UP = 39;
    final int DOWN = 40;
    final int SHOW_DELAY = 350;//delay for animation
    final int [][][] SHAPE = {
            {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}, {4, 0x00f0f0}},
            {{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}, {4, 0xf0f000}},
            {{1,0,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x0000f0}},
            {{0,0,1,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf0a000}},
            {{0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x00f000}},
            {{1,1,1,0}, {0,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xa000f0}},
            {{1,1,0,0}, {0,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf00000}}
    };
    //GAME OVER
    final int[][] GAME_OVER_MSG = {
            {0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},
            {1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
            {1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
            {1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
            {1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
            {0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}
    };

    final int[] SCORES = {100, 300, 700, 1500};
    int gameScores = 0;
    int [][] mine = new int[FIELD_HEIGHT][FIELD_WIDTH];
    JFrame frame;
    Canvas canvasPanel = new Canvas();
    Random random = new Random();
    Figure figure = new Figure();
    boolean gameOver = false;



    public static void main(String[] args) {
        new GameTetris().go();
    }

    void go(){
        frame = new JFrame(TITLE_OF_PROGRAM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH*BLOKE_SIZE+FIELD_DX,FIELD_HEIGHT*BLOKE_SIZE+FIELD_DY);
        frame.setLocation(START_LOCATION,START_LOCATION);
        frame.setResizable(false);

        canvasPanel.setBackground(Color.black);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if(!gameOver) {
                    if(ke.getKeyCode() == DOWN) figure.drop();
                    if(ke.getKeyCode() == UP) figure.rotate();
                    if(ke.getKeyCode() == LEFT || ke.getKeyCode() == RIGHT) figure.move(ke.getKeyCode());
                }
                canvasPanel.repaint();
            }
        });
        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);

        frame.setVisible(true);

        Arrays.fill(mine[FIELD_HEIGHT],1);
        
        while(!gameOver) {
            try {
                Thread.sleep(SHOW_DELAY);
            }catch(Exception e){ 
                e.printStackTrace(); 
            }canvasPanel.repaint();
            if(figure.isTouchGround()){
                figure.leaveOnTheGround();
                checkFilling();
                figure = new Figure();
                gameOver = figure.isCrossGround();
            }else {
                figure.stepDown();
            }
        }
    }
    
    void checkFilling() {
        
    }

    private class Figure {
        private ArrayList<Block> figure = new ArrayList<>();
        private int [][] shape = new int[4][4];
        private int type, size, color;
        private int x  = 3, y = 0;

        public Figure() {
            type =
        }

        public void drop() {
        }

        public void rotate() {
        }

        public void move(int keyCode) {
            
        }

        public void leaveOnTheGround() {
        }

        public boolean isTouchGround() {
            return false;
        }

        public boolean isCrossGround() {
            return false;
        }

        public void stepDown() {

        }
    }

    private class Block{
        private int x, y;

        public Block(int x, int y) {
            setX(x);
            setY(y);
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        void paint(Graphics g, int color){
            g.setColor(new Color(color));
            g.drawRoundRect(x*BLOKE_SIZE+1, y*BLOKE_SIZE+1,
                         BLOKE_SIZE-2, BLOKE_SIZE-2, ARC_RADIUS,ARC_RADIUS);
        }
    }

    private class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
}
