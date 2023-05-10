package com.Knowledge.wiki;

import com.Knowledge.wiki.config.WikiApplication;
import com.Knowledge.wiki.domain.Ebook;
import com.Knowledge.wiki.domain.EbookExample;
import com.Knowledge.wiki.mapper.EbookMapper;
import com.Knowledge.wiki.resp.EbookQueryResp;
import com.Knowledge.wiki.util.CopyUtil;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = WikiApplication.class)
@ContextConfiguration
@ComponentScan("com.Knowledge")
@MapperScan("com.Knowledge.wiki.mapper")
class WikiApplicationTests {

    @Resource
    private EbookMapper ebookMapper;

    @Test
    public void listByViewCount() {
        EbookExample ebookExample = new EbookExample();
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        Collections.sort(ebookList);
        List<Ebook> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list1.add(ebookList.get(i));
        }

        List<EbookQueryResp> list = CopyUtil.copyList(list1, EbookQueryResp.class);

        System.out.println(list);

    }
}
