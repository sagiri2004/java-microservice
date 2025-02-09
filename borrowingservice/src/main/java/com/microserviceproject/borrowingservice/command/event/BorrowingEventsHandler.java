package com.microserviceproject.borrowingservice.command.event;

import com.microserviceproject.borrowingservice.command.data.Borrowing;
import com.microserviceproject.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowingEventsHandler {
	@Autowired
	private BorrowingRepository borrowingRepository;

	@EventHandler
	public void on(BorrowingCreatedEvent event) {
		Borrowing model = new Borrowing();
		model.setId(event.getId());
		model.setBookId(event.getBookId());
		model.setEmployeeId(event.getEmployeeId());
		borrowingRepository.save(model);
	}
}
