package FinalBuild;

import java.awt.event.*;

public class InputManager implements MouseListener, MouseWheelListener, KeyListener {

    private boolean lClick = false;
    private boolean space = false;
    private int x = 0;
    private int y = 0;
    private int fps = 30;
    private boolean restart = false;
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            x = e.getX();
            y = e.getY();
            lClick = true;
            System.out.println("Click");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            lClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean islClick() {
        return lClick;
    }

    public boolean isSpace() {
        return space;
    }

    public boolean isRestart() {
        return restart;
    }

    public int getFps() {
        return fps;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> fps++;
            case KeyEvent.VK_S -> fps--;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE -> space = true;
            case KeyEvent.VK_R -> restart = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE -> space = false;
            case KeyEvent.VK_R -> restart = false;
        }
    }
}
