package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateCommentResponse {
    private Long maniPostId;
    private Long commentId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //프론트에서 내가 쓴 댓글 보여줄지 말지 수정삭제여부
    private boolean updateButton;
    private boolean deleteButton;

}
