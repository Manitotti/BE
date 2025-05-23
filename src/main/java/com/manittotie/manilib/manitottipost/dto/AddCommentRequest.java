package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddCommentRequest {
    private Long maniPostId;
    private String content;
}
