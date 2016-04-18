package com.github.henriquesmoco.decathlon.classificacao;

import com.github.henriquesmoco.decathlon.participacao.Participacao;

public class Classificacao {
		private String posicao;
		private Participacao participacao;
		private int totalDePontos;
		
		public Classificacao(Participacao part) {
			this.participacao = part;
		}
		public Classificacao(String posicao, int pontos, Participacao part) {
			this.participacao = part;
			this.posicao = posicao;
			this.totalDePontos = pontos;			
		}
		void setPosicao(String posicao) {
			this.posicao = posicao;
		}
		public String getPosicao() {
			return posicao;
		}
		public Participacao getParticipacao() {
			return participacao;
		}
		void setTotalDePontos(int pontos) {
			this.totalDePontos = pontos;
		}
		public int getTotalDePontos() {
			return totalDePontos;
		}
	}