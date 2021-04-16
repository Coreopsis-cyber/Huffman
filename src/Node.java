
/**
 * Node class representing a symbol, encryption and a frequency in a tree
 */
public class Node {

    String symbol;
    long frequency;
    Node leftChild;
    Node rightChild;
    String encryption = "";
    /**
     * Single argument constructor for the Node class
     * @param s a String of a single character to create the Node from
     */
    public Node(String s)
    {
        symbol = s;
        frequency = 1;
        leftChild = null;
        rightChild = null;
    }
    /**
     * Single argument constructor for the Node class
     * @param f frequency to make a node of
     */
    public Node (long f){
        frequency = f;
        leftChild = null;
        rightChild = null;
    }
    /**
     * Creates huffman encoding for letter based on location in the tree.
     * @return String encoding for a character
     */
    public String getEncryption(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(encryption);
        String returnString = (stringBuilder.reverse()).toString();
        return returnString;
    }
    /**
     * Adds a digit onto the nodes encoding
     * @param s A 0 or a 1 based on the location in the tree
     * here
     */
    public void addEncryption(String s){
        encryption += s;
        if(leftChild != null)
        leftChild.addEncryption(s);
        if(rightChild != null)
        rightChild.addEncryption(s);
    }
    /**
     * Increases the frequency by 1
     */
    public void addFrequency(){
        frequency++;
    }
    /**
     * Getter method returning frequency for a node
     * @return long frequency of a node
     */
    public long getFrequency(){
        return frequency;
    }
    /**
     * Setter method for the leftChild node
     * @param left A node set as the left child node
     */
    public void setLeftNode(Node left){
        leftChild = left;
    }
    /**
     * Setter method for the rightChild node
     * @param right A node set as the right child node
     */
    public void setRightNode(Node right){
        rightChild = right;
    }
    /**
     * Getter method for the leftChild node
     * @return Node the leftChild of the node
     */
    public Node getLeftNode(){
        return leftChild;
    }
    /**
     * Getter method for thr rightChild node
     * @return Node the rightChild of the node
     */
    public Node getRightNode(){
        return rightChild;
    }
    /**
     * Getter method returning the symbol of the node
     * @return String symbol of the node
     */
    public String getSymbol(){
        return symbol;
    }
    /**
     * Setter method for the symbol of a node
     * @param s the string symbol to set as for the node
     */
    public void setSymbol(String s){
        symbol = s;
    }
    /**
     * String method to represent the node
     * @return String representation of this node
     */
    public String toString(){
        return " | Symbol: '" + symbol + "' | frequency: " + frequency + " | Encryption: " + encryption + "|";
    }
    /**
     * Checks whether the symbol has been repeated and is previously a node
     * @param n Node to check again
     * @return boolean determining whether or not the symbols repeat
     */
}
