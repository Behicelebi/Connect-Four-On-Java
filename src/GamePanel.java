import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class GamePanel extends JPanel implements ActionListener , MouseListener {
    int WIDTH, HEIGHT, tileSize=55;
    Color bgColor,outlineColor,player1Color,player2Color;
    JButton row1=new JButton(),row2=new JButton(),row3=new JButton(),row4=new JButton(),row5=new JButton(),row6=new JButton(),row7=new JButton();


    private static class Tile{
        int x,y;
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    Random random;
    boolean gameOver = false, turn=false, twoPlayer=true, gameOverNone=false;
    ArrayList<Tile> snakeBody;
    ArrayList<JButton> buttons = new ArrayList<>();
    Integer[][] placed = new Integer[12][12];//8,7

    GamePanel(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
        for(int i=0; i<12; i++){for(int j=0; j<12; j++){placed[i][j] = 0;}}
        buttons.add(row1);
        buttons.add(row2);
        buttons.add(row3);
        buttons.add(row4);
        buttons.add(row5);
        buttons.add(row6);
        buttons.add(row7);
        for(int i=0; i<buttons.size(); i++){
            buttons.get(i).setFocusable(false);
            buttons.get(i).setBounds(tileSize*(i+1),2*tileSize,tileSize,tileSize*6);
            buttons.get(i).setBackground(new Color(102,102,102,0));
            buttons.get(i).setOpaque(false);
            buttons.get(i).addActionListener(this);
            this.add(buttons.get(i));
        }
        random = new Random();
        snakeBody = new ArrayList<>();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //BACKGROUND
        g.setColor(bgColor);
        g.fillRect(0,0,WIDTH,HEIGHT);

        //OUTLINE
        g.setColor(outlineColor);
        g.fillRect(30,30+tileSize,17*25,25);
        g.fillRect(30,30+tileSize,25,14*25+5);
        g.fillRect(30,8*tileSize,17*25+10,25);
        g.fillRect(8*tileSize,30+tileSize,25,14*25+5);
        for (int i=1; i<8; i++){
            for(int j=1; j<7; j++){
                g.fillOval(tileSize*i,(tileSize*j)+tileSize,tileSize,tileSize);
            }
        }

        //PLACED
        for (int i=0; i<7; i++){
            for(int j=0; j<6; j++){
                if(placed[i][j]==1){
                    g.setColor(player1Color);
                    g.fillOval(tileSize*(i+1),(tileSize*(7-(j+1)))+tileSize,tileSize,tileSize);
                }else if(placed[i][j]==2){
                    g.setColor(player2Color);
                    g.fillOval(tileSize*(i+1),(tileSize*(7-(j+1)))+tileSize,tileSize,tileSize);
                }
            }
        }

        //GRID
        g.setColor(outlineColor);
        for(int i=1; i<(WIDTH/tileSize)-1; i++){
            g.drawLine(i*tileSize,tileSize*2,i*tileSize,HEIGHT-tileSize);
            if(i>1){g.drawLine(tileSize,i*tileSize,WIDTH-tileSize,i*tileSize);}
        }

        g.setFont(new Font("Arial",Font.PLAIN,16));
        g.drawString("CONNECT FOUR",tileSize*4-25,20);
        g.setColor(player2Color);
        g.drawString("P2 Score: "+ snakeBody.size(),tileSize*7,20);
        g.setColor(player1Color);
        g.drawString("P1 Score: "+ snakeBody.size(),tileSize-16,20);

        g.setFont(new Font("Arial",Font.PLAIN,20));
        if(!turn && !gameOver){g.drawString("PLAYER 1 TURN",tileSize*4-38,tileSize+10);}
        else if(turn && !gameOver){
            g.setColor(player2Color);
            g.drawString("PLAYER 2 TURN",tileSize*4-38,tileSize+10);
        }
        if(gameOver){
            g.setFont(new Font("Arial",Font.PLAIN,20));
            if(!gameOverNone){
                if(turn){
                    g.setColor(player1Color);
                    g.drawString("PLAYER 1 WON",tileSize*4-38,tileSize+10);
                }else{
                    g.setColor(player2Color);
                    g.drawString("PLAYER 2 WON",tileSize*4-38,tileSize+10);
                }
            }
            else{
                g.setColor(player1Color);
                g.drawString("GAME OVER",tileSize*4-38,tileSize+10);
            }
        }
    }

    public void checkWin(){
        int which=0, test=0;
        for (int i=0; i<7; i++){
            for(int j=0; j<6; j++){
                if (placed[i][j]!=0 && which==0) {which=placed[i][j];}
                if(which!=0){
                    if(placed[i][j+1]==which && placed[i][j+2]==which && placed[i][j+3]==which){gameOver=true;repaint();break;}
                    else if(placed[i+1][j]==which && placed[i+2][j]==which && placed[i+3][j]==which){gameOver=true;repaint();break;}
                    else if(placed[i+1][j+1]==which && placed[i+2][j+2]==which && placed[i+3][j+3]==which){gameOver=true;repaint();break;}
                    else if (j>2) {if(placed[i+1][j-1]==which && placed[i+2][j-2]==which && placed[i+3][j-3]==which){gameOver=true;repaint();break;}}
                }
                which=0;
                if(placed[i][5]!=0){test++;}
            }
            which=0;
        }
        if(test==7){gameOver=true;gameOverNone=true;}
        if(gameOver){for (JButton button : buttons) {button.setEnabled(false);}}
    }

    public void turn(){
        for (JButton button : buttons) {button.setEnabled(false);}
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                while(true){
                    int i = random.nextInt(7);
                    boolean test = false;
                    for(int j=0; j<6; j++){
                        if(placed[i][j]==0){
                            placed[i][j]=2;
                            turn = false;
                            checkWin();
                            test = true;
                            break;
                        }
                    }
                    if(test) {break;}
                }
                repaint();
                if(!gameOver){for (JButton button : buttons) {button.setEnabled(true);}}
            }
        };
        timer.schedule(task,1000);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i=0; i<buttons.size(); i++) {
            if (e.getSource() == buttons.get(i)){
                for(int j=0; j<6; j++){
                    if(placed[i][j]==0){
                        if(!turn){
                            placed[i][j]=1;
                            turn = true;
                            checkWin();
                            if(!twoPlayer && !gameOver){turn();}
                        }else{
                            placed[i][j]=2;
                            turn = false;
                            checkWin();
                        }
                        break;
                    }
                }
                repaint();
            }
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        for (JButton button : buttons) {
            if (e.getSource() == button){
                button.setBackground(new Color(102,102,102,116));
                repaint();
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (JButton button : buttons) {
            if (e.getSource() == button){
                button.setBackground(new Color(102,102,102,0));
                repaint();
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

}