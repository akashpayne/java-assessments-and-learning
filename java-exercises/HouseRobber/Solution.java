/*
	Each house depends on front and back houses
	Two possible case for the last house: rob or not robbed. So we can do two for loop, then compare the 
	two differnet future.
*/
public class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
        	return 0;
        } else if (nums.length == 1) {
        	return nums[0];
        } else if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int n = nums.length;

        //Last house not robbed
        int[] dp1 = new int[n];
        dp1[0] = nums[0];
        dp1[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n - 1; i++) {
        	dp1[i] = Math.max(dp1[i - 1], dp1[i - 2] + nums[i]);
        }
        dp1[n - 1] = dp1[n - 2];

        //Last house robbed
        int[] dp2 = new int[n];
        dp2[0] = 0;
        dp2[1] = nums[1];
        for (int i = 2; i < n - 2; i++) {
        	dp2[i] = Math.max(dp2[i - 1], dp2[i - 2] + nums[i]);
        }
        dp2[n - 1] = dp2[n - 3] + nums[n - 1];

        //Compare
        return Math.max(dp2[n - 1], dp1[n - 1]);
    }
}
