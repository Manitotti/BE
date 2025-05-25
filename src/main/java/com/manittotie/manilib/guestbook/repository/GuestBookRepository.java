package com.manittotie.manilib.guestbook.repository;

import com.manittotie.manilib.guestbook.domain.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {

    List<GuestBook> findAllByOwner_IdOrderByCreatedAt(Long ownerId);
}
