import java.io.*;
import java.util.*;

// This class allows for the representation of a collection of graded sports cards, storing them
// in a very efficient manner.
public class CollectionManager {
    private CardNode root;

    // Behavior: 
    //   - This method constructs an empty collection manager
    public CollectionManager(){
        root = null;
    }

    // Behavior: 
    //   - This method constructs a collection manager, from the user's input, adding whatever
    //   - cards the user wants.
    // Parameters:
    //   - input: the user's input
    // Exceptions:
    //   - if the given input is null, an IllegalArgumentException is thrown
    public CollectionManager(Scanner input){
        if(input == null){
            throw new IllegalArgumentException();
        }
        root = null;
        processInput(input);
    }

    // Behavior:
    //   - This method processes the input from the Scanner recursively. It reads the next 
    //     GradedSportsCard from the input, adds it to the collection, and then makes a recursive 
    //     call to process the remaining input.
    //   - The recursion continues until there are no more items left to process in the input.
    // Parameters:
    //   - input: the Scanner object used to read the data for creating GradedSportsCard objects.
    private void processInput(Scanner input){
        if(input.hasNext()){
            GradedSportsCard card = GradedSportsCard.parse(input);
            add(card);
            processInput(input);
        }
    }

    // Behavior: 
    //   - This method adds cards to the collection of cards
    // Parameters:
    //   - card: the card being added
    // Exceptions:
    //   - if the given card is null, an IllegalArgumentException is thrown.
    public void add(GradedSportsCard card){
        if(card == null){
            throw new IllegalArgumentException();
        }
        root = add(root, card);
    }

    // Behavior:
    //   - This method adds a GradedSportsCard to the binary search tree recursively.
    //   - If the node is null, a new CardNode with the card is created and returned.
    //   - If the card to be added is less than the current node's card, it is inserted into
    //     the left subtree. If the card is greater, it is inserted into the right subtree.
    // Parameters:
    //   - node: the current node being checked in the binary search tree.
    //   - card: the GradedSportsCard to be added to the tree.
    // Returns:
    //   - CardNode: the node after insertion, ensuring the tree structure remains intact.
    private CardNode add(CardNode node, GradedSportsCard card){
        if(node == null){
            return new CardNode(card);
        }
        int comparison = card.compareTo(node.card);
        if(comparison < 0){
            node.left = add(node.left, card);
        }
        else if(comparison > 0){
            node.right = add(node.right, card);
        }
        return node;
    }

    // Behavior: 
    //   - This method checks to see if a card is contained in the collection of cards
    // Parameters:
    //   - card: the card being checked
    // Returns:
    //   - boolean: true if it is contained, false otherwise
    // Exceptions:
    //   - if the card is null, an IllegalArgumentException is thrown
    public boolean contains(GradedSportsCard card){
        if(card == null){
            throw new IllegalArgumentException();
        }
        return contains(root, card);
    }

    // Behavior:
    //   - This method checks if a given GradedSportsCard exists in the binary search tree.
    //   - It recursively searches for the card by comparing it with the current node's card.
    //   - If the card matches the current node's card, it returns true.
    //   - If the card is smaller, the search continues in the left subtree; if larger in the right
    // Parameters:
    //   - node: the current node being checked in the binary search tree.
    //   - card: the GradedSportsCard to search for in the tree.
    // Returns:
    //   - boolean: true if the card exists in the tree, false otherwise.
    private boolean contains(CardNode node, GradedSportsCard card){
        if(node == null){
            return false;
        }
        int comparison = card.compareTo(node.card);
        if(comparison == 0){
            return true;
        }
        else if(comparison < 0){
            return contains(node.left, card);
        }
        else{
            return contains(node.right, card);
        }
    }

    // Behavior: 
    //   - This method displays the cards in an orderly fashion in order
    // Returns:
    //   - String: the nicely formatted display of cards in order
    public String toString(){
        return toString(root);
    }

    // Behavior:
    //   - This method returns a string representation of the binary search tree in order.
    //   - It recursively traverses the tree using an in-order traversal (left, root, right).
    //   - The method first processes the left subtree, then the current node, and finally 
    //     the right subtree, ensuring sorted order.
    // Parameters:
    //   - node: the current node being processed in the binary search tree.
    // Returns:
    //   - String: a concatenated string of all node values, each separated by a newline.
    private String toString(CardNode node){
        if(node == null){
            return "";
        }
        String left = toString(node.left);
        String middle = node.toString() +"\n";
        String right = toString(node.right);
        return left + middle + right;
    }

    // Behavior: 
    //   - This method saves the collection of cards to a file
    // Parameters:
    //   - output: the file the user wants the output to be written in
    // Exceptions:
    //   - if the given output is null, an IllegalArgumentException is thrown
    public void save(PrintStream output){
        if(output == null){
            throw new IllegalArgumentException();
        }
        save(root, output);
    }

    // Behavior:
    //   - This method saves the binary search tree's contents to a PrintStream output.
    //   - It performs a pre-order traversal (root, left, right) to write each node's card data.
    //   - The method first prints the current node's card, then recursively processes the left 
    //     and right subtrees.
    // Parameters:
    //   - node: the current node being processed in the binary search tree.
    //   - output: the PrintStream to which the tree's data will be written.
    private void save(CardNode node, PrintStream output){
        if(node != null){
            output.println(node.card.toString());
            save(node.left, output);
            save(node.right, output);
        }
    }

    // Behavior: 
    //   - This method filters cards to only allow cards with a certain grade to be shown to the
    //   - user
    // Parameters:
    //   - grade: the grade the cards must be
    // Returns:
    //   - List<GradedSportsCard>: the list of all cards with that grade
    public List<GradedSportsCard> filter(int grade){
        List<GradedSportsCard> answer = new ArrayList<>();
        filter(root, grade, answer);
        return answer;
    }

    // Behavior:
    //   - This method filters the binary search tree to find cards with a specific grade.
    //   - It performs a recursive traversal, checking each node's card grade.
    //   - If a card matches the given grade, it is added to the provided list.
    //   - The method continues searching in both left and right subtrees.
    // Parameters:
    //   - node: the current node being processed in the binary search tree.
    //   - grade: the grade to filter for in the collection of cards.
    //   - list: a list that stores the filtered GradedSportsCard objects.
    private void filter(CardNode node, int grade, List<GradedSportsCard> list){
        if(node != null){
            if(node.card.getGrade() == grade){
                list.add(node.card);
            }
            filter(node.left, grade, list);
            filter(node.right, grade, list);
        }
    }

    // This class produces the nodes for the binary tree, with each node being a card that has
    // access to its left and right child (unless it's a leaf node)
    private class CardNode{
        public GradedSportsCard card;
        public CardNode left;
        public CardNode right;

        // Behavior: 
        //   - This method constructs a CardNode object with no children
        // Parameters:
        //   - card: the card being used as a node
        public CardNode(GradedSportsCard card){
            this.card = card;
            this.left = null;
            this.right = null;
        }

        // Behavior: 
        //   - This method allows for the string representation of a card--see GradedSportsCard 
        //   - class for more info
        // Returns:
        //   - String: the string representation of a card object
        public String toString(){
            return card.toString();
        }
    }
}
