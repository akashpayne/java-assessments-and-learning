public class TicTacToe
{
    /**
     * Cell class to keep track of first player and second player row/column count
     */
    private class Cell
    {
        int player1 = 0, player2 = 0;
    }

    Cell[] rows, columns; //Array of row and column cells
    private int N, fPD1 = 0, fPD2 = 0, sPD1 = 0, sPD2 = 0; //fPD1 -> first_player_diagonal1

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        TicTacToe toe = new TicTacToe(3);
        System.out.println(toe.move(0, 0, 1));
        System.out.println(toe.move(0, 2, 2));
        System.out.println(toe.move(1, 0, 1));
        System.out.println(toe.move(1, 1, 2));
        System.out.println(toe.move(2, 0, 1));
    }

    /** Initialize your data structure here. */
    public TicTacToe(int n)
    {
        N = n;
        rows = new Cell[N];
        columns = new Cell[N];
    }

    /**
     * Move and check who wins.
     * @param row row
     * @param col col
     * @param player player
     * @return integer indicating the winner
     */
    public int move(int row, int col, int player)
    {
        switch (player)
        {
            case 1:
                increment(rows, row, 1);
                increment(columns, col, 1);
                if((col + row) == (N - 1))
                    fPD2 ++;
                if(row == col)
                    fPD1 ++;
                if(rows[row].player1 == N
                        || columns[col].player1 == N || fPD1 == N || fPD2 == N)
                    return 1;
                break;

            case 2:
                increment(rows, row, 2);
                increment(columns, col, 2);
                if((col + row) == (N - 1))
                    sPD2 ++;
                if(row == col)
                    sPD1 ++;
                if(rows[row].player2 == N
                        || columns[col].player2 == N || sPD1 == N || sPD2 == N)
                    return 2;
                break;
        }
        return 0;
    }

    /**
     * Increment row / col count
     * @param cells array of cells
     * @param key row / col key
     * @param player Player object
     */
    private void increment(Cell[] cells, int key, int player) {
        Cell p = cells[key];
        if(p == null) {
            p = new Cell();
            cells[key] = p;
        }
        if(player == 1)
            p.player1++;
        else p.player2++;
    }
}