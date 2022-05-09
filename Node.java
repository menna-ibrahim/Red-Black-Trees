/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbtrees;

/**
 *
 * @author MIX
 */
public class Node {
    String data;
    Node left;
    Node right;
    int color; //0=black 1=red
    Node parent;
  public Node(String d, Node l, Node r, int c, Node p)
  {
      data=d;
      left=l;
      right=r;
      color=c;
      parent=p;
  }
  //function to get uncle of the node to test which actions to take during the fix of insertion
  public Node getUncle()
  {
      Node p=parent;
      Node g=p.parent;
      //parent is on the left then uncle is on the right and vice versa
      if(g.left==p)
          return g.right;
      else
          return g.left;
  }

}
