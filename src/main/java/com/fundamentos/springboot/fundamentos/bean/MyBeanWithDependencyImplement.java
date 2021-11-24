package com.fundamentos.springboot.fundamentos.bean;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency {

    private  MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependendy() {
        int numero = 1;
        System.out.println(myOperation.sum(numero));
        System.out.println("Hola deade la implementacion de un bean con dependencia");
    }
}
