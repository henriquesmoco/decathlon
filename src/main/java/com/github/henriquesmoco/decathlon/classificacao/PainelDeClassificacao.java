package com.github.henriquesmoco.decathlon.classificacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.henriquesmoco.decathlon.participacao.IPerformance;
import com.github.henriquesmoco.decathlon.participacao.Participacao;

public class PainelDeClassificacao {
	private List<Participacao> participacoes = new ArrayList<Participacao>();
	private ICalculadorPontuacao calculadorPontuacao;
	
	public PainelDeClassificacao() {
		calculadorPontuacao = new CalculadorPontuacaoPerformance();
	}
	public PainelDeClassificacao(ICalculadorPontuacao calculador) {
		calculadorPontuacao = calculador;
	}
	public void add(Participacao participacao) {
		participacoes.add(participacao);
	}
	public void addAll(List<Participacao> lstParticipacao) {
		participacoes.addAll(lstParticipacao);
	}
	
	public List<Classificacao> getClassificacao() {
		List<Classificacao> listaClassificacao = transformaEmListaDeClassificacao(participacoes);
		atualizaPontuacaoIndividual(listaClassificacao);
		ordenaPorPontuacaoDecrescente(listaClassificacao);
		atualizaPosicaoPelaOrdemDaLista(listaClassificacao);
		return listaClassificacao;
	}

	private List<Classificacao> transformaEmListaDeClassificacao(List<Participacao> lst) {
		List<Classificacao> listaClassificacao = new ArrayList<Classificacao>();
		for (Participacao part : lst) {
			listaClassificacao.add(new Classificacao(part));
		}
		return listaClassificacao;
	}
	
	private void atualizaPontuacaoIndividual(List<Classificacao> listaClassificacao) {
		for (Classificacao classificacao : listaClassificacao) {
			int pontos = calculaPontuacaoTotal(classificacao.getParticipacao());
			classificacao.setTotalDePontos(pontos);
		}
	}
	
	private int calculaPontuacaoTotal(Participacao part) {
		int pontos = 0;
		for (IPerformance perf : part.getPerformances()) {
			pontos += calculadorPontuacao.getPontuacao(perf);
		}
		return pontos;
	}		

	private void ordenaPorPontuacaoDecrescente(List<Classificacao> listaClassificacao) {
		Collections.sort(listaClassificacao, new Comparator<Classificacao>() {
			public int compare(Classificacao item1, Classificacao item2) {
				return Integer.valueOf(item2.getTotalDePontos()).compareTo(item1.getTotalDePontos());
			}
		});
	}
	
	private void atualizaPosicaoPelaOrdemDaLista(List<Classificacao> listaClassificacao) {
		for (int i = 0; i < listaClassificacao.size(); i++) {
			Classificacao classificacao = listaClassificacao.get(i);
			String posicaoAtual = String.valueOf(i + 1);
			int pontuacaoAtual = classificacao.getTotalDePontos();
			atualizaPosicaoDasPontuacoesIguais(posicaoAtual, pontuacaoAtual, listaClassificacao);
		}
	}
	
	private void atualizaPosicaoDasPontuacoesIguais(String posicao, int pontuacao, List<Classificacao> lista) {
		for (Classificacao classificacao : lista) {
			if (classificacao.getTotalDePontos() == pontuacao) {
				String novaPosicao = concatenaPosicao(classificacao.getPosicao(), posicao);
				classificacao.setPosicao(novaPosicao);
			}
		}			
	}
	
	private String concatenaPosicao(String posicaoAtual, String novaPosicao) {
		if (posicaoAtual != null) {
			return String.format("%s-%s", posicaoAtual, novaPosicao);
		}
		return novaPosicao;
	}
}
