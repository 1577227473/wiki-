package com.Knowledge.wiki.controller;

import com.Knowledge.wiki.req.EbookQueryReq;
import com.Knowledge.wiki.req.EbookSaveReq;
import com.Knowledge.wiki.resp.CommonResp;
import com.Knowledge.wiki.resp.EbookQueryResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;


    @GetMapping("/list")
    public CommonResp list(EbookQueryReq ebookQueryReq) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(ebookQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq ebookReq) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(ebookReq);
        return resp;
    }

}
