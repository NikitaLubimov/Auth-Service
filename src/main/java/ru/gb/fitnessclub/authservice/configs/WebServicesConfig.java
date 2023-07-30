package ru.gb.fitnessclub.authservice.configs;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.gb.fitnessclub.authservice.properties.AccountServiceIntergrationProperties;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AccountServiceIntergrationProperties.class)
public class WebServicesConfig {

    private  final AccountServiceIntergrationProperties accountServiceIntergrationProperties;

    @Bean
    public WebClient accountServiceWebClient(){
        TcpClient tcpClient =TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,accountServiceIntergrationProperties.getConnectionTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(accountServiceIntergrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(accountServiceIntergrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });
        return WebClient
                .builder()
                .baseUrl(accountServiceIntergrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
