package Client;

import Client.View.ClientInterface;

import javax.swing.*;

/**
 * Created by Adrian on 17.04.2017.
 */
public class MainTest {
    public static void main(String[] args) {
        JFrame ci = new JFrame();
        ci.add(new ClientInterface());
        ci.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ci.setVisible(true);
        ci.setBounds(100,100,300,300);
    }
}
