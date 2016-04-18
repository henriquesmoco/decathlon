package com.github.henriquesmoco.decathlon.classificacao;

import com.github.henriquesmoco.decathlon.participacao.IPerformance;


public interface ICalculadorPontuacao {
	int getPontuacao(IPerformance performance);
}