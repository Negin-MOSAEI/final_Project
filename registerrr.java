import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
class register extends JFrame implements ActionListener {
    //adding buttons and textfields
    JButton login = new JButton("login");
    JButton submit = new JButton("submit");
    JTextField user_name = new JTextField("user name");
    JTextField pass = new JTextField("password");
    JTextField phone = new JTextField("phone");
    JTextField email = new JTextField("email");
    JTextField adminpass = new JTextField("if you are admin enter your pass");
    //method to check if a user already exists
    public static Boolean checkUser(String username) {
        User user = null;
        String query = "SELECT USERNAME FROM users WHERE USERNAME = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //method to hash the password using SHA-256 algorithm
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    //method to write user's data into database
    public void writedb(String name,String pass,String phone,String email,Boolean admincheck){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO USERS (USERNAME, PASSWORD,PHONE,EMAIL,ADMIN,BALANCE) VALUES (?, ?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,hashPassword(pass));
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, admincheck.toString());
            preparedStatement.setString(6, "0");
            int rowsAffected = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    register(){
        //set title and size for the register window
        this.setTitle("register");
        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(true);
        login.addActionListener(this);
        //adding action listener for login button
        submit.addActionListener(this);
        //adding action listener for submit button
        panell();
        //calling the method to set up the panel
    }
    public void panell(){
        //creating a panel for registration form
        JPanel base_panel=new JPanel();
        base_panel.setSize(500,500);
        base_panel.setLayout(null);
        //adding a label for instructions
        JLabel text= new JLabel("write your information");
        text.setBounds(180, 30, 200, 30);
        //adding input fields and buttons to the panel
        submit.setBounds(30,420,100,30);
        login.setBounds(360,420,100,30);
        user_name.setBounds(150,160,200,30);
        pass.setBounds(150,200,200,30);
        phone.setBounds(150,240,200,30);
        email.setBounds(150,280,200,30);
        adminpass.setBounds(150,320,200,30);
        //adding mouse listener to clear the field when clicked
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
        email.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                email.setText("");
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
        phone.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                phone.setText("");
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
        adminpass.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminpass.setText("");
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
        //adding components to the panel

        base_panel.add(text);
        base_panel.add(submit);
        base_panel.add(login);
        base_panel.add(user_name);
        base_panel.add(pass);
        base_panel.add(phone);
        base_panel.add(email);
        base_panel.add(adminpass);
        this.add(base_panel);
        //adding the panel to the window
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //check if the source of the action event is the login button
        if(e.getSource()==login){
            new login();
            this.dispose();
        }
        else if(e.getSource()==submit){
            //check if the source of the action event is the submit button
            Boolean admincheck=false;
            //check if the admin password matches root
            if(adminpass.getText().equals("root"))
                admincheck=true;
            //check if the username already exists
            if(checkUser(user_name.getText())){
                user_name.setText("user already exist");
                pass.setText("");
                phone.setText("");
                email.setText("");
                adminpass.setText("");
            }
            //validate the langth and format of user inputs
            else if (user_name.getText().length() <4) {
                JOptionPane.showMessageDialog(null, " address must be at least 4 characters long");
            } else if (phone.getText().length() < 4) {
                JOptionPane.showMessageDialog(null, " number must be at least 4 characters long");
            } else if (!phone.getText().matches("^98\\d{10}$")) {
                JOptionPane.showMessageDialog(null, " number must be valid");
            } else if (user_name.getText().length() < 4) {
                JOptionPane.showMessageDialog(null, "user name must be at least 4 characters long");
            } else if (pass.getText().length() < 4) {
                JOptionPane.showMessageDialog(null, "password must be at least 4 characters long");
            } else if (!pass.getText().matches(".*[A-Z].*") || !pass.getText().matches(".*[a-z].*") ) {
                JOptionPane.showMessageDialog(null, "password must contain uppercase and lowercase latters");
            }
//if all input validation pass,creat a new user profile and write to the databasw
            else{
                this.dispose();
                writedb(user_name.getText(),pass.getText(),phone.getText(),email.getText(),admincheck);
                user_profile user=new user_profile(user_name.getText());
            }
        }
    }
} 
