package ru.otus.acl.services;

import ru.otus.acl.domain.NoticeMessage;

import java.util.List;

public interface NoticeMessageService {

    void add(NoticeMessage noticeMessage);

    NoticeMessage getById(Long id);

    List<NoticeMessage> getAll();

    void update(NoticeMessage noticeMessage);

    void delete(NoticeMessage noticeMessage);
}
