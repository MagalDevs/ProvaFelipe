package com.fiec.provafinal.models;

import java.util.List;

public interface GenericRepositorio<T, U> {
    T criar(T pessoa);
    List<T> acharTodos();
    T acharPorId(U id);
    void atualizar(T pessoa, U id);
    void deletar(U id);
}
