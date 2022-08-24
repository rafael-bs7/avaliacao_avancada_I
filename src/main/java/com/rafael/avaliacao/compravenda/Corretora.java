package com.rafael.avaliacao.compravenda;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import org.jfree.ui.RefineryUtilities;

import com.rafael.avaliacao.AvaliacaoPrimeiraParte.CsvReaderApplication;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import jxl.write.WriteException;

public class Corretora extends Thread {

	private final String CSV_PATH_FILE1 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDCAD_M1.csv";
	private final String CSV_PATH_FILE2 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDCHF_M1.csv";
	private final String CSV_PATH_FILE3 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDHKD_M1.csv";
	private final String CSV_PATH_FILE4 = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\USDJPY_M1.csv";

	private final List<Map<String, String>> fileCSVA = CsvReaderApplication.ReaderFile(CSV_PATH_FILE1);
	private final List<Map<String, String>> fileCSVB = CsvReaderApplication.ReaderFile(CSV_PATH_FILE2);
	private final List<Map<String, String>> fileCSVC = CsvReaderApplication.ReaderFile(CSV_PATH_FILE3);
	private final List<Map<String, String>> fileCSVD = CsvReaderApplication.ReaderFile(CSV_PATH_FILE4);

	public static AtomicInteger operacoes;

	public static ArrayList<Double> ativoA = new ArrayList<>();
	public static ArrayList<Double> ativoB = new ArrayList<>();
	public static ArrayList<Double> ativoC = new ArrayList<>();
	public static ArrayList<Double> ativoD = new ArrayList<>();

	public static Hashtable<Integer, List<Double>> joinListAtivos;

	private static Semaphore semaforo = new Semaphore(2);

	private static ArrayList<Hashtable<String, String>> caixaGeral;

	final DynamicDataDemo demoAtivoA;
	final DynamicDataDemo demoAtivoB;
	final DynamicDataDemo demoAtivoC;
	final DynamicDataDemo demoAtivoD;

	public Corretora() {
			
		operacoes = new AtomicInteger(1000);
		caixaGeral = new ArrayList<Hashtable<String, String>>();
	
		joinListAtivos = new Hashtable<Integer, List<Double>>();

//		Thread t = new Thread(this);
//		t.start();
		demoAtivoA = new DynamicDataDemo("Media Móvel Ativo A", 1.30, 1.31);
		demoAtivoA.pack();
		RefineryUtilities.centerFrameOnScreen(demoAtivoA);
		demoAtivoA.setVisible(true);

		// grafico Ativo B
		demoAtivoB = new DynamicDataDemo("Media Móvel Ativo B", 0.98, 0.99);
		demoAtivoB.pack();
		RefineryUtilities.centerFrameOnScreen(demoAtivoB);
		demoAtivoB.setVisible(true);

		// grafico Ativo C
		demoAtivoC = new DynamicDataDemo("Media Móvel Ativo C", 7.847, 7.852);
		demoAtivoC.pack();
		RefineryUtilities.centerFrameOnScreen(demoAtivoC);
		demoAtivoC.setVisible(true);

		// grafico Ativo D
		demoAtivoD = new DynamicDataDemo("Media Móvel Ativo D", 137, 137.8);
		demoAtivoD.pack();
		RefineryUtilities.centerFrameOnScreen(demoAtivoD);
		demoAtivoD.setVisible(true);
	}

	@Override
	public void run() {
		addAtivos();
	}

	private void addAtivos() {

		try {
			int i = 0;
			while (executaThread()) {

				ativoA.add(Double.parseDouble(fileCSVA.get(i).get("<CLOSE>")));
				ativoB.add(Double.parseDouble(fileCSVB.get(i).get("<CLOSE>")));
				ativoC.add(Double.parseDouble(fileCSVC.get(i).get("<CLOSE>")));
				ativoD.add(Double.parseDouble(fileCSVD.get(i).get("<CLOSE>")));

				joinListAtivos.put(1, ativoA);
				joinListAtivos.put(2, ativoB);
				joinListAtivos.put(3, ativoC);
				joinListAtivos.put(4, ativoD);

				demoAtivoA.incrementValue(ativoA, fileCSVA.get(i).get("<TIME>"));
				demoAtivoB.incrementValue(ativoB, fileCSVA.get(i).get("<TIME>"));
				demoAtivoC.incrementValue(ativoC,fileCSVA.get(i).get("<TIME>"));
				demoAtivoD.incrementValue(ativoD,fileCSVA.get(i).get("<TIME>"));
				
				saveCaixa();
				Thread.sleep(1000);

				i++;

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean executaThread() {
		if (operacoes.get() > 1 && ativoA.size() < 295 && ativoB.size() < 295 && ativoC.size() < 295
				&& ativoD.size() < 295) {
			return true;
		} else {
			return false;
		}

	}

	public boolean compravenda(int idCliente, int abcd, boolean cv) {

		// abcd =1 quando trata-se do ativo A
		// abcd =2 quando trata-se do ativo B
		// abcd =3 quando trata=se do ativo C
		// abcd = 4 quando trata-se do ativo D

		// cv = 0, quando é venda do cliente
		// cv =1 , quando é compra do cliente

		try {

			semaforo.acquire();

			if (operacoes.get() > 1) {
				if (abcd == 1) {
					// ativo A

					if (!cv) {
						// venda ativo A
						int posTime = ativoA.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoA", "venda", fileCSVA.get(posTime).get("<DATE>"),
								fileCSVA.get(posTime).get("<TIME>"), ativoA.get(posTime));
						// qtAtivoA.incrementAndGet();
						operacoes.decrementAndGet();
						return true;

					} else {
						// compra ativo A

						// conferir se tem ativo A disponivel

						// if (qtAtivoA.get() > 0) {

						// executa compra do ativo e adiciona no caixa geral da corretora

						int posTime = ativoA.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoA", "compra", fileCSVA.get(posTime).get("<DATE>"),
								fileCSVA.get(posTime).get("<TIME>"), ativoA.get(posTime));

						// qtAtivoA.decrementAndGet();
						operacoes.decrementAndGet();

						return true;

						// }

					}

				} else if (abcd == 2) {
					// ativo B

					if (!cv) {
						// venda ativo B

						int posTime = ativoB.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoB", "venda", fileCSVB.get(posTime).get("<DATE>"),
								fileCSVB.get(posTime).get("<TIME>"), ativoB.get(posTime));

						// qtAtivoB.incrementAndGet();
						operacoes.decrementAndGet();
						return true;

					} else {
						// compra ativo B

						// conferir se tem ativo B disponivel

						// if (qtAtivoB.get() > 0) {

						// executa compra do ativo e adiciona no caixa geral da corretora

						int posTime = ativoB.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoB", "compra", fileCSVB.get(posTime).get("<DATE>"),
								fileCSVB.get(posTime).get("<TIME>"), ativoB.get(posTime));

						// qtAtivoB.decrementAndGet();
						operacoes.decrementAndGet();

						return true;

						// }

					}

				} else if (abcd == 3) {
					// ativo C

					if (!cv) {
						// venda ativo C

						int posTime = ativoC.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoC", "venda", fileCSVC.get(posTime).get("<DATE>"),
								fileCSVC.get(posTime).get("<TIME>"), ativoC.get(posTime));

						// qtAtivoC.incrementAndGet();
						operacoes.decrementAndGet();
						return true;

					} else {
						// compra ativo C

						// conferir se tem ativo C disponivel

						// if (qtAtivoC.get() > 0) {

						// executa compra do ativo e adiciona no caixa geral da corretora

						int posTime = ativoC.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoC", "compra", fileCSVC.get(posTime).get("<DATE>"),
								fileCSVC.get(posTime).get("<TIME>"), ativoC.get(posTime));

						// qtAtivoC.decrementAndGet();
						operacoes.decrementAndGet();

						return true;

						// }

					}

				} else if (abcd == 4) {
					// ativo D

					if (!cv) {
						// venda ativo D

						int posTime = ativoD.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoD", "venda", fileCSVD.get(posTime).get("<DATE>"),
								fileCSVD.get(posTime).get("<TIME>"), ativoD.get(posTime));

						// qtAtivoD.incrementAndGet();
						operacoes.decrementAndGet();
						return true;

					} else {
						// compra ativo D

						// conferir se tem ativo D disponivel

						// if (qtAtivoD.get() > 0) {

						// executa compra do ativo e adiciona no caixa geral da corretora

						int posTime = ativoD.size() - 1;

						salvandoCaixaGeral(idCliente, "ativoD", "compra", fileCSVD.get(posTime).get("<DATE>"),
								fileCSVD.get(posTime).get("<TIME>"), ativoD.get(posTime));

						// qtAtivoD.decrementAndGet();
						operacoes.decrementAndGet();

						return true;

						// }

					}

				}
				return false;

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			semaforo.release();
		}

		return false;

	}

	// salva no caixa geral da corretora

	private synchronized void salvandoCaixaGeral(int id_cliente, String ativo, String operacao, String date,
			String time, Double valor) {

		//String[] headers = { "id_cliente", "datetime", "ativo", "operacao", "valor" };

		Hashtable<String, String> campos = new Hashtable<String, String>();

		campos.put("id_cliente", String.valueOf(id_cliente));
		campos.put("datetime", date + "/" + time);
		campos.put("ativo", ativo);
		campos.put("operacao", operacao);
		campos.put("valor", String.valueOf(valor));

		caixaGeral.add(campos);

		// chamar algum metodo que grava no excel

	}

//	public ArrayList<Hashtable<String, String>> getCaixaGeral() {
//		return caixaGeral;
//	}

	private void saveCaixa() {
		final String EXCEL_FILE_LOCATION = "D:\\Ufla\\9° Período\\Automação Avançada\\Avaliacao Parte 1\\Dados Excel Caixa geral-Clientes\\caixaGeral.xls";

		// 1. Create an Excel file
		WritableWorkbook myFirstWbook = null;

		try {

			// FILE 1

			myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));

			// create an Excel sheet
			WritableSheet excelSheet = myFirstWbook.createSheet("Caixa_Corretora", 0);

			// add something into the Excel sheet Label(COLUNA, LINHA, VALOR);
			Label label = new Label(0, 0, "id_cliente");
			excelSheet.addCell(label);

			label = new Label(1, 0, "datetime");
			excelSheet.addCell(label);

			label = new Label(2, 0, "ativo");
			excelSheet.addCell(label);

			label = new Label(3, 0, "operacao");
			excelSheet.addCell(label);

			label = new Label(4, 0, "valor");
			excelSheet.addCell(label);

			for (int i = 0; i < caixaGeral.size(); i++) {

				label = new Label(0, i + 1, caixaGeral.get(i).get("id_cliente"));
				excelSheet.addCell(label);

				label = new Label(1, i + 1, caixaGeral.get(i).get("datetime"));
				excelSheet.addCell(label);

				label = new Label(2, i + 1, caixaGeral.get(i).get("ativo"));
				excelSheet.addCell(label);

				label = new Label(3, i + 1, caixaGeral.get(i).get("operacao"));
				excelSheet.addCell(label);

				label = new Label(4, i + 1, caixaGeral.get(i).get("valor"));
				excelSheet.addCell(label);

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