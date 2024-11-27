package com.redis.app.Redis.Application.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course implements Serializable {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
    private String link;
    private Integer comment_count;
}
