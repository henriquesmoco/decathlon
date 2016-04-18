package com.github.henriquesmoco.decathlon.arquivos;

import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.github.henriquesmoco.decathlon.classificacao.Classificacao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.henriquesmoco.decathlon.participacao.IPerformance;

public class EscritorDadosClassificacaoXml implements IEscritorDados<List<Classificacao>> {
	private IEscritorDados<String> escritor;
	
	public EscritorDadosClassificacaoXml(IEscritorDados<String> escritor) {
		this.escritor = escritor;
	}
	
	public void escrever(List<Classificacao> dados) {
		XmlBuilder xml = new XmlBuilder().novoXml().comTag("decathlon");
		escreveTagsClassificacao(dados, xml);
		String strXml = xml.criar();
		escritor.escrever(strXml);
	}

	private void escreveTagsClassificacao(List<Classificacao> dados, XmlBuilder xml) {
		for (Classificacao classif : dados) {
			if (classif.getParticipacao() == null)  {
				continue;
			}
			xml.comTag("classificacao")
				.comTagSemMudarNivel("posicao").comTexto(classif.getPosicao())
				.comTagSemMudarNivel("nome").comTexto(classif.getParticipacao().getNomeParticipante())
				.comTagSemMudarNivel("pontuacao").comTexto(String.valueOf(classif.getTotalDePontos()));
			escreveTagsPerformance(classif.getParticipacao().getPerformances(), xml);
		}
	}
	
	private void escreveTagsPerformance(List<IPerformance> performances, XmlBuilder xml) {
		xml.comTag("performances");
		for (IPerformance perf : performances) {
			xml.comTagSemMudarNivel("performance").comTexto(perf.getPerformance());
		}
		xml.voltaUmNivel();
		xml.voltaUmNivel();
	}


	private class XmlBuilder {
		private Document doc;
		private Node elementoPai;
		private Node ultimoElemento;
		
		private XmlBuilder novoXml() {
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

				DocumentBuilder docBuilder;
				docBuilder = factory.newDocumentBuilder();
				this.doc = docBuilder.newDocument();
				return this;
			} catch (ParserConfigurationException e) {
				throw new RuntimeException(e);
			}
		}
		
		public XmlBuilder voltaUmNivel() {
			elementoPai = elementoPai.getParentNode();
			return this;
		}

		public XmlBuilder comTexto(String texto) {
			ultimoElemento.appendChild(doc.createTextNode(texto));
			return this;
		}

		private Element criaTag(String tag) {
			Element elemento = doc.createElement(tag);
			if (elementoPai == null) {
				doc.appendChild(elemento);
			} else {
				elementoPai.appendChild(elemento);
			}
			ultimoElemento = elemento;
			return elemento;
		}
		
		public XmlBuilder comTagSemMudarNivel(String tag) {
			criaTag(tag);
			return this;
		}

		private XmlBuilder comTag(String tag) {
			elementoPai = criaTag(tag);
			return this;
		}
		
		private String criar() {
			try {
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer();
				
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new StringWriter());
				
				transformer.transform(source, result);
				return result.getWriter().toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
}