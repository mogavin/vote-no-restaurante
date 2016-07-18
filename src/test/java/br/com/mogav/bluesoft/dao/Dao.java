package br.com.mogav.bluesoft.dao;

import java.util.Collection;

interface Dao<T extends Persistivel> {

	T salvar(T t);
	Collection<T> listarTodos();
}