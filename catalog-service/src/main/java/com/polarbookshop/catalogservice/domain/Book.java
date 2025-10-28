package com.polarbookshop.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(
  @NotBlank(message = "The book ISBN must be defined.")
  @Pattern(
    regexp = "^[0-9]{10}|[0-9]{13}$",
    message = "The ISBN format must be valid."
  )
  @NotBlank(
    message = "The book title must be defined."
  )
  String isbn,
  @NotBlank(message = "The book title must bve defined.")
  String title,

  @NotBlank(message = "The book author must bve defined.")
  String author,
  @NotNull(message = "The book price must be defined.")
  @Positive(message = "The book price must be greater than zero") // 이 필드는 널 값이 되어서는 안 되고 0보다 큰 값을 가져야 한다.
  Double price
) {
}
