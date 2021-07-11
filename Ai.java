/*
@author  William Jordan
@version 0.1.0
 */

public class Ai {
	int piece;
	int[] players;
	
	int[][] board;
	OrderedPair lastMove;
	public Ai(int piece, int[] players) {
		this.piece=piece;
		this.players=players;
	}

	public int moveRandom() {
		return (int) (Math.random()* board.length+1);
	}

	public void update(int[][] board) {
		this.board=board;
	}

	public void update(int[][] board, OrderedPair lastMove) {
		this.board=board;
		this.lastMove=lastMove;
	}

	public static boolean checkWin(String[][] b, int x, int y, String s) {
		// North/South
		int total=0;
		Boolean NS = true;
		for(int i=1; i<5; i++) {
			//North
			try {
				if(NS && b[x][y+i].equals(s)) {
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
		for(int i=1; i<5; i++) {
			try {
				if(NS && b[x][y-i].equals(s)) {
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

		if(total>=3) {
			return true;
		}

		//East/West
		total=0;
		Boolean EW = true;
		for(int i=1; i<6; i++) {
			//West
			try {
				if(EW && b[x+i][y].equals(s)) {
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
		for(int i=1; i<6; i++) {
			try {
				if(EW && b[x-i][y].equals(s)) {
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

		if(total>=3) {
			return true;
		}

		//North-East / South-West
		total=0;
		Boolean NESW = true;
		for(int i=1; i<6; i++) {
			//South-West
			try {
				if(NESW && b[x+i][y-i].equals(s)) {
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
		for(int i=1; i<6; i++) {
			try {
				if(NESW && b[x-i][y+i].equals(s)) {
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

		if(total>=3) {
			return true;
		}
		//North-West / South-East
		total=0;
		Boolean NWSE = true;
		for(int i=1; i<6; i++) {
			//South-East
			try {
				if(NWSE && b[x+i][y+i].equals(s)) {
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
		for(int i=1; i<6; i++) {
			try {
				if(NWSE && b[x-i][y-i].equals(s)) {
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

		if(total>=3) {
			return true;
		}
		return false;
	}
}