package Sudoku;
/**
 * 
 * @author benjumeab
 *
 */
public class Sudoku_Generator {

	int[] mat[];
	int N; // number of rows/colmns
	int SQRN; // square root of N
	int K; // number of missging digits

	// Constructor
	Sudoku_Generator(int N, int K) {
		this.N = N;
		this.K = K;

		Double sqr = Math.sqrt(N);
		SQRN = sqr.intValue();

		mat = new int[N][N];
	}

	/**
	 * Creates the sudoku to be solved.
	 */
	public void fillIn() {
		fillDiagonal();

		fillRemaining(0, SQRN);

		removeKDigits();
	}

	/**
	 * Fill the diagonal SRN number of SRN x SRN matrices
	 */
	void fillDiagonal() {

		for (int i = 0; i < N; i = i + SQRN)
			// for diagonal box, start coordinates->i==j
			fillBox(i, i);
	}

	/**
	 * Fills a 3 x 3 matrix with random numbers.
	 * @param row
	 * @param col
	 */
	void fillBox(int row, int col) {
		int num;
		for (int i = 0; i < SQRN; i++) {
			for (int j = 0; j < SQRN; j++) {
				do {
					num = randomGenerator(N);
				} while (!unUsedInBox(row, col, num));

				mat[row + i][col + j] = num;
			}
		}
	}

	/**
	 * Random number generator.
	 * @param num
	 * @return random number generated.
	 */
	int randomGenerator(int num) {
		return (int) Math.floor((Math.random() * num + 1));
	}

	/**
	 * Boolean that checks if num has been used before in the 3x3 matrix.
	 * @param i
	 * @param j
	 * @param num
	 * @return True if the number hasn't been used already. False otherwise.
	 */
	boolean CheckIfSafe(int i, int j, int num) {
		return (unUsedInRow(i, num) && unUsedInCol(j, num) && unUsedInBox(i - i % SQRN, j - j % SQRN, num));
	}
	
	/**
	 * Boolean that returns false if given 3 x 3 block contains num.
	 * @param rowStart
	 * @param colStart
	 * @param num
	 * @return True is the number has been used in the 3x3 matrix, false otherwise 
	 */
	boolean unUsedInBox(int rowStart, int colStart, int num) {
		for (int i = 0; i < SQRN; i++)
			for (int j = 0; j < SQRN; j++)
				if (mat[rowStart + i][colStart + j] == num)
					return false;

		return true;
	}
	
	/**
	 * Check to see if number already exists in row. 
	 * @param i
	 * @param num
	 * @return True if the num has already been used in the row. False otherwise.
	 */
	boolean unUsedInRow(int i, int num) {
		for (int j = 0; j < N; j++)
			if (mat[i][j] == num)
				return false;
		return true;
	}

	/**
	 * Check to see if number already exists in column. 
	 * @param j
	 * @param num
	 * @return True if the number has already been used in the column. False otherwise.
	 */
	boolean unUsedInCol(int j, int num) {
		for (int i = 0; i < N; i++)
			if (mat[i][j] == num)
				return false;
		return true;
	}

	
	 /**
	  * A recursive function to fill remaining matrix with 0's.
	  * @param i
	  * @param j
	  * @return True once the matrix has been filled 
	  */
	boolean fillRemaining(int i, int j) {
		
		if (j >= N && i < N - 1) {
			i = i + 1;
			j = 0;
		}
		if (i >= N && j >= N)
			return true;

		if (i < SQRN) {
			if (j < SQRN)
				j = SQRN;
		} else if (i < N - SQRN) {
			if (j == (int) (i / SQRN) * SQRN)
				j = j + SQRN;
		} else {
			if (j == N - SQRN) {
				i = i + 1;
				j = 0;
				if (i >= N)
					return true;
			}
		}

		for (int num = 1; num <= N; num++) {
			if (CheckIfSafe(i, j, num)) {
				mat[i][j] = num;
				if (fillRemaining(i, j + 1))
					return true;

				mat[i][j] = 0;
			}
		}
		return false;
	}

	/**
	 * Remove the K number of digits to complete game.
	 */
	public void removeKDigits() {
		int count = K;
		while (count != 0) {
			int cellId = randomGenerator(N * N) - 1;

			// extract coordinates i and j
			int i = (cellId / N);
			int j = cellId % 9;

			if (mat[i][j] != 0) {
				count--;
				mat[i][j] = 0;
			}
		}
	}

	/**
	 * Print sudoku to console. 
	 */
	public void printSudoku() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(mat[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
}
