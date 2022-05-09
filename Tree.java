/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbtrees;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author MIX
 */
public class Tree {

    Node root;
    int elements;

    public Tree() {
        root = null;
        elements = 0;
    }

    public void insert(String s) {
        Node n = new Node(s, null, null, 1, null); //creating a new node
        elements++; //increase number of elements by 1 with each call
        Node p = null; //p to mark parent
        Node current = this.root; //current to mark current position in tree
        //keep moving down the tree till you reach the right position of the leaf
        while (current != null) {
            p = current;
            if (s.compareToIgnoreCase(current.data) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        //parent of n is p after the loop (the node right before we found the null position)
        n.parent = p;
        //in case it's the root
        if (p == null) {
            root = n;
            root.color = 0;
            return;
        }
        //insertion of new node
        if (s.compareToIgnoreCase(p.data) < 0) {
            p.left = n;
        } else {
            p.right = n;
        }
     //if node has a grandparent, check if any rules has been broken
        //if not there is no need to check because the first three nodes won't cause
        //any violations
        if (n.parent.parent != null) 
            check(n);
        

    }

    public void check(Node n) {
        Node u; //uncle node
        //keep looping if the n parent color is red to cover the iterative case of red uncle
        while (n.parent.color == 1) {   //red uncle case
            u=n.getUncle();
            if (u!=null && u.color == 1) {
                n.parent.color = 0;
                u.color = 0;
                n.parent.parent.color = 1;
                n = n.parent.parent; //loop will be repeated for grandparent now

            } 
            //black uncle cases
            else {
                if (n.parent.parent.right == n.parent && n.parent.right == n) // Right Right case
				rightRight(n);

			else if (n.parent.parent.left == n.parent && n.parent.left == n) // left left case
				leftLeft(n);

			else if (n.parent.parent.left == n.parent && n.parent.right == n) // left right case
				leftRight(n);

			else if (n.parent.parent.right == n.parent && n.parent.left == n) //right left case
				rightLeft(n);
                //break out of the loop no need to repeat in these cases
                break;

            }
            //break if n is now the root
            if(n==root)
                break;
        }
        //making sure root stays black in case it was changed in the red uncle case
        root.color=0;
    }
    
      /*
    Left Left case 
    
                     (g)                        (p)
                    /    \                     /    \
                   p      (u)                 n      g
                  / \     / \     ------>    / \    / \
                  n  T5  T3 T4              T6 T7  T5  (u)
                 / \                                   / \
                T6 T7                                 T3 T4
    */
    
public void leftLeft(Node n)
{
    Node p=n.parent;
    Node g=p.parent;
    //recolor p and g
    p.color=0;
    g.color=1;
    //if T5 is not null then move it to the left of g and if it is null then left of g is null
    if(p.right!=null)
    {
        g.left=p.right;
        g.left.parent=g;
    }
    else
        g.left=null;
    //if g is the root then p is the root
   if(g==root)
    {
        root=p;
        root.parent=null;
    
    }
    else
    {   //if g is not the root, check if it is in the left subtree or right subtree to move p in its place correctly
        if(g==g.parent.left)
            g.parent.left=p;
        else
         g.parent.right=p;
        p.parent=g.parent;
    }
    //move g to the right of p
    p.right=g;
    g.parent=p;
    
}

public void leftRight(Node n)
{
    Node p=n.parent;
    Node g=p.parent;
    //save the left of n in the right of p
    if(n.left!=null)
    {
        p.right=n.left;
        p.right.parent=p;
    }
    else
    p.right=null;
    //rotate within the branch to go to the left left case
    n.parent=g;
    g.left=n;
    p.parent=n;
    n.left=p;
    leftLeft(p);
}
    /*
    Right Right case
                     (g)                   (p)
                   /    \                 /   \
                 (u)     p  ----->       g      n
                 / \    / \             /  \   / \
                T3  T4  T5 n           (u) T5  T6 T7
                          / \         /   \
                         T6 T7       T3   T4
    */
public void rightRight(Node n)
{
    Node p=n.parent;
    Node g=p.parent;
    //recolor of p and g
    p.color=0;
    g.color=1;
    //if T5 is not null then move it to the right of g and if it is null then right of g is null
    if(p.left!=null)
    {
        g.right=p.left;
        g.right.parent=g;
    }
    else
        g.right=null;
    //if g is the root then p is the root
    if(g==root)
    {
        root=p;
        root.parent=null;
    
    }
    else
    { //if g is not the root, check if it is in the left subtree or right subtree to move p in its place correctly
        if(g==g.parent.left)
            g.parent.left=p;
        else
         g.parent.right=p;
        p.parent=g.parent;
    }
    //move g to the left of p
    p.left=g;
    g.parent=p;
}
public void rightLeft(Node n)
{
    Node p=n.parent;
    Node g=p.parent;
    //save the right of n in the left of p
    if(n.right!=null)
    {
        p.left=n.right;
        p.left.parent=p;
    }
    else
        p.left=null;
    //rotate within the branch to move to right right case
    n.parent=g;
    g.right=n;
    p.parent=n;
    n.right=p;
    rightRight(p);
    
}
   //search function returning true or false, first called by sending root of tree
    public boolean search(Node r, String s) {
        //if the r is null, we have exhaused all nodes, word is not found
        if (r == null) {
            return false;
        }
        //if the Node r (parsed into function) has the same data, word is found
        if (s.compareToIgnoreCase(r.data) == 0) {
            return true;
        }
        //if Node r doesn't match, search left or right depending on if the word is smaller or bigger than the data of r
        if (s.compareToIgnoreCase(r.data) < 0) {
            return search(r.left, s);
        } else {
            return search(r.right, s);
        }
    }
    //search returning Node, used for tracing
      public Node searchNode(Node r, String s) {
        
        if (s.compareToIgnoreCase(r.data) == 0) {
            return r;
        }

        if (s.compareToIgnoreCase(r.data) < 0) {
            return searchNode(r.left, s);
        } else {
            return searchNode(r.right, s);
        }
    }
   //search function called by the user using string only
    public void searchUser(String s) {
        if (search(this.root, s) == true) {
            System.out.println("Word is in the dictionary");
        } else {
            System.out.println("Word is not in the dictionary");
        }
    }
     //height of the tree is the number of nodes on the longest path
    public int getHeight(Node r) {
        if (r == null) {
            return 0;
        } else {
            return Integer.max(getHeight(r.left), getHeight(r.right)) + 1;
        }

    }
    //size of the tree is the number of elements
    public int getSize() {
        return elements;
    }
    
    //loading file into the tree
    public void loadFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            insert(temp);
            //for tracing
          //  System.out.println(temp);
          //  System.out.println(getHeight(this.root));

        }
        bufferedReader.close();
    }
}
