package com.Knowledge.wiki.service;


import com.Knowledge.wiki.domain.Ebook;
import com.Knowledge.wiki.domain.EbookExample;
import com.Knowledge.wiki.mapper.EbookMapper;
import com.Knowledge.wiki.req.EbookReq;
import com.Knowledge.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {


    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq ebookReq)
    {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+ebookReq.getName()+"%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook,ebookResp);
            respList.add(ebookResp);
        }
        return respList;
    }
}
