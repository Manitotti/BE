package com.manittotie.manilib.guestbook.repository;

import com.manittotie.manilib.guestbook.domain.GuestBook;
import com.manittotie.manilib.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
    List<GuestBook> findAllByMemberOrderByCreatedAt(Member member);
}
