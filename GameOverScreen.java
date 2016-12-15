import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends State{
    public void enter(){}
    public void exit(){}
    public GameOverScreen(GameState state){
        super(state);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        add(new JLabel("BLUIUUUUUUUUUUB"));
        add(new JLabel("BLUUUU:LFJ:LKJ;lfkja;lfjie"));
        JButton exit = new JButton("EXIT");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state.changeState(GameState.MAIN_MENU);
            }
        });
        add(exit);
    }
}

