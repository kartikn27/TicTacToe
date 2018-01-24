import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


// This class contains the main method which is executed first for every game 
public class TicTacToe extends Board{
	
	public static final Random RANDOM = new Random();
	
	// Main method is executed the first time the program is run
	
	public static void main(String[] arg) {
		Board board = new Board();
		Scanner scanner = new Scanner(System.in);
		System.out.println("---------- Human is X vs Computer is O ----------");
		board.displayBoard();
		
		// This while block is executed till the game is over
		// That is one of the player wins or game is drawn
		
		while(!board.isGameOver()) {
			boolean moveOk = true;
			boolean wrongEntry = true;
			
			// The following do while blocks conditionally runs till following two conditions
			// 1. A user enters wrong entry other than 1-9 OR
			// 2. A user enter a cell that is already filled
			// Proper prompts are displayed in such case 
			
			do {
				if(!moveOk) {
					 System.out.println("*********That cell is already filled*********");
					 board.displayBoard();
				}
				
				if(!wrongEntry) {
					System.out.println("\n*********Enter a proper cell between 1-9*********\n"
							+ "1-3 for Row 1 \n"
							+ "4-6 for Row 2 \n"
							+ "7-9 for Row 3 \n");					
				}
				
				System.out.println("Enter your move(1-9)");				
				int move = scanner.nextInt();
				ArrayList<Integer> cellN = new ArrayList<>();
				if (move >=1 && move <=9) {
					
					cellN = getCellNumber(move);					
					Point userMove = new Point(cellN.get(0), cellN.get(1));
					
					 moveOk = board.placeMove(userMove, Board.PLAYER_X);
					 wrongEntry = true;
				}else {					
					wrongEntry = false;
					moveOk = true;
				}				
				
			}while(!moveOk || !wrongEntry);
			System.out.println("Board's status after your move");
			board.displayBoard();
			
			if(board.isGameOver()) {
				break;
			}
			
			// The minimax method is called with depth 0 and player as computer
			board.minimax(0, Board.PLAYER_O);
						
			ArrayList<Integer> arrayList = new ArrayList<>();
			arrayList.add(board.computerMove.x);
			arrayList.add(board.computerMove.y);						
			int computerNumber = getCellNumber(arrayList);
			computerNumber += 1;			
			System.out.println("Computer Chooses " + computerNumber);			
			
			// A computer move is placed with the x and y coordinates
			board.placeMove(board.computerMove, Board.PLAYER_O);
			System.out.println("Board's status after computer's move");
			board.displayBoard();
			System.out.println("----------------------------------------------------------------------");
		}
		
		// The conditions are checked for winning players and match drawn
		// For all of the three possible outcomes the result is displayed
		// And the program is safely terminated
		
		if(board.hasPlayerWon(Board.PLAYER_O)) {
			System.out.println("Computer Wins");
			System.exit(0);
		}else if(board.hasPlayerWon(Board.PLAYER_X)) {
			System.out.println("You Win");
			System.exit(0);
		}else {
			System.out.println("Game is drawn");
			System.exit(0);
		}
		scanner.close();
	}
	
	// This Private method returns the number which is associated to its corresponding coordinates
	// Example: for [2,2] the method returns 9
	
	private static int getCellNumber(ArrayList<Integer> coordinates) {		
		ArrayList<ArrayList<Integer>> arrli	= possibleMoves();
		return arrli.indexOf(coordinates);				
	}
	
	// This Private method maps the User Input (1-9) to its correct cell location on board
	// For input as a integer between 1 to 9, method returns integer array for its locations
	// Example: for input 5, method returns [1,1]
	
	private static ArrayList<Integer> getCellNumber(int number) {
		ArrayList<ArrayList<Integer>> arrli	= possibleMoves();
		number -= 1;
		return arrli.get(number);
		
	}
	
	// Method returns a list of list all the possible moves 
	// A list of [0,0], [0,1] [1,0] [1,1] .... [2,2] is returned
	
	private static ArrayList<ArrayList<Integer>> possibleMoves(){
		ArrayList<ArrayList<Integer>> arrli = new ArrayList<ArrayList<Integer>>();
		for(int i = 0;i<3;i++){	
            for(int j = 0;j<3;j++){
                ArrayList<Integer> intArray = new ArrayList<>();
            		intArray.add(i);
            		intArray.add(j);
                arrli.add(intArray);
            }
        }
		return arrli;
	}
		
}
