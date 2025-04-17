
package com.sns.project.chat.kafka;

import com.sns.project.chat.kafka.dto.request.KafkaChatEnterRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ChatEnterConsumer {

    @KafkaListener(
        topics = "chat-enter",
        groupId = "chat-enter-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(KafkaChatEnterRequest message) {
        System.out.println("🎯 사용자 " + message.getUserId() + "님이 방 " + message.getRoomId() + "에 입장했습니다.");
    }

}

