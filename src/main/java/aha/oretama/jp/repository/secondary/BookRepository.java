package aha.oretama.jp.repository.secondary;

import aha.oretama.jp.entity.secondary.Book;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author aha-oretama
 */
public interface BookRepository extends JpaRepository<Book,String> {
}
