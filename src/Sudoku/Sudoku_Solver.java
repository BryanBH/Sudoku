package Sudoku;

import java.util.Scanner;

import Sudoku.Sudoku_Generator;
/**
 * 
 * @author benjumeab
 *
 */
public class Sudoku_Solver {

	/**
	 * Mwthod check if num is safe to be put into the table 
	 * @param board
	 * @param row
	 * @param col
	 * @param num
	 * @return True if num is safe to be put into the board, false otherwise. 
	 */
	public static boolean isSafe(Sudoku_Generator board, int row, int col, int num) {

		// Check for row uniqueness
		for (int i = 0; i < board.N; i++) {

			// Check to see if the number is already present
			// in that row
			if (board.mat[row][i] == num) {
				return false;
			}
		}

		// Check for column uniqueness
		for (int j = 0; j < board.N; j++) {

			if (board.mat[j][col] == num) {
				return false;
			}
		}

		// Check if corresponding square has unqiue number
		int sqrt = (int) Math.sqrt(board.N);
		int boxRow = row - row % sqrt;
		int boxCol = col - col % sqrt;

		for (int r = boxRow; r < boxRow + sqrt; r++) {
			for (int d = boxCol; d < boxCol + sqrt; d++) {

				if (board.mat[r][d] == num) {
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * Main method that solves the Sudoku table.
	 * @param board
	 * @param int n number of rows/columns in the table 
	 * @return True when the table is solved, false if the table cannot be solved. 
	 */
	public static boolean solveSudoku(Sudoku_Generator board, int n) {

		int row = -1;
		int col = -1;
		boolean isEmpty = true;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board.mat[i][j] == 0) {
					row = i;
					col = j;

					isEmpty = false;
					break;
				}
			}
			if (!isEmpty)
				break;
		}
		// no empty space left
		if (isEmpty)
			return true;

		// else backtrack
		for (int num = 1; num <= n; num++) {
			if (isSafe(board, row, col, num)) {
				board.mat[row][col] = num;
				if (solveSudoku(board, n)) {
					return true;
				} else {
					board.mat[row][col] = 0;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Enter the number of empty spaces you want in the 9x9 table: ");
		int K = input.nextInt();
		input.close();
		if (K < 1 || K > 81) {
			System.out.println("Number must be between 1 and 81.");
			System.exit(0);
		}
		
		Sudoku_Generator board = new Sudoku_Generator(36, K);
		board.fillIn();
		System.out.println("***Initializing Sudoku***");
		board.printSudoku();
		System.out.println();

		if (solveSudoku(board, board.N)) {
			System.out.println("***Solved Sudoku***");
			board.printSudoku();

		} else {
			System.out.println("No solution to this Sodoku. ");
		}

	}
}
