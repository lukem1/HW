//
//	wordLadder.java
//	lukem1
//	April 11 2019
//

import java.util.*;
import java.io.*;

public class wordLadder
{
	private static String inputFile = "words.txt";
	
	private static ArrayList<String> words;
	
	private static String startWord;
	private static String endWord;
	
	//Method for reading a wordlist file and returning it as an ArrayList
	public static ArrayList<String> readWordlist(String file)
	{
		ArrayList<String> wordlist = new ArrayList<String>();
		Scanner inputStream = null;
		
		try
		{
			inputStream = new Scanner(new File(file));
			
			while (inputStream.hasNextLine())
			{
				wordlist.add(inputStream.nextLine().toLowerCase());
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error Locating " + file);
			System.exit(-1);
		}
		finally
		{
			inputStream.close();
		}
		System.out.println("Read in wordlist of length " + wordlist.size());
		return wordlist;
	}
	
	//Method to remove words not of length len from the ArrayList list
	public static void WordLenFilter(ArrayList<String> list, int len)
	{
		ArrayList<String> filteredList = new ArrayList<String>();
		
		for (String word: list)
		{
			if (word.length() == len)
			{
				filteredList.add(word);
			}
		}
		
		list.clear();
		
		for (String word: filteredList)
		{
			list.add(word);
		}
	}
	
	//Recursive method to find a word ladder
	public static void findLadder(ArrayList<String> path)
	{
		/*System.out.print("Current Path: ");
		for (String word: path)
		{
			System.out.print("/"+word);
			//System.out.print("+");
		}
		System.out.println();*/
		
		//Find possible next words
		ArrayList<String> possibleNexts = new ArrayList<String>();
		
		for (String word: words)
		{
			int d = 0;
			for (int i = 0; i < word.length(); i++)
			{
				if (word.charAt(i) != path.get(path.size()-1).charAt(i))
				{
					d += 1;
				}
			}
			
			if (d == 1)
			{
				possibleNexts.add(word);
			}
		}
		
		if (possibleNexts.contains(endWord))
		{
			path.add(endWord);
			System.out.println("Found Word Ladder:");
			for (String word: path)
			{
				System.out.println(word);
			}
		}
		else if (possibleNexts.size() >= 1)
		{
			path.add(possibleNexts.get(0));
			words.remove(words.indexOf(possibleNexts.get(0)));
			findLadder(path);
		}
		else if (path.size() > 1)
		{
			path.remove(path.size() - 1);
			findLadder(path);
		}
		else
		{
			System.out.println("No Word Ladder Found");
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
				if (args.length != 2)
				{
					throw new Exception("Error: Invalid Arguments\nUsage: java wordLadder <start word> <end word>");
				}
				if (args[0].length() != args[1].length())
				{
					throw new Exception("Error: <start word> and <end word> must be of equal length");
				}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
		startWord = args[0].toLowerCase();
		endWord = args[1].toLowerCase();
		
		words = readWordlist(inputFile);
		
		WordLenFilter(words, startWord.length());
		System.out.println("Words length has been filtered to: " + words.size());
		
		if (!words.contains(endWord))
		{
			words.add(endWord);
			System.out.println("added endWord to words");
		}
		
		ArrayList<String> startPath = new ArrayList<String>();
		startPath.add(startWord);
		
		findLadder(startPath);
	}
}
