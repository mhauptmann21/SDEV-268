public class BinarySearch {
    static int binarySearch(int a[], int start, int end,  int x) {
        while (start <= end) {
            // Finds middle of the array
            int mid = (start + end)/2;

            // Checks if the target is the middle number
            if (a[mid] == x) {
                return mid;
            } 

             // Checks if the target is smaller than the middle number
            else if (a[mid] > x) {
                end = mid - 1;  // Starts the search in the first 
            } 
            
            // Checks if the target is bigger than the middle number
            else if (a[mid] < x) {
                start = mid + 1; // Starts the search in the second half of the array
            } 
           
            else {
                start = mid + 1;
            }
        }

        // No element was found
        return -1;
    }

    public static void main(String[] args) {
        int a[] = {2, 5, 8, 12, 14, 15};
        int n = a.length;
        int x = 14;

        int res = binarySearch(a, 0, n-1, x);

        System.out.println("Element to be searched is: " + x);

        if (res == -1) {
            System.out.println("Element is not present in array.");
        } else {
            System.out.println("Element is present at index: " + res);
        }
    }
}