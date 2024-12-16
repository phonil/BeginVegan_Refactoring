package com.beginvegan.domain.tmp.suggestion.domain.parent;

import com.beginvegan.domain.common.BaseEntity;
import com.beginvegan.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략 사용
public class Suggestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private SuggestionType suggestionType;

    @Enumerated(EnumType.STRING)
    private Inspection inspection;

}
