import java.awt.*;

public class GridSquare
{
    private int xPos, yPos;
    private int state;

    public GridSquare(int x, int y)
    {
        xPos = x;
        yPos = y;
        state = Constants.CELL_STATE_EMPTY;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getState()
    {
        return state;
    }

    public void drawSelf(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(xPos, yPos, Constants.CELL_SIZE, Constants.CELL_SIZE);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(xPos, yPos, Constants.CELL_SIZE, Constants.CELL_SIZE);
        switch (state)
        {
            case Constants.CELL_STATE_WALL -> drawWall(g);
            case Constants.CELL_STATE_SNAKE_BODY -> drawBody(g);
            case Constants.CELL_STATE_APPLE -> drawApple(g);
        }
    }

    public void drawWall(Graphics g)
    {
        g.setColor(Color.YELLOW);
        g.fillRect(xPos+2, yPos+2, Constants.CELL_SIZE-4, Constants.CELL_SIZE-4);
    }

    public void drawBody(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6);
    }

    public void drawApple(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillOval(xPos+4, yPos+4, Constants.CELL_SIZE-8, Constants.CELL_SIZE-8);
    }


}
