package com.Knowledge.wiki.mapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface DocMapperCust {

    public void increaseViewCount(@Param("id") Long id);
    public void increaseVoteCount(@Param("id") Long id);
}