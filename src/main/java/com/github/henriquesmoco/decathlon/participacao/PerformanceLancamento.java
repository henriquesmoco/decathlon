package com.github.henriquesmoco.decathlon.participacao;
import com.github.henriquesmoco.decathlon.Evento;


public class PerformanceLancamento implements IPerformance {
	private Evento evento;
	private String performance;
	private double valor;

	public PerformanceLancamento(String performance, Evento evt) {
		this.performance = performance;
		this.evento = evt;
		this.valor = Double.valueOf(performance);
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