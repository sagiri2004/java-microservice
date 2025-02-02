package com.microserviceproject.bookservice.command.controller;


import com.microserviceproject.bookservice.command.command.CreateBookCommand;
import com.microserviceproject.bookservice.command.command.DeleteBookCommand;
import com.microserviceproject.bookservice.command.command.UpdateBookCommand;
import com.microserviceproject.bookservice.command.model.BookRequestModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
	@Autowired
	private CommandGateway commandGateway;

	@PostMapping
	public String addBook(@Valid @RequestBody BookRequestModel model) {
		CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
		return commandGateway.sendAndWait(command);
	}

	@PutMapping("/{bookId}")
	public String updateBook(@PathVariable String bookId, @RequestBody BookRequestModel model) {
		UpdateBookCommand command = new UpdateBookCommand(bookId, model.getName(), model.getAuthor(), true);
		return commandGateway.sendAndWait(command);
	}

	@DeleteMapping("/{bookId}")
	public String deleteBook(@PathVariable String bookId) {
		DeleteBookCommand command = new DeleteBookCommand(bookId);
		return commandGateway.sendAndWait(command);
	}
}