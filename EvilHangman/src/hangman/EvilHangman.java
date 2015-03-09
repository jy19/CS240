package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EvilHangman implements EvilHangmanGame{

	Set<String> currentPosWords = new TreeSet<String>();
	Map<String, Set<String>> wordMap = new TreeMap<String, Set<String>>();
	Set<String> usedLetters = new TreeSet<String>();
	String chosenPattern = "";
	int inputWordLength = 0;
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		wordMap.clear();
		Scanner s = null;
		inputWordLength = wordLength;
		chosenPattern = getInitialPattern();
		try {
			s = new Scanner(dictionary);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(s.hasNext()){
			String currWord = s.next();
			if(currWord.length() == wordLength){
				currentPosWords.add(currWord);
			}
		}
		
		if(s != null){
			s.close();
		}
		
	}
	
	
	
	public void buildMap(char guess){
		wordMap.clear();
		for(String word : currentPosWords){
			String p = createPattern(word, guess);
			if(wordMap.containsKey(p)){
				wordMap.get(p).add(word);
			}
			else{
				Set<String> currPattern = new TreeSet<String>();
				currPattern.add(word);
				wordMap.put(p, currPattern);	
			}
		}
	}
	
	public String chooseKey(char guess){
		String chosenkey = "";
		filterbySize();
		if(wordMap.size() > 1){
			filterbyFrequency(guess);
			if(wordMap.size() > 1){
				filterbyRight(guess);
				for(String s: wordMap.keySet()){
					chosenkey = s;
				}
			}
			else{
				for(String s: wordMap.keySet()){
					chosenkey = s;
				}
			}
		}
		else{
			for(String s: wordMap.keySet()){
				chosenkey = s;
			}
		}
		chosenPattern = chosenkey;
		return chosenkey;
	}
	
	public void filterbySize(){
		int maxSize = 0;
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			if(maxSize < entry.getValue().size()){
				maxSize = entry.getValue().size();
			}
		}
		Set<String> filter = new TreeSet<String>();
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			if(entry.getValue().size() != maxSize){
				filter.add(entry.getKey());
			}
		}
		wordMap.keySet().removeAll(filter);
	}
	
	public void filterbyFrequency(char guess){
		int least = 100;
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			int frequency = 0;
			for(int i = 0; i < entry.getKey().length(); i++){
				if(entry.getKey().charAt(i) == guess){
					frequency++;
				}
			}
			if(frequency < least){
				least = frequency;
			}
		}
		Set<String> filter = new TreeSet<String>();
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			int frequency = 0;
			for(int i = 0; i < entry.getKey().length(); i++){
				if(entry.getKey().charAt(i) == guess){
					frequency++;
				}
			}
			if(frequency != least){
				filter.add(entry.getKey());
			}
		}
		wordMap.keySet().removeAll(filter);
	}
	
	public void filterbyRight(char guess){
		String comparing = "~";
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			String current = entry.getKey();
			if(current.compareTo(comparing) < 0){
				comparing = current;
			}
		}
		Set<String>filter = new TreeSet<String>();
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			if(!entry.getKey().equals(comparing)){
				filter.add(entry.getKey());
			}
		}
		wordMap.keySet().removeAll(filter);
	}
	
	
	public String createPattern(String currWord, char guess){
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < currWord.length(); i++){
			if(currWord.charAt(i) == guess){
				sb.append(guess);
			}
			else if(chosenPattern.charAt(i) != '-'){
				sb.append(chosenPattern.charAt(i));
			}
			else{
				sb.append('-');
			}
			
		}
		return sb.toString();	
	}
	
	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		String strGuess = Character.toString(guess);
		if(usedLetters.contains(strGuess)){
			throw new GuessAlreadyMadeException();
		}
		usedLetters.add(strGuess);
		buildMap(guess);
		String chosenkey = chooseKey(guess);
		currentPosWords = wordMap.get(chosenkey);
		return currentPosWords;
	}
	
	public Set<String> getUsedLetters(){
		return usedLetters;
	}
	
	public String getCurrentPattern(){
		return chosenPattern;
	}
	
	public String getInitialPattern(){
		StringBuilder p = new StringBuilder("");
		for(int i = 0; i < inputWordLength; i++){
			p.append('-');
		}
		return p.toString();
	}
	
}
