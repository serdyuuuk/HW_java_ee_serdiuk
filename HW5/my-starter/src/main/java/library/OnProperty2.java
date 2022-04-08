package library;

import org.springframework.beans.factory.InitializingBean;

public class OnProperty2 implements InitializingBean, InterfaceForConditional {
    public void something(){
        System.out.println("OnProperty2");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        something();
    }
}
