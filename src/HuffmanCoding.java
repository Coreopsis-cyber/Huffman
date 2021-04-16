import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

/**
 * Huffman class contains methods to create a tree, encodes and decodes a file.
 */
public class HuffmanCoding {
    private ArrayList<Node> tree = new ArrayList<Node>();
    private HashMap<String,Node> weights;
    private Node mroot;
    private SymbolCounter sCounter = new SymbolCounter();
    private String code = "";
    private boolean traverseFlag = false;
    private String originalFile = "";
    /**
     *
     *
     */
    public HuffmanCoding(File f) throws FileNotFoundException {
        try{
            weights = sCounter.counter(f);
            originalFile = f.getName();
        }catch(FileNotFoundException e){
            System.out.println("Error: File cannot be found.");
        }
    }
    /**
     * Finds the two nodes with the lowest frequencies in the HashMap
     * @param nodes Hashmap of <character, Node>, used to get the frequencies
     */
    public void findSmallest(HashMap<String, Node> nodes){
        Node smallest1 = null;
        Node smallest2 = null;
        if(nodes.size() >= 2){
            for(Node n: nodes.values()){
                if(smallest1 == null){
                    smallest1 = n;
                }else{
                    if(smallest1.getFrequency() >n.getFrequency()){
                        smallest1 = n;
                    }
                }
            }
            nodes.remove(smallest1.getSymbol());
            for(Node n: nodes.values()){
                if(smallest2 == null){
                    smallest2 = n;
                }else{
                    if(smallest2.getFrequency() > n.getFrequency()){
                        smallest2 = n;
                    }
                }
            }
            
            nodes.remove(smallest2.getSymbol());
            long newFrequency = smallest2.getFrequency() + smallest1.getFrequency();
            Node replaceNode = new Node(newFrequency);
            replaceNode.setLeftNode(smallest1);
            smallest1.addEncryption("0");
            replaceNode.setRightNode(smallest2);
            smallest2.addEncryption("1");
            String repNodeString = smallest1.getSymbol() + smallest2.getSymbol();
            replaceNode.setSymbol(repNodeString);
            nodes.put(repNodeString,replaceNode);
        }else
            System.out.println("Error");
    }
    /**
    * Creates a tree based on the hashmap of weights in the huffmanCoding method
     * @return Node the root node of the tree
     */
    public Node createTree() {
        while(weights.size()>2){
            findSmallest(weights);
        }
        for(Node n:weights.values()){
            tree.add(n);
        }
        if(tree.size() == 2){
            long mrootFrequency = tree.get(0).getFrequency() + tree.get(1).getFrequency();
            mroot = new Node(mrootFrequency);
            if(tree.get(0).getFrequency() < tree.get(1).getFrequency()){
                mroot.setLeftNode(tree.get(0));
                tree.get(0).addEncryption("0");
                mroot.setRightNode(tree.get(1));
                tree.get(1).addEncryption("1");
            }else{
                mroot.setLeftNode(tree.get(1));
                tree.get(1).addEncryption("0");
                mroot.setRightNode(tree.get(0));
                tree.get(0).addEncryption("1");
            }
            tree.remove(0);
            tree.remove(0);
            tree.add(mroot);
        }
        mroot.setSymbol("root");
        return mroot;
    }
    private boolean fileExists = true;
    /**
     * Checks that the file exists for the UI
     * @param f File to check if it exists
     * @return boolean whether or not the file exists
     */
    public boolean fileExists(File f){
        try {
            FileInputStream file = new FileInputStream(f);
            file.close();
            return true;
        }catch (IOException e){
            return false;
        }
    }

    /**
     * Encodes file based on a huffman tree
     * @param f File needing encoding
     */
    public void encode(File f){
        try{
            fileExists = true;
            //creating file for compressed text to go in
            String outputName = "compressed-" + f.getName().substring(0,f.getName().indexOf("."));
            File output = new File(outputName);
            DataOutputStream fileOS = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            if(!output.exists()){
                output.createNewFile();
            }
            FileInputStream file = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file, StandardCharsets.UTF_8));
            int t;
            String store = "";
            while((t = reader.read())!=-1){

                //finding symbols and their codes based on huffman tree
                traverseFlag= false;
                found = false;
                char s = (char)t;
                searchNode(mroot, Character.toString(s));
                if(found == false){
                    addNodeToTree(mroot, s);
                    searchNode(mroot,Character.toString(s));
                }
                store += code;
                if(store.length() >=8){
                    int byteString = 0;
                    for(int j = 0; j<8;j++){
                        if(store.charAt(j) == '0'){
                            byteString &= ~(1<<j);
                        }else if(store.charAt(j) =='1'){
                            byteString |= 1<<j;
                        }
                    }
                    store = store.substring(8);
                    fileOS.write(byteString);
                }
                   
            }
            
            if(store.length() !=  0){
                //adding padding
                int remainder = store.length()%8;
                     zeros = 8-remainder;
                    String zerosString = "";
                    for(int j =0; j<zeros; j++){
                        zerosString += "0";
                    }
                    store = store + zerosString;
                    int byteString = 0;
                    for(int j = 0; j<8;j++){
                        if(store.charAt(j) == '0'){
                            byteString &= ~(1<<j);
                        }else if(store.charAt(j) =='1'){
                            byteString |= 1<<j;
                        }
                    }
                    fileOS.write(byteString);
                }
            
            fileOS.flush();
            file.close();
            reader.close();
        }catch(IOException e){
            System.out.println("Error: File to encode missing. ");
            fileExists = false;
        }
    }
    private boolean found = false;
    /**
     * Recursive method to find the correct node given a symbol.
     * @param search Node checking if the symbol is correct or whether to keep searching
     * through
     * @param s symbol to find
     */
    public void searchNode(Node search, String s){
        if(search.getSymbol()!=null && search.getSymbol().equals(s) ){
                code = search.getEncryption();
                traverseFlag = true;
                found = true;
                return;
        }
        if(traverseFlag == false && search.getLeftNode() != null){
            searchNode(search.getLeftNode(),s);
        }
        if(traverseFlag == false && search.getRightNode() != null){
            searchNode(search.getRightNode(),s);
        }
    }
    private int zeros = 0;
    /**
     * Edge case when a symbol needs to be encoded that wasn't previously in the tree
     * @param nn Node to check if a new node could be attached to it as a child node
     * @param s character to create a new node for
     */
    public void addNodeToTree(Node nn, char s){
        if(nn.getLeftNode() == null && nn.getRightNode() == null){
            Node n2 = new Node(Character.toString(s));
            Node n3 = new Node(nn.getFrequency());
            n3.setSymbol(nn.getSymbol());
            nn.setRightNode(n2);
            nn.setLeftNode(n3);
            nn.setSymbol(n2.getSymbol() + n3.getSymbol());
            n2.addEncryption("1"+ nn.getEncryption());
            n3.addEncryption("0" +nn.getEncryption());
            nn.addFrequency();
        }else{
            nn.setSymbol(nn.getSymbol() + s);
            addNodeToTree(nn.getLeftNode(), s);
        }
    }
    /**
     * Decodes a file based on a Huffman tree
     * @param f File
     */
    public void decode(File f){
        try{
            File output = new File("de" + f.getName() + ".txt");
            if(!output.exists()){
                output.createNewFile();
            }
            DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            Node rootNode = mroot;
            DataInputStream reader = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));

            int t;
            System.out.println("This may take a while for large files");
            while((t=reader.read())!=-1){
                        for(int j = 0; j <8; j++){
                        if(rootNode==null){
                            System.out.println("root null");
                            break;
                        }
                        if(rootNode.getRightNode() == null && rootNode.getLeftNode()== null){
                            writer.write(rootNode.getSymbol().getBytes(StandardCharsets.UTF_8));
                            writer.flush();
                            rootNode = mroot;
                        } 
                        if(((t >>j) & 1) == 1){
                            rootNode = rootNode.getRightNode();
                        }else if(((t >>j) &1) == 0){
                            rootNode = rootNode.getLeftNode();
                        }
                    }
              
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }
    
}
