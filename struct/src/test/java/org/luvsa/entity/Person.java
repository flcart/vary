package org.luvsa.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aglet
 * @create 2022/11/24 9:29
 */
@Data
public class Person {

    private String name;

    private int age;

    private char sex;

    private LocalDateTime birth;
}
