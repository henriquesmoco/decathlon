package com.github.henriquesmoco.decathlon.arquivos;

import java.util.List;

public interface ILeitorDados<T> {
	List<T> lerRegistros();
}