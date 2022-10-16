package FinalBuild;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame implements Runnable {

    private Thread thread;
    private boolean running;

    private final Render render;
    private final String title = "Conway's Game of life";
    private final JFrame jFrame;

    protected static InputManager inputManager;
    protected static int WIDTH = 1024;
    protected static int HEIGHT = 1024;
    protected static final int TILES = 512;

    public Display() {
        jFrame = new JFrame();
        render = new Render();

        inputManager = new InputManager();

        jFrame.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        jFrame.setTitle(title);
        jFrame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.pack();
        jFrame.add(render);

        jFrame.setFocusable(true);

        jFrame.addMouseListener(inputManager);
        jFrame.addMouseWheelListener(inputManager);
        jFrame.addKeyListener(inputManager);
    }

    public static void main(String[] args) {
        Display display = new Display();
        display.start();
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / inputManager.getFps();
        double delta = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {

                delta--;
                frames++;
                interrupt();
                render.nextGeneration();
                render.drawMap();
                render.repaint();
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                jFrame.setTitle(String.format("%s | %d fps", title, frames));
                frames = 0;
            }
        }
    }

    private void interrupt() {
        do {
            checkInput();
            render.drawMap();
            render.repaint();
        } while (inputManager.isSpace());

    }

    private void checkInput() {
        if (inputManager.islClick()) {
            render.setEntity(inputManager.getX(), inputManager.getY());
        }
        if(inputManager.isRestart()){
            render.restart();
        }
    }

    public synchronized void start() {
        thread = new Thread();
        running = true;
        this.run();
    }
}
