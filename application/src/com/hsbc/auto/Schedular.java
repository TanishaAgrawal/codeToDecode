package com.hsbc.auto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.hsbc.daoImpl.OrderProcessingDAOImpl;
import com.hsbc.exceptions.OrderNotFoundForEmployee;
import com.hsbc.exceptions.ProductNotFoundException;

@WebListener("application schedular")
public class Schedular implements ServletContextListener {

	ScheduledExecutorService scheduler;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		scheduler.shutdownNow();

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		LocalTime midnight = LocalTime.of(23, 59, 59);
		LocalTime now = LocalTime.now();
		Duration initialDelay = Duration.between(midnight, now);
		long PERIOD = 86400000L;
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> {
		
//			try {
//				this.expire();
//			} catch (OrderNotFoundForEmployee | ProductNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				this.complete();
//			} catch (OrderNotFoundForEmployee | ProductNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}, initialDelay.toMillis(), PERIOD, TimeUnit.MILLISECONDS);

		// initial period, delay between every execution

	}

	public void expire() throws OrderNotFoundForEmployee, ProductNotFoundException {
		OrderProcessingDAOImpl ob = new OrderProcessingDAOImpl();
		ob.expiryOrder();
	}

	public void complete() throws OrderNotFoundForEmployee, ProductNotFoundException {
		OrderProcessingDAOImpl ob = new OrderProcessingDAOImpl();
		ob.completeOrder();
	}

}
