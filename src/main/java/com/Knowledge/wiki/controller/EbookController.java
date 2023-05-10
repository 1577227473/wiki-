package com.Knowledge.wiki.controller;

import com.Knowledge.wiki.req.EbookQueryReq;
import com.Knowledge.wiki.req.EbookSaveReq;
import com.Knowledge.wiki.resp.CommonResp;
import com.Knowledge.wiki.resp.EbookQueryResp;
import com.Knowledge.wiki.resp.PageResp;
import com.Knowledge.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;


    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq ebookQueryReq) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(ebookQueryReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq ebookReq) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(ebookReq);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

    @GetMapping("/listByViewCount")
    public CommonResp listByViewCount() {
        CommonResp<List<EbookQueryResp>> resp = new CommonResp<>();
        List<EbookQueryResp> list = ebookService.listByViewCount();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/listByVoteCount")
    public CommonResp listByVoteCount() {
        CommonResp<List<EbookQueryResp>> resp = new CommonResp<>();
        List<EbookQueryResp> list = ebookService.listByVoteCount();
        resp.setContent(list);
        return resp;
    }

}
