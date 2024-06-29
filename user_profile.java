import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class user_profile extends JFrame implements User, ActionListener {
    //declare instance variebles
    String user_name;
    String phone;
    String email;
    int balance;
    Boolean is_admin;
    JButton products= new JButton("products");
    //method to fill user informatin based on username
    public void fill_info(String username) {
        User user = null;
        String query = "SELECT PHONE, EMAIL,BALANCE,ADMIN FROM users WHERE USERNAME = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.phone= resultSet.getString("PHONE");
                this.email= resultSet.getString("EMAIL");
                this.balance= resultSet.getInt("BALANCE");
                this.is_admin=resultSet.getBoolean("ADMIN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //constructor for user profile class
    public user_profile(String name){
        this.setTitle("profile");
        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(true);
        this.user_name=name;

        fill_info(user_name);
        products.addActionListener(this);
        panell();
    }
    //method to set up the panels for user profile information
    public void panell() {
        JPanel toppanel = new JPanel();
        toppanel.setBackground(Color.cyan);
        toppanel.setBounds(0,0,500,100);
        toppanel.setLayout(null);
        JLabel name = new JLabel("name : "+user_name);
        name.setBounds(200,10,200,40);
        JPanel BottomPanel = new JPanel();
        BottomPanel.setBackground(Color.gray);
        BottomPanel.setBounds(0,100,500,400);
        BottomPanel.setLayout(null);
        JLabel phonelab = new JLabel("phone : "+phone);
        JLabel emaillab = new JLabel("email : "+email);
        JLabel balancelab = new JLabel("balance : "+balance);
        JLabel adminlab = new JLabel("you are admin :) ");
        phonelab.setBounds(200,10,200,40);
        emaillab.setBounds(200,80,200,40);
        balancelab.setBounds(200,150,200,40);
        adminlab.setBounds(200,220,200,40);
        products.setBounds(190,290,100,40);
        toppanel.add(name);
        BottomPanel.add(phonelab);
        BottomPanel.add(emaillab);
        BottomPanel.add(balancelab);
        BottomPanel.add(products);
        if(is_admin)
            BottomPanel.add(adminlab);
        this.add(toppanel);
        this.add(BottomPanel);
    }
//action performed method for actionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==products){
            this.dispose();
            new Product_list(user_name);
        }
    }
}