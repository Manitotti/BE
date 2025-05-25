package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateManiPostRequest {
    private String title;
    private String content;
}