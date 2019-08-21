
public class Point_score {
	
	int result = 0;

	public int score_point(char[][] board, int x, int y, char c){
		int count = 1;
		int block = 0;
		int empty = -1;
		
		// check the rows
		for(int i = y+1;; i++ ) {
			if(i>=8) {
				block++;
				break;
			}
			if(board[x][i]=='-') {
				if(empty ==-1 && i < 7 && board[x][i+1] == c){
					empty = count;
					continue;
				}
				else break;
			} 
			if(board[x][i]==c) {
				count++;
				continue;
			}
			else {
				block++;
				break;
			}
		}
		
		for(int i = y-1;; i-- ) {
			if(i<0) {
				block++;
				break;
			}
			if(board[x][i]=='-') {
				if(empty ==-1 && i > 0 && board[x][i-1] == c){
					empty = 0;
					continue;
				}
				else break;
			} 
			if(board[x][i]==c) {
				count++;
				if(empty != -1) {
					empty ++;
				}
				continue;
			}
			else {
				block++;
				break;
			}
		}
		
		result = result + calculateScore(empty, count, block);
		
		count = 1;
		block = 0;
		empty = -1;
		
		// check the columns
		for(int i = x+1;; i++ ) {
			if(i>=8) {
				block++;
				break;
			}
			if(board[i][y]=='-') {
				if(empty ==-1 && i < 7 && board[i+1][y] == c){
					empty = count;
					continue;
				}
				else break;
			} 
			if(board[i][y]==c) {
				count++;
				continue;
			}
			else {
				block++;
				break;
			}
		}
		
		for(int i = x-1;; i-- ) {
			if(i<0) {
				block++;
				break;
			}
			if(board[i][y]=='-') {
				if(empty ==-1 && i > 0 && board[i-1][y] == c){
					empty = 0;
					continue;
				}
				else break;
			} 
			if(board[i][y]==c) {
				count++;
				if(empty != -1) {
					empty ++;
				}
				continue;
			}
			else {
				block++;
				break;
			}
		}
		
		result = result + calculateScore(empty, count, block);
		
		return result;
	}
	

	public int calculateScore(int empty, int count, int block) {
		if(empty <= 0) {
			if(count >= 4) return 1000;
			if(block == 0) {
				switch(count) {
					case 1: return 10;
					case 2: return 100;
					case 3: return 1000;
				}
			}
			
			if(block == 1) {
				switch(count) {
					case 1: return 5;
					case 2: return 50;
					case 3: return 500;
				}
			}
			
			if(block == 2) {
				switch(count) {
					case 1: return 2;
					case 2: return 25;
					case 3: return 250;
				}
			}
		}
		
		// case X_X
		else if(empty == 1) {
			if(count >= 5) return 1000;
			if(block == 0) {
				switch(count) {
					case 2: return 20;
					case 3: return 110;
					case 4: return 1000;
				}
			}
			
			if(block == 1) {
				switch(count) {
					case 2: return 15;
					case 3: return 105;
					case 4: return 505;
				}
			}
			
			if(block == 2) {
				switch(count) {
					case 2: return 12;
					case 3: return 102;
					case 4: return 502;
				}
			}
		}
		
		// case XX_X
		else if(empty == 2) {
			if(count >= 6) return 1000;
			if(block == 0) {
				switch(count) {
					case 3: return 110;
					case 4: return 200;
					case 5: return 1000;
				}
			}
			
			if(block == 1) {
				switch(count) {
					case 3: return 105;
					case 4: return 150;
					case 5: return 510;
				}
			}
			
			if(block == 2) {
				switch(count) {
					case 3: return 102;
					case 4: return 125;
					case 5: return 505;
				}
			}
		}
		
		// case XXX_X
		else if(empty == 3) {
			if(count >= 7) return 1000;
			if(block == 0) {
				switch(count) {
					case 4: return 1000;
					case 5: return 1000;
					case 6: return 1000;
				}
			}
			
			if(block == 1) {
				switch(count) {
					case 4: return 505;
					case 5: return 510;
					case 6: return 115;
				}
			}
			
			if(block == 2) {
				switch(count) {
					case 4: return 502;
					case 5: return 505;
					case 6: return 1000;
				}
			}
		}
		return 0;
	}
}