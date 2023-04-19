package bin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameBorder extends JFrame implements ActionListener {

    JTextField tfA, tfB, tfC;

    public FrameBorder() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JPanel with a BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout());

        // Set the preferred size of the content pane
        contentPane.setPreferredSize(this.getSize());

        // Add padding to the content pane using setBorder()
        contentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set the content pane of the JFrame
        this.setContentPane(contentPane);

        // Add some components to the content pane

        JPanel mainPanel = new JPanel(null);
        mainPanel.setSize(100, 100);
        mainPanel.setPreferredSize(new Dimension(100, 100));
        mainPanel.setBackground(Color.GRAY);

        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(new JPanel(), BorderLayout.PAGE_START);
        contentPane.add(new JPanel(), BorderLayout.PAGE_END);
        contentPane.add(new JPanel(), BorderLayout.LINE_START);
        contentPane.add(new JPanel(), BorderLayout.LINE_END);

        setMinimumSize(new Dimension(150, 150));
        setSize(300, 30);
        this.pack();
        this.setVisible(true);
    }

    public FrameBorder(int padding) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JPanel with a BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout());

        // Set the preferred size of the content pane
        contentPane.setPreferredSize(this.getSize());

        // Add padding to the content pane using setBorder()
        contentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        // Set the content pane of the JFrame
        this.setContentPane(contentPane);

        setMinimumSize(new Dimension(300, 300));
        setSize(700, 500);
        setPreferredSize(new Dimension(700, 500));
        setLocation(50, 50);

        repaint();

        this.pack();
        this.setVisible(true);
    }


    @Override
    public void paintComponents(Graphics g) {
        //super.paintComponents(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.ORANGE);
        g2d.fillRect(0  ,0,getWidth(), getHeight());

        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        GridLayout gridLayout = new GridLayout(3, 2);
        gridLayout.setVgap(20);
        gridLayout.setHgap(20);

        JPanel mainPanel = new JPanel(gridLayout);
        JTextField tfA = new JTextField();
        JTextField tfB = new JTextField();
        JTextField tfC = new JTextField();

        tfA.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfB.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfC.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tfA.setHorizontalAlignment(SwingConstants.CENTER);
        tfB.setHorizontalAlignment(SwingConstants.CENTER);
        tfC.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(new JLabel("Hệ số a", SwingConstants.CENTER));
        mainPanel.add(tfA);
        mainPanel.add(new JLabel("Hệ số b", SwingConstants.CENTER));
        mainPanel.add(tfB);
        mainPanel.add(new JLabel("Hệ số c", SwingConstants.CENTER));
        mainPanel.add(tfC);
        mainPanel.setBackground(new Color(230, 230, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.setMaximumSize(new Dimension(500, 500));

        FrameBorder frame = new FrameBorder(50);
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}