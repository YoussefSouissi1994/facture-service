package tn.ipsas.factureservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("tn.ipsas")
public class FactureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FactureServiceApplication.class, args);
    }

}
