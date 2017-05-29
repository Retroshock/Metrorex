package Client.View;

import Client.Controller.Controller;
import Client.View.ClientInterface;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Adrian on 17.04.2017.
 */
public class PaintView {

    private ClientInterface clientInterface;
    private JFrame ciFrame;

    public PaintView(){

        paintOnScreen();
    }

    public void paintOnScreen () {
        clientInterface = new ClientInterface();
        ciFrame = new JFrame();
        ciFrame.add(clientInterface);
        ciFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //ci.setBounds(100,200,800,500);
        ciFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ci.setUndecorated(true);
        ciFrame.setVisible(true);
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public JFrame getCiFrame() {
        return ciFrame;
    }
}
