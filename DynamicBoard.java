/*
@author  William Jordan
@version 0.1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DynamicBoard extends JPanel {
    int rows, columns, size, save, piece;
    int[][] board;
    Color[] colors = new Color[]{Color.red, Color.yellow, Color.orange, Color.green, Color.blue, Color.cyan,
                                    Color.magenta, Color.pink, Color.lightGray, Color.gray, Color.darkGray, Color.black};
    String[] printColor = new String[]{"red", "yellow", "orange", "green", "blue", "cyan", "magenta", "pink",
            "light gray", "gray", "dark gray", "black"};
    ArrayList<Color> colorList;
    ArrayList<BoardClickEvent> listeners;
    public DynamicBoard (int c, int r, int size) {
        this.rows = r;
        this.columns = c;
        this.size = size;
        listeners = new ArrayList<>();
        board = new int[columns][rows];
        for(int i=0; i < board.length; i++){
            for(int j=0; j < board[0].length; j++){
                board[i][j] = -1;
            }
        }
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (!(e.getX() > save * columns -1 || e.getY() < 0 || e.getY() > save * rows)) { //checks if out of the box
                    for (BoardClickEvent listener : listeners) {
                        listener.onBoardClicked(e.getX()/save);
                    }
                }
            }
        });

        colorList = new ArrayList<>();
        for(Color color : colors){
            colorList.add(color);
        }
    }
    protected void paintComponent(Graphics g) {
        this.getWidth();
        this.getHeight();
        save = Math.min((size / columns), (size / rows));
        //clear
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(238,238,238));
        g2d.fillRect(0,0,getWidth(),getHeight());
        for(int c=0; c<board.length; c++){
            for(int r=0; r<board[0].length; r++) {
                //draw boxes
                g2d.setColor(Color.black);
                g2d.drawRect(c * save, r * save, save, save);

                //draw pieces
                if (board[c][r] != -1) {
                    try {
                        g2d.setColor(colorList.get(board[c][r]));
                        g2d.fillOval(c * save, r * save, save, save);
                    } catch (Exception e) {
                        colorList.add(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                        g.setColor(colorList.get(board[c][r]));
                    }
                }
                g2d.setColor(Color.black);
                g2d.setFont(new Font("Dialog", Font.PLAIN, 14));
                if(r==1){
                    g2d.drawString((c+1)+"",c*save+1,15);
                }
            }
        }
    }

    /**
     * Updates the board given a new 2D int array.
     *
     * @param board the board.
     */
    public void updateBoard(int[][] board, int piece){
        this.board=board;
        this.piece=piece;
        columns = board.length;
        rows = board[0].length;
        save = Math.min((size / columns), (size / rows));
        this.repaint();
        repaint();
    }

    /**
     * Updates the size of the board given a number of rows and columns.
     *
     * @param columns the number of columns
     * @param rows the number of rows
     */
    public void updateBoard(int columns, int rows){
        board = new int[columns][rows];
        for(int c=0; c<board[0].length; c++) {
            for(int r=0; r<board.length; r++) {
                board[r][c] = -1;
            }
        }
        this.columns = columns;
        this.rows = rows;
        save = Math.min((size / columns), (size / rows));
        this.repaint();
    }

    public String getColor(int i){
        if(i<printColor.length)
            return printColor[i];
        else
            return "random";
    }

    /**
     * Adds specified listener to receive events form this Board.
     *
     * @param listener the event listener
     */
    public void addBoardClickListener(BoardClickEvent listener) {
        listeners.add(listener);
    }
}
