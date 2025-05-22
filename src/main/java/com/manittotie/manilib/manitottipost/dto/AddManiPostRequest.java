package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddManiPostRequest {
    private Long groupId;
    private String title;
    private String content;
}
