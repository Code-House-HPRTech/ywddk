package com.hprtech.ywddk.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDTO {
    int id;
    String title;
    String content;
    String excerpt;
    int featureImage;
    String category;
    String postDate;
    String slug;
    String status;
    String tag;
}