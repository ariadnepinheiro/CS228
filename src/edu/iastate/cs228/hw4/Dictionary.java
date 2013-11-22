package edu.iastate.cs228.hw4;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Darren Hushak
 * 
 *         An application class
 */
public class Dictionary {
	public static void main(String[] args) {
		// Create tree
		EntryTree<Character, String> tree = new EntryTree<Character, String>();
		FileReader inputFile = null;
		try {
			// Get input file from args
			inputFile = new FileReader(args[0]);
		} catch (FileNotFoundException e) {
			// If file not found, print the directory that the user should place
			// the file in, then terminate
			String current;
			try {
				current = new java.io.File(".").getCanonicalPath();
				System.out.println("Place file in this directory:" + current);
			} catch (IOException e1) {
			}
			System.out.println("Terminating the program.");
			System.exit(-1);
		}

		// Start scanning
		Scanner sc = new Scanner(inputFile);
		while (sc.hasNextLine()) {
			String line;
			line = sc.nextLine(); // read the next line into a String object
			String[] sArr = line.split("\\s+"); // split the string by spaces
			if (sArr[0].equalsIgnoreCase("add")) {
				System.out.println("Command: " + sArr[0] + " " + sArr[1] + " "
						+ sArr[2]);
				System.out.println("Result from add: "
						+ tree.add(toCharacterArray(sArr[1]), sArr[2]));
			}
			if (sArr[0].equalsIgnoreCase("remove")) {

				System.out.println("Command: " + sArr[0] + " " + sArr[1]);
				System.out.println("Result from remove: "
						+ tree.remove(toCharacterArray(sArr[1])));
			}
			if (sArr[0].equalsIgnoreCase("showTree")) {
				System.out.println("Result from showTree:");
				tree.showTree();
			}
			if (sArr[0].equalsIgnoreCase("search")) {
				System.out.println("Command: " + sArr[0] + " " + sArr[1]);
				System.out.println("Result from search: " + tree.search(toCharacterArray(sArr[1])));
			}
			if (sArr[0].equalsIgnoreCase("prefix")) {
				System.out.println("Command: " + sArr[0] + " " + sArr[1]);
				System.out.print("Result from prefix: ");
				Character[] charray = tree.prefix(toCharacterArray(sArr[1]));
				for (int i = 0; i < charray.length; i++) {
					System.out.print(charray[i]);
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}

	private static Character[] toCharacterArray(String s) {
		if (s == null) {
			return null;
		}
		Character[] array = new Character[s.length()];
		for (int i = 0; i < s.length(); i++) {
			array[i] = new Character(s.charAt(i));
		}

		return array;
	}

}