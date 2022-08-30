package com.Knowledge.wiki.controller;

import com.Knowledge.wiki.req.CategoryQueryReq;
import com.Knowledge.wiki.req.CategorySaveReq;
import com.Knowledge.wiki.resp.CommonResp;
import com.Knowledge.wiki.resp.CategoryQueryResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq categoryQueryReq) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(categoryQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq categoryReq) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(categoryReq);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }

}
