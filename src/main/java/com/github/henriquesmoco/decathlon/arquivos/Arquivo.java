package com.github.henriquesmoco.decathlon.arquivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Arquivo implements ILeitorDados<String>, IEscritorDados<String> {
	private String nomeArquivo;
	private String encoding = "UTF-8";

	public Arquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public void escrever(String dados) {
		try {
			escreverArquivo(nomeArquivo, encoding, dados);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	private void escreverArquivo(String nomeArquivo, String encoding, String conteudo) throws IOException {
		Writer writer = null;
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(nomeArquivo), encoding);
			writer = new BufferedWriter(out);
			writer.write(conteudo);
		} finally {
			fecharArquivo(writer);
		}
	}

	public List<String> lerRegistros() {
		try {
			return lerArquivo(nomeArquivo, encoding);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<String> lerArquivo(String nomeArquivo, String encoding) throws IOException {
		List<String> resultado = new ArrayList<String>();
		BufferedReader buf = null;
		try {
			buf = new BufferedReader(new InputStreamReader(new FileInputStream(nomeArquivo), encoding));
			String line = null;
			while ((line = buf.readLine()) != null) {
				resultado.add(line);
			}
			return resultado;
		} finally {
			fecharArquivo(buf);
		}
	}
	
	private void fecharArquivo(Closeable file) throws IOException {
		if (file != null) {
			file.close();
		}
	}

}
