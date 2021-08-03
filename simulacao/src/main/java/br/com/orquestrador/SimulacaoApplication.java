package br.com.orquestrador;

import io.grpc.netty.shaded.io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@TypeHint(types = NioServerSocketChannel.class, typeNames = "io.grpc.netty.shaded.io.netty.channel.socket.nio.NioServerSocketChannel$NioServerSocketChannelConfig")
public class SimulacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulacaoApplication.class, args);
	}

}
