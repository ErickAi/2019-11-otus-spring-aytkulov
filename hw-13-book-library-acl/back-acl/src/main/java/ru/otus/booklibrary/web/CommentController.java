package ru.otus.booklibrary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.otus.booklibrary.domain.Comment;
import ru.otus.booklibrary.repo.CommentRepo;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequiredArgsConstructor
@Slf4j
class CommentController {

    private final CommentRepo commentRepo;
    private final MutableAclService aclService;

    @GetMapping(value = "/comments/book/{bookId}")
    public List<Comment> findByBookId(@PathVariable("bookId") Long bookId) {
        log.info("getAll");
        return commentRepo.findAllByBookId(bookId);
    }

    @PostMapping(value = "/comments")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment newComment, Authentication authentication) {
        log.info("create: {} user: ", newComment);
        commentRepo.save(newComment);

        final Sid owner = new PrincipalSid( authentication );
        ObjectIdentity oid = new ObjectIdentityImpl( newComment.getClass(), newComment.getId() );

        final Sid admin = new GrantedAuthoritySid("ROLE_MODERATOR");

        MutableAcl acl = aclService.createAcl( oid );
        acl.setOwner( owner );
        acl.insertAce( acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true );

        aclService.updateAcl( acl );

        return newComment;
    }

    @PutMapping(value = "/comments/{id}")
    @Transactional
    public void update(@PathVariable("id") Long id, @RequestBody Comment forUpdate) {
        log.info("update: {}", forUpdate);
        commentRepo.save(forUpdate);
    }

    @DeleteMapping(value = "/comments/{id}")
    @Transactional
    public void delete(@PathVariable("id") Long id) {
        log.info("delete");
        commentRepo.deleteById(id);
    }
}
