package rbtrees;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * IDs: 6221 - 6309
 * Dictionary implemented using red black trees
 */
public class RBTrees {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // loading dictionary in the beginning of the program
        Tree t = new Tree();
        t.loadFile("EN-US-Dictionary.txt");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(
                    "Choose the Operation you want to perform:\nPlease enter the corresponding number for the operation:\n1-Search\n2-Insertion\n3-Print Tree Height\n4-Print Tree Size\n5-End Program");

            int choice = scanner.nextInt();
            scanner.nextLine(); // to skip '\n'

            switch (choice) {
                case 1: {
                    //searching for an element
                    System.out.println("Enter The word you want to search: ");
                    String s = scanner.nextLine();
                    t.searchUser(s);
                    break;
                }
                case 2: {
                    //inserting a new element
                    System.out.println("Enter The word you want to insert: ");
                    String s = scanner.nextLine();
                    if(t.search(t.root,s)==true)
                        System.out.println("Word is already in the dictionary");
                    else 
                       t.insert(s);
                    break;

                }
                case 3: {
                    //printing height of the tree
                    System.out.print("The Height of the tree (defined as the number of nodes on the longest path) is: ");
                    System.out.println(t.getHeight(t.root));
                    System.out.print("The Height of the tree (defined as the number of edges on the longest path) is: ");
                    System.out.println(t.getHeight(t.root)-1);
                    break;
                }
                case 4: {
                    //printing size of the tree
                    System.out.print("The Size of the tree is: ");
                    System.out.println(t.getSize());
                    break;
                }
                case 5:{
                    //exiting program
                    System.out.println("Thank you for using our program");
                    System.exit(0);
                               
            }
                default:
                    System.out.println("Please enter a number within 1-5");
            }
            System.out.println();
        }
    }

}
