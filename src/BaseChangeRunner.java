
import javax.swing.JFrame;

public class BaseChangeRunner {

    /**
     * creates a frame and skows it to the user
     * @param args
     */
    public static void main( String args[] ){

        BaseChangeFrame baseChangeFrame = new BaseChangeFrame();
        baseChangeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseChangeFrame.setSize(600,200);
        baseChangeFrame.setResizable(false);
        baseChangeFrame.setVisible(true);
    }
}
