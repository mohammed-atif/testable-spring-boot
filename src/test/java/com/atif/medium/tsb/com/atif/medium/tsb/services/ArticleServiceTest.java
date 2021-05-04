/*
 * Copyright 2021 mohammed Atif
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.atif.medium.tsb.com.atif.medium.tsb.services;

import com.atif.medium.tsb.dto.ArticleDTO;
import com.atif.medium.tsb.mappers.ArticleMapper;
import com.atif.medium.tsb.models.ArticleModel;
import com.atif.medium.tsb.repositories.ArticleDAO;
import com.atif.medium.tsb.services.ArticleService;
import com.atif.medium.tsb.services.impl.ArticleServiceImpl;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ArticleServiceTest {

    private static final String VALID_ID = "valid_id";
    private static final String INVALID_ID = "invalid_id";

    private final ArticleService articleService;

    public ArticleServiceTest() {
        ArticleModel articleModel = new ArticleModel();
        articleModel.setId(VALID_ID);

        ArticleDAO articleDAO = Mockito.mock(ArticleDAO.class);

        // Defining the rule and behaviour for the findById
        when(articleDAO.findById(VALID_ID)).thenReturn(Optional.of(articleModel));
        when(articleDAO.findById(INVALID_ID)).thenReturn(Optional.empty());

        // Defining the rule and behaviour for the findByUserId
        when(articleDAO.findByUserId(VALID_ID)).thenReturn(Collections.singletonList(articleModel));
        when(articleDAO.findByUserId(INVALID_ID)).thenReturn(Collections.emptyList());

        ArticleMapper articleMapper = Mappers.getMapper(ArticleMapper.class);
        articleService = new ArticleServiceImpl(articleDAO, articleMapper);
    }

    @Test
    public void testGetArticle(){
        Optional<ArticleDTO> validArticleDTO = articleService.getArticle(VALID_ID);
        assertTrue(validArticleDTO.isPresent());
        assertEquals(VALID_ID, validArticleDTO.get().getId());

        Optional<ArticleDTO> invalidArticleDTO = articleService.getArticle(INVALID_ID);
        assertFalse(invalidArticleDTO.isPresent());
    }

    @Test
    public void testGetArticleForUser(){
        List<ArticleDTO> articleDTOList = articleService.getAllArticles(VALID_ID);
        assertEquals(1, articleDTOList.size());

        articleDTOList = articleService.getAllArticles(INVALID_ID);
        assertTrue(articleDTOList.isEmpty());
    }

}
