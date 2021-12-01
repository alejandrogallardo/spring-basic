/*
* NOTAS
* Para los que tengan dudas de para que sirve una clase DTO (Data Transfer Object) les dejo el siguiente link donde se explica de manera detallada su uso.
  https://www.oscarblancarteblog.com/2018/11/30/data-transfer-object-dto-patron-diseno/
*/

package com.fundamentos.springboot.fundamentos.dto;

import java.time.LocalDate;

public class UserDto {
    private Long id;
    private String name;
    private LocalDate birthName;

    public UserDto(Long id, String name, LocalDate birthName) {
        this.id = id;
        this.name = name;
        this.birthName = birthName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthName() {
        return birthName;
    }

    public void setBirthName(LocalDate birthName) {
        this.birthName = birthName;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthName='" + birthName + '\'' +
                '}';
    }
}
