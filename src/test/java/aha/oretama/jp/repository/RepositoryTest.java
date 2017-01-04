package aha.oretama.jp.repository;

import aha.oretama.jp.entity.primary.User;
import aha.oretama.jp.entity.secondary.Book;
import aha.oretama.jp.repository.primary.UserRepository;
import aha.oretama.jp.repository.secondary.BookRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author aha-oretama
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RepositoryTest {


  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BookRepository bookRepository;

  @Test
  public void whenCreateUser_thenCreated() {
    User user = new User();
    user.setName("aha-oretama");
    userRepository.save(user);

    User findUser = userRepository.findOne(user.getId());
    assertEquals(user.getName(), findUser.getName());
  }

  @Test
  public void whenCreateBook_thenCreated() {
    Book book = new Book();
    book.setIsbn("1234abcd");
    book.setTitle("aha-oretama's diary");
    bookRepository.save(book);

    Book findBook = bookRepository.findOne(book.getIsbn());
    assertEquals(book.getTitle(),findBook.getTitle());

  }

  @Test
  public void whenCreateUserProduct_thenCreated() {
    User user = new User();
    user.setName("aha-oretama");
    userRepository.save(user);

    Book book = new Book();
    book.setIsbn("1234abcd");
    book.setTitle("aha-oretama's diary");
    bookRepository.save(book);


    User findUser = userRepository.findOne(user.getId());
    assertEquals(user.getName(), findUser.getName());

    Book findBook = bookRepository.findOne(book.getIsbn());
    assertEquals(book.getTitle(),findBook.getTitle());
  }

}
