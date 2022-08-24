package com.rafael.avaliacao.AvaliacaoPrimeiraParte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MediaDesvio {

	private static final int PERIODO_CURTO = 5;
	private static final int PERIODO_INTERMEDIARIO = 20;
	private static final int PERIODO_LONGO = 35;

	private static ArrayList<Double> media;
	private static ArrayList<Double> mediaCurta;
	private static ArrayList<Double> mediaIntermediaria;
	private static ArrayList<Double> mediaLonga;
	private static ArrayList<Double> desvio;

	@SuppressWarnings("rawtypes")
	public static HashMap Media_Desvio(ArrayList<Double> arrayClose, List timeClose) {

		media = new ArrayList<>();
		mediaCurta = new ArrayList<>();
		mediaIntermediaria = new ArrayList<>();
		mediaLonga = new ArrayList<>();
		desvio = new ArrayList<>();
		HashMap<String, ArrayList<Double>> joingReturn = new HashMap<String, ArrayList<Double>>();

		double soma = 0.0;
		double erro = 0.0;
		double somaPC = 0.0;
		double somaPI = 0.0;
		double somaPL = 0.0;

		for (int i = 0; i < arrayClose.size(); i++) {

			// Media Simples
			soma += (double) arrayClose.get(i);
			media.add(soma / (i + 1));

			// Desvio Padrão
			erro += Math.pow((double) media.get(i) - (double) arrayClose.get(i), 2); // Math.pow(valor,expoente)
			desvio.add(Math.sqrt(erro / (i + 1)));

			// Media Curta

			if (i < (PERIODO_CURTO-1)) {
				somaPC += (double) arrayClose.get(i);
				mediaCurta.add(null);

			} else if (i == (PERIODO_CURTO-1)) {
				somaPC += (double) arrayClose.get(i);
				mediaCurta.add(somaPC / PERIODO_CURTO);
			} else {
				somaPC = somaPC - (double) arrayClose.get(i - PERIODO_CURTO) + (double) arrayClose.get(i);
				mediaCurta.add(somaPC / PERIODO_CURTO);

			}

			// Media Intermediario

			if (i < (PERIODO_INTERMEDIARIO - 1)) {
				somaPI += (double) arrayClose.get(i);
				mediaIntermediaria.add(null);

			} else if (i == (PERIODO_INTERMEDIARIO - 1)) {
				somaPI += (double) arrayClose.get(i);
				mediaIntermediaria.add(somaPI / PERIODO_INTERMEDIARIO);
			} else {
				somaPI = somaPI - (double) arrayClose.get(i - PERIODO_INTERMEDIARIO) + (double) arrayClose.get(i);
				mediaIntermediaria.add(somaPI / PERIODO_INTERMEDIARIO);

			}

			// Media Longa

			if (i < (PERIODO_LONGO - 1)) {
				somaPL += (double) arrayClose.get(i);
				mediaLonga.add(null);

			} else if (i == (PERIODO_LONGO - 1)) {
				somaPL += (double) arrayClose.get(i);
				mediaLonga.add(somaPL / PERIODO_LONGO);
			} else {
				somaPL = somaPL - (double) arrayClose.get(i - PERIODO_LONGO) + (double) arrayClose.get(i);
				mediaLonga.add(somaPL / PERIODO_LONGO);

			}

		}
		
		joingReturn.put("Media", media);
		joingReturn.put("Desvio", desvio);
		joingReturn.put("MediaCurta", mediaCurta);
		joingReturn.put("MediaIntermediaria", mediaIntermediaria);
		joingReturn.put("MediaLonga", mediaLonga);

		return joingReturn;

	}

}
