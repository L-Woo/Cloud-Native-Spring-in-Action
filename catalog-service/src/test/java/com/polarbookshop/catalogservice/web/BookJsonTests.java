package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

  @Autowired
  private JacksonTester<Book> json;

  @Test
  void testSerialize() throws Exception {
    var book = new Book("1234567890", "Title", "Author", 9.90);
    var jsonContent = json.write(book);
    // JsonPath 형식을 사용해 JSON 객체를 탐색하고 자바의 JSON 변환을 확인한다.
    assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
      .isEqualTo(book.isbn());
    assertThat(jsonContent).extractingJsonPathStringValue("@.title")
      .isEqualTo(book.title());
    assertThat(jsonContent).extractingJsonPathStringValue("@.author")
      .isEqualTo(book.author());
    assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
      .isEqualTo(book.price());
  }

  @Test
  void testDeserialize() throws Exception {
    // 자바 텍스트 블록 기능을 사용해 JSON 객체를 정의한다.
    var content = """ 
      {
        "isbn" : "1234567890",
        "title" : "Title",
        "author" : "Author",
        "price" : 9.90
      }
      """;
    assertThat(json.parse(content)) // JSON에서 자바 객체로의 변환을 확인한다.
      .usingRecursiveComparison()
      .isEqualTo(new Book("1234567890", "Title", "Author", 9.90));
  }
}
