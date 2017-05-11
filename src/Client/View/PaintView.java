package Client.View;

import Client.View.ClientInterface;

import javax.swing.*;

/**
 * Created by Adrian on 17.04.2017.
 */
public class PaintView {

    private ClientInterface clientInterface;

    public PaintView(){

        paintOnScreen();
    }

    public void paintOnScreen () {
        clientInterface = new ClientInterface();
        JFrame ci = new JFrame();
        ci.add(clientInterface);
        ci.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //ci.setBounds(100,200,800,500);
        ci.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //ci.setUndecorated(true);
        ci.setVisible(true);
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }
}
