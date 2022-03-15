package com.bilgeadam.rabbitmq.producer;

import static com.bilgeadam.constants.Constants.*;
import com.bilgeadam.rabbitmq.model.MailNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMailToUsersProducer {
    private final RabbitTemplate rabbitTemplate;

    public SendMailToUsersProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotificationUserServiceForMails(MailNotification mailNotification){
        rabbitTemplate.convertAndSend(RABBIT_EXCHANGENAME, RABBIT_ROUTINGKEYMAILTOUSER, mailNotification);
    }
}
