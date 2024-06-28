import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
public class Product_list extends JFrame implements ActionListener{
    int page=2,productnum=100;
    JButton next=new JButton("next page");
    JButton previous=new JButton("previous page");
    JButton profile = new JButton("profile");
    JButton client_cart = new JButton("cart");
    Product_list(){
        this.setTitle("Product_list");
        this.setSize(600, 700);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(true);
        next.addActionListener(this);
        previous.addActionListener(this);
        profile.addActionListener(this);
        client_cart.addActionListener(this);
        main_panel();
    }
    public void main_panel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.cyan);
        topPanel.setLayout(null);
        profile.setBounds(20,10,120,50);
        client_cart.setBounds(450,10,120,50);
        topPanel.add(profile);
        topPanel.add(client_cart);
        JPanel prodPanel = new JPanel();
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(null);
        BottomPanel.setBackground(Color.gray);
        topPanel.setBounds(0,0,600,100);
        prodPanel.setBounds(10,110,580,480);
        BottomPanel.setBounds(0,600,600,100);
        prodPanel.setLayout(new GridLayout(3,3,30,30));

        JPanel products[] = new JPanel[10];
        for(int i=0;i<9;i++){
            products[i]= new JPanel();
            products[i].setLayout(null);
            products[i].setBackground(Color.darkGray);
            JLabel price_label= new JLabel("price");
            JButton add_button= new JButton("add");
            price_label.setBounds(50,70,70,20);
            add_button.setBounds(50,95,70,20);
            products[i].add(price_label);
            products[i].add(add_button);
            prodPanel.add(products[i]);
        }
        if((productnum+8)/9>page){
            next.setBounds(450,5,120,50);
            BottomPanel.add(next);
        }
        JLabel page_label=new JLabel();
        page_label.setText(String.valueOf(page));
        page_label.setBounds(300,5,50,50);
        BottomPanel.add(page_label);
        if(1<page){
            previous.setBounds(20,5,120,50);
            BottomPanel.add(previous);
        }

        this.add(topPanel);
        this.add(prodPanel);
        this.add(BottomPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==previous){
            page--;
            this.getContentPane().removeAll();
            main_panel();
            this.revalidate();
            this.repaint();
        }else if(e.getSource()==next){
            page++;
            this.getContentPane().removeAll();
            main_panel();
            this.revalidate();
            this.repaint();
        }
    }
}