//设计一个算法，找出数组中两数之和为指定值的所有整数对。一个数只能属于一个数对。 
//
// 示例 1: 
//
// 输入: nums = [5,6,5], target = 11
//输出: [[5,6]] 
//
// 示例 2: 
//
// 输入: nums = [5,6,5,6], target = 11
//输出: [[5,6],[5,6]] 
//
// 提示： 
//
// 
// nums.length <= 100000 
// 
// Related Topics 数组 哈希表 
// 👍 18 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> pairSums(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums)
            map.put(num, map.getOrDefault(num, 0) + 1);
        for (int num1 : map.keySet()) {
            int num2 = target - num1;
            //能够组成数对
            if (map.containsKey(num2) && map.get(num2) > 0) {
                //组成数对的个数
                int count = 0;
                if (num1 == num2)
                    count = map.get(num1) / 2;
                else
                    count = Math.min(map.get(num1), map.get(num2));
                //添加进列表
                for (int i = 0; i < count; i++)
                    res.add(Arrays.asList(num1, num2));
                map.put(num1, 0);
                map.put(num2, 0);
            }
        }
        return res;
    }
}

//leetcode submit region end(Prohibit modification and deletion)
