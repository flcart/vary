package org.luvsa.converter;

import org.luvsa.entity.Animal;
import org.luvsa.entity.Person;
import org.mapstruct.Mapper;

/**
 * @author Aglet
 * @create 2022/11/24 9:27
 */
@Mapper
public interface AnimalConverter {

    Person toPerson(Animal animal);


    Animal toAnimal(Person person);

}
