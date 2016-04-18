package com.github.henriquesmoco.decathlon.arquivos;

import java.util.ArrayList;
import java.util.List;

import com.github.henriquesmoco.decathlon.participacao.PerformanceCorrida;
import org.testng.annotations.Test;

import com.github.henriquesmoco.decathlon.classificacao.Classificacao;
import com.github.henriquesmoco.decathlon.participacao.Participacao;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


public class EscritorDadosClassificacaoXmlTests {
	private static final String XML_VAZIO = "<decathlon/>";
	
	@Test
	public void escreverRegistros_SemDados_GravaXmlSemClassificacoes() throws Exception {
		ArquivoTextoTestavel arquivoTxt = new ArquivoTextoTestavel();
		IEscritorDados<List<Classificacao>> escritor = new EscritorDadosClassificacaoXml(arquivoTxt);
		List<Classificacao> listaVazia = new ArrayList<Classificacao>();
		
		escritor.escrever(listaVazia);

		assertThat(arquivoTxt.getConteudo(), containsString(XML_VAZIO));
	}
	
	@Test
	public void escreverRegistros_DeUmaClassificacaoSemParticipacao_GravaXmlSemClassificacoes() {
		ArquivoTextoTestavel arquivoTxt = new ArquivoTextoTestavel();
		IEscritorDados<List<Classificacao>> escritor = new EscritorDadosClassificacaoXml(arquivoTxt);
		List<Classificacao> lista = new ArrayList<Classificacao>();
		lista.add(new Classificacao(null));
		
		escritor.escrever(lista);
		
		assertThat(arquivoTxt.getConteudo(), containsString(XML_VAZIO));
	}
	
	@Test
	public void escreverRegistros_DeUmaClassificacao_EscreveDadosDaClassificacao() {
		ArquivoTextoTestavel arquivoTxt = new ArquivoTextoTestavel();
		IEscritorDados<List<Classificacao>> escritor = new EscritorDadosClassificacaoXml(arquivoTxt);
		List<Classificacao> lista = new ArrayList<Classificacao>();
		lista.add(new Classificacao("1", 2, new Participacao("Usain")));
		
		escritor.escrever(lista);

		assertThat(arquivoTxt.getConteudo(), containsString("<posicao>1</posicao"));
		assertThat(arquivoTxt.getConteudo(), containsString("<nome>Usain</nome>"));
		assertThat(arquivoTxt.getConteudo(), containsString("<pontuacao>2</pontuacao>"));
	}
	
	@Test
	public void escreverRegistros_DeUmaClassificacaoComDuasPerformances_EscreveTodasAsPerformances() {
		ArquivoTextoTestavel arquivoTxt = new ArquivoTextoTestavel();
		IEscritorDados<List<Classificacao>> escritor = new EscritorDadosClassificacaoXml(arquivoTxt);
		List<Classificacao> lista = new ArrayList<Classificacao>();
		lista.add(new Classificacao("1", 2, new Participacao("Usain")
				.addPerformance(new PerformanceCorrida("1", null))
				.addPerformance(new PerformanceCorrida("2", null))));
		
		escritor.escrever(lista);
		
		assertThat(arquivoTxt.getConteudo(), containsString("<performance>1</performance>"));
		assertThat(arquivoTxt.getConteudo(), containsString("<performance>2</performance>"));
	}
	
	private class ArquivoTextoTestavel implements IEscritorDados<String> {
		private String conteudoArquivo = "";
		
		String getConteudo() {
			return conteudoArquivo;
		}

		public void escrever(String dados) {
			this.conteudoArquivo += dados;
		}
	}
}
