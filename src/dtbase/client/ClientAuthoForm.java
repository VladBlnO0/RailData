package dtbase.client;

import dtbase.MyJDBC;
import dtbase.RequestsForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientAuthoForm extends JFrame{
    private JPanel clientPanel;
    private JButton button1;
    private JTextField textField1;

    public ClientAuthoForm() {
        setContentPane(clientPanel);
        setTitle("Client Login");
        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ClientCompany = textField1.getText();
                if (textField1.equals("")) {
                    JOptionPane.showMessageDialog(clientPanel,"Some Field Are Empty!","Error",1);
                } else {
                    try {
                        Connection con = MyJDBC.getConnection();
                        PreparedStatement pst = con.prepareStatement("select * from clients where nameClientCompany=?");
                        pst.setString(1,ClientCompany);
                        ResultSet rs =pst.executeQuery();
                        if (rs.next()) {
                            setVisible(false);
                            ClientGetForm formCGF = new ClientGetForm(ClientCompany);
                            formCGF.setVisible(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(clientPanel,"Username Not Matched!","Error",1);
                        }
                    } catch (Exception ex) {
                        System.out.println(""+ex);
                    }
                }
            }
        });
    }
}
