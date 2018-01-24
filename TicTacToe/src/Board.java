import java.util.ArrayList;
import java.util.List;

// Class Board acts like the brain of the Application
// It consists implementation of MiniMax Algorithm used in deciding the next computer move

public class Board{
	
	// 1 is used to depict a move by computer: Player X is computer
	// 2 is used to depict a move by human: Player H is human
	// 0 is used to depict a draw
	
	public static final int NO_PLAYER = 0;
	public static final int PLAYER_O = 1;
	public static final int PLAYER_X = 2;
	public Point computerMove;
	
	// 3X3 matrix which depicts the board
	
	private int[][] board = new int[3][3];
	
	// Method to check if game is over
	// Returns True if game is over else returns false
	
	public boolean isGameOver() {
		return hasPlayerWon(PLAYER_X) || hasPlayerWon(PLAYER_O) || getAvailableCells().isEmpty(); 
	}
	
	// Method to check if one of the two players has won
	// For human the rows, columns and diagonals are checked to 1
	// For computer the rows, columns and diagonals are checked to 0
	// If player has won, returns true else return false
	
	public boolean hasPlayerWon(int player) {
		int i;
		if((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player) ||
				(board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {
			return true;
		}
		
		for(i = 0;i<3;i++) {
			if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player) || 
					(board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player)) {
				return true;
			}
		}		
		return false;
	}
	
	// Method returns all the unoccupied coordinates in 3X3 matrix
	// It returns a list of points(x,y)
	
	public List<Point> getAvailableCells(){		
		int i, j;
		List<Point> availableCells = new ArrayList<>();
		for(i=0;i<3;i++) {
			for(j=0;j<3;j++) {
				if(board[i][j] == NO_PLAYER) {
					availableCells.add(new Point(i, j));
				}
			}
		}
		return availableCells;
	}
	
	// Method receives point coordinates and the player which chose to fill that cell
	// The cell is filled with the appropriate player value
	
	public boolean placeMove(Point point, int player) {
		if(board[point.x][point.y] != NO_PLAYER) {
			return false;			
		}
		board[point.x][point.y] = player;
		return true;
	}
	
	// Method to display the 3X3 board on console
	
	public void displayBoard() {
		int i, j;	
		for(i=0;i<3;i++) {
			for(j=0;j<3;j++) {
				String value = "-";
				
				if(board[i][j] == PLAYER_O) {
					value = "O";
				}
				else if(board[i][j] == PLAYER_X) {
					value = "X";
				}
				System.out.print(value + " ");
				
			}
			System.out.println();
		}
		System.out.println();
		
	}
	
	// Method is the implementation of the minimax algorithm which determines the next move 
	// chosen by the computer.
	// It is a recursive function which receives the depth of the each level of the possible
	// tree generated of all future possible moves and returns either  -1, 0 or 1
	
	public int minimax(int depth, int turn) {
		
		List<Point> availableCells = getAvailableCells();
		if(hasPlayerWon(PLAYER_O)) {
			return 1;
		}
		if(hasPlayerWon(PLAYER_X)) {
			return -1;
		}				
		if(availableCells.isEmpty()) {
			return 0;
		}				
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int i;
		for(i=0;i<availableCells.size();i++) {			
			Point point = availableCells.get(i);
			if(turn == PLAYER_O) {				
				placeMove(point, PLAYER_O);
				int currentScore = minimax(depth + 1, PLAYER_X);				
				max = Math.max(currentScore, max);				
				
				if(depth == 0) {
					//System.out.println("Computer Score for position " + point + "= " + currentScore);
				}
				
				if(currentScore >= 0) {
					if(depth == 0) {
						computerMove = point;
					}
				}
				
				if(currentScore == 1) {
					board[point.x][point.y] = NO_PLAYER;
					break;
				}
				
				if(i == availableCells.size() -1 && max < 0) {
					if(depth == 0) {
						computerMove = point;
					}
				}											

			}else if(turn == PLAYER_X) {				
				placeMove(point, PLAYER_X);
				int currentScore = minimax(depth + 1, PLAYER_O);				
				min = Math.min(currentScore, min);				
				
				if(min == -1) {
					board[point.x][point.y] = NO_PLAYER;
					break;
				}
			}
			board[point.x][point.y] = NO_PLAYER;
		}
		if(turn == PLAYER_O) {
			return max;
		}else {
			return min;
		} 		
	}
	
	
	
	
	
}
