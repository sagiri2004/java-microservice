package com.microserviceproject.bookservice.query.controller;

import com.microserviceproject.bookservice.query.queries.GetBookDetailQuery;
import com.microserviceproject.bookservice.query.model.BookResponseModel;
import com.microserviceproject.bookservice.query.queries.GetAllBookQuery;
import com.microserviceproject.commonservice.services.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
	@Autowired
	private QueryGateway queryGateway;

	@GetMapping
	public List<BookResponseModel> getAllBooks() {
		GetAllBookQuery query = new GetAllBookQuery();
		return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
	}

	@GetMapping("{bookId}")
	public BookResponseModel getBookDetail(@PathVariable String bookId) {
		GetBookDetailQuery query = new GetBookDetailQuery(bookId);
		return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseModel.class)).join();
	}

	@Autowired
	private KafkaService kafkaService;

	// test kafka
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestBody String message) {
		kafkaService.sendMessage("test", message);
	}
}
