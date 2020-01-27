package cn.e3mall;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
public class TestPublish {

    @Test
    public void publishService()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-*.xml");
        //System.in.read();
    }
}
