package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args){
		String dictionaryName = "";
		int wordLength = 0;
		int guesses = 0;
		if(args.length == 3){
			dictionaryName = args[0];
			wordLength = Integer.parseInt(args[1]);
			guesses = Integer.parseInt(args[2]);
		}
		else{
			System.out.println("USAGE: java [main class name] dictionary wordlength guesses");
		}
		EvilHangman game = new EvilHangman();
		game.startGame(new File(dictionaryName), wordLength);
		runGame(game, guesses);
	}
	
	public static void runGame(EvilHangman game, int guesses){
		
		Set<String> usedLetters = game.getUsedLetters();
		String pattern = game.getInitialPattern();
		printGame(guesses, usedLetters, pattern);
		Scanner input = new Scanner(System.in);
		String inputChar = "";
		char guess = 0;
		while(guesses > 0){
			Set<String> results = new TreeSet<String>();
			inputChar = input.next().toLowerCase();
			guess = inputChar.charAt(0);
			if(!Character.isLetter(guess)){
				System.out.println("Invalid character, please enter alphabet: ");
				continue;
			}
			try {
				results = game.makeGuess(guess);
				pattern = game.getCurrentPattern();
				if(pattern.contains(String.valueOf(guess))){
					int numchars = 0;
					for(int i = 0; i < pattern.length(); i++){
						if(pattern.charAt(i) == guess){
							numchars++;
						}
					}
					guesses++;
					printProcess(true, guess, numchars);
				}
				else{
					printProcess(false, guess, 0);
				}
			} catch (GuessAlreadyMadeException e) {
				System.out.println("You already made that guess, choose another: ");
				continue;
			}
			int count = 0;
			for(int i = 0; i < pattern.length(); i++){
				if(pattern.charAt(i) != '-'){
					count++;
				}
			}
			if(count == pattern.length()){
				printWin(results.toString());
				break;
			}
			guesses--;
			if(guesses == 0){
				String word = results.iterator().next();
				printLose(word);
			}
			printGame(guesses, usedLetters, pattern);
		}
		if(input != null){
			input.close();
		}
		
	}
	
	public static void printProcess(boolean contains, char guess, int numbers){
		if(contains){
			System.out.println("Yes, there is " + numbers + " " + guess);
		}
		else{
			System.out.println("Sorry, there are no " + guess + "'s");
		}
	}
	
	public static void printGame(int guesses, Set<String> usedLetters, String pattern){
			
			System.out.println("You have " + guesses + " guesses left");
			System.out.println("Used letters: " + usedLetters.toString());
			System.out.println("Word: " + pattern);
			System.out.println("Enter guess:");
			
	}
	
	public static void printWin(String correct){
		System.out.println("You win! " + correct);
		System.exit(0);
	}
	
	public static void printLose(String correct){
		System.out.println("You lose!");
		System.out.println("The word was: " + correct);
		System.exit(0);
	}
}
