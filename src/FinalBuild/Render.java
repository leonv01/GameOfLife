package FinalBuild;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Render extends JPanel {

    private int[][] grid;
    private final int TILES;
    private final BufferedImage image;
    private final Graphics2D graphics2D;
    private final int TILE_SIZE;
    private long currentGeneration, births, deaths;

    public Render() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        int WIDTH = Display.WIDTH;
        int HEIGHT = Display.HEIGHT;

        TILES = Display.TILES;
        TILE_SIZE = WIDTH / TILES;

        jPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = image.createGraphics();
        grid = generateGrid();
        currentGeneration = 1;
        births = deaths = 0;
    }

    public void setEntity(int x, int y) {
        int tempX = x / TILE_SIZE;
        int tempY = y / TILE_SIZE;
        grid[tempY][tempX] = 1;
    }

    public void drawMap() {
        for (int y = 0; y < TILES; y++) {
            for (int x = 0; x < TILES; x++) {
                graphics2D.setColor(grid[y][x] == 1 ? Color.GREEN : Color.BLACK);
                //    if (x == 0 && y == 0) graphics2D.setColor(Color.CYAN);
                graphics2D.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void nextGeneration() {
        currentGeneration++;
        int[][] nextGen = new int[TILES][TILES];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == 0 && checkNeighbours(x, y) == 3) {
                    nextGen[y][x] = 1;
                    births++;
                } else if (grid[y][x] == 1 && checkNeighbours(x, y) < 2) {
                    nextGen[y][x] = 0;
                    deaths++;
                } else if (grid[y][x] == 1 && (checkNeighbours(x, y) == 2 || checkNeighbours(x, y) == 3))
                    nextGen[y][x] = 1;
                else if (grid[y][x] == 1 && checkNeighbours(x, y) > 3) {
                    nextGen[y][x] = 0;
                    deaths++;
                }
            }
        }
        grid = nextGen.clone();
    }

    public void restart(){
        grid = generateGrid();
        deaths = births = 0;
        currentGeneration = 1;
    }

    private int checkNeighbours(int x, int y) {
        int sum = 0;
        int tempY ;
        int tempX ;

        for (int deltaY = y - 1; deltaY <= y + 1; deltaY++) {
            for (int deltaX = x - 1; deltaX <= x + 1; deltaX++) {

                if (!(deltaX == x && deltaY == y)) {
                    if (deltaY < 0) tempY = grid.length - 1;
                    else if (deltaY > grid.length - 1) tempY = 0;
                    else tempY = deltaY;

                    if (deltaX < 0) tempX = grid[0].length - 1;
                    else if (deltaX > grid[0].length - 1) tempX = 0;
                    else tempX = deltaX;

                    sum += grid[tempY][tempX];
                }
            }
        }
        return sum;
    }

    private int[][] generateGrid() {
        Random random = new Random();

        int[][] out = new int[TILES][TILES];
        for (int y = 0; y < out.length; y++) {
            for (int x = 0; x < out[0].length; x++) {
                int randomInt = random.nextInt(2);
                if (randomInt == 1) births++;
                else deaths++;
                out[y][x] = randomInt;
            }
        }
        return out;
    }
}
