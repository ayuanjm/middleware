//现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。 
//
// 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。 
//
// 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。 
//
// 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。 
//
// 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。 
//
// 换句话说: 
//
// 
// 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。 
// 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。 
// 
//
// 书写满足这些限制条件的氢、氧线程同步代码。 
//
// 
//
// 示例 1: 
//
// 输入: "HOH"
//输出: "HHO"
//解释: "HOH" 和 "OHH" 依然都是有效解。
// 
//
// 示例 2: 
//
// 输入: "OOHHHH"
//输出: "HHOHHO"
//解释: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" 和 "OH
//HOHH" 依然都是有效解。
// 
//
// 
//
// 提示： 
//
// 
// 输入字符串的总长将会是 3n, 1 ≤ n ≤ 50； 
// 输入字符串中的 “H” 总数将会是 2n 。 
// 输入字符串中的 “O” 总数将会是 n 。 
// 
// 👍 70 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class H2O {
    private Semaphore hydrogen = new Semaphore(2);
    private Semaphore oxygen = new Semaphore(2);


    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hydrogen.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        oxygen.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygen.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        hydrogen.release(2);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
