package com.rafael.avaliacao.AvaliacaoPrimeiraParte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationMain {

	
	private static final String  CSV_PATH_FILE1 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDCAD_M1.csv"; // Dólar Canadense
	private static final String  CSV_PATH_FILE2 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDCHF_M1.csv"; // Dólar Franco Suíço
	private static final String  CSV_PATH_FILE3 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDHKD_M1.csv"; // Dólar de Hong Kong
	private static final String  CSV_PATH_FILE4 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDJPY_M1.csv"; // Dólar iene Japonês
	
	
	private static List<Map<String, String>> fileCSV1;
	private static List<Map<String, String>> fileCSV2;
	private static List<Map<String, String>> fileCSV3;
	private static List<Map<String, String>> fileCSV4;
	
	private static ArrayList<Double> arrayClose1;
	private static ArrayList<Double> arrayClose2;
	private static ArrayList<Double> arrayClose3;
	private static ArrayList<Double> arrayClose4;
	
	private static ArrayList<String> timeClose;
	
	private static HashMap<String, ArrayList<Double>> joingReturn1;
	private static HashMap<String, ArrayList<Double>> joingReturn2;
	private static HashMap<String, ArrayList<Double>> joingReturn3;
	private static HashMap<String, ArrayList<Double>> joingReturn4;
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		arrayClose1 = new ArrayList<>();
		arrayClose2 = new ArrayList<>();
		arrayClose3 = new ArrayList<>();
		arrayClose4 = new ArrayList<>();
		
		timeClose = new ArrayList<>();

		fileCSV1 =  CsvReaderApplication.ReaderFile(CSV_PATH_FILE1);
		fileCSV2 =  CsvReaderApplication.ReaderFile(CSV_PATH_FILE2);
		fileCSV3 =  CsvReaderApplication.ReaderFile(CSV_PATH_FILE3);
		fileCSV4 =  CsvReaderApplication.ReaderFile(CSV_PATH_FILE4);

	
		
		closeArray();
		
		joingReturn1 = MediaDesvio.Media_Desvio(arrayClose1,timeClose);
		joingReturn2 = MediaDesvio.Media_Desvio(arrayClose2,timeClose);
		joingReturn3 = MediaDesvio.Media_Desvio(arrayClose3,timeClose);
		joingReturn4 = MediaDesvio.Media_Desvio(arrayClose4, timeClose);
		
		SaveData.saveData(joingReturn1, joingReturn2, joingReturn3, joingReturn4 );

	}	
	private static void closeArray() {
		
		fileCSV1.forEach(cols -> {
			arrayClose1.add(Double.parseDouble(cols.get("<CLOSE>")));
			timeClose.add(cols.get("<TIME>"));
		});
		
		fileCSV2.forEach(cols -> {
			arrayClose2.add(Double.parseDouble(cols.get("<CLOSE>")));
		});
		fileCSV3.forEach(cols -> {
			arrayClose3.add(Double.parseDouble(cols.get("<CLOSE>")));
		});
		fileCSV4.forEach(cols -> {
			arrayClose4.add(Double.parseDouble(cols.get("<CLOSE>")));
		});

	}

}

