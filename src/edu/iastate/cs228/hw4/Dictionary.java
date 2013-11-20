package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * @author Darren Hushak
 * 
 *         An application class
 */
public class Dictionary {
	public static void main(String[] args) {
		EntryTree<Character, String> tree = new EntryTree<Character, String>();
		tree.add(toCharacterArray("df"), "fasdf");

		tree.add(toCharacterArray("ad"), "qqqq");

		tree.add(toCharacterArray("af"), "fartnugget");

		tree.add(toCharacterArray("d"), "queef");

		tree.add(toCharacterArray("c"), "d");

		tree.remove(toCharacterArray("d"));

		tree.remove(toCharacterArray("df"));

		System.out.println(tree.remove(toCharacterArray("c")));
		Character[] charray = tree.prefix(toCharacterArray("adfq"));
		for (int i = 0; i < charray.length; i++) {
			System.out.print(charray[i]);
		}
		System.out.println("");
		
		System.out.println(tree.search(toCharacterArray("dfd")));
		System.out.println(tree.search(toCharacterArray("add")));
		System.out.println(tree.search(toCharacterArray("ad")));

		System.out.println(tree.search(toCharacterArray("af")));

		tree.showTree();
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