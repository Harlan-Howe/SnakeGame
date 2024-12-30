import java.awt.event.KeyEvent;

public interface Constants
{
    public final int CELL_SIZE = 15;
    public final int NUM_COLUMNS = 40;
    public final int NUM_ROWS = 40;

    public final int CELL_STATE_EMPTY = 0;
    public final int CELL_STATE_WALL = 1;
    public final int CELL_STATE_SNAKE_BODY = 2;
    public final int CELL_STATE_APPLE = 3;

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
