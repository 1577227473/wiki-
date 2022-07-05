package com.Knowledge.wiki.service;


import com.Knowledge.wiki.domain.Ebook;
import com.Knowledge.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {


    @Resource
    private EbookMapper ebookMapper;

    public List<Ebook> list()
    {
        return ebookMapper.selectByExample(null);
    }
}
