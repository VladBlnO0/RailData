import dtbase.admin.AdminForm;
import dtbase.client.ClientAuthoForm;
import dtbase.manager.ManagerForm;

import javax.swing.*;

public class AuthenticatorForm extends JFrame {
    private JPanel authenticatorPanel;
    private JButton enterButton;
    private JComboBox comboBox1;
    public AuthenticatorForm() {
        setContentPane(authenticatorPanel);
        setTitle("Authenticator");
        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        enterButton.addActionListener(e -> {
            int ch = comboBox1.getSelectedIndex();
            switch (ch) {
                case 0:
                    setVisible(false);
                    AdminForm formA = new AdminForm();
                    formA.setVisible(true);
                    break;
                case 1:
                    setVisible(false);
                    ManagerForm formM = new ManagerForm();
                    formM.setVisible(true);
                    break;
                case 2:
                    setVisible(false);
                    ClientAuthoForm formC = new ClientAuthoForm();
                    formC.setVisible(true);
                    break;
            }
        });
    }
    public static void main(String[] args) {
        new AuthenticatorForm();
    }
}
