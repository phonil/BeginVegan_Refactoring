package com.beginvegan.domain.tmp.suggestion.domain.parent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Inspection {

    COMPLETE_REJECTION("COMPLETE_REJECTION"),
    COMPLETE_REWARD("COMPLETE_REWARD"),
    INCOMPLETE("INCOMPLETE");

    private String value;
}
