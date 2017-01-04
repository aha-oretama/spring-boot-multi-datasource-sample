package aha.oretama.jp.entity.secondary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author aha-oretama
 */
@Entity
@Data
public class Book {
  @Id
  private String isbn;
  private String title;
}
