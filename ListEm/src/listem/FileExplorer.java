package listem;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FileExplorer {
	
	/*File directory;
	String fileSelectionPattern;
	String subStringSelectionPattern;
	boolean recursive;*/
	
	//abstract boolean processLines();
	
	public List<File> readFile(File directory, String fileSelectionPattern, boolean recursive) throws FileNotFoundException{
		List<File> allFiles = new ArrayList<File>();
		File[] files = directory.listFiles();
		Pattern pattern = Pattern.compile(fileSelectionPattern);
		for(File f: files){
			Matcher matcher = pattern.matcher(f.getName());
			if(matcher.matches() && f.isFile()){
				//System.out.println(f.getName());
				allFiles.add(f);
			}
			else if(f.isDirectory() && recursive == true){
				recRead(f, fileSelectionPattern, allFiles);
			}
		}
		return allFiles;
	}
	
	public void recRead(File directory, String fileSelectionPattern, List<File> allFiles){
		File[] tempFiles = directory.listFiles();
		Pattern pattern = Pattern.compile(fileSelectionPattern);
		for(File f: tempFiles){
			Matcher matcher = pattern.matcher(f.getName());
			if(f.isDirectory()){
				recRead(f, fileSelectionPattern, allFiles);
			}
			else if(matcher.matches()){
				allFiles.add(f);
			}
		}
		return;
	}
}
