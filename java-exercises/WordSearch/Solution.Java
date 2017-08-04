public class Solution {
  class TrieNode {
    String str;
    boolean isEnd;
    boolean visited;
    HashMap<Character, TrieNode> children;
    public TrieNode () {
      this.isEnd = false;
      this.visited = false;
      this.str = "";
      children = new HashMap<Character, TrieNode>();
    }
  }
  public TrieNode root;
  
    public List<String> findWords(char[][] board, String[] words) {
      List<String> rst = new ArrayList<String>();
      if (board == null || board.length == 0 || board[0].length == 0 
        || words == null || words.length == 0) {
        return rst;
      }

      //Build Trie with words
      root = new TrieNode();
      for (int i = 0; i < words.length; i++) {
        insert(words[i]);
      }

      //Validate existance of the words in board
      boolean[][] visited = new boolean[board.length][board[0].length];
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
          dfs(rst, "", i, j, visited, board);
        }
      }

      return rst;
    }

    //4 direction DFS search
    public void dfs(List<String> rst, String s, int x, int y, boolean[][] visited, char[][] board) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
            return;
        }
        if (visited[x][y]) {
            return;
        }
        s += board[x][y];
        if (!startWith(s)) {
            return;
        }
        
        if (search(s)) {
            rst.add(s);
        }
        int[] dx = {0,0,1,-1};
        int[] dy = {1,-1,0,0};
        visited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            dfs(rst, s, nx, ny, visited, board);
        }
        visited[x][y] = false;
    }

    /**************************Trie section *********************/
    public void insert (String s){
      char[] arr = s.toCharArray();
      TrieNode node = root;
      for (int i = 0; i < arr.length; i++) {
        char c = arr[i];
        if (!node.children.containsKey(c)) {
          node.children.put(c, new TrieNode());
        }
        node = node.children.get(c);
        if (i == arr.length - 1) {
          node.isEnd = true;
          node.str = s;
        }
      }
    }

    public boolean search(String s) {
      char[] arr = s.toCharArray();
      TrieNode node = root;
      for (int i = 0; i < arr.length; i++) {
        char c = arr[i];
        if (!node.children.containsKey(c)) {
          return false;
        }
        node = node.children.get(c);
      }
      if (node.visited || !node.isEnd) {
          return false;
      }
      node.visited = true;
      return true;
    }

    public boolean startWith(String s) {
      char[] arr = s.toCharArray();
      TrieNode node = root;
      for (int i = 0; i < arr.length; i++) {
        char c = arr[i];
        if (!node.children.containsKey(c)) {
          return false;
        }
        node = node.children.get(c);
      }
      return true;
    }
}
