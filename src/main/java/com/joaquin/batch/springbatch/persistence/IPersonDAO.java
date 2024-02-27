package com.joaquin.batch.springbatch.persistence;

import com.joaquin.batch.springbatch.entities.Person;
import org.springframework.data.repository.CrudRepository;
public interface IPersonDAO extends CrudRepository<Person, Long> {
}
