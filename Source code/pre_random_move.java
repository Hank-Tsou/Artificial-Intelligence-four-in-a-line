import java.util.Random;

public class pre_random_move {

	public Board AI_first_move(Board board) throws CloneNotSupportedException {
		int[][] first_moves = {{3,3}, {3,4}, {4,3}, {4,4}};
		 
		Random rand = new Random();
		int steps = rand.nextInt(4);
		
		board = board.generateSuccessor('X', first_moves[steps][0], first_moves[steps][1]);
		System.out.println("-------- AI -------");
		board.printBoard();
		System.out.println("AI move: " + ((char)(first_moves[steps][0]+97)) + (first_moves[steps][1]+1));
		System.out.println();
		
		return board;
	}

	public Board AI_second_move(Board board) throws CloneNotSupportedException {
		int Xx=0, Xy=0, Ox=0, Oy=0;
		int decision_X = 0, decision_Y = 0;
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(board.board[i][j] == 'X') {
					Xx = i;
					Xy = j;
				}
				if(board.board[i][j] == 'O') {
					Ox = i;
					Oy = j;
				}
			}
		}
		
		if(Xx==Ox) {
			
			decision_Y = Xy;
			
			if(Xx <= 3) {
				decision_X = Xx+1;
			}
			else {
				decision_X = Xx-1;
			}
		}
		
		if(Xy==Oy) {
			
			decision_X = Xx;
			
			if(Xy <= 3) {
				decision_Y = Xy+1;
			}
			else {
				decision_Y = Xy-1;
			}
		}
	
		// section 1
		if(Xx > Ox && Xy > Oy) {
			if(Xx == 3 && Xy == 3) {
				decision_X = 3;
				decision_Y = 4;
			}
			if(Xx == 3 && Xy == 4) {
				decision_X = 4;
				decision_Y = 4;
			}
			if(Xx == 4 && Xy == 3) {
				decision_X = 4;
				decision_Y = 4;
			}
			if(Xx == 4 && Xy == 4) {
				decision_X = 4;
				decision_Y = 3;
			}
		}
		
		// section 2
		if(Xx > Ox && Xy < Oy) {
			if(Xx == 3 && Xy == 3) {
				decision_X = 4;
				decision_Y = 3;
			}
			if(Xx == 3 && Xy == 4) {
				decision_X = 3;
				decision_Y = 3;
			}
			if(Xx == 4 && Xy == 3) {
				decision_X = 4;
				decision_Y = 4;
			}
			if(Xx == 4 && Xy == 4) {
				decision_X = 4;
				decision_Y = 3;
			}
		}
		
		// section 3
		if(Xx < Ox && Xy > Oy) {
			if(Xx == 3 && Xy == 3) {
				decision_X = 3;
				decision_Y = 4;
			}
			if(Xx == 3 && Xy == 4) {
				decision_X = 3;
				decision_Y = 3;
			}
			if(Xx == 4 && Xy == 3) {
				decision_X = 4;
				decision_Y = 4;
			}
			if(Xx == 4 && Xy == 4) {
				decision_X = 3;
				decision_Y = 4;
			}
		}
		
		// section 4
		if(Xx < Ox && Xy < Oy) {
			if(Xx == 3 && Xy == 3) {
				decision_X = 3;
				decision_Y = 4;
			}
			if(Xx == 3 && Xy == 4) {
				decision_X = 3;
				decision_Y = 3;
			}
			if(Xx == 4 && Xy == 3) {
				decision_X = 3;
				decision_Y = 3;
			}
			if(Xx == 4 && Xy == 4) {
				decision_X = 3;
				decision_Y = 4;
			}
		}
		
		board = board.generateSuccessor('X', decision_X, decision_Y);
		System.out.println("-------- AI -------");
		board.printBoard();
		System.out.println("AI move: " + ((char)(decision_X+97)) + (decision_Y+1));
		System.out.println();
		
		return board;
	}

	public Board AI_connect_move(Board board) throws CloneNotSupportedException {
		
		int Xx=0, Xy=0, Ox=0, Oy=0;
		int decision_X = 0, decision_Y = 0;
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(board.board[i][j] == 'O') {
					Ox = i;
					Oy = j;
				}
			}
		}
		
		if(Ox < 4 && Oy < 4) {
			if(7-Ox > 7-Oy) {
				decision_X = Ox+1;
				decision_Y = Oy;
			}
			else {
				decision_X = Ox;
				decision_Y = Oy+1;
			}	
		}
		
		else if(Ox < 4 && Oy >= 4) {
			if(7-Ox > Oy) {
				decision_X = Ox+1;
				decision_Y = Oy;
			}
			else {
				decision_X = Ox;
				decision_Y = Oy-1;
			}	
		}
		
		else if(Ox >= 4 && Oy < 4) {
			if(Ox > 7-Oy) {
				decision_X = Ox-1;
				decision_Y = Oy;
			}
			else {
				decision_X = Ox;
				decision_Y = Oy+1;
			}	
		}
		
		else if(Ox >= 4 && Oy >= 4) {
			if(Ox > Oy) {
				decision_X = Ox-1;
				decision_Y = Oy;
			}
			else {
				decision_X = Ox;
				decision_Y = Oy-1;
			}	
		}
		
		board = board.generateSuccessor('X', decision_X, decision_Y);
		System.out.println("-------- AI -------");
		board.printBoard();
		System.out.println("AI move: " + ((char)(decision_X+97)) + (decision_Y+1));
		System.out.println();
		
		return board;
	}
	
}
