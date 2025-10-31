public class Recursion {
    static int oddCountdown(int n) {
        if (n < 0) {
            return 0; // returns 0 if n is a negative integer

        // checks if n is an odd integer, prints n, and starts process on next odd number
        } else if(n % 2 != 0) {
            System.out.println(n);
            return oddCountdown(n - 2);

        // if n is even, it subtracts only one to find the odd number
        } else {
            return oddCountdown(n - 1);
        }
    }

    public static void main(String[] args) {
        int result = oddCountdown(12);
        
        if (result > 0) {   // only prints the integer if it is greater than 0
            System.out.println(result);
        }
    }
}