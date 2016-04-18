package com.github.henriquesmoco.decathlon.participacao;

import java.util.ArrayList;
import java.util.List;


public class Participacao {
	private String nomeParticipante;
	private List<IPerformance> performances = new ArrayList<IPerformance>();

	public Participacao(String nomeParticipante) {
		this.nomeParticipante = nomeParticipante;
	}
	public String getNomeParticipante() {
		return nomeParticipante;
	}
	public Participacao addPerformance(IPerformance perf) {
		performances.add(perf);
		return this;
	}
	public List<IPerformance> getPerformances() {
		return java.util.Collections.unmodifiableList(performances);
	}
	
	@Override
	public String toString() {
		return String.format("Participante %s", nomeParticipante);
	}
}