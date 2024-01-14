package co.com.bancolombia.mq.reqreply;

import co.com.bancolombia.commons.jms.mq.EnableReqReply;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
@AllArgsConstructor
@EnableReqReply
public class SampleMQReqReply /* implements SomeGateway */ {
    private final ReqReplyGateway sender;

    public Mono<String> doRequest(String message) {
        return sender.requestReply(message)
                .name("mq_req_reply")
                .tag("operation", "my-operation") // TODO: Change operation name
                .metrics()
                .map(this::extractResponse);
    }

    @SneakyThrows
    private String extractResponse(Message message) {
        TextMessage textMessage = (TextMessage) message;
        return textMessage.getText();
    }
}
