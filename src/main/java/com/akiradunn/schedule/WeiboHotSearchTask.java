package com.akiradunn.schedule;

import com.akiradunn.config.MailConfig;
import com.akiradunn.db.model.Record;
import com.akiradunn.db.service.IRecordService;
import com.akiradunn.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author akiradunn
 * @since 2020/8/27 17:46
 */
@Slf4j
@Component
public class WeiboHotSearchTask {

    @Autowired
    private IRecordService recordService;

    public void execute(MailConfig mailConfig) {

        //1.获取热搜榜单列表
        Elements hotSearchElements = getHotSearchElements(mailConfig);
        if (hotSearchElements == null) {
            return;
        }

        //2.找到包含热搜关键字的条款，并解析dom结构拿到热搜的网页跳转链接
        boolean isSend = false;
        StringBuffer stringBuffer = new StringBuffer(mailConfig.getMail_content_head());
        List<Record> records = recordService.selectAll();
        Set<String> newRecordSet = new HashSet<>();
        Set<String> hotSearchTitleSet = records.stream().map(Record::getTitle).collect(Collectors.toSet());
        for (Element element : hotSearchElements) {
            String title = element.text();
            if (title != null && title.contains(mailConfig.getWeibo_hotsearch_keyword()) && !hotSearchTitleSet.contains(title)) {
                newRecordSet.add(title);
                String address = element.attr("abs:href");
                stringBuffer.append("*").append(title).append(",热搜地址是：");
                stringBuffer.append(address).append("</br>");
                isSend = true;
            }
        }

        //3.建立邮件服务，拼凑邮件内容发送邮件给配置收件人（读取配置文件）
        if (isSend) {
            pushMail(mailConfig, stringBuffer, newRecordSet);
        } else {
            log.info("暂时无关注的热搜需要邮件推送...");
        }
    }

    /**
     * 获取热搜榜单列表
     *
     * @param mailConfig
     * @return 热搜榜单列表
     * @date 2020/8/27 17:51
     */
    private Elements getHotSearchElements(MailConfig mailConfig) {
        Elements elements = null;
        try {
            Document doc = Jsoup.connect(mailConfig.getWeibo_hotsearch_url()).get();
            elements = doc.select("td.td-02 > a");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("微博热搜获取出错,原因为:", e);
        }
        return elements;
    }

    /**
     * 推送热搜邮件
     *
     * @param mailConfig
     * @param stringBuffer
     * @param newRecordSet
     * @return
     * @date 2020/8/27 17:46
     */
    private void pushMail(MailConfig mailConfig, StringBuffer stringBuffer, Set<String> newRecordSet) {
        log.info("开始邮件发送...");
        try {
            List<Record> newRecords = newRecordSet.stream().map(t -> new Record(t)).collect(Collectors.toList());
            recordService.batchInsert(newRecords);
            MailUtils.sendMail("邮件提醒", stringBuffer.toString(), mailConfig);
            log.info("已有热搜信息邮件推送成功...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送出错", e);
        }
        log.info("结束邮件发送...");
    }
}
