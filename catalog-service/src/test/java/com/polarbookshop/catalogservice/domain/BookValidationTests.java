package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {

    private static Validator validator;

    @BeforeAll // 클래스 내의 테스트를 실행하기 전에 가장 먼저 실행할 코드 블록임을 나타낸다.
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test // 테스트 케이스임을 나타낸다.
    void whenAllFieldCorrectThenValidationSucceeds() {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty(); // 유효성 검사에서 오류가 없음을 확인한다.
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationsFails() {
       var book = new Book("a234567890", "Title", "Author", 9.90);
       Set<ConstraintViolation<Book>> violations = validator.validate(book);
       assertThat(violations).hasSize(1);
       assertThat(violations.iterator().next().getMessage())
               .isEqualTo("The ISBN format must be vaild.");
    }

}
