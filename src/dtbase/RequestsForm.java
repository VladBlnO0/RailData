package dtbase;

import dtbase.RequestsForms.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class RequestsForm extends JFrame{

    private JPanel requestsPanel;
    private JComboBox comboBox1;
    private JButton getButton;
    Connection con;

    public RequestsForm() {
        setContentPane(requestsPanel);
        setTitle("Requests");
        setSize(600,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ch = comboBox1.getSelectedIndex();
                switch (ch) {
                    case 0, 3, 4, 6, 7, 8:
                        WithoutParameter formWP0 = new WithoutParameter(ch);
                        formWP0.setVisible(true);
                        break;
                    case 1, 2, 5:
                        WithParameter formWP1 = new WithParameter(ch);
                        formWP1.setVisible(true);
                        break;
                }
            }
        });
    }
}
