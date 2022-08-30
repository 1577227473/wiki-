package com.Knowledge.wiki.service;


import com.Knowledge.wiki.domain.Category;
import com.Knowledge.wiki.domain.CategoryExample;
import com.Knowledge.wiki.mapper.CategoryMapper;
import com.Knowledge.wiki.req.CategoryQueryReq;
import com.Knowledge.wiki.req.CategorySaveReq;
import com.Knowledge.wiki.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq categoryQueryReq)
    {

        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        PageHelper.startPage(categoryQueryReq.getPage(), categoryQueryReq.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo=new PageInfo<>(categoryList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
//            //CategoryResp categoryResp = new CategoryResp();
//            //BeanUtils.copyProperties(category,categoryResp);
//            //对象复制
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            respList.add(categoryResp);
//        }

        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
    public void save(CategorySaveReq req){
        Category category=CopyUtil.copy(req,Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //Id为空，则新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            //Id不为空，则更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
