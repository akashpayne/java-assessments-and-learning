class Solution {
    public int maxProfit(int k, int[] prices) {
    	if (prices == null || prices.length < 2 || k <= 0) {
    		return 0;
    	}
    	if (k >= prices.length) {
    		int profit = 0;
    		for (int i = 1; i < prices.length; i++) {
    			if (prices[i] > prices[i - 1]) {
    				profit += prices[i] - prices[i - 1];
    			}
    		}
    		return profit;
    	}
    	int[][] local = new int[prices.length][k + 1];
    	int[][] global = new int[prices.length][k + 1];
    	for (int i = 1; i < prices.length; i++) {
    		int diff = prices[i] - prices[i - 1];
    		for (int j = 1; j <= k; j++) {
    			local[i][j] = Math.max(global[i-1][j-1] + diff, local[i - 1][j] + diff);
    			global[i][j] = Math.max(global[i-1][j], local[i][j]);
    		}
    	}
    	return global[prices.length - 1][k];
    }
};