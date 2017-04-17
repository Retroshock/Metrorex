package Client.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adrian on 17.04.2017.
 */
public class BuyMenu extends JPanel{

    private JLabel title;
    public JButton buyMonthBtn;
    public JButton buyDayBtn;
    public JButton buyTenBtn;
    public JButton buyTwoBtn;

    public BuyMenu(){
        this.setLayout(new GridBagLayout());
        initializeText();
        initializeButtons();
        initializeConstraints();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

    }

    private void initializeText() {
        title = new JLabel("<html><div style='text-align: center;'>" + "Cumpara: " + "</div></html>");
    }

    private void initializeButtons(){
        buyDayBtn = new JButton("Abonament de o zi");
        buyMonthBtn = new JButton("Abonament de o luna");
        buyTenBtn = new JButton("Cartela 10 calatorii");
        buyTwoBtn = new JButton("Cartela 2 calatorii");
    }

    private void initializeConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 5;
        constraints.ipady = 5;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(title, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        this.add(buyMonthBtn, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        this.add(buyDayBtn, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        this.add(buyTenBtn, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        this.add(buyTwoBtn, constraints);


    }
}
