package com.dopamines.backend.review.service;

import com.dopamines.backend.review.entity.Comment;

public interface CommentService {

    // 댓글 생성
    Long createComment(String userEmail, Long planId, String content);

    void updateComment(String userEmail, Long planId, String content);

    boolean isMyComment(String userEmail, Long commentId);

    void deleteComment(Long commentId);

}
