package com.dopamines.backend.review.service;

import com.dopamines.backend.review.dto.Commentdto;
import com.dopamines.backend.review.entity.Comment;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CommentService {

    // 댓글 생성
    Long createComment(String userEmail, Long planId, String content);

    void updateComment(String userEmail, Long planId, String content);

    boolean isMyComment(String userEmail, Long commentId);

    void deleteComment(Long commentId);

    Map<LocalDate, List<Commentdto>> getCommentList(Long planId);

}
