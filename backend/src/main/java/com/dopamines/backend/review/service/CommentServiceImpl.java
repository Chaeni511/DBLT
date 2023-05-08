package com.dopamines.backend.review.service;

import com.dopamines.backend.account.entity.Account;
import com.dopamines.backend.account.service.UserService;
import com.dopamines.backend.plan.entity.Participant;
import com.dopamines.backend.plan.entity.Plan;
import com.dopamines.backend.plan.repository.ParticipantRepository;
import com.dopamines.backend.plan.repository.PlanRepository;
import com.dopamines.backend.plan.service.PlanService;
import com.dopamines.backend.review.entity.Comment;
import com.dopamines.backend.review.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    PlanRepository planRepository;

    @Autowired
    UserService userService;


    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글 정보가 없습니다."));
    }

    @Override
    public Long createComment(String userEmail, Long planId, String content) {

        Account account = userService.findByEmail(userEmail);

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약속 정보가 없습니다."));

        Participant participant = participantRepository.findByPlanAndAccount(plan,account)
                .orElseThrow(() -> new IllegalArgumentException("해당 약속에 참가자의 정보가 없습니다."));

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        Comment comment = Comment.builder()
                .participant(participant)
                .plan(plan)
                .content(content)
                .registerTime(now)
                .updateTime(now)
                .build();
        commentRepository.save(comment);
        log.info(account.getEmail() + " 님이 댓글이 등록되었습니다");
        return comment.getCommentId();
    }

    @Override
    public void updateComment(String userEmail, Long commentId, String content) {

        Comment comment = getCommentById(commentId);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        comment.setContent(content);
        comment.setUpdateTime(now);
        commentRepository.save(comment);
        log.info(userEmail + " 님이 댓글이 수정되었습니다");
    }

    // 약속 삭제
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }


    // 내 댓글이니? (권한 확인)
    @Override
    public boolean isMyComment(String userEmail, Long commentId) {
        Comment comment = getCommentById(commentId);
        return comment.getParticipant().getAccount().getEmail().equals(userEmail);
    }
}
