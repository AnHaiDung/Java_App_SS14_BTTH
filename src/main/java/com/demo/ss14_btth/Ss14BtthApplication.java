package com.demo.ss14_btth;

import com.demo.ss14_btth.service.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Ss14BtthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ss14BtthApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(WalletService walletService) {
        return args -> {
            walletService.rechargeMoney(1L, 100.0);
            walletService.testCache(1L);
            walletService.transferMoney(1L, 2L, 50.0);
            walletService.showWalletLazy(1L);
            walletService.showWalletJoinFetch(1L);
        };
    }
}