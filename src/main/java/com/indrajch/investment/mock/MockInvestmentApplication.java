package com.indrajch.investment.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import com.indrajch.investment.mock.run.InvestmentRun;

@Import({ MockInvestmentConfiguration.class })
@SpringBootApplication
public class MockInvestmentApplication {

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("shutdown");
			}
		});
		ConfigurableApplicationContext ac = SpringApplication.run(MockInvestmentApplication.class, args);
		try (AutoCloseable closable = () -> {
			// TODO: Auto close
			System.out.println("auto close");
		}) {
			InvestmentRun investmentRun = ac.getBean(InvestmentRun.class);
			investmentRun.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
