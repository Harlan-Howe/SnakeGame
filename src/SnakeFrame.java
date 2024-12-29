import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeFrame extends JFrame implements ActionListener
{
    private JButton resetButton;
    private MainSnakePanel mainPanel;

    public SnakeFrame()
    {
        super("Snake!");
        setSize(Constants.CELL_SIZE*Constants.NUM_COLUMNS,Constants.CELL_SIZE*Constants.NUM_ROWS+68);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildControlPanel(), BorderLayout.NORTH);
        mainPanel = new MainSnakePanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    public JPanel buildControlPanel()
    {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        result.add(resetButton);

        return result;
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
