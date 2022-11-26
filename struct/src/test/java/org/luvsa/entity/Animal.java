package org.luvsa.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Aglet
 * @create 2022/11/24 9:28
 */
@Data
public class Animal {

    private String name;

    private int age;

    private LocalDateTime birth;
}
