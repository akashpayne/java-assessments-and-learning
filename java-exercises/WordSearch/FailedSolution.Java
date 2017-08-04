public class Solution {
    public ArrayList<String> wordSearchII(char[][] board, ArrayList<String> words) {
      ArrayList<String> rst = new ArrayList<String>();
      if (board == null || words == null || words.size() == 0) {
        return rst;
      }
      for (String word : words) {
        if (exist(board, word)) {
          rst.add(word);
        }
      }
      return rst;
    }
    //The following are from Word Search I
     public boolean exist(char[][] board, String word) {
      if (word == null || word.length() == 0) {
        return true;
      }
      if (board == null) {
        return false;
      }
      
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
          if (board[i][j] == word.charAt(0)) {
            boolean rst = search(board, word, i, j, 0);
            if (rst) {
              return true;
            }
          }
        }
      }
      return false;
    }

    public boolean search(char[][] board, String word, int i, int j, int start) {
      if (start == word.length()) {
        return true;
      }
      if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != word.charAt(start)) {
        return false;
      }
      board[i][j] = '#';
      boolean rst = search(board, word, i, j - 1, start + 1)
      || search(board, word, i, j + 1, start + 1)
      || search(board, word, i + 1, j, start + 1)
      || search(board, word, i - 1, j, start + 1);   
      board[i][j] = word.charAt(start);
      return rst;
    }
}