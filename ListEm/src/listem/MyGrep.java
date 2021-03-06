package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyGrep extends FileExplorer implements Grep {

	
	@Override
	public Map<File, List<String>> grep(File directory, String fileSelectionPattern, String substringSelectionPattern, boolean recursive) {
		Scanner scan = null;
		List<File> allFiles = new ArrayList<File>();
		Map<File, List<String>> grepmap = new TreeMap <File, List<String>>();
		try {
			allFiles = readFile(directory, fileSelectionPattern, recursive);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile(substringSelectionPattern);
		for(File f: allFiles){
			try {
				scan = new Scanner(f);
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
				e.printStackTrace();
			}
			List<String> matching = new ArrayList<String>();
			while(scan.hasNextLine()){
				String currLine = scan.nextLine();
				Matcher matcher = pattern.matcher(currLine);
				if(matcher.find()){
					matching.add(currLine);
				}
			}
			if(!matching.isEmpty()){
				grepmap.put(f, matching);
			}
			
		}
		if(scan != null){
			scan.close();
		}
	
		return grepmap;
	}

	

}
