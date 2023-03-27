package ms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	private String fileName;
	private File file;
	
	public FileManager(String fileName) {
		this.fileName = fileName;
		this.file = new File(this.fileName);
	}
	
	private String[] load() {
		if(!file.exists())
			return null;
		
		ArrayList<String> data = new ArrayList<>();
		
		try (FileReader fr = new FileReader(this.fileName);
			 BufferedReader br = new BufferedReader(fr)){
			
			while(br.ready()) {
				data.add(br.readLine());
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		String[] resultData = data.toArray(new String[data.size()]);
		
		return resultData;
	}
	
	public int[][] getMap() {
		String[] receivedData = load();
		
		int size = Map.TOTAL_SIZE;		
		int[][] result = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			String[] tmp = receivedData[i].split(" ");
			for(int j = 0; j < size; j++) {
				result[i][j] = Integer.parseInt(tmp[j]);
			}
		}
		return result;
	}
	
	public int[][] getBattleGround(){
		String[] receivedData = load();
		boolean isTrue = this.fileName.contains("Boss");
		int size = isTrue ? Battle.BOSS_GROUND_SIZE : Battle.GENERAL_GROUND_SIZE;
		
		int[][] result = new int[size][];
		
		for(int i = 0; i < size; i++) {
			String[] tmpStringArr = receivedData[i].split(" ");
			int[] processingArr = new int[tmpStringArr.length];
			for(int j = 0; j < processingArr.length; j++) {
				processingArr[j] = Integer.parseInt(tmpStringArr[j]);
			}
			result[i] = processingArr;
		}
		
		return result;
	}
	
}
