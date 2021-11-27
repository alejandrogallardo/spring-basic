package com.fundamentos.springboot.fundamentos.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency {

    Log LOGGER = LogFactory.getLog(MyBeanWithDependencyImplement.class);

    private  MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependendy() {
        LOGGER.info("Hemos ingresado al metoo print");
        int numero = 1;
        LOGGER.debug("El numero enfia es: " + numero);
        System.out.println(myOperation.sum(numero));
        System.out.println("Hola deade la implementacion de un bean con dependencia");
    }
}
