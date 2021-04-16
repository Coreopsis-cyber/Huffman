import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
/**
 * Counts the frequency of symbols and creates a HashMap to be used in the HuffmanCoding class
 */
public class SymbolCounter {

    private HashMap<String, Node> character = new HashMap<String,Node>();
    /**
     * Counts the frequencies of all the symbols in the file
     *
     * @param  f File to get the symbols from
     * @return HashMap<String,Node> HashMap containing all frequencies of symbols
     */
    public HashMap<String,Node> counter(File f) throws FileNotFoundException {

        FileInputStream file = new FileInputStream(f);
        InputStreamReader reader = new InputStreamReader(file);
        int t;
        try{
            while((t = reader.read()) != -1){
                char s = (char)t;
                Node n = new Node(Character.toString(s));
                if(character.get(Character.toString(s)) != null){
                    Node n2 = character.get(Character.toString(s));
                    n2.addFrequency();
                }else{
                    character.put(Character.toString(s),n);
                }
                
            }
            
        }catch(FileNotFoundException e){
            System.out.println("Error: File doesn't exist.");
        }catch(IOException e){
            System.out.println("Error: Cannot read file.");
        }
        return character;
    }
}
