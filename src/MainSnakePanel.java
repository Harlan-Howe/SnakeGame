import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// NOTE: initial draft of this class was written by Flint K12.
public class MainSnakePanel extends JPanel implements Runnable {
    private static final long DELAY = 250; // 0.25 seconds in milliseconds
    private volatile boolean running;
    private Thread animationThread;

    private GridSquare[][] theGrid;

    // Add your animation state variables here
    private int animationStep = 0;

    public MainSnakePanel() {
       setBackground(Color.WHITE);
        // Enable double buffering
        setDoubleBuffered(true);
        generateGrid();
    }

    public void generateGrid()
    {
        theGrid = new GridSquare[Constants.NUM_ROWS][Constants.NUM_COLUMNS];
        for (int r=0; r<Constants.NUM_ROWS; r++)
            for (int c=0; c<Constants.NUM_COLUMNS; c++)
            {
                theGrid[r][c] = new GridSquare(r*Constants.CELL_SIZE, c*Constants.CELL_SIZE);
            }
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
        // Add your animation logic here
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
        g2d.setColor(Color.BLUE);
        g2d.fillRect(animationStep % getWidth(), 50, 50, 50);
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
