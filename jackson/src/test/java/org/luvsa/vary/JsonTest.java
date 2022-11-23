package org.luvsa.vary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.luvsa.jackson.VaryModule;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Aglet
 * @create 2022/11/21 14:25
 */
public class JsonTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void init(){
        mapper.registerModule(new VaryModule());
    }

    @Test
    public void toJson() throws JsonProcessingException {
        var bean = new Bean();
        bean.setName("张三");
        bean.setAge(18);
        bean.setSex('男');
        bean.setBirth(LocalDateTime.of(2004, 5 , 7, 10, 50, 30));

        var value = mapper.writeValueAsString(bean);
        System.out.println(value);
    }


    private static class Bean {


        private String name;

        private int age;

        private char sex;

        private LocalDateTime birth;

        private boolean marry;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public char getSex() {
            return sex;
        }

        public void setSex(char sex) {
            this.sex = sex;
        }

        public LocalDateTime getBirth() {
            return birth;
        }

        public void setBirth(LocalDateTime birth) {
            this.birth = birth;
        }

        public boolean isMarry() {
            return marry;
        }

        public void setMarry(boolean marry) {
            this.marry = marry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bean bean = (Bean) o;
            return age == bean.age && sex == bean.sex && marry == bean.marry && Objects.equals(name, bean.name) && Objects.equals(birth, bean.birth);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, sex, birth, marry);
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", birth=" + birth +
                    ", marry=" + marry +
                    '}';
        }
    }

}
