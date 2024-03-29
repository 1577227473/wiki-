package com.Knowledge.wiki.service;


import com.Knowledge.wiki.domain.Ebook;
import com.Knowledge.wiki.domain.EbookExample;
import com.Knowledge.wiki.mapper.EbookMapper;
import com.Knowledge.wiki.req.EbookQueryReq;
import com.Knowledge.wiki.req.EbookSaveReq;
import com.Knowledge.wiki.resp.EbookQueryResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.util.CopyUtil;
import com.Knowledge.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq ebookQueryReq)
    {

        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(ebookQueryReq.getName())){
            criteria.andNameLike("%"+ ebookQueryReq.getName()+"%");
        }
        if(!ObjectUtils.isEmpty(ebookQueryReq.getCategoryId2())){
            criteria.andCategory2IdEqualTo(ebookQueryReq.getCategoryId2());
        }
        PageHelper.startPage(ebookQueryReq.getPage(), ebookQueryReq.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo=new PageInfo<>(ebookList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//            //EbookResp ebookResp = new EbookResp();
//            //BeanUtils.copyProperties(ebook,ebookResp);
//            //对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }

        //列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
    public void save(EbookSaveReq req){
        Ebook ebook=CopyUtil.copy(req,Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //Id为空，则新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            //Id不为空，则更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }

    public List<EbookQueryResp> listByViewCount() {
        EbookExample ebookExample = new EbookExample();
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        Collections.sort(ebookList);

        List<Ebook> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list1.add(ebookList.get(i));
        }

        List<EbookQueryResp> list = CopyUtil.copyList(list1, EbookQueryResp.class);

        return list;
    }

    public List<EbookQueryResp> listByVoteCount() {
        EbookExample ebookExample = new EbookExample();
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookQueryResp> list1 = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        Collections.sort(list1);

        List<EbookQueryResp> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(list1.get(i));
        }

        return list;
    }


}
