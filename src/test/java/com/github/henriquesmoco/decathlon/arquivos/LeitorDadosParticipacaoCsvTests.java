package com.github.henriquesmoco.decathlon.arquivos;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import com.github.henriquesmoco.decathlon.participacao.Participacao;
import com.github.henriquesmoco.decathlon.participacao.PerformanceCorrida;
import com.github.henriquesmoco.decathlon.participacao.PerformanceLancamento;
import org.testng.annotations.Test;

import com.github.henriquesmoco.decathlon.participacao.PerformanceSalto;


public class LeitorDadosParticipacaoCsvTests {
	private static final String CSV_VAZIO = "";
	private static final String UMA_PARTICIPACAO_COMPLETA = "Usain;1;2;3;4;5;6;7;8;9;10";
	private static final String UMA_PARTICIPACAO_INCOMPLETA = "Usain;1;;3;;;6";
	
	private ILeitorDados<Participacao> criaLeitorDadosCsv(String conteudoCsv) {
		ILeitorDados<String> arquivoTxt = new ArquivoTextoTestavel(conteudoCsv);
		ILeitorDados<Participacao> arquivoParticipacao = new LeitorDadosParticipacaoCsv(arquivoTxt);
		return arquivoParticipacao;
	}
	
	@Test
	public void lerRegistros_DeCsvSemDados_RetornaListaVazia() throws Exception {
		ILeitorDados<Participacao> dadosParticipacao = criaLeitorDadosCsv(CSV_VAZIO);
		
		List<Participacao> participacoes = dadosParticipacao.lerRegistros();
		
		assertTrue(participacoes.isEmpty());
	}
	
	@Test
	public void lerRegistros_DeCsvComUmaParticipacaoCompleta_RetornaTipoCorretoParaCadaPerformance() {
		ILeitorDados<Participacao> dadosParticipacao = criaLeitorDadosCsv(UMA_PARTICIPACAO_COMPLETA);
		
		List<Participacao> participacoes = dadosParticipacao.lerRegistros();
		
		Participacao part = participacoes.get(0);
		assertTrue(part.getPerformances().get(0) instanceof PerformanceCorrida);
		assertTrue(part.getPerformances().get(1) instanceof PerformanceSalto);
		assertTrue(part.getPerformances().get(2) instanceof PerformanceLancamento);
		assertTrue(part.getPerformances().get(3) instanceof PerformanceSalto);
		assertTrue(part.getPerformances().get(4) instanceof PerformanceCorrida);
		assertTrue(part.getPerformances().get(5) instanceof PerformanceCorrida);
		assertTrue(part.getPerformances().get(6) instanceof PerformanceLancamento);
		assertTrue(part.getPerformances().get(7) instanceof PerformanceSalto);
		assertTrue(part.getPerformances().get(8) instanceof PerformanceLancamento);
		assertTrue(part.getPerformances().get(9) instanceof PerformanceCorrida);
	}
	
	@Test
	public void lerRegistros_DeCsvComUmaParticipacaoIncompleta_NaoRetornaPerformancesFaltantes() {
		ILeitorDados<Participacao> dadosParticipacao = criaLeitorDadosCsv(UMA_PARTICIPACAO_INCOMPLETA);
		
		List<Participacao> participacoes = dadosParticipacao.lerRegistros();
		
		Participacao part = participacoes.get(0);
		assertEquals(part.getPerformances().size(), 3);
	}
	
	@Test
	public void lerRegistros_DeCsvComUmaParticipacaoIncompleta_RetornaParticipacaoComPerformancesInformadas() {
		ILeitorDados<Participacao> dadosParticipacao = criaLeitorDadosCsv(UMA_PARTICIPACAO_INCOMPLETA);
		
		List<Participacao> participacoes = dadosParticipacao.lerRegistros();
		
		Participacao part = participacoes.get(0);
		String performance1 = part.getPerformances().get(0).getPerformance();
		String performance2 = part.getPerformances().get(1).getPerformance();
		String performance3 = part.getPerformances().get(2).getPerformance();
		assertEquals(performance1, "1");
		assertEquals(performance2, "3");
		assertEquals(performance3, "6");
	}
	
	@Test
	public void lerRegistros_DeCsvComDuasParticipacoes_RetornaNomeCorretoDosParticipantes() {
		final String DUAS_PARTICIPACOES = "Usain\nBruce";
		ILeitorDados<Participacao> arquivoPerformance = criaLeitorDadosCsv(DUAS_PARTICIPACOES);
		
		List<Participacao> participacoes = arquivoPerformance.lerRegistros();
		
		assertEquals(participacoes.get(0).getNomeParticipante(), "Usain");
		assertEquals(participacoes.get(1).getNomeParticipante(), "Bruce");
	}
	
	
	private class ArquivoTextoTestavel implements ILeitorDados<String> {
		private String conteudoArquivo;

		ArquivoTextoTestavel(String conteudo) {
			conteudoArquivo = conteudo;
		}

		public List<String> lerRegistros() {
			return Arrays.asList(conteudoArquivo.split("\n"));
		}
	}
}
