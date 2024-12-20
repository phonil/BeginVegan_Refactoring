package com.beginvegan.domain.review.domain;

import com.beginvegan.domain.common.BaseEntity;
import com.beginvegan.domain.restaurant.domain.Restaurant;
import com.beginvegan.domain.recommendation.domain.Recommendation;
import com.beginvegan.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Double rate;

    private Boolean visible = true;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
    private List<Recommendation> recommendations;

    @Builder
    public Review(Long id, String content, Restaurant restaurant, User user, Double rate, ReviewType reviewType) {
        this.id = id;
        this.content = content;
        this.restaurant = restaurant;
        this.user = user;
        this.rate = rate;
        this.visible = true;
        this.reviewType = reviewType;
    }

    public void updateReview(String content, Double rate) {
        this.content = content;
        this.rate = rate;
    }

    public void updateReviewType(ReviewType reviewType) { this.reviewType = reviewType; }

    public void updateVisible(boolean visible) { this.visible = visible; }
}
