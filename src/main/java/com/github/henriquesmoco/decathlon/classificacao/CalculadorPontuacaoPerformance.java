package com.github.henriquesmoco.decathlon.classificacao;
import java.util.HashMap;
import java.util.Map;

import com.github.henriquesmoco.decathlon.Evento;
import com.github.henriquesmoco.decathlon.participacao.IPerformance;

public class CalculadorPontuacaoPerformance implements ICalculadorPontuacao {
	private Map<Evento, ParametroCalculo> parametros = new HashMap<Evento, ParametroCalculo>();
	private class ParametroCalculo {
		private double a;
		private double b;
		private double c;

		public ParametroCalculo(double a, double b, double c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public double getA() {
			return a;
		}

		public double getB() {
			return b;
		}

		public double getC() {
			return c;
		}
	}
	
	public CalculadorPontuacaoPerformance() {
		parametros.put(Evento.CORRIDA_100_METROS, new ParametroCalculo(25.4347, 18.0, 1.81));
		parametros.put(Evento.CORRIDA_400_METROS, new ParametroCalculo(1.53775, 82.0, 1.81));
		parametros.put(Evento.CORRIDA_1500_METROS, new ParametroCalculo(0.03768, 480, 1.85));
		parametros.put(Evento.CORRIDA_110_METROS_COM_BARREIRAS, new ParametroCalculo(5.74352, 28.5, 1.92));
		
		parametros.put(Evento.ARREMESSO, new ParametroCalculo(51.39, 1.5, 1.05));
		parametros.put(Evento.LANCAMENTO_DISCO, new ParametroCalculo(12.91, 4, 1.1));
		parametros.put(Evento.LANCAMENTO_DARDO, new ParametroCalculo(10.14, 7, 1.08));
		
		parametros.put(Evento.SALTO_A_DISTANCIA, new ParametroCalculo(0.14354, 220, 1.4));
		parametros.put(Evento.SALTO_EM_ALTURA, new ParametroCalculo(0.8465, 75, 1.42));
		parametros.put(Evento.SALTO_COM_VARA, new ParametroCalculo(0.2797, 100, 1.35));
	}
	
	public int getPontuacao(IPerformance performance) {
		int pontuacao = 0;
		switch (performance.getEvento()) {
		case CORRIDA_100_METROS:
		case CORRIDA_110_METROS_COM_BARREIRAS:
		case CORRIDA_1500_METROS:
		case CORRIDA_400_METROS:
			pontuacao = calculaPontuacaoCorrida(performance);
			break;
		case ARREMESSO:
		case LANCAMENTO_DARDO:
		case LANCAMENTO_DISCO:
		case SALTO_A_DISTANCIA:
		case SALTO_COM_VARA:
		case SALTO_EM_ALTURA:
			pontuacao = calculaPontuacao(performance);
			break;

		default:
			break;
		}
		return pontuacao;
	}

	private int calculaPontuacaoCorrida(IPerformance performance) {
		ParametroCalculo param = parametros.get(performance.getEvento());
		double segundos = performance.getValorParaCalculo();
		return (int) (param.getA() * Math.pow(param.getB() - segundos, param.getC()));
	}
	
	private int calculaPontuacao(IPerformance performance) {
		ParametroCalculo param = parametros.get(performance.getEvento());
		return (int) (param.getA() * Math.pow(performance.getValorParaCalculo() - param.getB(), param.getC()));
	}
}