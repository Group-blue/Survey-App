package com.bilgeadam.rabbitmq.consumer;

import static com.bilgeadam.constants.Constants.*;
import com.bilgeadam.rabbitmq.model.MailNotification;
import com.bilgeadam.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceConsumer {

    private final StudentService studentService;

    public MailServiceConsumer(StudentService studentService) {
        this.studentService = studentService;
    }

    @RabbitListener(queues = RABBIT_QUENAMEMAILTOUSER)
    public void consumeMailNotification(MailNotification notification){
        log.info(".............istek geldi......."+notification.toString());
        studentService.createTokensAndMailToUsers(notification);
    }

}
