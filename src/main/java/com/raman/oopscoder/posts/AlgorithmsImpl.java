package com.raman.oopscoder.posts;

import java.util.*;

public class AlgorithmsImpl {

    public static void main(String[] args) {

        Map<String, Integer> nameAndAgeMap = hashing();
        Set<String> namesSet = nameAndAgeMap.keySet();
        System.out.println("Hashing output:\t\t "
                           + "Bob's age: " + nameAndAgeMap.get("Bob")
                           + ", Candice exists? " + nameAndAgeMap.containsKey("Candice")
                           + ", Set of names: " + namesSet);
        // Bob's age: 30, Candice exists? false, Set of names: [Bob, Alice]

        System.out.println("Linear Search:\t\t "
                           + "Searching 14 in [10, 7, 5, 14, 2]: "
                           + linearSearch(new int[]{10, 7, 5, 14, 2}, 14));
        // Searching 14 in [10, 7, 5, 14, 2]: 3

        System.out.println("Binary Search:\t\t "
                           + "Searching 7 in [2, 5, 7, 10, 14]: "
                           + binarySearch(new int[]{2, 5, 7, 10, 14}, 7));
        // Searching 7 in [2, 5, 7, 10, 14]: 2

        /* *** *** *** *** *** test code *** *** *** *** *** */
        int[] arr = {9, 3, 6, 2, 4};
        bubbleSort(arr);
        System.out.println("Bubble Sort:\t\t "
                           + "Sorted array: " + Arrays.toString(arr));
        // Sorted array: [2, 3, 4, 6, 9]

        arr = new int[]{10, 3, 8, 2, 5};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("Merge Sort:\t\t\t "
                           + "Sorted array: " + Arrays.toString(arr));
        // Sorted array: [2, 3, 5, 8, 10]

        arr = new int[]{7, 3, 9, 1, 5};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Quick Sort:\t\t\t "
                           + "Sorted array: " + Arrays.toString(arr));
        // Sorted array: [1, 3, 5, 7, 9]

        Map<Integer, List<Integer>> graph = Map.of(
                0, List.of(1, 2),
                1, List.of(3, 4),
                2, List.of(5, 6)
        );
        System.out.print("BFS output:\t\t\t Breadth First Search: ");
        bfs(graph, 0);
        // Breadth First Search: 0 1 2 3 4 5 6

        System.out.print("\nDFS output:\t\t\t Depth First Search: ");
        dfs(graph, 0, new HashSet<>());
        // Depth First Search: 0 1 3 4 2 5 6

        /* Problem: Longest Common Subsequence (LCS) i.e. Given two strings, find the length of their longest common subsequence (LCS).
        A subsequence is a sequence that appears in the same relative order but not necessarily contiguous.
        Input: "abcde", "ace"       Output: 3       Explanation: The LCS is "ace", so the length is 3.
         */
        String s1 = "abcde", s2 = "ace";
        System.out.println("\nRecursion output:\t "
                           + "LCS length of " + s1 + ", " + s2 + " is: "
                           + lcsRecursive(s1, s2, s1.length(), s2.length()));
        // LCS length of abcde, ace is: 3

        System.out.println("DP output:\t\t\t "
                           + "LCS length of " + s1 + ", " + s2 + " is: "
                           + lcsDP(s1, s2));
        // LCS length of abcde, ace is: 3

        System.out.println("Backtracking output: "
                           + "LCS sequence of " + s1 + ", " + s2 + " is: "
                           + findLCS(s1, s2));
        // LCS sequence of abcde, ace is: ace
    }

    // 1. Linear Search (O(N))
    // Sequentially checks each element to find a target value.
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    // 2. Binary Search (O(log N))
    // Binary search works on a sorted array.
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    // 3. Hashing (Using HashMap)
    // Hashing is commonly implemented using HashMap/HashSet in Java. Efficient O(1) average-time complexity for insert/search.
    public static Map<String, Integer> hashing() {
        Map<String, Integer> nameAndAgeMap = new HashMap<>();
        nameAndAgeMap.put("Alice", 25);
        nameAndAgeMap.put("Bob", 30);
        return nameAndAgeMap;
    }

    // 4. Bubble sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // Optimization: break early if no swaps occurred (array is sorted)
            if (!swapped) break;
        }
    }

    // 5. Recursive Solution of LCS (Exponential Time - O(2^min(m,n)))
    // A simple recursive approach tries all possibilities by either including or excluding a character from each string.
    // Drawback: Overlapping sub-problems → Solving the same sub-problems multiple times.
    public static int lcsRecursive(String s1, String s2, int m, int n) {
        // Base Case: If either string is empty
        if (m == 0 || n == 0) return 0;

        // If last characters match, add 1 and check remaining
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return 1 + lcsRecursive(s1, s2, m - 1, n - 1);
        } else {
            // If last characters don't match, take max of excluding either character
            return Math.max(lcsRecursive(s1, s2, m - 1, n), lcsRecursive(s1, s2, m, n - 1));
        }
    }

    // 6. Merge Sort (O(N log N))
    // Merge Sort is a divide-and-conquer sorting algorithm. O(N log N) time complexity, stable sorting.
    public static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = Arrays.copyOfRange(arr, left, right + 1);
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            arr[k++] = (temp[i - left] <= temp[j - left]) ? temp[i++ - left] : temp[j++ - left];
        }
        while (i <= mid) arr[k++] = temp[i++ - left];
        while (j <= right) arr[k++] = temp[j++ - left];
    }

    // 7. Quick Sort (O(N log N) Average)
    // QuickSort is another divide-and-conquer sorting algorithm. O(N log N) average, but O(N²) worst case.
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 8. Graph BFS (O(V + E))
    // Uses a queue for level-order traversal.
    public static void bfs(Map<Integer, List<Integer>> graph, int start) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");
            for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    // 9. Graph DFS (O(V + E))
    // Recursive depth-first traversal.
    public static void dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited) {
        if (visited.contains(node)) return;
        System.out.print(node + " ");
        visited.add(node);
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            dfs(graph, neighbor, visited);
        }
    }

    // 10. Dynamic Programming (Bottom-Up) Solution of LCS (𝑂(𝑚×𝑛), space complexity can be optimized to O(n) with rolling arrays)
    // To avoid redundant calculations, use a DP table to store already computed results.
    public static int lcsDP(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    // 11. Backtracking to Print the LCS
    // To print the actual LCS sequence, backtrack from the DP table.
    public static String findLCS(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Backtrack to find the LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                lcs.append(s1.charAt(i - 1));
                i--;
                j--; // Move diagonally
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // Move up
            } else {
                j--; // Move left
            }
        }
        return lcs.reverse().toString(); // Reverse to get correct order
    }

}
