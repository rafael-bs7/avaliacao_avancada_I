package com.rafael.avaliacao.AvaliacaoPrimeiraParte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class SaveData {

	private static final String EXCEL_FILE_LOCATION = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\saveData.xls";

	private static ArrayList<Double> media;
	private static ArrayList<Double> mediaCurta;
	private static ArrayList<Double> mediaIntermediaria;
	private static ArrayList<Double> mediaLonga;
	private static ArrayList<Double> desvio;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void saveData(HashMap joingReturn1, HashMap joingReturn2, HashMap joingReturn3,
			HashMap joingReturn4) {

		
		WritableWorkbook myFirstWbook = null; // 1. Create an Excel file

		try {

			// FILE 1

			media = (ArrayList<Double>) joingReturn1.get("Media");
			desvio = (ArrayList<Double>) joingReturn1.get("Desvio");
			mediaCurta = (ArrayList<Double>) joingReturn1.get("MediaCurta");
			mediaIntermediaria = (ArrayList<Double>) joingReturn1.get("MediaIntermediaria");
			mediaLonga = (ArrayList<Double>) joingReturn1.get("MediaLonga");

			myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));  // Cria o arquivo excel no caminho definido

			// create an Excel sheet
			WritableSheet excelSheet = myFirstWbook.createSheet("Ativo A", 0); // Cria uma planilha

			//cabeçalho
			
			// escreve na célula de coluna 0 e linha 0 o valor de Media
			Label label = new Label(0, 0, "Media");   
			excelSheet.addCell(label);
			
			
			// escreve na célula de coluna 1 e linha 0 o valor de Desvio
			label = new Label(1, 0, "Desvio");
			excelSheet.addCell(label);

			// escreve na célula de coluna 2 e linha 0 o valor de Media Curta
			label = new Label(2, 0, "Media Curta");
			excelSheet.addCell(label);

			// escreve na célula de coluna 3 e linha 0 o valor de Media Intermediaria
			label = new Label(3, 0, "Media Intermediaria");
			excelSheet.addCell(label);

			// escreve na célula de coluna 4 e linha 0 o valor de Media Longa
			label = new Label(4, 0, "Media Longa");
			excelSheet.addCell(label);

			Number number;
			
			//laço de iteração para registrar os valores de media, desvio, media intermediaria e media longa 
			// do ativo A na planilha "Ativo A"
			for (int i = 0; i < media.size(); i++) {
				
				
				number = new Number(0, i + 1, (double) media.get(i));
				excelSheet.addCell(number);

				number = new Number(1, i + 1, (double) desvio.get(i));
				excelSheet.addCell(number);

				if (mediaCurta.get(i) != null) {
					number = new Number(2, i + 1, (double) mediaCurta.get(i));
					excelSheet.addCell(number);
				}

				if (mediaIntermediaria.get(i) != null) {
					number = new Number(3, i + 1, (double) mediaIntermediaria.get(i));
					excelSheet.addCell(number);
				}

				if (mediaLonga.get(i) != null) {
					number = new Number(4, i + 1, (double) mediaLonga.get(i));
					excelSheet.addCell(number);
				}

			}

			// Ativo B

			media = (ArrayList<Double>) joingReturn2.get("Media");
			desvio = (ArrayList<Double>) joingReturn2.get("Desvio");
			mediaCurta = (ArrayList<Double>) joingReturn2.get("MediaCurta");
			mediaIntermediaria = (ArrayList<Double>) joingReturn2.get("MediaIntermediaria");
			mediaLonga = (ArrayList<Double>) joingReturn2.get("MediaLonga");

			//cabeçalho
			
			// Cria uma nova planilha no mesmo arquivo excel para o ativo B
			excelSheet = myFirstWbook.createSheet("Ativo B", 1);

			// add something into the Excel sheet Label(COLUNA, LINHA, VALOR);
			label = new Label(0, 0, "Media");
			excelSheet.addCell(label);

			label = new Label(1, 0, "Desvio");
			excelSheet.addCell(label);

			label = new Label(2, 0, "Media Curta");
			excelSheet.addCell(label);

			label = new Label(3, 0, "Media Intermediaria");
			excelSheet.addCell(label);

			label = new Label(4, 0, "Media Longa");
			excelSheet.addCell(label);

			for (int i = 0; i < media.size(); i++) {

				number = new Number(0, i + 1, (double) media.get(i));
				excelSheet.addCell(number);

				number = new Number(1, i + 1, (double) desvio.get(i));
				excelSheet.addCell(number);

				if (mediaCurta.get(i) != null) {
					number = new Number(2, i + 1, (double) mediaCurta.get(i));
					excelSheet.addCell(number);
				}

				if (mediaIntermediaria.get(i) != null) {
					number = new Number(3, i + 1, (double) mediaIntermediaria.get(i));
					excelSheet.addCell(number);
				}

				if (mediaLonga.get(i) != null) {
					number = new Number(4, i + 1, (double) mediaLonga.get(i));
					excelSheet.addCell(number);
				}

			}

			// Ativo C

			media = (ArrayList<Double>) joingReturn3.get("Media");
			desvio = (ArrayList<Double>) joingReturn3.get("Desvio");
			mediaCurta = (ArrayList<Double>) joingReturn3.get("MediaCurta");
			mediaIntermediaria = (ArrayList<Double>) joingReturn3.get("MediaIntermediaria");
			mediaLonga = (ArrayList<Double>) joingReturn3.get("MediaLonga");

			// Cria uma nova planilha no mesmo arquivo excel para o ativo C
			excelSheet = myFirstWbook.createSheet("Ativo C", 2);

			// add something into the Excel sheet Label(COLUNA, LINHA, VALOR);
			label = new Label(0, 0, "Media");
			excelSheet.addCell(label);

			label = new Label(1, 0, "Desvio");
			excelSheet.addCell(label);

			label = new Label(2, 0, "Media Curta");
			excelSheet.addCell(label);

			label = new Label(3, 0, "Media Intermediaria");
			excelSheet.addCell(label);

			label = new Label(4, 0, "Media Longa");
			excelSheet.addCell(label);

			for (int i = 0; i < media.size(); i++) {

				number = new Number(0, i + 1, (double) media.get(i));
				excelSheet.addCell(number);

				number = new Number(1, i + 1, (double) desvio.get(i));
				excelSheet.addCell(number);

				if (mediaCurta.get(i) != null) {
					number = new Number(2, i + 1, (double) mediaCurta.get(i));
					excelSheet.addCell(number);
				}

				if (mediaIntermediaria.get(i) != null) {
					number = new Number(3, i + 1, (double) mediaIntermediaria.get(i));
					excelSheet.addCell(number);
				}

				if (mediaLonga.get(i) != null) {
					number = new Number(4, i + 1, (double) mediaLonga.get(i));
					excelSheet.addCell(number);
				}

			}

			// Ativo D

			media = (ArrayList<Double>) joingReturn4.get("Media");
			desvio = (ArrayList<Double>) joingReturn4.get("Desvio");
			mediaCurta = (ArrayList<Double>) joingReturn4.get("MediaCurta");
			mediaIntermediaria = (ArrayList<Double>) joingReturn4.get("MediaIntermediaria");
			mediaLonga = (ArrayList<Double>) joingReturn4.get("MediaLonga");

			// Cria uma nova planilha no mesmo arquivo excel para o ativo D
			excelSheet = myFirstWbook.createSheet("Ativo D", 3);

			// add something into the Excel sheet Label(COLUNA, LINHA, VALOR);
			label = new Label(0, 0, "Media");
			excelSheet.addCell(label);

			label = new Label(1, 0, "Desvio");
			excelSheet.addCell(label);

			label = new Label(2, 0, "Media Curta");
			excelSheet.addCell(label);

			label = new Label(3, 0, "Media Intermediaria");
			excelSheet.addCell(label);

			label = new Label(4, 0, "Media Longa");
			excelSheet.addCell(label);

			for (int i = 0; i < media.size(); i++) {

				number = new Number(0, i + 1, (double) media.get(i));
				excelSheet.addCell(number);

				number = new Number(1, i + 1, (double) desvio.get(i));
				excelSheet.addCell(number);

				if (mediaCurta.get(i) != null) {
					number = new Number(2, i + 1, (double) mediaCurta.get(i));
					excelSheet.addCell(number);
				}

				if (mediaIntermediaria.get(i) != null) {
					number = new Number(3, i + 1, (double) mediaIntermediaria.get(i));
					excelSheet.addCell(number);
				}

				if (mediaLonga.get(i) != null) {
					number = new Number(4, i + 1, (double) mediaLonga.get(i));
					excelSheet.addCell(number);
				}

			}

			myFirstWbook.write();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {

			if (myFirstWbook != null) {
				try {
					myFirstWbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
