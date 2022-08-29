package com.Knowledge.wiki.controller;

import com.Knowledge.wiki.req.EbookReq;
import com.Knowledge.wiki.resp.CommonResp;
import com.Knowledge.wiki.resp.EbookResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;


    @GetMapping("/list")
    public CommonResp list(EbookReq ebookReq) {
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(ebookReq);
        resp.setContent(list);
        return resp;
    }

}
