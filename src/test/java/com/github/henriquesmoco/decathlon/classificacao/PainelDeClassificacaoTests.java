package com.github.henriquesmoco.decathlon.classificacao;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.henriquesmoco.decathlon.participacao.Participacao;
import com.github.henriquesmoco.decathlon.participacao.PerformanceCorrida;
import org.testng.annotations.Test;

import com.github.henriquesmoco.decathlon.participacao.IPerformance;

public class PainelDeClassificacaoTests {
  	
	@Test
	public void obterClassificacao_SemParticipantes_RetornaListaVazia() {
		PainelDeClassificacao painel = new PainelDeClassificacaoBuilder().criar();
		
		List<Classificacao> lst = painel.getClassificacao();
		
		assertTrue(lst.isEmpty());		
	}
	
	@Test
	public void obterClassificacao_DeUmParticipante_RetornaEleEmPrimeiraPosicao() {
		Participacao usain = criaParticipante("Usain");
		PainelDeClassificacao painel = new PainelDeClassificacaoBuilder()
			.comParticipante(usain).criar();
		
		List<Classificacao> lst = painel.getClassificacao();
		
		String posicaoPrimeiroColocado = lst.get(0).getPosicao();		
		assertEquals(posicaoPrimeiroColocado, "1");	
	}
	
	@Test
	public void obterClassificacao_DeParticipantesComPontuacaoDiferente_RetornaParticipantesPorOrdemDePontuacaoDecrescente() {
		Participacao usain = criaParticipante("Usain");
		Participacao mike = criaParticipante("Mike");
		Participacao bruce = criaParticipante("Bruce");
		PainelDeClassificacao painel = new PainelDeClassificacaoBuilder()
			.comParticipante(usain).comPontuacao(1)
			.comParticipante(mike).comPontuacao(10)
			.comParticipante(bruce).comPontuacao(2)
			.criar();
		
		List<Classificacao> lst = painel.getClassificacao();
		
		assertEquals(lst.get(0).getParticipacao(), mike);
		assertEquals(lst.get(1).getParticipacao(), bruce);
		assertEquals(lst.get(2).getParticipacao(), usain);
	}
	
	@Test
	public void obterClassificacao_DeParticipantesComPontuacaoDiferente_RetornaPosicoesEmOrdemCrescente() {
		Participacao usain = criaParticipante("Usain");
		Participacao mike = criaParticipante("Mike");
		Participacao bruce = criaParticipante("Bruce");
		PainelDeClassificacao painel = new PainelDeClassificacaoBuilder()
			.comParticipante(usain).comPontuacao(1)
			.comParticipante(mike).comPontuacao(10)
			.comParticipante(bruce).comPontuacao(2)
			.criar();
		
		List<Classificacao> lst = painel.getClassificacao();
		
		assertEquals(lst.get(0).getPosicao(), "1");
		assertEquals(lst.get(1).getPosicao(), "2");
		assertEquals(lst.get(2).getPosicao(), "3");
	}
	
	@Test
	public void obterClassificacao_DeDoisParticipantesComPontuacaoIgual_RetornaPosicaoCompartilhadaEntreEles() {
		Participacao usain = criaParticipante("Usain");
		Participacao mike = criaParticipante("Mike");
		PainelDeClassificacao painel = new PainelDeClassificacaoBuilder()
			.comParticipante(usain, mike)
			.comPontuacao(1).criar();
		
		List<Classificacao> lst = painel.getClassificacao();
		
		assertEquals(lst.get(0).getPosicao(), "1-2");
		assertEquals(lst.get(1).getPosicao(), "1-2");
	}
	
	@Test
	public void obterClassificacao_DeTresParticipantesComPontuacaoIgualEUmDiferente_RetornaPosicaoCompratilhadaEntreOsIguais() {
		Participacao usain = criaParticipante("Usain");
		Participacao mike = criaParticipante("Mike");
		Participacao bruce = criaParticipante("Bruce");
		Participacao martin = criaParticipante("Martin");
		PainelDeClassificacao painel = new PainelDeClassificacaoBuilder()
			.comParticipante(usain).comPontuacao(2)
			.comParticipante(mike, bruce, martin).comPontuacao(1)
			.criar();
		
		List<Classificacao> lst = painel.getClassificacao();
		
		assertEquals(lst.get(0).getPosicao(), "1");
		assertEquals(lst.get(1).getPosicao(), "2-3-4");
		assertEquals(lst.get(2).getPosicao(), "2-3-4");
		assertEquals(lst.get(3).getPosicao(), "2-3-4");
	}
	
	@Test
	public void obterClassificacao_DeDoisParticipantesComPontuacaoIgual_NaoInterfereNaNumeracaoDoRestoDaLista() {
		Participacao usain = criaParticipante("Usain");
		Participacao mike = criaParticipante("Mike");
		Participacao bruce = criaParticipante("Bruce");
		PainelDeClassificacao painel = new PainelDeClassificacaoBuilder()
			.comParticipante(bruce).comPontuacao(1)
			.comParticipante(usain, mike).comPontuacao(2)
			.criar();
		
		List<Classificacao> lst = painel.getClassificacao();
		
		assertEquals(lst.get(0).getPosicao(), "1-2");
		assertEquals(lst.get(1).getPosicao(), "1-2");
		assertEquals(lst.get(2).getPosicao(), "3");	
	}
	
	private Participacao criaParticipante(String nome) {
		return new Participacao(nome).addPerformance(new PerformanceCorrida("0", null));
	}
	
	private class PainelDeClassificacaoBuilder {
		private List<Participacao> participacoes = new ArrayList<Participacao>();
		private List<Integer> pontuacoes = new ArrayList<Integer>();
		PainelDeClassificacaoBuilder comParticipante(Participacao... part) {
			participacoes.addAll(Arrays.asList(part));
			return this;
		}
		
		/**
		 * Devido a implementacao com Mocks, se tiver menos pontos que participantes, 
		 * o ultimo ponto recebido sera utilizado para os participates extras
		 * @param pontos
		 * @return
		 */
		PainelDeClassificacaoBuilder comPontuacao(Integer... pontos) {
			pontuacoes.addAll(Arrays.asList(pontos));
			return this;
		}
		PainelDeClassificacao criar() {
			ICalculadorPontuacao calculador = criaCalculadorComPontuacao(pontuacoes);
			PainelDeClassificacao painel = new PainelDeClassificacao(calculador);
			for (Participacao part : participacoes) {
				painel.add(part);		
			}
			return painel;
		}
		private ICalculadorPontuacao criaCalculadorComPontuacao(List<Integer> pontos) {
			ICalculadorPontuacao calculador = mock(ICalculadorPontuacao.class);
			if (pontos.size() == 1) {
				when(calculador.getPontuacao(any(IPerformance.class))).thenReturn(pontos.get(0));
			}
			if (pontos.size() > 1) {
				Integer primeiroPonto = pontos.get(0);
				Integer[] demaisPontos = pontos.subList(1, pontos.size()).toArray(new Integer[0]);
				when(calculador.getPontuacao(any(IPerformance.class))).thenReturn(primeiroPonto, demaisPontos);
			}
			return calculador;
		}
	}
}
