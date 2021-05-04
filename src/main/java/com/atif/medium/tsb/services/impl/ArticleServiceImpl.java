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

package com.atif.medium.tsb.services.impl;

import com.atif.medium.tsb.dto.ArticleDTO;
import com.atif.medium.tsb.mappers.ArticleMapper;
import com.atif.medium.tsb.models.ArticleModel;
import com.atif.medium.tsb.repositories.ArticleDAO;
import com.atif.medium.tsb.services.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDAO articleDAO;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleDAO articleDAO, ArticleMapper articleMapper) {
        this.articleDAO = articleDAO;
        this.articleMapper = articleMapper;
    }

    @Override
    public Optional<ArticleDTO> getArticle(String id) {
        Optional<ArticleModel> articleModelOptional = articleDAO.findById(id);
        ArticleDTO articleDTO = articleModelOptional.map(articleMapper::convertToDTO)
                .orElse(null);
        return Optional.ofNullable(articleDTO);
    }

    @Override
    public List<ArticleDTO> getAllArticles(String userId) {
        List<ArticleModel> articleModelList = articleDAO.findByUserId(userId);
        return articleModelList.stream()
                .map(articleMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
