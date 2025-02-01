package com.microserviceproject.bookservice.query.projection;

import com.microserviceproject.bookservice.command.data.Book;
import com.microserviceproject.bookservice.command.data.BookRepository;
import com.microserviceproject.bookservice.query.queries.GetBookDetailQuery;
import com.microserviceproject.bookservice.query.model.BookResponseModel;
import com.microserviceproject.bookservice.query.queries.GetAllBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
	@Autowired
	private BookRepository bookRepository;

	@QueryHandler
	public List<BookResponseModel> handle(GetAllBookQuery query) {
		List<Book> list = bookRepository.findAll();
		List<BookResponseModel> listBookResponse = new ArrayList<>();
		list.forEach(book -> {
			BookResponseModel model = new BookResponseModel();
			BeanUtils.copyProperties(book, model);
			listBookResponse.add(model);
		});
		return listBookResponse;
	}

	@QueryHandler
	public BookResponseModel handle(GetBookDetailQuery query) {
		BookResponseModel bookResponseModel = new BookResponseModel();
		bookRepository.findById(query.getId()).ifPresent(book -> {
			BeanUtils.copyProperties(book, bookResponseModel);
		});
		return bookResponseModel;
	}
}