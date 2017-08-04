public class DungeonGame {
    private final int[] R = {0, -1};
    private final int[] C = {-1, 0};
    private int DP[][];
    public static void main(String[] args) throws Exception{
        int[][] dungeon = {{200}};
        System.out.println(new DungeonGame().calculateMinimumHP(dungeon));
    }

    public int calculateMinimumHP(int[][] dungeon) {
        DP = new int[dungeon.length][dungeon[0].length];
        int l = 0, h = Integer.MAX_VALUE, ans = 0;
        while(l <= h){
            int m = l + (h - l) / 2;
            for(int i = 0; i < dungeon.length; i ++)
                Arrays.fill(DP[i], Integer.MIN_VALUE);
            DP[0][0] = m + dungeon[0][0];
            if(dp(dungeon, dungeon.length - 1, dungeon[0].length - 1) > 0){
                ans = m;
                h = m - 1;
            }else{
                l = m + 1;
            }
        }
        return ans == 0 ? 1 : ans;
    }

    private int dp(int[][] dungeon, int r, int c){
        if(DP[r][c] != Integer.MIN_VALUE)
            return DP[r][c];
        for(int i = 0; i < 2; i ++){
            int newR = r + R[i];
            int newC = c + C[i];
            if(newR >= 0 && newR < dungeon.length && newC >=0 && newC < dungeon[0].length){
                int life = dp(dungeon, newR, newC);
                if(life <= 0)
                    DP[r][c] = Math.max(DP[r][c], 0);
                else DP[r][c] = Math.max(DP[r][c], dungeon[r][c] + life);
            }

        }
        return DP[r][c];
    }
}