package br.com.mogav.bluesoft.dao;

import java.util.Collection;

import br.com.mogav.bluesoft.model.Persistivel;

interface Dao<T extends Persistivel> {

	T salvar(T t);
	Collection<T> listarTodos();
}