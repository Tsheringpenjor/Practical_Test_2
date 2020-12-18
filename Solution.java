import java.util.*;

public class Solution<Key extends Comparable<Key>, Value> {
    int size;
    static int nodeCount; //used in select function
    private Node root;
   
    public Solution() {
        size = 0;
        root = null;
    }
    class Node{
        Key key;
        Value val;
        Node left,right;
    
    public Node(Key key, Value val){
            this.key = key;
            this.val = val;
        }
    }

    public boolean isEmpty() {
         if (size() == 0) {
            return true;
        }
        return false;
       
    }

    public int size() {
      return size(root);
       
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null){
         return 0;
        }
        return 1 + size(x.left) + size(x.right);
        
       
    }




    public Value get(Key key) {
         Node curNode = root; 
         while(curNode.key != key){
            int cmp = key.compareTo(curNode.key);
            if(cmp < 0){
                curNode = curNode.left;
            }
            else if(cmp > 0){
                curNode = curNode.right;
            }
        }
        if (curNode.key == key) {
                    return curNode.val;
                }
            return null;
    }


    public void put(Key key, Value val) {
        if (key == null){
            System.out.println("Key Cannot be Null!!!");
            return;
        }

        Node newest = new Node(key, val);

        if (root == null){
            root = newest;
            size++;
        }
        else{
            Node curNode = root;
            Node parent;
            while (true){
                parent = curNode;
                int cmp = key.compareTo(curNode.key);
                if (cmp<0){
                    curNode = curNode.left;
                    if (curNode==null){
                        parent.left = newest;
                        size++;
                        return;
                    }
                    else if(curNode.key == key){
                        curNode.val = val;
                        return;
                    }
                }
                else if (cmp>0){
                    curNode = curNode.right;
                    if (curNode==null){
                        parent.right = newest;
                        size++;
                        return;
                    }
                    else if (curNode.key == key){
                        curNode.val = val;
                        return;
                    }
                }
            }
        }
    }

    
   


    public Key min() {
        if(isEmpty()){
            throw new NoSuchElementException("There is no element in the tree");
        }
        else{
            Node curNode = root;
            while(curNode.left != null){
                curNode = curNode.left;
            }
            return curNode.key;
       }
    }

    public Key max() {
        if(isEmpty()){
            throw new NoSuchElementException("There is no element in the tree");
        }
        else{
            Node curNode = root;
            while(curNode.right != null){
                curNode = curNode.right;
            }
            return curNode.key;
       }
    }

    public Key floor(Key key){
        if(isEmpty()){
            throw new NoSuchElementException("calls floor() with empty symbol table");
        }
        Node curNode = root;
        Node parent = null;
        while(curNode != null){
            parent = curNode;
            int cmp = key.compareTo(parent.key);
            if(key == null){
                throw new IllegalArgumentException("calls floor() with a null key");
            }
            if(cmp == 0){
                return parent.key;
            }
            if(cmp < 0){
                curNode = parent.left;
            }
           
            else if(cmp > 0){
                curNode = parent.right;
               
                int cmp1 = key.compareTo(curNode.key);
                if(cmp1 < 0 ){
                    return parent.key;
                }
                
                else{
                    curNode = parent.right;
                } 
            }
        }
        return parent.key;
    }


    public Key select(int k) {
        nodeCount=0;
        if ((k<0) || k>=size()) {
            throw new IllegalArgumentException("Rank should be between 0 ans its size");
        }
        return select(root, k+1).key;
    }
    
    // Return key of rank k. 
     private Node select(Node x, int k) {
        Stack<Node> stack = new Stack<Node>();
        Node curNode = root;
        while (!stack.empty() || curNode != null)
        {
            if (curNode != null)
            {
                stack.push(curNode);
                curNode = curNode.left;
            }
            else
            {
                curNode = stack.pop();
                nodeCount++;
                if(nodeCount == k)
                    break;
                curNode = curNode.right;
            }
        }
        return curNode;
    } 



    


    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }

        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }

        ArrayList<Key> arr = new ArrayList<Key>();
        for (Node x = root; x == lo; x = x.left) {
            arr.add(x.key);     
        }
        keys(root, arr, lo, hi);
        return arr;
        
    } 

    private void keys(Node x, ArrayList<Key> arr, Key lo, Key hi) { 
        if (x == null) return;

        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key);

        if (cmplo < 0) {
            keys(x.left, arr, lo, hi); 
        }
        if (cmplo <= 0 && cmphi >= 0) {
            arr.add(x.key); 
        }
        if (cmphi > 0) {
            keys(x.right, arr, lo, hi); 
        }
    }  

}