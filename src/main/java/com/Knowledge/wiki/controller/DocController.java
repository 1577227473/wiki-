package com.Knowledge.wiki.controller;

import com.Knowledge.wiki.req.DocQueryReq;
import com.Knowledge.wiki.req.DocSaveReq;
import com.Knowledge.wiki.resp.DocQueryResp;
import com.Knowledge.wiki.resp.CommonResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;


    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }
    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq docQueryReq) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(docQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq docReq) {
        CommonResp resp = new CommonResp<>();
        docService.save(docReq);
        return resp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp resp = new CommonResp<>();
        List<String> list=Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }

}
