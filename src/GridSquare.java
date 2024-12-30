import java.awt.*;

public class GridSquare
{
    private int xPos, yPos;
    private int state;
    private final Color BROWN = new Color(128,64,0);

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

    public int getRow()
    {
        return yPos/Constants.CELL_SIZE;
    }

    public int getCol()
    {
        return xPos/Constants.CELL_SIZE;
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
            case Constants.CELL_STATE_SNAKE_BODY_E_W -> drawBody_E_W(g);
            case Constants.CELL_STATE_SNAKE_BODY_N_S -> drawBody_N_S(g);
            case Constants.CELL_STATE_SNAKE_BODY_NW_SE -> drawBody_NW_SE(g);
            case Constants.CELL_STATE_SNAKE_BODY_NE_SW -> drawBody_NE_SW(g);
            case Constants.CELL_STATE_SNAKE_HEAD_E -> drawHead_E(g);
            case Constants.CELL_STATE_SNAKE_HEAD_W -> drawHead_W(g);
            case Constants.CELL_STATE_SNAKE_HEAD_S -> drawHead_S(g);
            case Constants.CELL_STATE_SNAKE_HEAD_N -> drawHead_N(g);
            case Constants.CELL_STATE_SNAKE_TAIL -> drawTail(g);
            case Constants.CELL_STATE_APPLE -> drawApple(g);
        }
    }

    public void drawWall(Graphics g)
    {
        g.setColor(Color.YELLOW);
        g.fillRect(xPos+2, yPos+2, Constants.CELL_SIZE-4, Constants.CELL_SIZE-4);
    }

    public void drawTail(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6);
    }

    public void drawBody_E_W(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6);
        g.setColor(BROWN);
        g.fillOval(xPos+Constants.CELL_SIZE/2-2, yPos+4, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE/2-2, yPos+Constants.CELL_SIZE-8, 4, 4);
    }

    public void drawBody_N_S(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6);
        g.setColor(BROWN);
        g.fillOval(xPos+4, yPos+Constants.CELL_SIZE/2-2, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE-8,yPos+Constants.CELL_SIZE/2-2, 4, 4);
    }
    public void drawBody_NW_SE(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6);
        g.setColor(BROWN);
        g.fillOval(xPos+Constants.CELL_SIZE*65/100-2, yPos+Constants.CELL_SIZE*35/100-2, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE*35/100-2, yPos+Constants.CELL_SIZE*65/100-2, 4, 4);
    }

    public void drawBody_NE_SW(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillOval(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6);
        g.setColor(BROWN);
        g.fillOval(xPos+Constants.CELL_SIZE*35/100-2, yPos+Constants.CELL_SIZE*35/100-2, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE*65/100-2, yPos+Constants.CELL_SIZE*65/100-2, 4, 4);
    }

    public void drawHead_E(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRoundRect(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6, 4, 4);
        g.setColor(Color.BLACK);
        g.fillOval(xPos+Constants.CELL_SIZE/2-2, yPos+4, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE/2-2, yPos+Constants.CELL_SIZE-8, 4, 4);
        g.setColor(Color.RED);
        g.drawLine(xPos, yPos+Constants.CELL_SIZE/2, xPos+Constants.CELL_SIZE/2, yPos+Constants.CELL_SIZE/2);
    }

    public void drawHead_W(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRoundRect(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6, 4, 4);
        g.setColor(Color.BLACK);
        g.fillOval(xPos+Constants.CELL_SIZE/2-2, yPos+4, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE/2-2, yPos+Constants.CELL_SIZE-8, 4, 4);
        g.setColor(Color.RED);
        g.drawLine(xPos+Constants.CELL_SIZE, yPos+Constants.CELL_SIZE/2, xPos+Constants.CELL_SIZE/2, yPos+Constants.CELL_SIZE/2);
    }

    public void drawHead_S(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRoundRect(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6,4,4);
        g.setColor(Color.BLACK);
        g.fillOval(xPos+4, yPos+Constants.CELL_SIZE/2-2, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE-8,yPos+Constants.CELL_SIZE/2-2, 4, 4);
        g.setColor(Color.RED);
        g.drawLine(xPos+Constants.CELL_SIZE/2, yPos+Constants.CELL_SIZE/2, xPos+Constants.CELL_SIZE/2, yPos+Constants.CELL_SIZE);
    }

    public void drawHead_N(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRoundRect(xPos+3, yPos+3, Constants.CELL_SIZE-6, Constants.CELL_SIZE-6,4,4);
        g.setColor(Color.BLACK);
        g.fillOval(xPos+4, yPos+Constants.CELL_SIZE/2-2, 4, 4);
        g.fillOval(xPos+Constants.CELL_SIZE-8,yPos+Constants.CELL_SIZE/2-2, 4, 4);
        g.setColor(Color.RED);
        g.drawLine(xPos+Constants.CELL_SIZE/2, yPos+Constants.CELL_SIZE/2, xPos+Constants.CELL_SIZE/2, yPos);
    }
    public void drawApple(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillOval(xPos+4, yPos+4, Constants.CELL_SIZE-8, Constants.CELL_SIZE-8);
    }


}
