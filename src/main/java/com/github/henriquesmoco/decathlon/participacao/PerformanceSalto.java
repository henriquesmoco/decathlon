package com.github.henriquesmoco.decathlon.participacao;
import com.github.henriquesmoco.decathlon.Evento;

public class PerformanceSalto implements IPerformance {
	private Evento evento;
	private String performance;
	private double valor;

	public PerformanceSalto(String performance, Evento evt) {
		this.performance = performance;
		this.evento = evt;
		this.valor = getCentimetros(performance);
	}
	
	private double getCentimetros(String performance) {
		return Double.valueOf(performance) * 100;
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