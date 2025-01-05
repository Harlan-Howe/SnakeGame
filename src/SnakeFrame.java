import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeFrame extends JFrame implements ActionListener
{
    private JButton resetButton;
    private final MainSnakePanel mainPanel;
    private JLabel scoreLabel;

    public SnakeFrame()
    {
        super("Snake!");
        setSize(Constants.CELL_SIZE*Constants.NUM_COLUMNS,Constants.CELL_SIZE*Constants.NUM_ROWS+95);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildControlPanel(), BorderLayout.NORTH);
        getContentPane().add(buildScorePanel(), BorderLayout.SOUTH);
        mainPanel = new MainSnakePanel();
        mainPanel.requestFocusInWindow();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    public JPanel buildControlPanel()
    {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        result.add(resetButton);


        return result;
    }

    public JPanel buildScorePanel()
    {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scoreLabel = new JLabel("Score: 0");
        result.add(scoreLabel);
        return result;
    }

    public void setScore(int score)
    {
        scoreLabel.setText(STR."Score: \{score}");
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(resetButton))
        {
            System.out.println("Reset pressed.");
            mainPanel.reset();
        }
    }
}
