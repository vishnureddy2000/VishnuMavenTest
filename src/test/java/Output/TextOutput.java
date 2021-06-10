package Output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextOutput {
	BufferedWriter bw;  // Creating BufferedWriter to write in the external text file
	public TextOutput() {
		 try {
			bw = new BufferedWriter(new FileWriter("/Users/vishnureddy/eclipse-workspace/Miniproject/src/test/java/Output/output.txt")); //opening the external text file at specific location
		} catch (IOException e) {
			System.out.println("Error in creating output file"+e);
			}
	}
	public void ratingswrite(String rentdetails) throws IOException {
		bw.write(rentdetails+"\n"); //writing and storing the rentdetails in external text file
	}
	
	public void close() throws IOException {
		bw.close();  // closing the text file
	}

}
