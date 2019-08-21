import java.util.ArrayList;

public class Board {
	
	int rows = 8, cols = 8;
	char[][] board;
	
	//--------------------------------------------------------------------------------------------------// create an empty board
	public Board()
	{
		this.board=new char[rows][cols];
		
		//fill the board up with blanks
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++)
				this.board[i][j]='-';
	}
	//--------------------------------------------------------------------------------------------------// end create an empty board
	
	//--------------------------------------------------------------------------------------------------// clone board function
	public Object clone() throws CloneNotSupportedException {
		
        Board new_board=new Board();
        
		for (int i=0; i<this.rows; i++)
			new_board.board[i] = (char[]) this.board[i].clone();
		
		return new_board;
	}
	//--------------------------------------------------------------------------------------------------// end clone board function
	
	//--------------------------------------------------------------------------------------------------// successor generator
	public Board generateSuccessor(char c, int row, int col) throws CloneNotSupportedException {
		
		Board new_board=(Board)this.clone();
		new_board.board[row][col] = c;
		
		return new_board;
	}
	//--------------------------------------------------------------------------------------------------// end successor generator
	
	//--------------------------------------------------------------------------------------------------// board printer
	public void printBoard(){
		System.out.println("-------------------");
		
		System.out.print("  ");
		for(int col_counter =1; col_counter<this.rows+1; col_counter++){
			System.out.print(col_counter+" ");
		}
		System.out.println();
		
		for(int i=0; i<this.rows; i++){
			System.out.print((char)(i+97)+" ");
			for(int j=0; j<this.cols; j++){
				System.out.print(this.board[i][j]+" ");
			}
			System.out.println();
		}	
		System.out.println("-------------------");
	}
	//--------------------------------------------------------------------------------------------------// end board printer
	
	//--------------------------------------------------------------------------------------------------// check the goal state
	public boolean isGoal(char c){
		
		String find = "" + c + "" + c + "" + c + "" + c;

		//check rows
		for(int i=0; i<this.rows; i++)
			if(String.valueOf(this.board[i]).contains(find))
				return true;
		
		//check cols
		for(int j=0; j<this.cols; j++){
			String col="";
			for(int i=0; i<this.rows; i++)
				col+=this.board[i][j];
			
			if(col.contains(find))
				return true;
		}
		return false;
	}
	//--------------------------------------------------------------------------------------------------// end check the goal state
    
	//--------------------------------------------------------------------------------------------------// get children after generator
	public ArrayList<ArrayList<Integer>> getChildren() {
		
		ArrayList<ArrayList<Integer>> outer = new ArrayList<ArrayList<Integer>>();
	    ArrayList<Integer> inner = new ArrayList<Integer>();
		
		for(int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				if(this.board[i][j]=='-') {
					inner.add(i);
					inner.add(j);
					outer.add(inner);
					inner = new ArrayList<Integer>();
				}
			}
		}
		return outer;
	}
	//--------------------------------------------------------------------------------------------------// end get children after generator
	
	//--------------------------------------------------------------------------------------------------// check the neighbor
	public boolean hasNeighbor(int x, int y ) {
		int distance = 2;
		
		for(int i = x-distance; i <= x+distance; i++) {
			if(i<0||i>=8) continue;
			if(i == x) continue;
			if(this.board[i][y] != '-') return true;
		}
		
		for(int j = y-distance; j<= y+distance; j++) {
			if(j<0||j>=8) continue;
			if(j == y) continue;
			if(this.board[x][j] != '-') return true;
		}
		
		return false;
	}
	//--------------------------------------------------------------------------------------------------// end check the neighbor

	//--------------------------------------------------------------------------------------------------// evaluation function
	public int evaluation(int X, int Y) {
		
		int result = 0;
		
		Point_score point_score = new Point_score();
		
		if(this.board[X][Y] == 'X') {
			result = point_score.score_point(this.board, X, Y, 'X');
		}
		if(this.board[X][Y] == 'O') {
			result = (point_score.score_point(this.board, X, Y, 'O'))*(-1);
		}
		
		return result;
	}
	//--------------------------------------------------------------------------------------------------// end evaluation function
	
	//--------------------------------------------------------------------------------------------------// check invalid input
	public boolean inputcheck(Board board, int row, int col) {

		if(col > 7 || row > 7) {
			return false;
		}

		System.out.println("error");
		if(board.board[row][col]!='-') {
			return false;
		}
		return true;
	}
	//--------------------------------------------------------------------------------------------------// end check invalid input

	//--------------------------------------------------------------------------------------------------// check game draw
	public boolean isDraw() {
		for(int i = 0; i<this.rows; i++) {
			for(int j = 0; j<this.cols; j++) {
				if(this.board[i][j]=='-') {
					return false;
				}
			}
		}
		return true;
	}

	//--------------------------------------------------------------------------------------------------// end check game draw
	
	//--------------------------------------------------------------------------------------------------// check killer move  ////////////////
	public boolean killer_check(Board board) throws CloneNotSupportedException {
		char x = 'X';
		int move = 0;
		
		ArrayList<String> killerCases = new ArrayList<String>();
		killerCases.add("-" + x + x + x + ""); // _ xxx
		killerCases.add( "" + x + x + x + "-"); // xxx _
		killerCases.add("-" + x + x + x + "-"); // _ xxx _
		killerCases.add("" + x + "-" + x + x + ""); // x_xx
		killerCases.add("" + x + x + "-" + x + ""); // xx_x
		
		for (int check = 0; check<killerCases.size(); check++) {
			//check rows
			for(int i=0; i<this.rows; i++) {
				if(String.valueOf(this.board[i]).contains(killerCases.get(check))) {
					
					if(check == 0 || check ==1 || check==2) {
						for (int j = 0; j< this.cols-2; j++) {
							if(this.board[i][j] == x && this.board[i][j+1] == x && this.board[i][j+2] == x ) {
								if(j-1>=0 && this.board[i][j-1] == '-') {
									board = board.generateSuccessor('X',i, j-1);
									move = j-1;
								}
								else if(j+3<8 && this.board[i][j+3]=='-') {
									board = board.generateSuccessor('X',i, j+3);
									move = j+3;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97)) + move);
								System.out.println();
							}
						}
					}
					else if(check ==3 || check==4) {
						for (int j = 0; j< this.cols-1; j++) {
							if(this.board[i][j] == x && this.board[i][j+1] == x) {
								if(j-2 >= 0 && this.board[i][j-1] == '-' && this.board[i][j-2] == x) {
									board = board.generateSuccessor('X',i, j-1);
									move = j-1;
								}
								else if(j+3 < 8 && this.board[i][j+2] == '-' && this.board[i][j+3] == x) {
									board = board.generateSuccessor('X',i, j+2);
									move = j+2;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97)) + move);
								System.out.println();
							}
						}	
					}
					return true;
				}
			}

			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(killerCases.get(check))) {
					
					if(check ==0 || check ==1 || check ==2){
						for (int i = 0; i< this.cols-2; i++) {
							if(this.board[i][j] == x && this.board[i+1][j] == x && this.board[i+2][j] == x ) {
								if(i-1>=0 && this.board[i-1][j] == '-') {
									board = board.generateSuccessor('X',i-1, j);
									move = i-1;
								}
								else if(i+3<8 && this.board[i+3][j]=='-') {
									board = board.generateSuccessor('X',i+3, j);
									move = i+3;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(move+97)) + (j+1));
								System.out.println();
							}
						}
					}
					
					else if(check ==3 || check==4) {
						for (int i = 0; i< this.rows-1; i++) {
							if(this.board[i][j] == x && this.board[i+1][j] == x) {
								if(i-2 >= 0 && this.board[i-1][j] == '-' && this.board[i-2][j] == x) {
									board = board.generateSuccessor('X',i-1, j);
									move = i-1;
								}
								else if(i+3 < 8 && this.board[i+2][j] == '-' && this.board[i+3][j] == x) {
									board = board.generateSuccessor('X',i+2, j);
									move = i+2;
								}
								
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(move+97)) + (j+1));
								System.out.println();
							}
						}	
					}
					
					return true;
				}
					
			}
		}
		return false;
	}
	//--------------------------------------------------------------------------------------------------// end check killer move
	
	//--------------------------------------------------------------------------------------------------// check pre-killer move
	public Boolean pre_killer_check(Board board) {

		char o = 'O';
		char x = 'X';
		
		ArrayList<String> PrekillerCases = new ArrayList<String>();
		PrekillerCases.add("-" + x + x + "-" + "-");
		PrekillerCases.add("-" + "-" + x + x + "-");
		PrekillerCases.add("-" + x + "-" + x + "-");
		
		ArrayList<String> checkOpponent = new ArrayList<String>();
		checkOpponent.add("-" + o + o + o + "");
		checkOpponent.add( "" + o + o + o + "-");
		checkOpponent.add("-" + o + o + o + "-");
		checkOpponent.add( "" + o + "-" + o + o + "");
		checkOpponent.add( "" + o + o + "-" + o + "");
		
		// check opponent
		for (int check = 0; check<checkOpponent.size(); check++) {
			
			for(int i=0; i<this.rows; i++)
				if(String.valueOf(this.board[i]).contains(checkOpponent.get(check)))
					return false;
			
			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(checkOpponent.get(check)))
					return false;
			}
		}
		
		//self check
		for (int check = 0; check<PrekillerCases.size(); check++) {
			
			for(int i=0; i<this.rows; i++)
				if(String.valueOf(this.board[i]).contains(PrekillerCases.get(check)))
					return true;
			
			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(PrekillerCases.get(check)))
					return true;
			}
		}
		
		return false;
	}
	//--------------------------------------------------------------------------------------------------// end check pre-killer move

	//--------------------------------------------------------------------------------------------------// pre-killer move
	public Board prekiller_move(Board board, char c) throws CloneNotSupportedException {
		char x = c;
		
		ArrayList<String> PrekillerCases = new ArrayList<String>();
		PrekillerCases.add("-" + x + x + "-");
		PrekillerCases.add("-" + x + "-" + x + "-");
		
		for (int check = 0; check<PrekillerCases.size(); check ++) {
			for(int i=0; i<this.rows; i++) {
				if(String.valueOf(this.board[i]).contains(PrekillerCases.get(check))) {
					
					if(check == 0) {
						for (int j = 0; j< this.cols-3; j++) {
							if(this.board[i][j] == '-' && this.board[i][j+1] == x && this.board[i][j+2] == x &&  this.board[i][j+3] == '-') {
								if(j-1>=0 && this.board[i][j-1] =='-') {
									board = board.generateSuccessor('X',i, j);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97)) + (j+1));
									System.out.println();
								}
								else if(j+4<8 && this.board[i][j+4] =='-') {
									board = board.generateSuccessor('X',i, j+3);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97)) + (j+1+3));
									System.out.println();
								}
							}
						}
					}
					
					if(check == 1) {
						for (int j = 0; j< this.cols-4; j++) {
							if(this.board[i][j] == '-' && this.board[i][j+1] == x && this.board[i][j+2] == '-' && this.board[i][j+3] == x &&  this.board[i][j+4] == '-') {
								board = board.generateSuccessor('X',i, j+2);
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97)) + (j+1+2));
								System.out.println();
							}	
						}
					}
					return board;
				}	
			}
			
			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(PrekillerCases.get(check))) {
					if(check == 0) {
						for (int i = 0; i< this.rows-3; i++) {
							if(this.board[i][j] == '-' && this.board[i+1][j] == x && this.board[i+2][j] == x &&  this.board[i+3][j] == '-') {
								if(i-1>=0 && this.board[i-1][j] =='-') {
									board = board.generateSuccessor('X',i, j);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97)) + (j+1));
									System.out.println();
								}
								else if(i+4<8 && this.board[i+4][j] =='-') {
									board = board.generateSuccessor('X',i+3, j);
									System.out.println("-------- AI -------");
									board.printBoard();
									System.out.println("AI move: " + ((char)(i+97+3)) + (j+1));
									System.out.println();
								}
							}
						}
					}
					
					
					if(check == 1) {
						for (int i = 0; i< this.rows-4; i++) {
							if(this.board[i][j] == '-' && this.board[i+1][j] == x && this.board[i+2][j] == '-' && this.board[i+3][j] == x &&  this.board[i+4][j] == '-') {
								board = board.generateSuccessor('X',i+2, j);
								System.out.println("-------- AI -------");
								board.printBoard();
								System.out.println("AI move: " + ((char)(i+97+2)) + (j+1));
								System.out.println();
							}	
						}
					}
				}
			}
		}

		return board;
	}
	//--------------------------------------------------------------------------------------------------// end pre-killer move

	//--------------------------------------------------------------------------------------------------// decide attack or defense
	public int AttackDfence(Board board) {
		int Attack = 3;
		int Defence = 2;
		char o = 'O';
		char x = 'X';
		
		ArrayList<String> DefenceCases = new ArrayList<String>();
		DefenceCases.add("-" + o + o + o + "");
		DefenceCases.add("" + o + o + o + "-");
		DefenceCases.add("-" + o + o + o + "-");
		DefenceCases.add( "" + o + "-" + o + o + "");
		DefenceCases.add( "" + o + o + "-" + o + "");
		
		ArrayList<String> AttackCases = new ArrayList<String>();
		AttackCases.add("" + x + x + "-" + "-");
		AttackCases.add("-" + "-" + x + x + "");
		AttackCases.add("-" + x + x + "-");
		AttackCases.add("" + x + "-" + x + "-");
		AttackCases.add("-" + x + "-" + x + "");
		
				
		for (int check = 0; check<DefenceCases.size(); check++) {
			for(int i=0; i<this.rows; i++)
				if(String.valueOf(this.board[i]).contains(DefenceCases.get(check))) {
					return Defence;
				}
					
			
			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(DefenceCases.get(check))) {
					return Defence;
				}
			}
		}
		
		for (int check = 0; check<AttackCases.size(); check++) {
			
			for(int i=0; i<this.rows; i++)
				if(String.valueOf(this.board[i]).contains(AttackCases.get(check))) {
					return Attack;
				}
			
			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(AttackCases.get(check))) {
					return Attack;
				}
			}
		}
		return Attack;
	}
	//--------------------------------------------------------------------------------------------------// end decide attack or defense

	//--------------------------------------------------------------------------------------------------// check opponent Pre-killer move
	public Boolean pre_killer_defense_check() {
		char o = 'O';
		char x = 'X';
		
		ArrayList<String> selfCheck = new ArrayList<String>();
		selfCheck.add("-" + x + x + "-");
		selfCheck.add("" + x + x + "-" + "-");
		selfCheck.add("-" + "-" + x + x + "");
		selfCheck.add("" + x + "-" + x + "-");
		selfCheck.add("-" + x + "-" + x + "");
		
		ArrayList<String> OpponentPrekillerCases = new ArrayList<String>();
		OpponentPrekillerCases.add("-" + o + o + "-" + "-");
		OpponentPrekillerCases.add("-" + "-" + o + o + "-");
		OpponentPrekillerCases.add("-" + o + "-" + o + "-");
		
		for (int check = 0; check<selfCheck.size(); check++) {
			for(int i=0; i<this.rows; i++)
				if(String.valueOf(this.board[i]).contains(selfCheck.get(check))) {
					return false;
				}
			
			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(selfCheck.get(check))) {
					return false;
				}
			}
		}
		
		
		for (int check = 0; check<OpponentPrekillerCases.size(); check++) {
			for(int i=0; i<this.rows; i++)
				if(String.valueOf(this.board[i]).contains(OpponentPrekillerCases.get(check))) {
					System.out.println("defense 3");
					return true;
				}
			
			//check cols
			for(int j=0; j<this.cols; j++){
				String col="";
				for(int i=0; i<this.rows; i++)
					col+=this.board[i][j];
					
				if(col.contains(OpponentPrekillerCases.get(check))) {
					System.out.println("defense 4");
					return true;
				}
			}
		}
		return false;
	}
	//--------------------------------------------------------------------------------------------------// end check opponent Pre-killer move	
}


//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------

//End.

