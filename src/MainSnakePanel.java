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

    private SnakeFrame parentFrame; // the window that holds this panel, which we use to update the score.
    private int score;

    public MainSnakePanel(SnakeFrame parent) {
        setBackground(Color.WHITE);
        parentFrame = parent;
        setDoubleBuffered(true);
        setFocusable(true); // "focus" is about making this panel be the one that gets told about keyboard events.
        requestFocusInWindow();
        addKeyListener(this);
        reset();
    }

    public void incrementScore()
    {
        score++;
        parentFrame.setScore(score);
    }
    /**
     * create the 2-d grid of GridSquares and set the ones on the border to have a state of "wall."
     */
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

    /**
     * restart the game, stopping the current run (if there is one), recreating the board, restarting the snake,
     * picking a new apple and starting the run again. This is called at the start of the game, as well as when the
     * "Reset" button is pressed.
     */
    public void reset()
    {
        stopAnimation();
        generateGrid();
        parentFrame.setScore(0);
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

    /**
     * select a new, unoccupied location for the apple.
     */
    public void resetApple()
    {
        GridSquare previousSquareWithApple = currentSquareWithApple;
        do
        {
            currentSquareWithApple = theGrid[(int)(Math.random()*Constants.NUM_ROWS)]
                    [(int)(Math.random()*Constants.NUM_COLUMNS)];

        }while (currentSquareWithApple.getState() != Constants.CELL_STATE_EMPTY);
        if (previousSquareWithApple != null && previousSquareWithApple.getState() == Constants.CELL_STATE_APPLE)
            previousSquareWithApple.setState(Constants.CELL_STATE_EMPTY);
        currentSquareWithApple.setState(Constants.CELL_STATE_APPLE);
    }

    /**
     * begin the multithreaded execution of the animation loop.
     */
    public void startAnimation() {
        if (animationThread == null || !running) {
            running = true;
            animationThread = new Thread(this);
            animationThread.start();
            parentFrame.updateRunStatus(true);
        }
    }

    /**
     * stop the current execution of the animation loop.
     */
    public void stopAnimation() {
        running = false;
        if (animationThread != null) {
            try {
                animationThread.join(); // this stops the thread and brings its execution back to this one.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * the code that is executed when we start the thread; in this case, it calls the updateAnimation() method over and
     * over again and tells the screen to refresh when it can, with a delay between executions.
     */
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

    /**
     * the step of the animation that happens every DELAY ms. Attempts to move the snake's head by one,
     * potentially eating an apple or crashing the snake in the process.
     */
    private void updateAnimation()
    {
        // determine the location and state of the square where the head is about to go.
        int destRow, destCol;
        boolean ateApple = false;
        final int[][] deltas = {{0,+1}, {+1,0}, {0,-1}, {-1,0}};
        destRow = currentRowOfHead+deltas[currentDirection][0];
        destCol = currentColOfHead+deltas[currentDirection][1];
        int destination_state = theGrid[destRow][destCol].getState();

        // Check whether there is an apple in that location.
        if (destination_state == Constants.CELL_STATE_APPLE)
        {
            System.out.println("Ate apple.");
            incrementScore();
            resetApple();
            ateApple = true;
        }

        // clear the head from the current location (????)
        theGrid[currentRowOfHead][currentColOfHead].setState(Constants.CELL_STATE_EMPTY);

        // check whether the snake has crashed.
        if (destination_state != Constants.CELL_STATE_EMPTY && !ateApple)
        {
            System.out.println("Crashed.");
            running = false;
            parentFrame.updateRunStatus(false);
            repaint();
            return;
        }

        // move the head to the new location (????)
        currentRowOfHead = destRow;
        currentColOfHead = destCol;
        theGrid[currentRowOfHead][currentColOfHead].setState(Constants.CELL_STATE_SNAKE_HEAD_W+currentDirection);

        repaint();
    }

    /**
     * draw the grid of GridSquares.
     * @param g the <code>Graphics</code> object to draw with/into
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create a copy of Graphics for double buffering
        Graphics2D g2d = (Graphics2D) g.create();

        try {
            // Enable antialiasing for smoother rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw your content here
            for (GridSquare[] row: theGrid)
                for (GridSquare square: row)
                    square.drawSelf(g2d);

        } finally {
            // Dispose of the graphics copy
            g2d.dispose();
        }
    }

    /**
     * we are required to have this method to fulfill the KeyListener Interface, but we aren't going to use it.
     * This is another way to process keystrokes.
     * @param e the event to be processed containing info about which key was pressed and released.
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
         // do nothing.
    }

    /**
     * respond to the user first pressing a key.
     * @param e the event to be processed containing information about the keyPress.
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        // Note: this is probably more complicated than normal, because I am trying to make it difficult for the user
        // to reverse the snake directly back onto itself. (Though it isn't impossible, if the user moves fast enough.)
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

    /**
     * Respond to the user letting off of one of the keys in the keyboard.
     * @param e the event to be processed containing information about the key that was released.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        // This, too, is extra complicated because if the user has two keys down and lets go of one, I'd like the other
        // to potentially take effect, if it isn't going to cause a reversal and if we don't still have two opposing
        // keys selected.
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

}
