package Client.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adrian on 17.04.2017.
 */
public class ClientInterface extends JPanel{

    private BuyMenu buyMenu;
    private ContCurent contCurent;
    private JTextField responseFromServer;

    public ClientInterface(){
        this.setLayout(new GridBagLayout());

        //Instantiez meniul de cumparare
        buyMenu = new BuyMenu();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.ipadx = 10;
        constraints.ipady = 40;
        this.add(buyMenu,constraints);

        //Instantiez fereastra cu cartelele existente, cumparate de utilizator
        contCurent = new ContCurent();
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.ipadx = 200;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(contCurent, constraints);

        responseFromServer = new JTextField();
        responseFromServer.setEditable(false);
        responseFromServer.setFont(new Font("Courier New", Font.PLAIN, 17));
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.ipady = 20;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(responseFromServer, constraints);

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }

    public BuyMenu getBuyMenu() {
        return buyMenu;
    }

    public ContCurent getContCurent() {
        return contCurent;
    }

    public JTextField getResponseFromServer() {
        return responseFromServer;
    }
}
