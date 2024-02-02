import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BrickPanel extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT = 20;
    Random random;
    Timer timer;


    int ballPosX = WIDTH / 2 - (UNIT / 2);
    int ballPosY = HEIGHT - 4 * UNIT;

    int counter = 0;

    int xVelocity = 3;

    int yVelocity = 2;
    int lives = 3;

    boolean isRunning = false;


    static final int TIM_DEL = 5;
    int brickStartXPosition[][] = new int[600][600];
    int brickStartYPosition[][] = new int[600][600];

    int brickVisited[][] = new int[600][600];
    Rectangle rectangle;
    int rectX = WIDTH / 2 - UNIT * 3;
    int rectY = HEIGHT - 2 * UNIT;
    JLabel label = new JLabel();
    JLabel label2 = new JLabel();


    BrickPanel() {

        rectangle = new Rectangle(rectX, rectY, 120, 20); // Create a rectangle

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                rectX = mouseX - rectangle.width / 2;

                if (rectX < 0) {
                    rectX = 0;
                } else if (rectX + rectangle.width > getWidth()) {
                    rectX = getWidth() - rectangle.width;
                }
                repaint();
            }
        });
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        random = new Random();
        this.setBackground(Color.PINK);
        this.setFocusable(true);
        timer = new Timer(TIM_DEL, this);
        start();

        label2.setForeground(Color.white);
        label2.setText("Lives: " + lives);
        label2.setBounds(20, 500, 200, 50);
        label2.setFont(new Font("Comic Sans", Font.BOLD, 20));
        this.add(label2);


    }

    public void start() {
        isRunning = true;
        timer.start();

    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        for (int i = 0; i < (HEIGHT / UNIT); i++) {
//            g.drawLine(i * UNIT * 2, 0, i * UNIT * 2, HEIGHT);
//            g.drawLine(0, i * UNIT, WIDTH, i * UNIT);
//        }


        drawRectangles(g);
        drawMovingRect(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (isRunning) {
            g.setColor(Color.BLACK);
            g.fillOval(ballPosX, ballPosY, 20, 20);
            repaint();

        }
    }

    public void drawMovingRect(Graphics g) {
        if (isRunning) {
            g.setColor(Color.RED);
            g.fillRect(rectX, rectY, rectangle.width, rectangle.height);
        }
    }


    public void drawRectangles(Graphics g) {
        if (isRunning) {
            for (int j = 0; j < HEIGHT / 4; j = j + UNIT) {
                for (int i = 0; i < WIDTH / (UNIT * 2); i++) {
//          g.setColor(new Color(random.nextInt(0,255),random.nextInt(0,255), random.nextInt(0,255)));


                    brickStartXPosition[j][i] = i * UNIT * 2;
                    brickStartYPosition[j][i] = j;
                    if (counter == 120) {
                        gameOver();
                    }

                    if (ballPosX >= brickStartXPosition[j][i] && ballPosX <= brickStartXPosition[j][i] + (UNIT * 2)
                            && ballPosY >= brickStartYPosition[j][i] && ballPosY <= brickStartYPosition[j][i] + UNIT) {
                        brickVisited[j][i] = brickVisited[j][i] + 1;
                    }


                    if (brickVisited[j][i] == 0) {
//                        g.setColor(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)));
//                        xVelocity = xVelocity*-1;
//                        yVelocity = -yVelocity;
                        g.setColor(Color.BLUE);
                        g.fillRect(brickStartXPosition[j][i], brickStartYPosition[j][i], (UNIT * 2), UNIT);
                        g.setColor(Color.WHITE);
                        g.drawRect(brickStartXPosition[j][i], brickStartYPosition[j][i], UNIT * 2, UNIT);
                    }

//                    if (brickVisited[j][i]==1) {
//                        g.clearRect(brickStartXPosition[j][i], brickStartYPosition[j][i], (UNIT * 2), UNIT);
//                        g.setColor(new Color(0, 150, 255));
//                        g.fillRect(brickStartXPosition[j][i], brickStartYPosition[j][i], UNIT * 2, UNIT);
//                        g.setColor(Color.white);
//                        g.drawRect(brickStartXPosition[j][i], brickStartYPosition[j][i], UNIT * 2, UNIT);
//                    }

                    if (brickVisited[j][i] == 1) {
                        counter++;
                        brickVisited[j][i] = brickVisited[j][i] + 1;
//                        g.setColor(Color.PINK);
                        yVelocity = -yVelocity;
                        g.clearRect(brickStartXPosition[j][i], brickStartYPosition[j][i], UNIT * 2, UNIT);
//                        System.out.println(counter);
                    }

                }
            }
        }
    }


    public void gameOver() {
        this.remove(label2);
        isRunning = false;
        timer.stop();
        this.setBackground(Color.BLACK);
        if (counter == 120) {
            label.setForeground(Color.white);
            label.setText("You won!");
            label.setBounds(220, 330, 150, 50);
            label.setFont(new Font("Comic Sans", Font.BOLD, 20));
            this.add(label);
        } else {
            label.setForeground(Color.white);
            label.setText("Game over! Score: " + counter);
            label.setBounds(200, 330, 300, 50);
            label.setFont(new Font("Comic Sans", Font.BOLD, 20));
            this.add(label);
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
            repaint();
        }

    }



    public void move() {
        if (ballPosX >= WIDTH - 20 || ballPosX <= 0) {
            xVelocity = xVelocity * -1;
        }
        ballPosX = ballPosX + xVelocity;
        if (ballPosY <= 0) {
            yVelocity = yVelocity * -1;
        }
        if (ballPosY == rectY - 20 && ballPosX >= rectX && ballPosX <= rectX + 120) {
            yVelocity = yVelocity * -1;
        }
        ballPosY = ballPosY - yVelocity;

        if (ballPosY >= HEIGHT - 20) {
            lives--;
            timer.stop();
            ballPosX = WIDTH / 2 - (UNIT / 2);
            ballPosY = HEIGHT - 4 * UNIT;

            label2.setText("Lives: " + lives);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!timer.isRunning()){
                        timer.start();
                        repaint();
                    }

                };
            });


            if (lives == 0) {
                gameOver();
            }



        }






    }



}
