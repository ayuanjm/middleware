package com.yuan.middleware.jdk.algorithm.leetcode;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @author yuanjm
 * @date 2020/9/16 2:06 下午
 */
public class TwoEqualsString {
    public static void main(String[] args) {
        String str = "abcabaca";
        String ans = "aca";
        HashSet a = setAdd(str);
        HashSet b = setAdd(ans);
        Iterator iterator = b.iterator();
        String max = "";
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            if (a.contains(next)) {
                max = max.length() > next.length() ? max : next;
            }
        }
        System.out.println(max);
    }

    private static HashSet setAdd(String str) {
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            int right = i;
            while (right <= str.length()) {
                set.add(str.substring(i, right));
                right++;
            }
        }
        return set;
    }
}
