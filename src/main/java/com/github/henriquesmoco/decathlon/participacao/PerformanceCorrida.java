package com.github.henriquesmoco.decathlon.participacao;
import com.github.henriquesmoco.decathlon.Evento;


public class PerformanceCorrida implements IPerformance {
	private Evento evento;
	private String performance;
	private double valor;

	public PerformanceCorrida(String performance, Evento evt) {
		this.performance = performance;
		this.evento = evt;
		this.valor = getTempoEmSegundos(performance);
	}
	
	private double getTempoEmSegundos(String performance) {
		String tempo = corrigeFormatoDePerformancePorTempo(performance);
		String[] valores = tempo.split(":");
		int minutos = Integer.parseInt(valores[0]);
		double segundos = Double.parseDouble(valores[1]);
		return (minutos * 60) + segundos;
	}
	
	private String corrigeFormatoDePerformancePorTempo(String performance) {
		StringBuilder performanceCorrigida = new StringBuilder(performance);
		boolean temMinutoSeparadoPorPonto = performance.matches("\\d+\\.\\d+\\.\\d+");
		if (temMinutoSeparadoPorPonto) {
			int posicaoPontoDeMinuto = performanceCorrigida.indexOf(".");
			performanceCorrigida.replace(posicaoPontoDeMinuto, posicaoPontoDeMinuto + 1, ":");
		}
		if (performanceCorrigida.indexOf(":") == -1) {
			performanceCorrigida.insert(0, "0:");
		}
		return performanceCorrigida.toString();
	}
	
	public Evento getEvento() {
		return evento;
	}

	public String getPerformance() {
		return performance;
	}

	public double getValorParaCalculo() {
		return valor;
	}		
}
