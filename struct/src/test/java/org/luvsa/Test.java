package org.luvsa;

import org.junit.jupiter.api.BeforeEach;
import org.luvsa.converter.AnimalConverter;
import org.luvsa.entity.Animal;
import org.luvsa.entity.Person;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

/**
 * @author Aglet
 * @create 2022/11/24 9:32
 */
public class Test {

    private Animal animal;

    private Person person;

    @BeforeEach
    void init() {
        animal = new Animal();
        animal.setName("张三");
        animal.setAge(18);
        animal.setBirth(LocalDateTime.now());

        person = new Person();
        person.setName("李四");
        person.setSex('男');
        person.setAge(22);
        person.setBirth(LocalDateTime.of(2012, 8, 8, 1, 1, 1));
    }

    @org.junit.jupiter.api.Test
    void start() {
        var converter = Mappers.getMapper(AnimalConverter.class);

        var animal1 = converter.toAnimal(person);

        System.out.println(animal1);
    }

}
