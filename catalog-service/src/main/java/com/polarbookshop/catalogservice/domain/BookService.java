package com.polarbookshop.catalogservice.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    /**
     * 존재하지 않는 책을 보려고 할 때 BookNotFoundException 예외발생
     * @param isbn
     * @return
     */
    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    /**
     * 동일한 책을 여러 번 추가하려고 시도하는 경우 BookAlreadyExistsException 예외 발생
     * @param book
     * @return
     */
    public Book addBookToCatalog(Book book) {
        if(bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    /**
     * 책을 수정할 때 개체 식별자인 ISBN 코드를 제외한 모든 필드를 수정할 수 있으며
     * 카탈로그에 존재하지 않는 책을 수정하려고 하면 새로운 책을 만든다.
     * @param isbn
     * @param book
     * @return
     */
    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price());
                    return bookRepository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));
    }
}
