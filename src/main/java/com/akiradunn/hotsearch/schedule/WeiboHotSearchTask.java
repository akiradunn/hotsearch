package com.akiradunn.hotsearch.schedule;
import com.akiradunn.hotsearch.config.MailConfig;
import com.akiradunn.hotsearch.dao.RecordMapper;
import com.akiradunn.hotsearch.pojo.Record;
import com.akiradunn.hotsearch.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@Component
@Slf4j
public class WeiboHotSearchTask {

    @Autowired
    private RecordMapper recordMapper;

    public void execute(MailConfig mailConfig) {
        Document doc = null;
        try {
            doc = Jsoup.connect(mailConfig.getWeibo_hotsearch_url()).get();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("微博热搜地址请求出错",e);
        }
        //2.解析dom结构，提取出热搜前十
        Elements hotSearchElements = doc.select("td.td-02 > a");
        StringBuffer stringBuffer = new StringBuffer(mailConfig.getMail_content_head());
        Boolean isSend = false;
        //3.找到包含热搜关键字的条款，并解析dom结构拿到热搜的网页跳转链接
        List<Record> records = recordMapper.selectAll();
        Set<String> newRecordSet = new HashSet<>();
        Set<String> hotSearchTitleSet = records.stream().map(Record::getTitle).collect(Collectors.toSet());
        for (Element element : hotSearchElements) {
            String title = element.text();
            if(title != null && title.contains(mailConfig.getWeibo_hotsearch_keyword()) && !hotSearchTitleSet.contains(title) ){
                newRecordSet.add(title);
                String address = element.attr("abs:href");
                stringBuffer.append("*").append(title).append(",热搜地址是：");
                stringBuffer.append(address).append("</br>");
                isSend = true;
            }
        }
        //4.建立邮件服务，拼凑邮件内容发送邮件给配置收件人（读取配置文件）
        try {
            log.info("开始邮件发送...");
            if(isSend){
                List<Record> newRecords = newRecordSet.stream().map(t -> new Record(t)).collect(Collectors.toList());
                recordMapper.batchInsert(newRecords);
                MailUtils.sendMail("邮件提醒",stringBuffer.toString(),mailConfig);
                log.info("已有热搜信息邮件推送成功...");
            }else{
                log.info("暂时无关注的热搜需要邮件推送...");
            }
            log.info("结束邮件发送...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送出错",e);
        }
    }
}
