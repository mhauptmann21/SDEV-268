import java.util.Stack;

public class Module3Exercise2MadisonHauptmann {
    public static String reverse(char[] arr, int N) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < N; i++) {
            stack.push(arr[i]);
        }

        StringBuilder reversed = new StringBuilder();

        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }

        return reversed.toString();
    }

    public static void main(String[] args) {
        char[] arr = "hello".toCharArray();
        int N = arr.length;

        String result = reverse(arr, N);
        System.out.println("Reversed: " + result);
    }
}
