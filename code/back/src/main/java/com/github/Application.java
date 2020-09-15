package com.github;

import com.github.config.MyConfig;
import com.github.netty.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@SpringBootApplication
@RestController
public class Application implements CommandLineRunner {

	@Value("${netty.port}")
	private int port;

	@Value("${netty.url}")
	private String url;

	@Autowired
	private Server server;

	@Autowired
	private MyConfig config;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/")
	public void hello(){
		System.out.println(config);
	}

	@Override
	public void run(String... args) throws Exception {
		InetSocketAddress address = new InetSocketAddress(url,port);
		server.run(address);
		System.out.println("netty is ready 0.0 !");
	}
}
