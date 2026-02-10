package com.example.bfhl.util;

import java.util.*;

public class MathUtil {

    public static List<Integer> fibonacci(int n) {
        List<Integer> res = new ArrayList<>();
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            res.add(a);
            int c = a + b;
            a = b;
            b = c;
        }
        return res;
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0) return false;
        return true;
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static int hcf(List<Integer> arr) {
        int res = arr.get(0);
        for (int n : arr) res = gcd(res, n);
        return res;
    }

    public static int lcm(List<Integer> arr) {
        int res = arr.get(0);
        for (int n : arr)
            res = res * n / gcd(res, n);
        return res;
    }
}
