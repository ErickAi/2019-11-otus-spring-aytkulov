package ru.otus.acl.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.acl.repositories.NoticeMessageRepo;
import ru.otus.acl.security.AclConfig;
import ru.otus.acl.security.AclMethodSecurityConfiguration;
import ru.otus.acl.services.NoticeMessageService;

@DataJpaTest
@Import({AclMethodSecurityConfiguration.class, AclConfig.class})
@ComponentScan({"ru.otus.acl.services"})
@ActiveProfiles("test")
class AbstractAclSecurityIntegrationTest {

    @Autowired
    NoticeMessageService service;
    @Autowired
    NoticeMessageRepo repository;
}
