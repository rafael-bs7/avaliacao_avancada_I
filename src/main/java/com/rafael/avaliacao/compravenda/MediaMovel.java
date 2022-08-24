package com.rafael.avaliacao.compravenda;

import java.util.ArrayList;

public class MediaMovel {

	
	public static void mediaMovel(ArrayList<Double> ativo, int p, ArrayList<Double> mediaMovel) {

		if (ativo.size() > p) {
			double sum = 0;
			for (int j = ativo.size(); j > ativo.size()-p; j--) {	
				sum +=  ativo.get(j-1);
			}
			mediaMovel.add(sum / p);;
		}

	}
	
}
