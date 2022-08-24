package com.rafael.avaliacao.AvaliacaoPrimeiraParte;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class CsvReaderApplication {

	@SuppressWarnings("rawtypes")
	public static List ReaderFile(String CSV_PATH) {

		try {
			CSVReader csvReader = new CSVReader(new FileReader(new File(CSV_PATH)));

			List<Map<String, String>> retorno = new ArrayList<Map<String, String>>();

			String[] headers = csvReader.readNext();

			String[] linha = null;

			while ((linha = csvReader.readNext()) != null) {
				Map<String, String> campos = new HashMap<String, String>();
				for (int i = 0; i < linha.length; i++) {
					campos.put(headers[i], linha[i]);
				}
				retorno.add (campos) ;
			}
			
			return retorno;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}

