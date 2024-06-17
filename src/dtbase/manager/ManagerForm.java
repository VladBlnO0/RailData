package dtbase.manager;

import dtbase.MyJDBC;
import dtbase.RequestsForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class ManagerForm extends JFrame {
    private JPanel managerPanel;
    private JButton button1;
    private JPasswordField passwordField1;
    private JTextField textField1;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public ManagerForm() {
        setContentPane(managerPanel);
        setTitle("Manager Login");
        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();
                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(managerPanel,"Some Field Are Empty!","Error",1);
                } else {
                    try {
                        con = MyJDBC.getConnection();
                        pst = con.prepareStatement("select * from logins where username=? and password=?");
                        pst.setString(1,username);
                        pst.setString(2,password);
                        rs=pst.executeQuery();
                        if (rs.next()) {
                            setVisible(false);
                            RequestsForm formR = new RequestsForm();
                            formR.setVisible(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(managerPanel,"Username or Password Not Matched!","Error",1);
                        }
                        con.close();
                    } catch (Exception ex) {
                        System.out.println(""+ex);
                    }
                }

            }
        });
    }
}
