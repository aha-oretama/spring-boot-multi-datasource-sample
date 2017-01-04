package aha.oretama.jp.repository.primary;

import aha.oretama.jp.entity.primary.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author aha-oretama
 */
public interface UserRepository extends JpaRepository<User,Integer>{
}
