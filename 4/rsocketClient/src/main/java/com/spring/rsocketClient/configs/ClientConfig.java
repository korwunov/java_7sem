package com.spring.rsocketClient.configs;

import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.boot.rsocket.server.RSocketServerFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

@Configuration
public class ClientConfig {

    @Bean
    public RSocketRequester getRequest(RSocketRequester.Builder builder) {
        SocketAcceptor responder = RSocketMessageHandler.responder(clientRsocketStrategies(), new ClientHandler())
        return builder.rsocketConnector(r -> r.acceptor())
                .rsocketStrategies(clientRsocketStrategies())
                .tcp("127.0.0.1", 8080);

//        .rsocketConnector(connector -> connector
//                .payloadDecoder(PayloadDecoder.ZERO_COPY)
//                .reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
//                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
    }

    @Bean
    public RSocketStrategies clientRsocketStrategies() {
        return RSocketStrategies.builder()
                .routeMatcher(new PathPatternRouteMatcher())
                .encoder(new Jackson2CborEncoder())
                .decoder(new Jackson2CborDecoder()).build();
    }
}
