package Client.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adrian on 17.04.2017.
 */
public class BuyMenu extends JPanel{

    private JLabel title;
    private JButton buyMonthBtn;
    private JButton buyDayBtn;
    private JButton buyTenBtn;
    private JButton buyTwoBtn;
    private JButton validationBtn;

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
        title = new JLabel("<html><div style='text-align: center; width: 100%;'>" + "Cumpara: " + "</div></html>");
    }

    private void initializeButtons(){
        buyDayBtn = new JButton("Abonament de o zi");
        buyMonthBtn = new JButton("Abonament de o luna");
        buyTenBtn = new JButton("Cartela 10 calatorii");
        buyTwoBtn = new JButton("Cartela 2 calatorii");
        validationBtn = new JButton("Valideaza cartela!");
        validationBtn.setEnabled(false);
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

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        this.add(validationBtn, constraints);

    }

    public JButton getBuyMonthBtn() {
        return buyMonthBtn;
    }

    public JButton getBuyDayBtn() {
        return buyDayBtn;
    }

    public JButton getBuyTenBtn() {
        return buyTenBtn;
    }

    public JButton getBuyTwoBtn() {
        return buyTwoBtn;
    }

    public JButton getValidationBtn() {
        return validationBtn;
    }
}
