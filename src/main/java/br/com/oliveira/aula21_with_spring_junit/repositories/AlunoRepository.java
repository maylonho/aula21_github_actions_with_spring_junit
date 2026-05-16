package br.com.oliveira.aula21_with_spring_junit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.oliveira.aula21_with_spring_junit.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
