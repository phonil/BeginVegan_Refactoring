package com.beginvegan.infrastructure.fcm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {

    LEVEL_UP,
    REVIEW_RECOMMEND,
    REVIEW_REPORT,

}
