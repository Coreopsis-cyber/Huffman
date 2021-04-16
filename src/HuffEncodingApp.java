import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * HuffmanEncodingApp class containing main to run a small interface to compress and decompress files.
 */
public class HuffEncodingApp {

    public static void main(String [] args){
        Scanner reader = new Scanner(System.in);
        String choice = "";
        boolean active = true;
        System.out.println("Huffman Compression application.");
        System.out.println("Please select an option:");
        System.out.println( "1) Huffman encoding \n-1) Quit Program");
        choice = reader.nextLine();
    
        while(active){
                
                if(choice.equals("-1")){
                    active = false;
                    System.out.println("Huffman Compression App closed.");

                }else if(choice.equals("1")){
                    File f = new File("doesn't exist");
                    String encodingFile = "";
                    System.out.println("Please enter the name of the file, without the file extension, you'd like to use for encoding.");
                    while(!f.exists()){
                        encodingFile = reader.nextLine();
                        f = new File(encodingFile + ".txt");
                    }
                    try{
                        System.out.println("Tree creation started...");
                        HuffmanCoding h = new HuffmanCoding(f);
                        h.createTree();
                        File f2 = new File("doesn't exist");
                        String fileName = "";

                        System.out.println("Tree created...");
                        System.out.println("Please enter the name of the file you want to compress, without the file extension.");
                        while(!h.fileExists(f2)){
                            fileName = reader.nextLine();
                            f2 = new File(fileName + ".txt");
                        }
                            System.out.println("Compression started...");
                            h.encode(f2);
                            File f3 = new File("compressed-" + fileName);
                            System.out.println("Compression completed. Compressed file is called compressed-"+ f2.getName().substring(0,f.getName().indexOf(".")));
                            System.out.println("Do you want to to decompress your file? Y/N");
                            choice = reader.nextLine();
                            if(choice.equalsIgnoreCase("y")){
                                System.out.println("Decompression started...");
                                h.decode(f3);
                                System.out.println("Decompression Completed. The compressed file is called decompressed-" + f2.getName());
                            }
                            System.out.println("Do you want to compress another file? Y/N");
                            choice = reader.nextLine();
                            if(choice.equalsIgnoreCase("y")){
                                System.out.println("Please enter your choice:");
                                System.out.println( "1) Huffman encoding \n-1) Quit Program");
                                choice = reader.nextLine();
                            }else{
                                active = false;
                                System.out.println("Huffman Compression App closed.");

                            }
                        
                    }catch(FileNotFoundException e){
                        System.out.println("Error: Cannot find file.");
                        }

                }
            }
        }
}
