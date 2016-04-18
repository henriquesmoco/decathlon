package com.github.henriquesmoco.decathlon.participacao;
import com.github.henriquesmoco.decathlon.Evento;


public interface IPerformance {
	String getPerformance();
	Evento getEvento();
	double getValorParaCalculo();
}