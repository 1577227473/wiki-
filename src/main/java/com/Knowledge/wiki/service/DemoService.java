package com.Knowledge.wiki.service;


import com.Knowledge.wiki.domain.Demo;
import com.Knowledge.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {


    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list()
    {
        return demoMapper.selectByExample(null);
    }
}
