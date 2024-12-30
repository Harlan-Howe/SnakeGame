import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// NOTE: initial draft of this class was written by Flint K12.
public class MainSnakePanel extends JPanel implements Runnable, KeyListener{
    private static final long DELAY = 250; // 0.25 seconds in milliseconds
    private volatile boolean running;
    private Thread animationThread;

    private GridSquare[][] theGrid;
    private GridSquare currentSquareWithApple;

    private int currentRowOfHead, currentColOfHead;
    private int currentDirection = Constants.DIRECTION_UP;
    private boolean leftKeyPressed, rightKeyPressed, upKeyPressed, downKeyPressed;

    // Add your animation state variables here
    private int animationStep = 0;

    public MainSnakePanel() {
       setBackground(Color.WHITE);

        // Enable double buffering
        setDoubleBuffered(true);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        reset();
    }

    public void generateGrid()
    {
        theGrid = new GridSquare[Constants.NUM_ROWS][Constants.NUM_COLUMNS];
        for (int r=0; r<Constants.NUM_ROWS; r++)
            for (int c=0; c<Constants.NUM_COLUMNS; c++)
            {
                theGrid[r][c] = new GridSquare(c*Constants.CELL_SIZE, r*Constants.CELL_SIZE);
                if (r==0 || c==0 || r== Constants.NUM_ROWS-1 || c==Constants.NUM_COLUMNS-1)
                    theGrid[r][c].setState(Constants.CELL_STATE_WALL);
            }
        /* Uncomment out these lines (temporarily) to see what the snake parts look like.
        theGrid[10][10].setState(Constants.CELL_STATE_SNAKE_BODY_E_W);
        theGrid[10][11].setState(Constants.CELL_STATE_SNAKE_BODY_NW_SE);
        theGrid[11][11].setState(Constants.CELL_STATE_SNAKE_BODY_N_S);
        theGrid[12][11].setState(Constants.CELL_STATE_SNAKE_BODY_NE_SW);
        theGrid[12][10].setState(Constants.CELL_STATE_SNAKE_HEAD_E);

        theGrid[15][15].setState(Constants.CELL_STATE_SNAKE_HEAD_W);
        theGrid[17][15].setState(Constants.CELL_STATE_SNAKE_HEAD_N);
        theGrid[19][15].setState(Constants.CELL_STATE_SNAKE_HEAD_S);
        theGrid[21][15].setState(Constants.CELL_STATE_SNAKE_TAIL);
        */

    }

    public void reset()
    {
        stopAnimation();
        generateGrid();
        // reset the snake
        currentRowOfHead = Constants.NUM_ROWS/2;
        currentColOfHead = Constants.NUM_COLUMNS/2;
        currentDirection = Constants.DIRECTION_UP;
        theGrid[currentRowOfHead][currentColOfHead].setState(Constants.CELL_STATE_SNAKE_HEAD_N);
        // reset the apple
        resetApple();
        startAnimation();
        repaint();
    }

    public void resetApple()
    {
        if (currentSquareWithApple != null && currentSquareWithApple.getState() == Constants.CELL_STATE_APPLE)
            currentSquareWithApple.setState(Constants.CELL_STATE_EMPTY);
        do
        {
            currentSquareWithApple = theGrid[(int)(Math.random()*Constants.NUM_ROWS)]
                    [(int)(Math.random()*Constants.NUM_COLUMNS)];

        }while (currentSquareWithApple.getState() != Constants.CELL_STATE_EMPTY);

        currentSquareWithApple.setState(Constants.CELL_STATE_APPLE);
    }

    public void startAnimation() {
        if (animationThread == null || !running) {
            running = true;
            animationThread = new Thread(this);
            animationThread.start();
        }
    }

    public void stopAnimation() {
        running = false;
        if (animationThread != null) {
            try {
                animationThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        requestFocusInWindow();
        while (running) {
            // Update animation state
            updateAnimation();

            // Request a repaint
            repaint();

            // Sleep for the specified delay
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void updateAnimation() {
        // Update your animation state here
        animationStep++;
        theGrid[currentRowOfHead][currentColOfHead].setState(Constants.CELL_STATE_EMPTY);
        int destRow, destCol;
        if (currentDirection == Constants.DIRECTION_RIGHT)
        {
            destRow = currentRowOfHead;
            destCol = currentColOfHead+1;
        }
        else if (currentDirection == Constants.DIRECTION_DOWN)
        {
            destRow = currentRowOfHead+1;
            destCol = currentColOfHead;
        }
        else if (currentDirection == Constants.DIRECTION_LEFT)
        {
            destRow = currentRowOfHead;
            destCol = currentColOfHead-1;
        }
        else // if (currentDirection == Constants.DIRECTION_UP)
        {
            destRow = currentRowOfHead-1;
            destCol = currentColOfHead;
        }
        // Add your animation logic here
        int destination_state = theGrid[destRow][destCol].getState();
        if (destination_state == Constants.CELL_STATE_APPLE)
        {
            System.out.println("Ate apple.");
            resetApple();
        }
        else if (destination_state != Constants.CELL_STATE_EMPTY)
        {
            System.out.println("Crashed.");
            running = false;
        }
        currentRowOfHead = destRow;
        currentColOfHead = destCol;
        theGrid[currentRowOfHead][currentColOfHead].setState(Constants.CELL_STATE_SNAKE_HEAD_W+currentDirection);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create a copy of Graphics for double buffering
        Graphics2D g2d = (Graphics2D) g.create();

        try {
            // Enable antialiasing for smoother rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw your animated content here
            drawAnimation(g2d);

        } finally {
            // Dispose of the graphics copy
            g2d.dispose();
        }
    }

    private void drawAnimation(Graphics2D g2d) {
        // Add your drawing code here
        // Example: Draw a moving rectangle
        for (GridSquare[] row: theGrid)
            for (GridSquare square: row)
                square.drawSelf(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
         // do nothing.
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        System.out.println(STR."pressed:\{e.getKeyChar()}");
        switch (e.getKeyCode())
        {
            case Constants.leftKey:
                leftKeyPressed = true;
                if (currentDirection != Constants.DIRECTION_RIGHT)
                    currentDirection = Constants.DIRECTION_LEFT;
                break;
            case Constants.rightKey:
                rightKeyPressed = true;
                if (currentDirection != Constants.DIRECTION_LEFT)
                    currentDirection = Constants.DIRECTION_RIGHT;
                break;
            case Constants.upKey:
                upKeyPressed = true;
                if (currentDirection != Constants.DIRECTION_DOWN)
                    currentDirection = Constants.DIRECTION_UP;
                break;
            case Constants.downKey:
                downKeyPressed = true;
                if (currentDirection != Constants.DIRECTION_UP)
                    currentDirection = Constants.DIRECTION_DOWN;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case Constants.leftKey:
                leftKeyPressed = false;
                if (currentDirection == Constants.DIRECTION_LEFT)
                    if(upKeyPressed && ! downKeyPressed)
                        currentDirection = Constants.DIRECTION_UP;
                    else if (!upKeyPressed && downKeyPressed)
                        currentDirection = Constants.DIRECTION_DOWN;
                break;
            case Constants.rightKey:
                rightKeyPressed = false;
                if (currentDirection == Constants.DIRECTION_RIGHT)
                    if(upKeyPressed && ! downKeyPressed)
                        currentDirection = Constants.DIRECTION_UP;
                    else if (!upKeyPressed && downKeyPressed)
                        currentDirection = Constants.DIRECTION_DOWN;
                break;
            case Constants.upKey:
                upKeyPressed = false;
                if (currentDirection == Constants.DIRECTION_UP)
                    if(leftKeyPressed && ! rightKeyPressed)
                        currentDirection = Constants.DIRECTION_LEFT;
                    else if (!leftKeyPressed && rightKeyPressed)
                        currentDirection = Constants.DIRECTION_RIGHT;
                break;
            case Constants.downKey:
                downKeyPressed = false;
                if (currentDirection == Constants.DIRECTION_DOWN)
                    if(leftKeyPressed && ! rightKeyPressed)
                        currentDirection = Constants.DIRECTION_LEFT;
                    else if (!leftKeyPressed && rightKeyPressed)
                        currentDirection = Constants.DIRECTION_RIGHT;
                break;
        }
    }

    // Example usage
    /*
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Animation Example");
            AnimationPanel panel = new AnimationPanel();
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Start animation when frame is shown
            panel.startAnimation();

            // Add window listener to stop animation when closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    panel.stopAnimation();
                }
            });
        });
    }*/
}
