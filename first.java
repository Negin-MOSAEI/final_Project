// Importing necessary Java libraries for GUI and event handling
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class declaration and implementation of ActionListener interface
class first extends JFrame implements ActionListener{
    JButton register = new JButton("register"); // Creating register button
    JButton login = new JButton("login"); // Creating login button

    // Constructor for the first class
    first(){
        this.setTitle("enter"); // Setting title of the window
        this.setSize(300, 250); // Setting size of the window
        this.setLayout(null); // Setting layout to null for absolute positioning
        this.setVisible(true); // Making the window visible
        this.setResizable(true); // Allowing window to be resizable
        register.addActionListener(this); // Adding action listener to register button
        login.addActionListener(this); // Adding action listener to login button
        panell(); // Calling the panell method to set up the panel
    }

    // Method to set up the panel with components
    public void panell(){
        JPanel base_panel=new JPanel(); // Creating a new panel
        base_panel.setSize(300,250); // Setting size of the panel
        base_panel.setLayout(null); // Setting layout to null for absolute positioning
        JLabel welcome= new JLabel("welcome"); // Creating a label
        welcome.setBounds(110,30,100,30); // Setting position and size of the label
        register.setBounds(30,170,100,30); // Setting position and size of the register button
        login.setBounds(160,170,100,30); // Setting position and size of the login button
        base_panel.add(welcome); // Adding the label to the panel
        base_panel.add(register); // Adding the register button to the panel
        base_panel.add(login); // Adding the login button to the panel
        this.add(base_panel); // Adding the panel to the window
    }

    // Overriding actionPerformed method from ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==login){ // Checking if the source of the action is the login button
            new login(); // Creating a new login object
            this.dispose(); // Disposing the current window
        }
        else if(e.getSource()==register){ // Checking if the source of the action is the register button
            new register(); // Creating a new register object
            this.dispose(); // Disposing the current window
        }
    }
}