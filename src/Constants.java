import java.awt.event.KeyEvent;

public interface Constants
{
    public final int CELL_SIZE = 15;
    public final int NUM_COLUMNS = 40;
    public final int NUM_ROWS = 40;

    public final int CELL_STATE_EMPTY = 0;
    public final int CELL_STATE_WALL = 1;
    public final int CELL_STATE_APPLE = 2;
    public final int CELL_STATE_SNAKE_BODY_E_W = 3;
    public final int CELL_STATE_SNAKE_BODY_N_S = 4;
    public final int CELL_STATE_SNAKE_BODY_NE_SW = 5;
    public final int CELL_STATE_SNAKE_BODY_NW_SE = 6;
    public final int CELL_STATE_SNAKE_HEAD_W = 7;
    public final int CELL_STATE_SNAKE_HEAD_S = 8;
    public final int CELL_STATE_SNAKE_HEAD_E = 9;
    public final int CELL_STATE_SNAKE_HEAD_N = 10;
    public final int CELL_STATE_SNAKE_TAIL = 11;

    public final int DIRECTION_RIGHT = 0;
    public final int DIRECTION_DOWN = 1;
    public final int DIRECTION_LEFT = 2;
    public final int DIRECTION_UP = 3;

    //   Initially set up for WASD controls.
    public final int leftKey = KeyEvent.VK_A;
    public final int rightKey = KeyEvent.VK_D;
    public final int upKey = KeyEvent.VK_W;
    public final int downKey = KeyEvent.VK_S;

    /* Activate this section instead, if you'd prefer arrow key controls.
    public final int leftKey = KeyEvent.VK_LEFT;
    public final int rightKey = KeyEvent.VK_RIGHT;
    public final int upKey = KeyEvent.VK_UP;
    public final int downKey = KeyEvent.VK_DOWN;
    */


}
