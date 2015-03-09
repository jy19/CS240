package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MyLineCounter extends FileExplorer implements LineCounter {

	@Override
	public Map<File, Integer> countLines(File directory, String fileSelectionPattern, boolean recursive) {
		Scanner scan = null;
		List<File> allFiles = new ArrayList<File>();
		Map<File, Integer> linesmap = new TreeMap<File, Integer>();
		try {
			allFiles = readFile(directory, fileSelectionPattern, recursive);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		}
		for(File f: allFiles){
			int lines = 0;
			try {
				scan = new Scanner(f);
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
				e.printStackTrace();
			}
			while(scan.hasNextLine()){
				lines++;
				String currLine = scan.nextLine();
			}
			linesmap.put(f, lines);
		}
		if(scan != null){
			scan.close();
		}
		
		return linesmap;
	}


}
