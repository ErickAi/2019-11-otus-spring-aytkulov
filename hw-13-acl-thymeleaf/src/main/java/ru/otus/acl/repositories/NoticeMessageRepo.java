package ru.otus.acl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.acl.domain.NoticeMessage;

@Repository
public interface NoticeMessageRepo extends JpaRepository<NoticeMessage, Long> {

}
