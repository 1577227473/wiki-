package com.Knowledge.wiki.service;


import com.Knowledge.wiki.domain.Content;
import com.Knowledge.wiki.domain.Doc;
import com.Knowledge.wiki.domain.DocExample;
import com.Knowledge.wiki.mapper.ContentMapper;
import com.Knowledge.wiki.mapper.DocMapper;
import com.Knowledge.wiki.req.DocQueryReq;
import com.Knowledge.wiki.req.DocSaveReq;
import com.Knowledge.wiki.resp.DocQueryResp;
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
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<DocQueryResp> list(DocQueryReq docQueryReq)
    {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(docQueryReq.getPage(), docQueryReq.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo=new PageInfo<>(docList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

//        List<DocResp> respList = new ArrayList<>();
//        for (Doc doc : docList) {
//            //DocResp docResp = new DocResp();
//            //BeanUtils.copyProperties(doc,docResp);
//            //对象复制
//            DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//            respList.add(docResp);
//        }

        //列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
    public void save(DocSaveReq req){
        Doc doc=CopyUtil.copy(req,Doc.class);
        Content content = CopyUtil.copy(req,Content.class);
    if(ObjectUtils.isEmpty(req.getId())){
            //Id为空，则新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);

        } else {
            //Id不为空，则更新
            docMapper.updateByPrimaryKey(doc);
            int count=contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

    public String findContent(Long id) {
        Content content=contentMapper.selectByPrimaryKey(id);
        return content.getContent();
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    public List<DocQueryResp> all()
    {

        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        //列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }
}