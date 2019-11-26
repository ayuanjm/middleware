package com.yuan.middleware.interview;

/**
 * 递归与迭代
 * 有n步台阶，一次只能上1步或2步，请共有多少种走法，请使用编程实现。
 * 第n步:
 * 走一步,即n-1,再求n-1个阶梯的走法,
 * 走两步,即n-2,再求n-2个阶梯的走法,
 * 以此,n级阶梯的走法是n-1个阶梯的走法与n-2个阶梯的走法的和.
 *
 * 动态规划：
 * 基本思想与分治法类似，也是将待求解的问题分解为若干个子问题（阶段），按顺序求解子阶段，前一子问题的解，为后一子问题的求解提供了有用的信息。
 * 在求解任一子问题时，列出各种可能的局部解，通过决策保留那些有可能达到最优的局部解，丢弃其他局部解。依次解决各子问题，最后一个子问题就是初始问题的解。

 *
 * @author yuan
 * @date 2019/11/26
 */
public class Step {
    public static void main(String[] args) {
        System.out.println(recursion(10));
        System.out.println(iteration(10));
    }

    /**
     * 递归
     *
     * @param n 台阶数
     * @return
     */
    public static int recursion(int n) {
        if (n < 1) {
            throw new RuntimeException("n 不能小于1");
        }
        if (n == 1 || n == 2) {
            return n;
        }
        return recursion(n - 1) + recursion(n - 2);
    }

    /**
     * 迭代
     *
     * @param n 台阶数
     * @return
     */
    public static int iteration(int n) {
        if (n < 1) {
            throw new RuntimeException("n 不能小于1");
        }
        if (n == 1 || n == 2) {
            return n;
        }
        //初始化int=3时最后跨1步有2种走法,指的是f(2)
        int one = 2;
        //初始化int=3时最后跨2步有1种走法,指的是f(1)
        int two = 1;
        //n阶台阶总的走法
        int sum = 0;

        for (int i = 3; i <= n; i++) {
            //最后跨2步的走法 + 最后跨1步,one和two指的是跨几步，他们的值指的是跨几步对应的走法
            //two = f(n-2),one = f(n-1),sum = f(n-2) + f(n-1) = f(n)
            sum = two + one;
            //最后跨2步的走法等于n-2台阶的走法 当i=3时 two最后的值是为了i=4跨2步准备的，走法为f(4-2)的走法，也就是f(2)的走法:2，
            //two = one = f(n-1),相对于下一个n+1来说还是f((n+1) - 2)
            two = one;
            //最后跨1步的走法等于n-1台阶的走法 当i=3时 one最后的值是为了i=4跨1步准备的，走法为f(4-1)的走法,也就是f(3)的走法:sum,
            //one = sum = f(n),相对于下一个n+1来说还是f((n+1) - 1)
            one = sum;
        }
        return sum;
    }
}
