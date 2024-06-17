package dtbase.admin;

import dtbase.MyJDBC;
import dtbase.RequestsForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminForm extends JFrame {
    private JPanel adminPanel;
    private JButton button1;
    private JPasswordField passwordField1;
    private JTextField textField1;

    public AdminForm() {
        setContentPane(adminPanel);
        setTitle("Admin Login");
        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();
                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(adminPanel,"Some Field Are Empty!","Error",1);
                } else {
                    try {
                        Connection con = MyJDBC.getConnection();
                        PreparedStatement pst = con.prepareStatement("select * from logins where username=? and password=?");
                        pst.setString(1,username);
                        pst.setString(2,password);
                        ResultSet rs =pst.executeQuery();

                        if (rs.next()) {
                            setVisible(false);
                            AdminCreateForm formACF = new AdminCreateForm();
                            formACF.setVisible(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(adminPanel,"Username or Password Not Matched!","Error",1);
                        }
                    } catch (Exception ex) {
                        System.out.println(""+ex);
                    }
                }
            }
        });
    }
}
