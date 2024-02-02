import javax.swing.*;

public class BrickFrame extends JFrame {
        BrickFrame(){
            this.add(new BrickPanel());
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setTitle("Bricks");
            this.pack();
            this.setVisible(true);



        }

}
