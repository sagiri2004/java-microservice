package com.microserviceproject.bookservice.command.event;


import com.microserviceproject.bookservice.command.data.Book;
import com.microserviceproject.bookservice.command.data.BookRepository;
import com.microserviceproject.commonservice.event.BookRollBackStatusEvent;
import com.microserviceproject.commonservice.event.BookUpdateStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventsHandler {
	@Autowired
	private BookRepository bookRepository;

	@EventHandler
	public void on(BookCreatedEvent event) {
		Book book = new Book();
		BeanUtils.copyProperties(event, book);
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookDeletedEvent event) {
		Optional<Book> oldBook = bookRepository.findById(event.getId());
		if (oldBook.isPresent()) {
			bookRepository.delete(oldBook.get());
		}
	}

	@EventHandler
	public void on(BookUpdatedEvent event) {
		Optional<Book> book = bookRepository.findById(event.getId());
		if (book.isPresent()) {
			book.get().setName(event.getName());
			book.get().setAuthor(event.getAuthor());
			book.get().setIsReady(event.getIsReady());
			bookRepository.save(book.get());
		}
	}

	@EventHandler
	public void on(BookUpdateStatusEvent event) {
		Optional<Book> oldBook = bookRepository.findById(event.getBookId());
		oldBook.ifPresent(book -> {
			book.setIsReady(event.getIsReady());
			bookRepository.save(book);
		});
	}

	@EventHandler
	public void on(BookRollBackStatusEvent event) {
		Optional<Book> oldBook = bookRepository.findById(event.getBookId());
		oldBook.ifPresent(book -> {
			book.setIsReady(event.getIsReady());
			bookRepository.save(book);
		});
	}
}
