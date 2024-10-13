import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener{
    int WIDTH,HEIGHT;
    JCheckBox checkBox = new JCheckBox("2 Player");
    JButton bgColor=new JButton("Color"),outlineColor=new JButton("Color"),player1Color=new JButton("Color"),player2Color=new JButton("Color");
    Color bg=Color.black,outline=Color.white,player1=Color.blue,player2=Color.red;

    Menu(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
        checkBox.setBounds(30,300,80,30);
        checkBox.setFont(new Font("Arial",Font.PLAIN,15));
        checkBox.setFocusable(false);
        checkBox.setBackground(Color.BLACK);
        checkBox.setForeground(Color.WHITE);
        //checkBox.setSelected(true);
        player1Color.setBounds(150,335,80,20);
        player1Color.setFocusable(false);
        player1Color.addActionListener(this);
        player1Color.setForeground(player1);
        player2Color.setBounds(150,365,80,20);
        player2Color.setFocusable(false);
        player2Color.addActionListener(this);
        player2Color.setForeground(player2);
        bgColor.setBounds(150,395,80,20);
        bgColor.setFocusable(false);
        bgColor.addActionListener(this);
        bgColor.setForeground(bg);
        outlineColor.setBounds(150,425,80,20);
        outlineColor.setFocusable(false);
        outlineColor.addActionListener(this);
        outlineColor.setForeground(outline);
        this.add(checkBox);
        this.add(player1Color);
        this.add(player2Color);
        this.add(bgColor);
        this.add(outlineColor);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,40));
        g.drawString("CONNECT FOUR",100,50);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Player 1 Color: ",30,350);
        g.drawString("Player 2 Color: ",30,380);
        g.drawString("BG Color: ",30,410);
        g.drawString("Outline Color: ",30,440);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==player1Color){
            new JColorChooser();
            Color color = JColorChooser.showDialog(null,"Pick a Color for Player 1",bg);
            player1 = color;
            player1Color.setForeground(color);
        }
        else if(e.getSource()==player2Color){
            new JColorChooser();
            Color color = JColorChooser.showDialog(null,"Pick a Color for Player 2",bg);
            player2 = color;
            player2Color.setForeground(color);
        }
        else if(e.getSource()==bgColor){
            new JColorChooser();
            Color color = JColorChooser.showDialog(null,"Pick a Color for the Background",bg);
            bg = color;
            bgColor.setForeground(color);
        }
        else if(e.getSource()==outlineColor){
            new JColorChooser();
            Color color = JColorChooser.showDialog(null,"Pick a Color for the Outline",outline);
            outline = color;
            outlineColor.setForeground(color);
        }
    }
}