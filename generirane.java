package telerik;

import java.util.Random;
import java.util.Scanner;

public class generirane {
	public boolean isVisited[][] = new boolean[7][7];
	public char maze[][] = new char[7][7];
	public int playersCurrentRow;
	public int playersCurrentColumn;
	public String command;
	public boolean isExit = false;
	public int playersMovesCount = 0;
	HighScoreBoard board;	

	/*
	 * Generates a new maze until at least one solution is found
	 * modifies isVisited, maze, playersCurrentRow and playersCurrentColumn global variables
	 */
	void initializeMaze(){
		Random randomgenerator = new Random();	
		do{
			for(int row=0;row<7;row++){
				for(int column=0;column<7;column++){
					isVisited[row][column]=false;
					if(randomgenerator.nextInt(2)==1){
						maze[row][column] = 'X';
					}
					else {
						maze[row][column] = '-';
					}
				}
			}
		}
		while(isSolvable(3, 3)==false);
		playersCurrentRow = 3;
		playersCurrentColumn = 3;		
		maze[playersCurrentRow][playersCurrentColumn] = '*';
		printMaze();
	}	
	public void initializeScoreBoard(){
		board = new HighScoreBoard();
	}	
	/*
	 * Checks whether the generated maze can be completed or not
	 * Calls small helper methods that modify variable isVisited
	 */
	public boolean isSolvable(int row, int col){
		if((row==6)||(col==6)||(row==0)||(col==0)){
			isExit = true;
			return isExit;
		}
		up(row, col);
		down(row, col);
		left(row, col);
		right(row, col);
		return isExit;
	}
	void up(int row, int col){
		if(maze[row-1][col]=='-' && !isVisited[row-1][col]){
			isVisited[row][col] = true;
			isSolvable(row - 1, col);
		}		
	}
	void down(int row, int col){
		if(maze[row+1][col]=='-' && !isVisited[row+1][col]){
			isVisited[row][col]=true;
			isSolvable(row+1, col);
		}		
	}
	void left(int row, int col){
		if(maze[row][col-1]=='-' && !isVisited[row][col-1]){
			isVisited[row][col] = true;
			isSolvable(row, col - 1);
		}		
	}
	void right(int row, int col){
		if(maze[row][col+1]=='-' && !isVisited[row][col+1]){
			isVisited[row][col] = true;
			isSolvable(row, col + 1);
		}		
	}
	void printMaze(){
		for(int row=0;row<7;row++){
			for(int column=0;column<7;column++){
				System.out.print(maze[row][column]+" ");
			}
			System.out.println();
		}
	}	
	/*
	 * Handles input commands. Available commands are "exit", "restart", "top" and directional commands.
	 */
	public void inputCommand(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your next move : L(left), " +
				"R(right), U(up), D(down) ");
		command = scanner.next();
		int size = command.length();
		if (command.equals("exit")) {
			System.out.println("Good bye!");
			System.exit(0);
		}
		else if(command.equals("restart")){
                isExit = false;
                initializeMaze();
        }
        else if(command.equals("top")){
			if(board.list.size()>0){
			    board.printBoard(board.list);
			}
			else{
			    System.out.println("The High score board is empty!");
			}
         }
        else if(size>1){
            System.out.println("Invalid command!");
        }
        else {
            movePlayer(command.charAt(0));
        }
	}	
	/*
	 * Checks for input direction and whether the player can move to that cell.
	 * global variables modified are playersCurrentColumn and playersCurrentRow
	 */
	public  void movePlayer(char firstLetter){
		firstLetter = Character.toLowerCase(firstLetter);
		if (firstLetter == 'l' && maze[playersCurrentRow][playersCurrentColumn - 1] != 'X') {
			swapCells(playersCurrentRow, playersCurrentRow,	playersCurrentColumn, playersCurrentColumn - 1);				
				playersCurrentColumn--;						
		} else if (firstLetter == 'r' && maze[playersCurrentRow][playersCurrentColumn + 1] != 'X') {
				swapCells(playersCurrentRow, playersCurrentRow,	playersCurrentColumn, playersCurrentColumn + 1);
				playersCurrentColumn++;
		} else if (firstLetter == 'u' && maze[playersCurrentRow - 1][playersCurrentColumn] != 'X') {
				swapCells(playersCurrentRow, playersCurrentRow - 1,	playersCurrentColumn, playersCurrentColumn);
				playersCurrentRow--;
		} else if (firstLetter == 'd' && maze[playersCurrentRow + 1][playersCurrentColumn] != 'X') {
				swapCells(playersCurrentRow, playersCurrentRow + 1,	playersCurrentColumn, playersCurrentColumn);
				playersCurrentRow++;
		} else {
			System.out.println("Invalid command or move direction!");
			printMaze();
		}
	}		
	/*
	 * Swaps player cell with adjacent non occupied cell
	 * modifies global variable playersMovesCount
	 */
	void swapCells(int currentRow, int newRow, int currentColumn, int newColumn){
			char previousCell = maze[currentRow][currentColumn];
			maze[currentRow][currentColumn] = maze[newRow][newColumn];
			maze[newRow][newColumn] = previousCell;
			System.out.println();
			printMaze();
			playersMovesCount++;
	}	
}