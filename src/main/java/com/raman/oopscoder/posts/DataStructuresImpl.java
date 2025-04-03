package com.raman.oopscoder.posts;

import java.util.*;

public class DataStructuresImpl {

    public static void main(String[] args) {
        DataStructuresImpl dsi = new DataStructuresImpl();
        dsi.array();
        dsi.string();
        dsi.list();
        dsi.map();
        dsi.set();
        dsi.stack();
        dsi.queue();
        dsi.priorityQueue();
        dsi.tree();
        dsi.binarySearchTree();
        dsi.redBlackTree();
        dsi.graph();
        dsi.trie();
    }

    // Array
    private void array() {
        // Fixed-size array
        int[] array = new int[10];
        array[4] = 5;

        // Dynamic array
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.addFirst(0);

        System.out.println("Array output:\t array: " + Arrays.toString(array) + ", list: " + list);
        // array: [0, 0, 0, 0, 5, 0, 0, 0, 0, 0], list: [0, 1]
    }

    // String
    private void string() {
        // Immutable
        String str = "Hello";
        str = str + " World"; // A new object is created, old one is discarded

        // Mutable, fast
        StringBuilder sb = new StringBuilder("Hello");
        sb.append(" ").append("World");

        // Thread-safe
        StringBuffer sbuf = new StringBuffer("Hello");
        sbuf.append(" ").append("World").append('!');

        System.out.println("String output:\t str: " + str + ", sb: " + sb + ", sbuf: " + sbuf);
        // str: Hello World, sb: Hello World, sbuf: Hello World!
    }

    // List (Dynamic Arrays and Linked Lists)
    private void list() {
        // Use ArrayList for fast access (random access) to elements and fewer insertions/removals.
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);

        // Use LinkedList for frequent insertions/deletions at the beginning or middle, but not random access.
        List<Integer> linkedList = new LinkedList<>();
        linkedList.addFirst(5);
        linkedList.add(7);
        linkedList.add(1, 6);

        System.out.println("List output:\t arrayList: [" + arrayList.get(0) + ", " + arrayList.get(1) + "], linkedList: " + linkedList);
        // arrayList: [1, 2], linkedList: [5, 6, 7]
    }

    // Map
    private void map() {
        // HashMap: No order guarantee, fast lookups (O(1))
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap.put(3, "C++");
        hashMap.put(1, "Java");
        hashMap.put(2, "Python");

        // TreeMap: Sorted by keys (Natural Ordering)
        Map<Integer, String> treeMap = new TreeMap<>(hashMap);

        // LinkedHashMap: Maintains insertion order
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(3, "C++");
        linkedHashMap.put(1, "Java");
        linkedHashMap.put(2, "Python");

        System.out.println("Map output:\t\t hashMap: " + hashMap + ", treeMap: " + treeMap + ", linkedHashMap: " + linkedHashMap);
        // hashMap: {1=Java, 2=Python, 3=C++}, treeMap: {1=Java, 2=Python, 3=C++}, linkedHashMap: {3=C++, 1=Java, 2=Python}
    }

    // Set
    private void set() {
        // HashSet: Unordered, fast access (O(1)), internally uses a HashMap
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Java");
        hashSet.add("Python");
        hashSet.add("C++");

        // TreeSet: Sorted order (Natural Ordering)
        Set<String> treeSet = new TreeSet<>(hashSet);

        // LinkedHashSet: Maintains insertion order
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Java");
        linkedHashSet.add("Python");
        linkedHashSet.add("C++");

        System.out.println("Set output:\t\t hashSet: " + hashSet + ", treeSet: " + treeSet + ", linkedHashSet: " + linkedHashSet);
        // hashSet: [Java, C++, Python], treeSet: [C++, Java, Python], linkedHashSet: [Java, Python, C++]
    }

    // Stack (LIFO)
    private void stack() {
        // Stack (Legacy API) is a subclass of Vector, which is synchronized and generally slower due to thread safety overhead.
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.pop();

        // ArrayDeque is not synchronized (unlike Stack, which extends Vector) and does not suffer from resizing overhead like an ArrayList-based stack.
        Deque<Integer> arrayDequeAsStack = new ArrayDeque<>(); // Recommended to use as Stack for java implementations
        arrayDequeAsStack.push(10);
        arrayDequeAsStack.push(20);
        arrayDequeAsStack.push(30);
        arrayDequeAsStack.pop();

        System.out.println("Stack output:\t top element: " + arrayDequeAsStack.peek() + ", arrayDequeAsStack:  " + arrayDequeAsStack);
        // top element: 20, arrayDequeAsStack:  [20, 10]
    }

    // Queue (FIFO)
    private void queue() {
        // LinkedList uses a doubly linked list, which requires O(1) for enqueue/dequeue but has extra memory overhead (pointers for each node).
        // LinkedList suffers from pointer chasing, which increases cache misses and degrades performance.
        Queue<Integer> linkedListAsQueue = new LinkedList<>();
        linkedListAsQueue.add(10);
        linkedListAsQueue.remove();

        // ArrayDeque is backed by a resizable array, providing O(1) time complexity for enqueue (offer/add) and dequeue (poll/remove) operations.
        // ArrayDeque has better cache locality (since arrays are stored contiguously in memory), making it faster in practice.
        Queue<Integer> arrayDequeAsQueue = new ArrayDeque<>(); // Faster than LinkedList and Recommended to use as Queue for java implementations
        // Enqueue elements
        arrayDequeAsQueue.offer(10);
        arrayDequeAsQueue.offer(20);
        arrayDequeAsQueue.offer(30);
        // Dequeue elements
        arrayDequeAsQueue.poll();

        System.out.println("Queue output:\t top element: " + arrayDequeAsQueue.peek() + ", arrayDequeAsQueue: " + arrayDequeAsQueue);
        // top element: 20, arrayDequeAsQueue: [20, 30]
    }

    // Priority Queue (Heap - MinHeap by default)
    private void priorityQueue() {
        PriorityQueue<Integer> priorityQueueAsMinHeap = new PriorityQueue<>(); // Min-Heap
        // Adding elements
        priorityQueueAsMinHeap.offer(30);
        priorityQueueAsMinHeap.offer(10);
        priorityQueueAsMinHeap.offer(20);
        priorityQueueAsMinHeap.offer(5);
        // Removing elements
        priorityQueueAsMinHeap.poll();

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // Max-Heap

        System.out.println("Heap output:\t top element: " + priorityQueueAsMinHeap.peek() + ", priorityQueueAsMinHeap: " + priorityQueueAsMinHeap);
        // top element: 10, priorityQueueAsMinHeap: [10, 30, 20]
    }

    // Trees (Binary Tree, BST, AVL, ...)
    static class TreeNode<T> {
        T data;
        TreeNode<T> left, right;

        public TreeNode(T data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    static class BinaryTree<T> {
        TreeNode<T> root;

        public BinaryTree(T rootData) {
            this.root = new TreeNode<>(rootData);
        }

        // Preorder Traversal (Root -> Left -> Right)
        public void preorder(TreeNode<T> node) {
            if (node == null) return;
            System.out.print(node.data + " ");
            preorder(node.left);
            preorder(node.right);
        }

        // Inorder Traversal (Left -> Root -> Right)
        public void inorder(TreeNode<T> node) {
            if (node == null) return;
            inorder(node.left);
            System.out.print(node.data + " ");
            inorder(node.right);
        }

        // Postorder Traversal (Left -> Right -> Root)
        public void postorder(TreeNode<T> node) {
            if (node == null) return;
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.data + " ");
        }

        // Level Order Traversal (BFS)
        public void levelOrder() {
            if (root == null) return;

            Queue<TreeNode<T>> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                TreeNode<T> current = queue.poll();
                System.out.print(current.data + " ");

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
        }
    }

    private void tree() {
        // Create a binary tree
        BinaryTree<Integer> tree = new BinaryTree<>(1);
        // Manually create left and right children
        tree.root.left = new TreeNode<>(2);
        tree.root.right = new TreeNode<>(3);
        tree.root.left.left = new TreeNode<>(4);
        tree.root.left.right = new TreeNode<>(5);
        tree.root.right.left = new TreeNode<>(6);
        tree.root.right.right = new TreeNode<>(7);

        System.out.print("Tree output:\t level order traversal: ");
        tree.levelOrder();
        System.out.println();
        // level order traversal: 1 2 3 4 5 6 7
    }

    // Binary Search Tree (BST)
    static class BinarySearchTree {
        private TreeNode<Integer> root;

        public void insert(int key) {
            root = insertElement(root, key);
        }

        private TreeNode<Integer> insertElement(TreeNode<Integer> root, int key) {
            if (root == null) return new TreeNode<>(key);
            if (key < root.data) root.left = insertElement(root.left, key);
            else if (key > root.data) root.right = insertElement(root.right, key);
            return root;
        }

        public boolean search(int key) {
            return searchElement(root, key);
        }

        private boolean searchElement(TreeNode<Integer> root, int key) {
            if (root == null) return false;
            if (key == root.data) return true;
            return key < root.data ? searchElement(root.left, key) : searchElement(root.right, key);
        }

        public void inorder() {
            inorderTraversal(root);
        }

        private void inorderTraversal(TreeNode<Integer> root) {
            if (root != null) {
                inorderTraversal(root.left);
                System.out.print(root.data + " ");
                inorderTraversal(root.right);
            }
        }
    }

    private void binarySearchTree() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(5);
        bst.insert(3);
        bst.insert(9);
        bst.insert(2);
        bst.insert(4);
        bst.insert(6);
        bst.insert(8);

        // Displaying the BST (keys are sorted)
        System.out.print("BST output:\t\t search 4: " + bst.search(4) + ", search 7: " + bst.search(7) + ", in-order traversal: ");
        bst.inorder();
        System.out.println();
        // search 4: true, search 7: false, in-order traversal: 2 3 4 5 6 8 9
    }

    // Red-Black Tree (Balanced BST)
    private void redBlackTree() {
        // A TreeMap in Java is a sorted map implementation based on a Red-Black Tree.
        // It maintains keys in sorted order and provides O(log n) time complexity for insertion, deletion, and lookup.
        TreeMap<Integer, String> treeMap = new TreeMap<>(); // keys sorted in natural order
        // Adding key-value pairs
        treeMap.put(3, "Three");
        treeMap.put(1, "One");
        treeMap.put(2, "Two");
        treeMap.put(5, "Five");
        treeMap.put(4, "Four");
        // Removing an entry
        treeMap.remove(2);

        Map<Integer, String> descTreeMap = new TreeMap<>(Comparator.reverseOrder()); // keys sorted in descending order

        // Displaying the TreeMap (keys are sorted)
        System.out.println("RedBlackTree:\t first key: " + treeMap.firstKey() + ", last key: " + treeMap.lastKey() + ", treeMap: " + treeMap);
        // first key: 1, last key: 5, treeMap: {1=One, 3=Three, 4=Four, 5=Five}
    }

    // Graph (Adjacency List)
    static class Graph {
        private final Map<Integer, List<Integer>> adjList;

        public Graph() {
            this.adjList = new HashMap<>();
        }

        // Add a vertex
        public void addVertex(int vertex) {
            adjList.putIfAbsent(vertex, new ArrayList<>());
        }

        // Add an edge (undirected)
        public void addEdge(int src, int dest) {
            adjList.putIfAbsent(src, new ArrayList<>());
            adjList.putIfAbsent(dest, new ArrayList<>());
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }

        // Print the graph
        public void printGraph() {
            for (var entry : adjList.entrySet()) {
                System.out.print(entry.getKey() + " -> " + entry.getValue() + "; ");
            }
        }
    }

    private void graph() {
        Graph graph = new Graph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);

        // Displaying the Graph
        System.out.print("Graph output:\t ");
        graph.printGraph();
        System.out.println();
        // 1 -> [2, 4]; 2 -> [1, 3]; 3 -> [2, 4]; 4 -> [3, 1];
    }

    // Trie (Prefix Tree)
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean endOfWord;

        public TrieNode() {
            this.endOfWord = false;
        }
    }

    static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        // Insert a word into the trie
        public void insert(String word) {
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                node.children.putIfAbsent(ch, new TrieNode());
                node = node.children.get(ch);
            }
            node.endOfWord = true;
        }

        // Search for a word in the trie
        public boolean search(String word) {
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                if (!node.children.containsKey(ch)) {
                    return false;
                }
                node = node.children.get(ch);
            }
            return node.endOfWord;
        }

        // Check if a prefix exists in the trie
        public boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char ch : prefix.toCharArray()) {
                if (!node.children.containsKey(ch)) {
                    return false;
                }
                node = node.children.get(ch);
            }
            return true;
        }
    }

    private void trie() {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");

        System.out.println("Trie output:\t " +
                           "search 'apple': " + trie.search("apple") +
                           ", search 'app': " + trie.search("app") +
                           ", search 'appl': " + trie.search("appl") +
                           ", starts with 'app': " + trie.startsWith("app"));
        // search 'apple': true, search 'app': true, search 'appl': false, starts with 'app': true
    }

}
