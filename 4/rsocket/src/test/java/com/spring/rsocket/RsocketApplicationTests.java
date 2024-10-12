package com.spring.rsocket;

import com.spring.rsocket.entities.Good;
import com.spring.rsocket.repositories.GoodsRepository;
import io.rsocket.frame.decoder.PayloadDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RsocketApplicationTests {

	@Autowired
	private GoodsRepository goodsRepository;
	private RSocketRequester requester;

	@BeforeEach
	public void setup() {
		requester = RSocketRequester.builder()
				.rsocketStrategies(builder -> builder.decoder(new
						Jackson2JsonDecoder()))
				.rsocketStrategies(builder -> builder.encoder(new
						Jackson2JsonEncoder()))
				.rsocketConnector(connector -> connector
						.payloadDecoder(PayloadDecoder.ZERO_COPY)
						.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
				.tcp("localhost", 5100);
	}
	@AfterEach
	public void cleanup() {
		requester.dispose();
	}

	@Test
	public void testGetGood() {
		Good good = new Good();
		good.setName("TestGood");
		good.setPrice(200);
		good.setDescription("Black");
		Good savedGood = goodsRepository.save(good);
		Mono<Good> result = requester.route("getGood")
				.data(savedGood.getId())
				.retrieveMono(Good.class);
		assertNotNull(result.block());
	}

	@Test
	public void testAddGoods() {
		Good good = new Good();
		good.setName("TestGood");
		good.setPrice(200);
		good.setDescription("Black");
		Mono<Good> result = requester.route("addGood")
				.data(good)
				.retrieveMono(Good.class);
		Good savedGood = result.block();
		assertNotNull(savedGood);
		assertNotNull(savedGood.getId());
		assertTrue(savedGood.getId() > 0);
	}

	@Test
	public void testGetGoods() {
		Flux<Good> result = requester.route("getGoods")
				.retrieveFlux(Good.class);
		assertNotNull(result.blockFirst());
	}

	@Test
	public void testDeleteGood() {
		Good good = new Good();
		good.setName("TestGood");
		good.setPrice(400);
		good.setDescription("RandomDescription");
		Good savedGood = goodsRepository.save(good);
		Mono<Void> result = requester.route("deleteGood")
				.data(savedGood.getId())
				.send();
		result.block();
		Good deletedCat = goodsRepository.findById(savedGood.getId()).get();
		assertNotSame(deletedCat, savedGood);
	}
}
