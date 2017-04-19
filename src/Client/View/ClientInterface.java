package Client.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adrian on 17.04.2017.
 */
public class ClientInterface extends JPanel{

    public BuyMenu buyMenu;
    public ContCurent contCurent;
    public ClientInterface(){
        this.setLayout(new GridBagLayout());

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

        contCurent = new ContCurent();
        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.ipadx = 200;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(contCurent, constraints);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }
}
