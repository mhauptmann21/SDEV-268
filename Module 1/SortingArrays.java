import java.util.Arrays;

public class SortingArrays {
    public static int[] linearSort(int[] arr) {

        // Creates a copy so the original array isn't changed
        int[] unsorted = Arrays.copyOf(arr, arr.length);
        int[] sorted = new int[arr.length];

        for(int i = 0; i < sorted.length; i++) {
            // Finds the minimum element and its index in the remaining array
            int minIndex = 0;
            for (int j = 1; j < unsorted.length; j++) {
                if ( unsorted[j] < unsorted[minIndex]) {
                    minIndex = j;
                }
            }

            // Copy the minimum element into the sorted array
            sorted[i] = unsorted[minIndex];

            // "Delete" it from the unsorted array by marking it as max value
            unsorted[minIndex] = Integer.MAX_VALUE;
        }

        return sorted;
    }

    public static void main(String[] args) {
        int arr[] = {2, 4, 1, 5, 3};

        int[] sortedArr = linearSort(arr);

        System.out.println("Original array: " + Arrays.toString(arr));
        System.out.println("Sorted array:   " + Arrays.toString(sortedArr));
    }
}
