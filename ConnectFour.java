/*
@author  William Jordan
@version 0.1.0
 */
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectFour {
    int[][] board;
    boolean gameOver = false, play = true, continues;
    final int TOTALMOVES;
    int moves = 0;
    int target;
    static int turn = 1;
    String[] players;
    static int[] piece;
    Ai[] AiList;
    int[] score;
    static ArrayList<ConnectFourBoardUpdateEvent> boardListeners;
    static ArrayList<ConnectFourTextUpdateEvent> textListeners;

    public ConnectFour(int rows, int columns, boolean continues, String[] players, int target){
        board = new int[columns][rows];
        this.continues = continues;
        TOTALMOVES = board.length * board[0].length;
        this.players = players;
        this.target=target;
        piece = new int[players.length];
        for(int i=0; i < players.length; i++){
            piece[i] = i;
        }
        AiList = new Ai[players.length];
        score = new int[piece.length];
        fill(board, -1);
        for(int i=0; i < AiList.length; i++) {
            if(players[i].equalsIgnoreCase("B")) {
                AiList[i] = new Ai(piece[i],piece);
                AiList[i].update(board);
            }
        }
        boardListeners = new ArrayList<>();
        textListeners = new ArrayList<>();
    }

    public void playAgain(){
        fill(board,-1);
        displayArray(board);
        gameOver=false;
        play = true;
        displayText("");
        if(players[turn-1].equalsIgnoreCase("B")) {
            move(0);
            displayArray(board);
        }
    }


    public void move(int n){
        if(play) {
            if(!gameOver) {
                if(players[turn-1].equalsIgnoreCase("B")) {
                    n=AiList[turn-1].moveRandom();
                }
                //check move
                if(!(n<1 || n> board.length || fall(board[n-1]) == -1)) {
                    //update board
                    displayArray(board);
                    board[n-1][fall(board[n-1])]=piece[turn-1];
                    moves++;
                    displayArray(board);
                }
                else if(moves < TOTALMOVES){
                    if(players[turn-1].equalsIgnoreCase("B")) {
                        move(0);
                    }
                }

                //check tie
                if(moves >= TOTALMOVES) {
                    gameOver=true;
                    displayText("Tie!");
                    moves=0;
                }
                //check win
                if(checkWin(board,n-1,fall(board[n-1])+1,piece[turn-1],target-1)) {
                    gameOver=true;
                    play = false;
                    moves=0;
                    score[turn-1]++;
                    displayText("Congratulations, P" + turn + "(" + piece[turn-1] +")" +
                            " is the winner! They have won " + score[turn-1] + " Times");
                    if(continues)
                        playAgain();
                }

                //advance turn
                if(!(n<1 || n> board.length || fall(board[n-1]) == -1)) {
                    turn++;
                    if (turn > piece.length) {
                        turn = 1;
                    }
                }
                if(players[turn-1].equalsIgnoreCase("B")) {
                    move(0);
                }
            }
        }
    }

    public static void fill (int[][] s , int filler) {
        for(int c=0; c<s[0].length; c++) {
            for(int r=0; r<s.length; r++) {
                s[r][c] = filler;
            }
        }
    }

    public void stop(){
        play = false;
    }

    public static int fall(int[] column) {
        if(column[0] != -1) {
            return -1;
        }
        for(int i=0; i<column.length-1; i++) {
            if(column[i+1] != -1) {
                return i;
            }
        }
        return column.length-1;
    }

    public void resetScores(){
        Arrays.fill(score, 0);
    }

    public void displayText(String s) {
        for(ConnectFourTextUpdateEvent listener: textListeners){
            listener.onConnectFourTextUpdate(s);
        }
    }

    public void displayArray(int[][] s) {
        for(ConnectFourBoardUpdateEvent listener: boardListeners) {
            listener.onConnectFourBoardUpdate(s, piece[turn-1]);
        }
    }

    public static boolean checkWin(int[][] b, int x, int y, int s, int t) {
        // North/South
        int total=0;
        boolean NS = true;
        for(int i=1; i<2*t; i++) {
            //North
            try {
                if(NS && b[x][y+i] == s) {
                    total++;
                }
                else {
                    NS=false;
                }
            }
            catch(Exception e) {
                NS=false;
            }
        }
        //South
        NS=true;
        for(int i=1; i<2*t; i++) {
            try {
                if(NS && b[x][y-i] == s) {
                    total++;
                }
                else {
                    NS=false;
                }
            }
            catch(Exception e) {
                NS=false;
            }
        }
        if(total>=t) {
            return true;
        }

        //East/West
        total=0;
        boolean EW = true;
        for(int i=1; i<2*t; i++) {
            //West
            try {
                if(EW && b[x+i][y] == s) {
                    total++;
                }
                else {
                    EW=false;
                }
            }
            catch(Exception e) {
                EW=false;
            }
        }
        //East
        EW=true;
        for(int i=1; i<2*t; i++) {
            try {
                if(EW && b[x-i][y] == s) {
                    total++;
                }
                else {
                    EW=false;
                }
            }
            catch(Exception e) {
                EW=false;
            }
        }

        if(total>=t) {
            return true;
        }

        //North-East / South-West
        total=0;
        boolean NESW = true;
        for(int i=1; i<2*t; i++) {
            //South-West
            try {
                if(NESW && b[x+i][y-i] == s) {
                    total++;
                }
                else {
                    NESW=false;
                }
            }
            catch(Exception e) {
                NESW=false;
            }
        }
        //North-East
        NESW=true;
        for(int i=1; i<2*t; i++) {
            try {
                if(NESW && b[x-i][y+i] == s) {
                    total++;
                }
                else {
                    NESW=false;
                }
            }
            catch(Exception e) {
                NESW=false;
            }
        }

        if(total>=t) {
            return true;
        }
        //North-West / South-East
        total=0;
        boolean NWSE = true;
        for(int i=1; i<2*t; i++) {
            //South-East
            try {
                if(NWSE && b[x+i][y+i] == s) {
                    total++;
                }
                else {
                    NWSE=false;
                }
            }
            catch(Exception e) {
                NWSE=false;
            }
        }
        //North-West
        NWSE=true;
        for(int i=1; i<2*t; i++) {
            try {
                if(NWSE && b[x-i][y-i] == s) {
                    total++;
                }
                else {
                    NWSE=false;
                }
            }
            catch(Exception e) {
                NWSE=false;
            }
        }

        if(total>=t) {
            return true;
        }
        return false;
    }

    public void addConnectFourBoardUpdateEvent(ConnectFourBoardUpdateEvent listener) {
        boardListeners.add(listener);
    }

    public void addConnectFourTextUpdateEvent(ConnectFourTextUpdateEvent listener) {
        textListeners.add(listener);
    }
}
