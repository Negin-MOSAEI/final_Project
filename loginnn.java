import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
class login extends JFrame implements ActionListener {
    JButton register = new JButton("register");
    JButton enter = new JButton("enter");
    JTextField user_name = new JTextField("user name");
    JTextField pass = new JTextField("password");
    public static Boolean checkUser(String username,String password) {

        User user = null;
        String query = "SELECT USERNAME,PASSWORD FROM users WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    login(){
        this.setTitle("login");
        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(true);
        register.addActionListener(this);
        enter.addActionListener(this);
        panell();
    }
    public void panell(){
        JPanel base_panel=new JPanel();
        base_panel.setBackground(Color.gray);
        base_panel.setSize(500,500);
        base_panel.setLayout(null);
        JLabel text= new JLabel("write your information");
        user_name.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user_name.setText("");
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        pass.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pass.setText("");
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        text.setBounds(180, 30, 200, 30);
        enter.setBounds(30,420,100,30);
        register.setBounds(360,420,100,30);
        user_name.setBounds(150,260,200,30);
        pass.setBounds(150,300,200,30);
        base_panel.add(text);
        base_panel.add(enter);
        base_panel.add(register);
        base_panel.add(pass);
        base_panel.add(user_name);
        this.add(base_panel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==register){
            new register();
            this.dispose();
        }
        else if(e.getSource()==enter){
            if(checkUser(user_name.getText(),pass.getText())){
                this.dispose();
                user_profile user=new user_profile(user_name.getText());
            }
           else{
                user_name.setText("wrong info");
                pass.setText("");
           }
        }
    }
}