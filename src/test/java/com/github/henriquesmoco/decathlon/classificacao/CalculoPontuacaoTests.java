package com.github.henriquesmoco.decathlon.classificacao;
import static org.testng.Assert.assertEquals;

import com.github.henriquesmoco.decathlon.participacao.PerformanceCorrida;
import com.github.henriquesmoco.decathlon.participacao.PerformanceLancamento;
import com.github.henriquesmoco.decathlon.participacao.PerformanceSalto;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.henriquesmoco.decathlon.Evento;
import com.github.henriquesmoco.decathlon.participacao.IPerformance;


public class CalculoPontuacaoTests {

	@DataProvider(name="DadosParaEventosDeCorrida")
	private Object[][] dadosParaEventoDeCorrida() {
		return new Object[][] {
			{ Evento.CORRIDA_100_METROS, "10.395", "1000"},
			{ Evento.CORRIDA_100_METROS, "11.756", "700"},
			{ Evento.CORRIDA_400_METROS, "46.17", "1000"},
			{ Evento.CORRIDA_400_METROS, "52.58", "700"},
			{ Evento.CORRIDA_110_METROS_COM_BARREIRAS, "13.8", "1000"},
			{ Evento.CORRIDA_110_METROS_COM_BARREIRAS, "16.29", "700"},
			{ Evento.CORRIDA_1500_METROS, "3.53.79", "1000"},
			{ Evento.CORRIDA_1500_METROS, "4.36.96", "700"}
		};		
	}
	
	@DataProvider(name="DadosParaEventosDeLancamento")
	private Object[][] dadosParaEventoDeLancamento() {
		return new Object[][] {
			{ Evento.ARREMESSO, "18.4", "1000"},
			{ Evento.ARREMESSO, "13.53", "700"},
			{ Evento.LANCAMENTO_DISCO, "56.17", "1000"},
			{ Evento.LANCAMENTO_DISCO, "41.72", "700"},
			{ Evento.LANCAMENTO_DARDO, "77.19", "1000"},
			{ Evento.LANCAMENTO_DARDO, "57.45", "700"},
		};		
	}

	@DataProvider(name="DadosParaEventosDeSalto")
	private Object[][] dadosParaEventoDeSalto() {
		return new Object[][] {
			{ Evento.SALTO_A_DISTANCIA, "7.76", "1000"},
			{ Evento.SALTO_A_DISTANCIA, "6.51", "700"},
			{ Evento.SALTO_EM_ALTURA, "2.208", "1000"},
			{ Evento.SALTO_EM_ALTURA, "1.884", "700"},
			{ Evento.SALTO_COM_VARA, "5.287", "1000"},
			{ Evento.SALTO_COM_VARA, "4.293", "700"},
		};		
	}
	
	@Test(dataProvider="DadosParaEventosDeCorrida")
	public void testCalculoPontuacaoParaEventosDeCorrida(Evento evento, String performance, String pontuacaoEsperada) {
		IPerformance performanceEvento = new PerformanceCorrida(performance, evento);
		CalculadorPontuacaoPerformance calculador = new CalculadorPontuacaoPerformance();
		
		int pontuacaoAtual = calculador.getPontuacao(performanceEvento);
		
		assertEquals(String.valueOf(pontuacaoAtual), pontuacaoEsperada);
	}

	@Test(dataProvider="DadosParaEventosDeLancamento")
	public void testCalculoPontuacaoParaEventosDeLancamento(Evento evento, String performance, String pontuacaoEsperada) {
		IPerformance performanceEvento = new PerformanceLancamento(performance, evento);
		CalculadorPontuacaoPerformance calculador = new CalculadorPontuacaoPerformance();
		
		int pontuacaoAtual = calculador.getPontuacao(performanceEvento);
		
		assertEquals(String.valueOf(pontuacaoAtual), pontuacaoEsperada);
	}
	
	@Test(dataProvider="DadosParaEventosDeSalto")
	public void testCalculoPontuacaoParaEventosDeSalto(Evento evento, String performance, String pontuacaoEsperada) {
		IPerformance performanceEvento = new PerformanceSalto(performance, evento);
		CalculadorPontuacaoPerformance calculador = new CalculadorPontuacaoPerformance();
		
		int pontuacaoAtual = calculador.getPontuacao(performanceEvento);
		
		assertEquals(String.valueOf(pontuacaoAtual), pontuacaoEsperada);
	}
}
