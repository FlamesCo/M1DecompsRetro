import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pong extends JPanel implements ActionListener {
    private int ballX = 200, ballY = 100, ballVelX = 2, ballVelY = 2, ballDiameter = 15;
    private int p1Y = 100, p2Y = 100, paddleHeight = 100, paddleWidth = 15;
    private Timer timer;

    public Pong() {
        this.setPreferredSize(new Dimension(400, 300));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W && p1Y > 0) {
                    p1Y -= 5;
                }
                if (key == KeyEvent.VK_S && p1Y < getHeight() - paddleHeight) {
                    p1Y += 5;
                }
                if (key == KeyEvent.VK_UP && p2Y > 0) {
                    p2Y -= 5;
                }
                if (key == KeyEvent.VK_DOWN && p2Y < getHeight() - paddleHeight) {
                    p2Y += 5;
                }
            }
        });
        timer = new Timer(10, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        ballX += ballVelX;
        ballY += ballVelY;

        // Ball collision with top and bottom
        if (ballY <= 0 || ballY >= this.getHeight() - ballDiameter) {
            ballVelY = -ballVelY;
        }

        // Ball collision with paddles
        if (ballX <= paddleWidth && ballY > p1Y && ballY < p1Y + paddleHeight ||
            ballX >= this.getWidth() - paddleWidth - ballDiameter && ballY > p2Y && ballY < p2Y + paddleHeight) {
            ballVelX = -ballVelX;
        }

        // Ball out of bounds
        if (ballX < 0 || ballX > this.getWidth()) {
            // Reset ball to the center
            ballX = this.getWidth() / 2 - ballDiameter / 2;
            ballY = this.getHeight() / 2 - ballDiameter / 2;
        }

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(paddleWidth, p1Y, paddleWidth, paddleHeight);
        g.fillRect(this.getWidth() - paddleWidth * 2, p2Y, paddleWidth, paddleHeight);
        g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        Pong pongGame = new Pong();
        frame.add(pongGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
