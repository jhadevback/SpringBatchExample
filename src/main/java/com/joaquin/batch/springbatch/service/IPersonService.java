package com.joaquin.batch.springbatch.service;

import com.joaquin.batch.springbatch.entities.Person;

import java.util.List;

public interface IPersonService {
    void saveAll(List<Person> personList);

}
