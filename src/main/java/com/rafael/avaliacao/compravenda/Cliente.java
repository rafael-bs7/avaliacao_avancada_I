package com.rafael.avaliacao.compravenda;

import java.util.ArrayList;
import java.util.Hashtable;



public class Cliente extends Thread {

	private Hashtable<String, String> transacao;

	private double saldoInit;
	private double saldoCorrente;
	private int indiceCliente;

	private int lastOperacao; // 1: ultima compra ou venda do ativo A
								// 2: ultima compra ou venda do ativo B
								// 3: ultima compra ou venda do ativo C
								// 4: ultima compra ou venda do ativo D

	private ArrayList<Integer> qtAtivos; // posicao 0: Ativo A
											// posicao 1: Ativo B
											// posicao 2: Ativo C
											// posicao 3: Ativo D

	// private Analisador analisador;

	private int tendenciaA; // -1: opera como vendido drawdown; 0: vende; 1: compra
	private int tendenciaB; // -1: nao compra nem vende; 0: vende; 1: compra
	private int tendenciaC; // -1: nao compra nem vende; 0: vende; 1: compra
	private int tendenciaD; // -1: nao compra nem vende; 0: vende; 1: compra

//	// verifica drawDown para cada ativo
//	private boolean drawdownA;
//	private boolean drawdownB;
//	private boolean drawdownC;
//	private boolean drawdownD;

	// parte de analise
	private Analisa analisaA;
	private Analisa analisaB;
	private Analisa analisaC;
	private Analisa analisaD;

	private Corretora corretora;

	// private Random radomQtd;

	private int qtOperacoes;

	//Thread t;
	private int priority;

	public Cliente(int i, Corretora corretora, double saldo) {
		this.indiceCliente = i;
		this.corretora = corretora;
		this.saldoInit = saldo;
		this.saldoCorrente = saldo;

		this.tendenciaA = -1;
		this.tendenciaB = -1;
		this.tendenciaC = -1;
		this.tendenciaD = -1;

		qtAtivos = new ArrayList<>();
		qtAtivos.add(0, 0); // qt ativo A
		qtAtivos.add(1, 0); // qt ativo B
		qtAtivos.add(2, 0); // qt ativo C
		qtAtivos.add(3, 0); // qt ativo D

		// Analisa
		analisaA = new Analisa(Corretora.ativoA);
		analisaB = new Analisa(Corretora.ativoB);
		analisaC = new Analisa(Corretora.ativoC);
		analisaD = new Analisa(Corretora.ativoD);

		// radomQtd = new Random();

		transacao = new Hashtable<>();

		transacao.put("saldoInicial", String.valueOf(saldoInit));
		transacao.put("saldoCorrente", String.valueOf(saldoInit));
		transacao.put("qtAtivoA", String.valueOf(qtAtivos.get(0)));
		transacao.put("qtAtivoB", String.valueOf(qtAtivos.get(1)));
		transacao.put("qtAtivoC", String.valueOf(qtAtivos.get(2)));
		transacao.put("qtAtivoD", String.valueOf(qtAtivos.get(3)));

		qtOperacoes = 0;

		priority = 5;
		//t = new Thread(this);
		this.setPriority(priority);
		//t.start();
	}

	@Override
	public void run() {
		while (Corretora.executaThread()) {

			try {
				analise();
				addTransacao();

				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void addTransacao() {
		transacao.put("saldoCorrente", String.valueOf(saldoCorrente));
		transacao.put("qtAtivoA", String.valueOf(qtAtivos.get(0)));
		transacao.put("qtAtivoB", String.valueOf(qtAtivos.get(1)));
		transacao.put("qtAtivoC", String.valueOf(qtAtivos.get(2)));
		transacao.put("qtAtivoD", String.valueOf(qtAtivos.get(3)));

	}

	// idAtivo = 1 para o ativo A
	// idAtivo = 2 para o ativo B
	// idAtivo = 3 para o ativo C
	// idAtivo = 4 para o arivo D

	@SuppressWarnings("static-access")
	private void comprado(int idAtivo) {
		if (lastOperacao != idAtivo) {

			if (saldoCorrente > corretora.joinListAtivos.get(idAtivo)
					.get(corretora.joinListAtivos.get(idAtivo).size() - 1)) {

				boolean retorno = corretora.compravenda(indiceCliente, idAtivo, true);
				if (retorno) {
					saldoCorrente = saldoCorrente - corretora.joinListAtivos.get(idAtivo)
							.get(corretora.joinListAtivos.get(idAtivo).size() - 1);
					System.out.println("Cliente " + indiceCliente + " comprou 1 ativo com id =" + idAtivo
							+ " e possui um saldo atual de " + saldoCorrente);
					qtAtivos.set(idAtivo - 1, qtAtivos.get(idAtivo - 1) + 1);
					qtOperacoes++;

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lastOperacao = idAtivo;
				}

			} else {

				System.out.println("Cliente " + indiceCliente + " nao tem saldo suficiente para realizar a operacao");
			}

		} else {
			System.out.println("Operacao anterior foi também do ativo " + idAtivo + " - Cliente " + indiceCliente);
		}
		return;

	}

	// idAtivo = 1 para o ativo A
	// idAtivo = 2 para o ativo B
	// idAtivo = 3 para o ativo C
	// idAtivo = 4 para o arivo D
	@SuppressWarnings("static-access")
	private void vendido(int idAtivo, int p) {

		if (lastOperacao != idAtivo) {

			if (qtAtivos.get(idAtivo - 1) > 0) { // verifica se tem o ativo. Se tiver, vende tudo
				priority = p;
				this.setPriority(priority);
				boolean retorno = corretora.compravenda(indiceCliente, idAtivo, false);
				if (retorno) {
					saldoCorrente = saldoCorrente + (corretora.joinListAtivos.get(idAtivo)
							.get(corretora.joinListAtivos.get(idAtivo).size() - 1)) * qtAtivos.get(idAtivo - 1);
					System.out.println("Cliente " + indiceCliente + " vendeu todos os ativos " + idAtivo
							+ " e tem saldo resultante de " + saldoCorrente);
					priority = 5;
					this.setPriority(priority);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lastOperacao = idAtivo;
					qtOperacoes++;
				} else {
					System.out.println("Operacao nao realizada! - Cliente "+ indiceCliente);
				}
			} else {
				System.out.println("Cliente " + indiceCliente + " nao tem ativo " + idAtivo + " para vender");

			}

		} else {
			System.out.println("Operacao anterior foi também do ativo " + idAtivo + " - Cliente "+ indiceCliente);
		}
		return;

	}

	private void analise() {
		// -1: vendido drowdawn
		// 0: vendido
		// 1: comprado
		tendenciaA = analisaA.analisa();
		tendenciaB = analisaB.analisa();
		tendenciaC = analisaC.analisa();
		tendenciaD = analisaD.analisa();

		if (tendenciaA == 1) {// opera como comprado

			System.out.println("Tendencia de alta para o ativo A- Cliente " + indiceCliente);

			comprado(1);

		} else if (tendenciaA == 0) { // opera como vendido

			System.out.println("Tendencia de baixa para o ativo A - Cliente " + indiceCliente );
			vendido(1, 5);

		} else if (tendenciaA == -1) {// opera como vendido com prioridade
			System.out.println("Tendencia de baixa para o ativo A e Drawdown maior do que 20% - Cliente " + indiceCliente);
			vendido(1, 10);
		} else {

			if (tendenciaB == 1) {// opera como comprado

				System.out.println("Tendencia de alta para o ativo B - Cliente " + indiceCliente);

				comprado(2);

			} else if (tendenciaB == 0) { // opera como vendido

				System.out.println("Tendencia de baixa para o ativo B - Cliente " + indiceCliente);
				vendido(2, 5);

			} else if (tendenciaB == -1) { // opera como vendido com prioridade
				System.out.println("Tendencia de baixa para o ativo B e Drawdown maior do que 20% - Cliente " + indiceCliente);
				vendido(2, 10);
			} else {

				if (tendenciaC == 1) { // opera como comprado

					System.out.println("Tendencia de alta para o ativo C - Cliente " + indiceCliente);

					comprado(3);

				} else if (tendenciaC == 0) { // opera como vendido

					System.out.println("Tendencia de baixa para o ativo C - Cliente " + indiceCliente);
					vendido(3, 5);

				} else if (tendenciaC == -1) { // opera como vendido com prioridade
					System.out.println("Tendencia de baixa para o ativo C e Drawdown maior do que 20% - Cliente "+ indiceCliente);
					vendido(3, 10);
				} else {

					if (tendenciaD == 1) { // opera como comprado

						System.out.println("Tendencia de alta para o ativo D - Cliente " + indiceCliente);

						comprado(4);

					} else if (tendenciaD == 0) { // opera como vendido

						System.out.println("Tendencia de baixa para o ativo D - Cliente " + indiceCliente);
						vendido(4, 5);

					} else if (tendenciaD == -1) { // opera como vendido com prioridade
						System.out.println("Tendencia de baixa para o ativo D e Drawdown maior do que 20% - Cliente " + indiceCliente );
						vendido(4, 10);
					}

				}

			}

		}

	}

	public Hashtable<String, String> getTransacaoCliente() {
		return transacao;
	}
	
}
