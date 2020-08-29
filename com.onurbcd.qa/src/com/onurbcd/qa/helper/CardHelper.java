package com.onurbcd.qa.helper;

import java.util.List;

import javax.annotation.Nonnull;

import com.onurbcd.qa.util.ReportMethod;

public class CardHelper {

	private CardHelper() {
	}
	
	/**
	 * 	- Criar uma classe Card com as seguintes propriedades:
	 *		. Nome da classe (simples)
	 *		. Numero do card
	 *		. Total de horas do card
	 *		. Lista de ReporMethod's
	 *
	 *	- Adicionar um propriedade: processado, ao ReportMethod, que sempre começa como false
	 *
	 *	- PSEUDO CÓDIGO:
	 *	  	. contar o nº total de horas da classe
	 *	  	. descobrir o numero total de cards por classe
	 *	  	. criar lista de cards
	 *	  	. while: enquanto existirem métodos não processados
	 *			- buscar o método com maior tempo 
	 *			- descobrir de quantos cards o metodo vai precisar
	 *			- encaixar no primeiro card com tempo disponivel, ou cards
	 *			- setar o report method como processado
	 *		. Retornar a lista de cards
	 */
	public static void organize(@Nonnull List<ReportMethod> reportMethods, double maxHours) {
		int i = 0;
		i++;
	}
}
