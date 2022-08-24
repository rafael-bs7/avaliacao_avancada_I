package com.rafael.avaliacao.compravenda;

import java.util.ArrayList;


public class Analisa implements Runnable {

	private static final int PERIODO_CURTO = 5;
	private static final int PERIODO_LONGO = 20;

	private ArrayList<Double> mediaCurta;
	private ArrayList<Double> mediaLonga;

	private ArrayList<Double> listaAtivo;

	private ArrayList<Double> maxminDrawDown;

	public Analisa(ArrayList<Double> listaAtivo) {
		this.listaAtivo = listaAtivo;

		mediaCurta = new ArrayList<>();
		mediaLonga = new ArrayList<>();

		// DrawDown
		maxminDrawDown = new ArrayList<>();
		maxminDrawDown.add(0, 0.0); // min
		maxminDrawDown.add(1, 0.0); // max

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (Corretora.executaThread()) {
			try {
				MediaMovel.mediaMovel(listaAtivo, PERIODO_CURTO, mediaCurta);
				MediaMovel.mediaMovel(listaAtivo, PERIODO_LONGO, mediaLonga);

				Thread.sleep(10);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// retorna 1 se opera como comprado
	// retorna 0 se opera como vendido
	// retorna -1 opera como vendido via drawdown
	public int analisa() {

		if (mediaLonga.size() > 1) {

			// media curta dois periodos anteriores eh menor que a media longa no periodo
			// atual
			// e a media curta atual eh maior ou igual a media longa atual. Logo, a media
			// curta cruzou
			// a media longa para cima e, por isso, tendencia de alta. Logo, pode-se operar
			// como comprado;
			if ((mediaCurta.get(mediaCurta.size() - 3) < mediaLonga.get(mediaLonga.size() - 1))
					&& (mediaCurta.get(mediaCurta.size() - 1) >= mediaLonga.get(mediaLonga.size() - 1))) {
				return 1;

				// media curta dois periodos anteriores eh maior que a media longa atual
				// e a media curta atual eh menor que a media longa atual. Portanto, a media
				// curta
				// cruza a media longa para baixo e, por isso, tendencia de baixa. Logo, opera-se
				// como vendido;
			} else if ((mediaCurta.get(mediaCurta.size() - 3) > mediaLonga.get(mediaLonga.size() - 1))
					&& (mediaCurta.get(mediaCurta.size() - 1) < mediaLonga.get(mediaLonga.size() - 1))) {

				// se o rebaixamento for menor do que 20%, prioridade para venda.
				if (drawdown()) {
					return -1;
				}

				return 0;

				// Condicao na qual a media curta anterior não  eh menor que a
				// media longa atual.
				// e a media curta atual eh maior do que a mediaLonga atual e a media curta
				// anterior eh menor do que
				// a media curta nesse periodo. Logo, a melhor operacao seria vender todos os
				// ativos, uma vez que chegou
				// na condicao de elevação máxima.

				// MEDIA CURTA ACIMA DA MEDIA LONGA
			} else if ((mediaCurta.get(mediaCurta.size() - 1) > mediaLonga.get(mediaLonga.size() - 1))
					&& (mediaCurta.get(mediaCurta.size() - 3) > (mediaCurta.size() - 1))) {

				// se o rebaixamento for menor do que 20%, prioridade para venda.
				if (drawdown()) {
					return -1;
				}
				return 0;

				// condicao na qual a media curta anterior eh menor que a media longa, media
				// curta atual eh menor que a media longa, ou seja, se esses dois cenários está
				// ocorrendo,
				// podemos considerar que a media curta esta abaixo da media longa. Considerando
				// isso, se a media curta atual for maior que media curta anteior, isso implica
				// que
				// que chegou no vale da curva media curta e, nesse ponto, a tendencia comecou a
				// subir. Com os preços baixos, e tendencia de alta, melhor opção seria operacao
				// comprado.
			} else if ((mediaCurta.get(mediaCurta.size() - 3) < mediaLonga.get(mediaLonga.size() - 1)
					&& (mediaCurta.get(mediaCurta.size() - 1) < mediaLonga.get(mediaLonga.size() - 1))
					&& (mediaCurta.get(mediaCurta.size() - 1) > mediaCurta.get(mediaCurta.size() - 3)))) {
				return 1;

			}
		} else if (mediaCurta.size() > 2) {
			if (mediaCurta.get(mediaCurta.size() - 1) > mediaCurta.get(mediaCurta.size() - 2)) {
				return 1;
			}
		}

		return -2;
	}

	private boolean drawdown() {

		double pCalculada; // porcentagem calculada
		double maxAtivo = maxminDrawDown.get(1);
		double minAtivo = maxminDrawDown.get(0);

		if ((double) listaAtivo.get(listaAtivo.size() - 1) > maxAtivo) {
			maxAtivo = (double) listaAtivo.get(listaAtivo.size() - 1);
			minAtivo = (double) listaAtivo.get(listaAtivo.size() - 1);
		} else if ((double) listaAtivo.get(listaAtivo.size() - 1) < minAtivo) {
			minAtivo = (double) listaAtivo.get(listaAtivo.size() - 1);
		}

		maxminDrawDown.set(1, maxAtivo);
		maxminDrawDown.set(0, minAtivo);
		pCalculada = ((maxAtivo - minAtivo) / maxAtivo) * 100;
		if (pCalculada > 20) {
			return true;
		}
		return false;

	}
}