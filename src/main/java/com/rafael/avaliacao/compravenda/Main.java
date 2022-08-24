package com.rafael.avaliacao.compravenda;

import java.util.ArrayList;

public class Main {
	static ArrayList<Cliente> arrayClientes;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
				
		
		Corretora corretora = new Corretora();
		corretora.start();
		
		
		Cliente c1 = new Cliente(1, corretora, 400);
		c1.start();
		Cliente c2 = new Cliente(2, corretora, 400);
		c2.start();
		Cliente c3 = new Cliente(3, corretora, 400);
		c3.start();
		Cliente c4 = new Cliente(4, corretora, 400);
		c4.start();
		Cliente c5 = new Cliente(5, corretora, 400);
		c5.start();
		Cliente c6 = new Cliente(6, corretora, 400);
		c6.start();
		Cliente c7 = new Cliente(7, corretora, 400);
		c7.start();
		Cliente c8 = new Cliente(8, corretora, 400);
		c8.start();
		Cliente c9 = new Cliente(9, corretora, 400);
		c9.start();
		Cliente c10 = new Cliente(10, corretora, 400);
		c10.start();

		
//		SaveDataGeral.saveData(corretora.getCaixaGeral(), c1.getTransacaoCliente(),
//				c2.getTransacaoCliente(), c3.getTransacaoCliente(), c4.getTransacaoCliente(),
//				c5.getTransacaoCliente(), c6.getTransacaoCliente(), c7.getTransacaoCliente(),
//				c8.getTransacaoCliente(), c9.getTransacaoCliente(), c10.getTransacaoCliente());
//	
	}

}
