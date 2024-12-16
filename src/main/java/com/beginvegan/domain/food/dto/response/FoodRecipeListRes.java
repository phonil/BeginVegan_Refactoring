package com.beginvegan.domain.food.dto.response;

import com.beginvegan.domain.food.dto.FoodIngredientDto;
import com.beginvegan.domain.user.domain.VeganType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FoodRecipeListRes {

    private Long id;

    private String name;

    private VeganType veganType;

    private List<FoodIngredientDto> ingredients;

    private Boolean isBookmarked;



    @Builder
    public FoodRecipeListRes(Long id, String name, VeganType veganType, List<FoodIngredientDto> ingredients, Boolean isBookmarked) {
        this.id = id;
        this.name = name;
        this.veganType = veganType;
        this.ingredients = ingredients;
        this.isBookmarked = isBookmarked;
    }
}
