/*
@author  William Jordan
@version 0.1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Graphics implements ActionListener { //Ties, turn skip
    JFrame frame;
    JPanel rowsPanel, rowsButtons, columnsPanel, columnButtons, togglePanel, addPanel, controlPanel, playerPanel, updatePanel, targetPanel, targetButtons;
    JLabel rows, columns, playersLabel, update, connect, numbers;
    Button rowsUp, rowsDown, columnsUp, columnsDown, targetUp, targetDown, toggleContinues, addPlayer, addBot, removePlayer, start, next, stop, newGame;
    int row = 6, column = 7, target = 4;
    ArrayList <String> players = new ArrayList<>();
    Boolean continues = false;
    ConnectFour c4;
    DynamicBoard d;

    public Graphics(){
        //Frame setup
        frame = new JFrame();
        frame.setLayout(null);
        frame.setPreferredSize(new Dimension(719, 768));
        frame.setTitle("Connect X");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panels
        rowsPanel = new JPanel(new GridLayout(1,1));
        rowsPanel.setBounds(0,0,96,64);

        rowsButtons = new JPanel(new GridLayout(2,1));
        rowsButtons.setBounds(96,0,32,64);

        columnsPanel = new JPanel(new GridLayout(1,1));
        columnsPanel.setBounds(128,0,96,64);

        columnButtons = new JPanel(new GridLayout(2,1));
        columnButtons.setBounds(224,0,32,64);

        togglePanel = new JPanel(new GridLayout(1,2));
        togglePanel.setBounds(384,0,128,64);

        targetPanel = new JPanel(new GridLayout(1,1));
        targetPanel.setBounds(256,0,96,64);

        targetButtons = new JPanel(new GridLayout(2,1));
        targetButtons.setBounds(352,0,32,64);

        addPanel = new JPanel(new GridLayout(1,3));
        addPanel.setBounds(512,0,192,64);

        controlPanel = new JPanel(new GridLayout(1,4));
        controlPanel.setBounds(0,64,512,64);

        playerPanel = new JPanel(new GridLayout(1,1));
        playerPanel.setBounds(512,64,1024,64);

        updatePanel = new JPanel(new GridLayout(1,1));
        updatePanel.setBounds(0,128,1024, 64);

        //Labels
        rows = new JLabel("Rows: " + row);
        rowsPanel.add(rows);

        columns = new JLabel("Columns: " + column);
        columnsPanel.add(columns);

        connect = new JLabel("Connect " + target);
        targetPanel.add(connect);

        playersLabel = new JLabel("Players: ");
        playersLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        playerPanel.add(playersLabel);

        update = new JLabel("");
        update.setFont(new Font("Dialog", Font.PLAIN, 18));
        updatePanel.add(update);

        //Buttons
        rowsUp = new Button("+");
        rowsUp.setName("rowsUp");
        rowsUp.addActionListener(this);
        rowsButtons.add(rowsUp);

        rowsDown = new Button("-");
        rowsDown.setName("rowsDown");
        rowsDown.addActionListener(this);
        rowsButtons.add(rowsDown);

        columnsUp = new Button("+");
        columnsUp.setName("columnsUp");
        columnsUp.addActionListener(this);
        columnButtons.add(columnsUp);

        columnsDown = new Button("-");
        columnsDown.setName("columnsDown");
        columnsDown.addActionListener(this);
        columnButtons.add(columnsDown);

        targetUp = new Button("+");
        targetUp.setName("targetUp");
        targetUp.addActionListener(this);
        targetButtons.add(targetUp);

        targetDown = new Button("-");
        targetDown.setName("targetDown");
        targetDown.addActionListener(this);
        targetButtons.add(targetDown);

        toggleContinues = new Button("Toggle Continues");
        toggleContinues.setName("toggleContinues");
        toggleContinues.addActionListener(this);
        toggleContinues.setBackground(Color.red);
        togglePanel.add(toggleContinues);

        addPlayer = new Button("Add Person");
        addPlayer.setName("addPlayer");
        addPlayer.addActionListener(this);
        addPanel.add(addPlayer);

        addBot = new Button("Add Bot");
        addBot.setName("addBot");
        addBot.addActionListener(this);
        addPanel.add(addBot);

        removePlayer = new Button("- Player");
        removePlayer.setName("removePlayer");
        removePlayer.addActionListener(this);
        addPanel.add(removePlayer);

        start = new Button("Start");
        start.setName("start");
        start.addActionListener(this);
        controlPanel.add(start);

        next = new Button("Next");
        next.setName("next");
        next.addActionListener(this);
        next.setEnabled(false);
        controlPanel.add(next);

        stop = new Button("Stop");
        stop.setName("stop");
        stop.addActionListener(this);
        stop.setEnabled(false);
        controlPanel.add(stop);

        newGame = new Button("New Game");
        newGame.setName("newGame");
        newGame.addActionListener(this);
        newGame.setEnabled(false);
        controlPanel.add(newGame);

        //board
        d = new DynamicBoard(7,6,512-2);
        d.setBounds(0,192,512,512);
        d.addBoardClickListener(i -> c4.move(i+1));

        //Fill Frame
        frame.add(d);
        frame.add(rowsPanel);
        frame.add(rowsButtons);
        frame.add(columnsPanel);
        frame.add(columnButtons);
        frame.add(targetPanel);
        frame.add(targetButtons);
        frame.add(togglePanel);
        frame.add(addPanel);
        frame.add(controlPanel);
        frame.add(playerPanel);
        frame.add(updatePanel);
        //Final Frame setup
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e+"";
        //prepare string for switch
        s=s.substring(s.indexOf(" on ")+4);

        switch (s){
            case "rowsUp" -> {
                row++;
                rows.setText("Rows: " + row);
                d.updateBoard(column,row);
                rowsDown.setEnabled(true);
            }
            case "rowsDown" -> {
                row--;
                rows.setText("Rows: " + row);
                d.updateBoard(column,row);
                if(row == 1){
                    rowsDown.setEnabled(false);
                }
            }
            case "columnsUp" -> {
                column++;
                columns.setText("Columns: " + column);
                d.updateBoard(column,row);
                columnsDown.setEnabled(true);
            }
            case "columnsDown" -> {
                column--;
                columns.setText("Columns: " + column);
                d.updateBoard(column,row);
                if(column == 1){
                    columnsDown.setEnabled(false);
                }
            }
            case "targetUp" -> {
                target++;
                connect.setText("Connect " + target);
                targetDown.setEnabled(true);
            }
            case "targetDown" -> {
                target--;
                connect.setText("Connect " + target);
                if(target == 1){
                    targetDown.setEnabled(false);
                }
            }
            case "toggleContinues" -> {
                if(continues) {
                    continues = false;
                    toggleContinues.setBackground(Color.red);
                }
                else {
                    continues = true;
                    toggleContinues.setBackground(Color.green);
                }
            }
            case "addPlayer" -> {
                players.add("P");
                playersLabel.setText(playersLabel.getText() + "P, ");
            }
            case "addBot" -> {
                players.add("B");
                playersLabel.setText(playersLabel.getText() + "B, ");
            }
            case "removePlayer" -> {
                players.remove(players.size()-1);
                playersLabel.setText(playersLabel.getText().substring(0, playersLabel.getText().length()-3));

            }
            case "start" -> {
                next.setEnabled(true);
                stop.setEnabled(true);
                start.setEnabled(false);
                c4 = new ConnectFour(row, column, continues, players.toArray(String[]::new),target);
                c4.addConnectFourBoardUpdateEvent((board, piece) -> d.updateBoard(board, piece));
                c4.addConnectFourTextUpdateEvent((str) -> {
                    update.setText(str);

                    try {
                        update.setText(str.substring(0,str.indexOf('(')+1)+
                                d.getColor(Integer.parseInt(str.substring(str.indexOf("(")+1,
                                str.indexOf(")")))) + str.substring(str.indexOf(')')));
                    }
                    catch (Exception ignored){ }

                });
                c4.playAgain();
                d.updateBoard(column,row);
                c4.resetScores();
                c4.playAgain();
            }
            case "next" -> c4.playAgain();
            case "stop" -> {
                next.setEnabled(false);
                stop.setEnabled(false);
                newGame.setEnabled(true);
                c4.stop();
            }
            case "newGame" -> {
                start.setEnabled(true);
                newGame.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        new Graphics();
    }
}
