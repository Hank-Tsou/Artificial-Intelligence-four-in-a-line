import java.util.Scanner;

public class main {

	public static void main(String[] args) throws CloneNotSupportedException {

		
////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------- Program Start -------------------------------------------//
////////////////////////////////////////////////////////////////////////////////////////////////////
		
//------------------------------------------------------------------------------------------------//
// 1. Max running time ( user input, requirement <25 second )										//
// 2. Algorithm depth  ( user input )															//
// 3. First hand       ( user input, 'c'/'C': Computer AI, 'o'/'O': Opponent )					//
// 4. Initial board    ( 8*8 empty board )														//
//------------------------------------------------------------------------------------------------//
		
		System.out.println("*** Start Game Connect Four ***");
		System.out.println();
		
		System.out.print("Enter the maximum running time (this program is required in 25 seconds): ");
		Scanner in_0 = new Scanner(System.in);
		int time = in_0.nextInt();
		
		System.out.print("Enter the depth: ");
		Scanner in = new Scanner(System.in);
		int depth = in.nextInt();
		
		System.out.print("Who goes first ( 'C' for Computer AI, 'O' for Opponent ): ");
		Scanner in2 = new Scanner(System.in);
		char order = in2.next().charAt(0);
		while(order != 'C' && order != 'O' && order != 'c' && order != 'o' ) {
			System.out.print("Please enter a correct first hand ( 'C' for Computer AI, 'O' for Opponent ): ");
			Scanner in3 = new Scanner(System.in);
			order = in2.next().charAt(0);
		}
		
		System.out.println();
		System.out.println("    -- Game Start --");
		System.out.println("****************************");
		System.out.println("|  -Maximum time (sec): " + time + " |");
		System.out.println("|  -Game Depth: " + depth + "          |");
		if(order == 'C' || order == 'c') {
			System.out.println("|  -First Hand: " + "AI" + "         |");
		}
		else {
			System.out.println("|  -First Hannd: " + "Human" + "     |");
		}
		System.out.println("****************************");
		System.out.println();
		System.out.println("---Initial Board---");	
		
		MiniMaxAgent Agent = new MiniMaxAgent(depth);
		Board board = new Board();
		board.printBoard();
		System.out.println();
//--------------------------------------------------------------------------------------------------

		
////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------- Random Move ----------------------------------------//
////////////////////////////////////////////////////////////////////////////////////////////////////	
		
		// AI first: For first two move
		if (order == 'C' || order == 'c'){
			pre_random_move preMove = new pre_random_move();
			
			board = preMove.AI_first_move(board); 	// Random AI move
			board = human_move(board); 				// human move
			
			board = preMove.AI_second_move(board); 	// Random AI move
			board = human_move(board);				// human move
		}
		
		// human first + AI first move
		if (order == 'O' || order == 'o'){
			pre_random_move preMove = new pre_random_move();
			
			board = human_move(board);				// human move
			board = preMove.AI_connect_move(board);	// Random AI move
			board = human_move(board);				// human move
			
		}
//--------------------------------------------------------------------------------------------------

////////////////////////////////////////////////////////////////////////////////////////////////////
//------------------------------------------ Game Start ------------------------------------------//
////////////////////////////////////////////////////////////////////////////////////////////////////

		// Start playing
		while(true) {
			
			// check killer move
			if(board.killer_check(board)) {
				System.out.println("AI win");
				break;
			}
			
			// check Pre-killer move
			Boolean pre_killer_check = board.pre_killer_check(board);
			
			if(pre_killer_check) {
				System.out.println("in1");
				board = board.prekiller_move(board, 'X');
			}
			
			// check Pre-killer move for opponent
			Boolean pre_killer_defense_check = board.pre_killer_defense_check();
			
			if(!pre_killer_check ) {
				if(pre_killer_defense_check) {
					board = board.prekiller_move(board, 'O');
				}
			}
			
			// decide to defense or attack
			Agent.setDepth(board.AttackDfence(board));
			
			
			// Computer AI play
			if (!pre_killer_check){
				if(!pre_killer_defense_check) {
					board = Agent.Action(board); // AI move
				}				
			}
			
			// check if AI win the game
			if(board.isGoal('X')) {
				System.out.println("AI win");
				break;
			}

			// Human or opponent play
			board = human_move(board); // Human move
			
			// check if opponent win the game
			if(board.isGoal('O')) {
				System.out.println("Opponent win");
				break;
			}
			
			// check if draw
			if(board.isDraw()) {
				System.out.println("Draw !");
				break;
			}
		}
	}
//--------------------------------------------------------------------------------------------------

////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------- Function for human move --------------------------------------//
////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// Function for human move
	public static Board human_move(Board board) throws CloneNotSupportedException {
		
		int row=0, col=0;
		char c='O';
		
		System.out.print("==================> Enter your next move: ");
		Scanner in3 = new Scanner(System.in);
		String temp = in3.nextLine();
		
		char[] splitted = temp.toCharArray();
		
		row = splitted[0]-97;
		col = Integer.parseInt(String.valueOf(splitted[1]))-1; 
		
		System.out.print("here");
		while(!board.inputcheck(board, row, col)) {
			System.out.println();
			System.out.println("The move you want is not valid !!!");
			System.out.println();
			System.out.print("==================> Enter your next move: ");
			in3 = new Scanner(System.in);
			temp = in3.nextLine();
			
			splitted = temp.toCharArray();
			
			row = splitted[0]-97;
			col = Integer.parseInt(String.valueOf(splitted[1])); 
		}
		
		board = board.generateSuccessor(c, row, col );
		
		System.out.println();
		System.out.println("----- OPPONENT ----");
		board.printBoard(); 
		System.out.println("Human move: " + splitted[0] + splitted[1]);
		System.out.println();
		
		return board;
	}
}

//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------

// End.





