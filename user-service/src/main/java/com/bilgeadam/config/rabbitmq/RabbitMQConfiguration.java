package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.bilgeadam.constants.Constants.*;

@Configuration
public class RabbitMQConfiguration {

    private String excahangeName= RABBIT_EXCHANGENAME;

    private String routingKeyMailToUser = RABBIT_ROUTINGKEYMAILTOUSER;
    private String queueNameMailToUser = RABBIT_QUENAMEMAILTOUSER;


    @Bean
    Queue queueMailToUsers(){
        return new Queue(queueNameMailToUser);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(excahangeName);
    }

    @Bean
    public Binding binding(final Queue queue, final DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(routingKeyMailToUser);
    }

}
