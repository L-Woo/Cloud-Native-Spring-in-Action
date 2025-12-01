package com.polarbookshop.catalogservice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public record Book(
  @NotBlank(message = "The book ISBN must be defined.")
  @Pattern(
    regexp = "^[0-9]{10}|[0-9]{13}$",
    message = "The ISBN format must be valid."
  )
  @NotBlank( // 이 필드는 널 값이 되어서는 안되고 화이트스페이스가 아닌 문자를 최소 하나 이상 가지고 있어야 한다.
    message = "The book title must be defined."
  )
  String isbn,
  @NotBlank(message = "The book title must be defined.")
  String title,

  @NotBlank(message = "The book author must be defined.")
  String author,
  @NotNull(message = "The book price must be defined.")
  @Positive(message = "The book price must be greater than zero") // 이 필드는 널 값이 되어서는 안 되고 0보다 큰 값을 가져야 한다.
  Double price
) {
}
