package com.Knowledge.wiki.service;


import com.Knowledge.wiki.mapper.TestMapper;
import com.Knowledge.wiki.domain.test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {


    @Resource
    private TestMapper testMapper;

    public List<test> list()
    {
        return testMapper.list();
    }
}
