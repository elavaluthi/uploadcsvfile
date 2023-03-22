package com.example.demo.Repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Person;
@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
}
