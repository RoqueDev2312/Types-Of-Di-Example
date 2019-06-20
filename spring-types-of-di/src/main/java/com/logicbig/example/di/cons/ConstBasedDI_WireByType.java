package com.logicbig.example.di.cons;

import com.logicbig.example.service.OrderService;
import com.logicbig.example.service.impl.OrderServiceImpl1;
import com.logicbig.example.service.impl.OrderServiceImpl2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan({"com.logicbig.example.di.cons"})
public class ConstBasedDI_WireByType {

    @Bean(name = "a")
    public OrderService orderServiceByProvider1 () {
        return new OrderServiceImpl1();
    }

    @Bean(name = "b")
    public OrderService orderServiceByProvider2 () {
        return new OrderServiceImpl2();
    }


    public static void main (String... strings) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                            ConstBasedDI_WireByType.class);
        OrderServiceClient bean = context.getBean(ConstBasedDI_WireByType.OrderServiceClient.class);
        bean.showPendingOrderDetails();
    }

    
    
    @Component
    public static class OrderServiceClient {

        private final OrderService orderService1;
        private final OrderService orderService2;

        @Autowired
        OrderServiceClient (@Qualifier("a") OrderService orderService1,
                            @Qualifier("b") OrderService orderService2) {
            this.orderService1 = orderService1;
            this.orderService2 = orderService2;
        }

        public void showPendingOrderDetails () {
            System.out.println(orderService1.getOrderDetails("100"));
            System.out.println(orderService2.getOrderDetails("100"));
        }
    }
}