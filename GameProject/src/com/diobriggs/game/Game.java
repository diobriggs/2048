package com.diobriggs.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;

public class Game extends JPanel implements KeyListener, Runnable {
    private static final long serialVersionUID=1L;
    public static final int WIDTH=650;
    public static final int HEIGHT=750;
    public static final Font main=new Font("Bebas Neue Regular", Font.PLAIN, 28);

    private Thread game;
    private boolean running;
    private BufferedImage image=new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private GameBoard board;
    private long start_time;
    private long elapsed;
    private boolean set;

    public Game(){
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        board=new GameBoard(WIDTH/2-GameBoard.BOARD_WIDTH/2, HEIGHT-GameBoard.BOARD_HEIGHT-10);
    }

    private void update(){
        board.update();
        Keyboard.update();
    }


    private void render(){
        Graphics2D g= (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // render board
        board.render(g);
        g.dispose();

        Graphics2D g2d= (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keyboard.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keyboard.keyReleased(e);

    }

    @Override
    public void run() {
        int fps=0, updates=0;
        long fpsTimer=System.currentTimeMillis();
        double nsPerUpdate=1000000000.0/60;

        // last update time in nanoseconds
        double then=System.nanoTime();
        double unprocessed=0;

        while(running) {
            boolean shouldRender=false;
            double now= System.nanoTime();
            unprocessed+=(now-then)/nsPerUpdate;
            then=now;

            // update queue
            while (unprocessed >= 1) {
                updates++;
                update();
                unprocessed--;
                shouldRender = true;
            }
            //rendering
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(System.currentTimeMillis()- fpsTimer > 1000){
            System.out.printf("%d fps %d updates", fps, updates);
            System.out.println();
            fps=0;
            updates=0;
            fpsTimer+=1000;

        }
    }
    public synchronized void start(){
        if(running) return;
        running=true;
        game=new Thread(this, "game");
        game.start();

    }
    public synchronized void stop(){
        if(!running) return;
        running=false;
        System.exit(0);
    }
}
