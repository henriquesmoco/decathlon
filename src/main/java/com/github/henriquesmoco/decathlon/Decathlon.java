package com.github.henriquesmoco.decathlon;

import java.util.List;

import com.github.henriquesmoco.decathlon.arquivos.Arquivo;
import com.github.henriquesmoco.decathlon.arquivos.EscritorDadosClassificacaoXml;
import com.github.henriquesmoco.decathlon.arquivos.IEscritorDados;
import com.github.henriquesmoco.decathlon.arquivos.ILeitorDados;
import com.github.henriquesmoco.decathlon.arquivos.LeitorDadosParticipacaoCsv;
import com.github.henriquesmoco.decathlon.classificacao.Classificacao;
import com.github.henriquesmoco.decathlon.classificacao.PainelDeClassificacao;
import com.github.henriquesmoco.decathlon.participacao.Participacao;

public class Decathlon {
	
	/**
	 * Converte arquivo CSV de performances em XML de classificacao
	 * @param origem Nome e caminho do arquivo CSV
	 * @param destino Nome e caminho do arquivo XML
	 */
	public void converterArquivo(String origem, String destino) {
		List<Participacao> participacoes = importarCsv(origem);
		List<Classificacao> classificacoes = getClassificacaoDosParticipantes(participacoes);
		exportarXml(destino, classificacoes);
	}

	private List<Participacao> importarCsv(String nomeArquivoCsv) {
		ILeitorDados<Participacao> leitor = new LeitorDadosParticipacaoCsv(new Arquivo(nomeArquivoCsv));
		List<Participacao> participacoes = leitor.lerRegistros();
		return participacoes;
	}

	private List<Classificacao> getClassificacaoDosParticipantes(List<Participacao> participacoes) {
		PainelDeClassificacao painel = new PainelDeClassificacao();
		painel.addAll(participacoes);
		List<Classificacao> classificacao = painel.getClassificacao();
		return classificacao;
	}
	
	private void exportarXml(String nomeArquivoXml, List<Classificacao> classificacao) {
		IEscritorDados<List<Classificacao>> escritor = new EscritorDadosClassificacaoXml(new Arquivo(nomeArquivoXml));
		escritor.escrever(classificacao);
	}
}
