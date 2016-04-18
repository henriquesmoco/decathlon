package com.github.henriquesmoco.decathlon.arquivos;

import java.util.ArrayList;
import java.util.List;

import com.github.henriquesmoco.decathlon.Evento;
import com.github.henriquesmoco.decathlon.participacao.IPerformance;
import com.github.henriquesmoco.decathlon.participacao.Participacao;
import com.github.henriquesmoco.decathlon.participacao.PerformanceCorrida;
import com.github.henriquesmoco.decathlon.participacao.PerformanceLancamento;
import com.github.henriquesmoco.decathlon.participacao.PerformanceSalto;

public class LeitorDadosParticipacaoCsv implements ILeitorDados<Participacao> {
		private ILeitorDados<String> arquivoTxt;
		private Evento[] eventosPorOrdemDeCampos = { Evento.CORRIDA_100_METROS, Evento.SALTO_A_DISTANCIA,
				Evento.ARREMESSO, Evento.SALTO_EM_ALTURA, Evento.CORRIDA_400_METROS,
				Evento.CORRIDA_110_METROS_COM_BARREIRAS, Evento.LANCAMENTO_DISCO,
				Evento.SALTO_COM_VARA, Evento.LANCAMENTO_DARDO, Evento.CORRIDA_1500_METROS };
		
		public LeitorDadosParticipacaoCsv(ILeitorDados<String> arquivoTxt) {
			this.arquivoTxt = arquivoTxt;
		}
		public List<Participacao> lerRegistros() {
			List<Participacao> participacoes = new ArrayList<Participacao>();
			List<String> registrosTxt = arquivoTxt.lerRegistros();
			for (String linha : registrosTxt) {
				if (NuloOuVazio(linha)) {
					continue;
				}
				Participacao part = criaParticipacao(linha);
				participacoes.add(part);
			}
			return participacoes;
		}
		
		private Participacao criaParticipacao(String linha) {
			String[] campos = linha.split(";");
			String nomeAtleta = campos[0];
			Participacao part = new Participacao(nomeAtleta);
			for (int posicaoCampo = 1; posicaoCampo < campos.length; posicaoCampo++) {
				String valorPerformance = campos[posicaoCampo];
				if (NuloOuVazio(valorPerformance)) {
					continue;
				}
				part.addPerformance(criaPerformance(posicaoCampo, valorPerformance));
			}
			return part;
		}
		
		private boolean NuloOuVazio(String linha) {
			return linha == null || linha.trim().length() == 0;
		}
		
		private IPerformance criaPerformance(int posicaoDoCampo, String valorPerformance) {
			IPerformance perf = null;
			int posicaoInicioCamposDePerformance = 1;
			Evento evento = eventosPorOrdemDeCampos[posicaoDoCampo - posicaoInicioCamposDePerformance];
			switch (evento) {
			case CORRIDA_100_METROS:
			case CORRIDA_110_METROS_COM_BARREIRAS:
			case CORRIDA_1500_METROS:
			case CORRIDA_400_METROS:
				perf = new PerformanceCorrida(valorPerformance, evento);
				break;
			case ARREMESSO:
			case LANCAMENTO_DARDO:
			case LANCAMENTO_DISCO:
				perf = new PerformanceLancamento(valorPerformance, evento);
				break;
			case SALTO_A_DISTANCIA:
			case SALTO_COM_VARA:
			case SALTO_EM_ALTURA:
				perf = new PerformanceSalto(valorPerformance, evento);
				break;
			default:
				break;
			}
			return perf;
		}		
	}
	