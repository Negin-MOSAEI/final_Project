import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class register extends JFrame implements ActionListener {
    JButton login = new JButton("login");
    JButton submit = new JButton("submit");
    JTextField user_name = new JTextField("user name");
    JTextField pass = new JTextField("password");
    JTextField phone = new JTextField("phone");
    JTextField email = new JTextField("email");
    JTextField adminpass = new JTextField("if you are admin enter your pass");
    register(){
        this.setTitle("register");
        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(true);
        login.addActionListener(this);
        submit.addActionListener(this);
        panell();
    }
    public void panell(){
        JPanel base_panel=new JPanel();
        base_panel.setSize(500,500);
        base_panel.setLayout(null);
        JLabel text= new JLabel("write your information");
        text.setBounds(180, 30, 200, 30);
        submit.setBounds(30,420,100,30);
        login.setBounds(360,420,100,30);
        user_name.setBounds(150,160,200,30);
        pass.setBounds(150,200,200,30);
        phone.setBounds(150,240,200,30);
        email.setBounds(150,280,200,30);
        adminpass.setBounds(150,320,200,30);
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
        base_panel.add(text);
        base_panel.add(submit);
        base_panel.add(login);
        base_panel.add(user_name);
        base_panel.add(pass);
        base_panel.add(phone);
        base_panel.add(email);
        base_panel.add(adminpass);
        this.add(base_panel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==login){
            new login();
            this.dispose();
        }
        else if(e.getSource()==submit){

        }
    }
}