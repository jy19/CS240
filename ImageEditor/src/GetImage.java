import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class GetImage{
	
	private File input;
	private File output;
	private String transtype;
	private String blurnum;
	
	public GetImage(File input, File output, String transtype, String blurnum){
		this.input = input;
		this.output = output;
		this.transtype = transtype;
		this.blurnum = blurnum;
	}
	
	public void copy() throws IOException{
		Scanner scanner = new Scanner(input).useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s+)|(#[^\\n]*\\n)");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(output)));
		
		String p3 = scanner.next();
		int width = scanner.nextInt();
		int height = scanner.nextInt();
		int maxvalue = scanner.nextInt();
		
		String misc = p3 + "\n" + width + "\t" + height + "\n" + maxvalue + "\n"; 
		
		Pixel[][] imageArray = new Pixel[height][width];
		Pixel[][] changedArray = new Pixel[height][width];
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				Pixel p = new Pixel(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
				imageArray[i][j] = p;
			}
		}
		switch(transtype){
		case "invert":
			changedArray = invert(width, height, imageArray);
			printChanged(width, height, changedArray, writer, misc);
			break;
		case "grayscale":
			changedArray = grayscale(width, height, imageArray);
			printChanged(width, height, changedArray, writer, misc);
			break;
		case "emboss":
			changedArray = emboss(width, height, imageArray);
			printChanged(width, height, changedArray, writer, misc);
			break;
		case "motionblur":
			changedArray = motionblur(width, height, imageArray, blurnum);
			printChanged(width, height, changedArray, writer, misc);
			break;
		}
		
		scanner.close();
		writer.close();
	}
	
	public void printChanged(int width, int height, Pixel[][] printing, PrintWriter writer, String misc){
		writer.write(misc);
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				String current = printing[i][j].getRed() + "\n" + printing[i][j].getGreen() + "\n" + printing[i][j].getBlue() + "\n";
				writer.write(current);
			}
		}
	}
	
	public Pixel[][] invert(int width, int height, Pixel[][] imageArray) {
		Pixel[][] changedArray = new Pixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int invRed = 255 - imageArray[i][j].getRed();
				int invGreen = 255 - imageArray[i][j].getGreen();
				int invBlue = 255 - imageArray[i][j].getBlue();
				Pixel invP = new Pixel(invRed, invGreen, invBlue);
				changedArray[i][j] = invP;
			}
		}
		return changedArray;
	}
	public Pixel[][] grayscale(int width, int height, Pixel[][] imageArray) {
		Pixel[][] changedArray = new Pixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int avg = (imageArray[i][j].getRed() + imageArray[i][j].getGreen() + imageArray[i][j].getBlue())/3;
				Pixel greyP = new Pixel(avg, avg, avg);
				changedArray[i][j] = greyP;
			}
		}
		return changedArray;
	}

	public Pixel[][] emboss(int width, int height, Pixel[][]imageArray) {
		Pixel[][] changedArray = new Pixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int v = 0;
				int maxDiff = 0;
				if(i == 0 || j == 0){
					maxDiff = 0;
				}
				else{
					int diffRed = imageArray[i][j].getRed() - imageArray[i-1][j-1].getRed();
					int diffGreen = imageArray[i][j].getGreen() - imageArray[i-1][j-1].getGreen();
					int diffBlue = imageArray[i][j].getBlue() - imageArray[i-1][j-1].getBlue();
					maxDiff = diffRed;
					if(Math.abs(diffGreen) > Math.abs(diffRed)){
						maxDiff = diffGreen;
						if(Math.abs(diffGreen) < Math.abs(diffBlue)){
							maxDiff = diffBlue;
						}
					}
					else if(Math.abs(diffBlue) > Math.abs(diffRed) && Math.abs(diffBlue) > Math.abs(diffGreen)){
						maxDiff = diffBlue;
					}
				}
				v = maxDiff + 128;
				if(v < 0){
					v = 0;
				}
				if(v > 255){
					v = 255;
				}
				Pixel diffP = new Pixel(v, v, v);
				changedArray[i][j] = diffP;
			}
		}
		return changedArray;
		
	}
	public Pixel[][] motionblur(int width, int height, Pixel[][] imageArray, String blurnum) {
		Pixel[][] changedArray = new Pixel[height][width];
		int n = Integer.parseInt(blurnum);
		int originalN = n;
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int avgRed = 0;
				int avgGreen = 0;
				int avgBlue = 0;
				if(j + n >= width){
					n = width - j;
				}
				for(int k = j; k < j + n; k++){
					avgRed += imageArray[i][k].getRed();
					avgGreen += imageArray[i][k].getGreen();
					avgBlue += imageArray[i][k].getBlue();
				}
				avgRed /= n;
				avgGreen /= n;
				avgBlue /= n;
				Pixel blurP = new Pixel(avgRed, avgGreen, avgBlue);
				changedArray[i][j] = blurP;
				n = originalN;
			}
		}
		return changedArray;
		
	}

}