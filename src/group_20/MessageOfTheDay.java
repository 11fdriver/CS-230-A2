package group_20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Gets the message of the day
 * @author Yoshan Mumberson
 *
 */
public class MessageOfTheDay {
	public static void main(String[] args) {
		String puzzle = "";
		try {
			puzzle = getPuzzle();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(puzzle);
		
		String key = decrypt(puzzle);
		
		String MOTD = "";
		try {
			MOTD = getMOTD(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(MOTD);
		//System.out.println(shiftChar('A',-3));
		//System.out.println(decrypt("CAB"));
	}
	
	/**
	 * @return Decrypted MOTD from the website
	 * @throws IOException
	 */
	public static String getMessage() throws IOException {
		String puzzle = "";
		puzzle = getPuzzle();
		//System.out.println(puzzle);
		
		String key = decrypt(puzzle);
		
		String MOTD = "";
		MOTD = getMOTD(key);
		//System.out.println(MOTD);
		return MOTD;
	}
	
	/**
	 * Gets the puzzle from the website
	 * @return encrypted puzzle ready to solve
	 * @throws IOException
	 */
	public static String getPuzzle() throws IOException {
		URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        String inputLine;
        String encryptedMOTD = "";
        while ((inputLine = in.readLine()) != null)
            //System.out.println(inputLine);
        	encryptedMOTD = inputLine;
        in.close();
		
		return encryptedMOTD;
	}
	
	/**
	 * Fetches the message of the day
	 * @param key As decrypted key
	 * @return MOTD
	 * @throws IOException
	 */
	public static String getMOTD(String key) throws IOException {
		URL url = new URL("http://cswebcat.swansea.ac.uk/message?solution=" + key);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        String inputLine;
        String encryptedMOTD = "";
        while ((inputLine = in.readLine()) != null)
            //System.out.println(inputLine);
        	encryptedMOTD = inputLine;
        in.close();
		
		return encryptedMOTD;
		
	}
	
	/**
	 * Decrypts a puzzle into a key
	 * @param s Puzzle
	 * @return Key
	 */
	public static String decrypt(String s) {
		String decrypted = "";
		int shiftDirection = -1;
		char[] inpArr = s.toCharArray();
		for (int i = 0; i < s.length(); i++) {
			decrypted += String.valueOf(shiftChar(inpArr[i], (i+1)*shiftDirection));
			shiftDirection = shiftDirection*-1;
		}
		decrypted = "CS-230" + decrypted;
		decrypted += decrypted.length();
		
		
		return decrypted;
	}
	
	/**
	 * Shifts characters alphabetically
	 * @param c Character to shift
	 * @param shiftAmount Amount to shift character by
	 * @return Shifted character
	 */
	public static char shiftChar(char c, int shiftAmount) {
		int ascii = (int) c;
		if (ascii > 64 && ascii < 91) {
			ascii += shiftAmount;
		}
		
		while (ascii < 65) {
			ascii += 26;
		}
		
		while (ascii > 90) {
			ascii -= 26;
		}
		
		return (char) ascii;
	}
}
