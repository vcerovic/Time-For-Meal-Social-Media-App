package com.veljkocerovic.timeformeal.api.recipe.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCommentRepository extends JpaRepository<RecipeComment, RecipeCommentKey> {
}
