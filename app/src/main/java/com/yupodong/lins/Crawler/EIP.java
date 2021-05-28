package com.yupodong.lins.Crawler;

import com.yupodong.lins.DTO.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class EIP {
    protected List<crawling> EIPList;

    public List<crawling> crawlingsEIP() {
        try {
            EIPList = new ArrayList<crawling>();

            // 크롤링할 TOPCIT 시험일정 URL
            String urlEIP = "http://www.q-net.or.kr/crf021.do?id=crf02101s03&IMPL_YY=2021&SERIES_CD=03";
            // TOPCIT의 시험일을 담을 List
            List<String> licenseDateEIP = new ArrayList<String>();
            // TOPCIT의 접수기간을 담을 List
            List<String> applyPeriodEIP = new ArrayList<String>();

            // TOPCIT 시험일정 페이지를 Connection 객체를 통해 접속
            Document document = Jsoup.connect(urlEIP).get();
            // 해당 Selector에 해당하는 HTML tag 가져오기
            Elements elements = document.select(".webCont tbody tr");

            // TOPCIT의 시험일과 접수기간을 각각의 list에 저장
            for (int i = 0; i < 3; i++) {
                // 시험일정이 있는 tag의 text 가져오기
                licenseDateEIP.add(elements.get(i).select("td:nth-child(3)").text());
                // 접수기간이 있는 tag의 text 가져오기
                applyPeriodEIP.add(elements.get(i).select("td:nth-child(2)").text());
                applyPeriodEIP.set(i, applyPeriodEIP.get(i).replace(" ", " ~ "));
            }

            // TestCode
            for (int i = 0; i < licenseDateEIP.size(); i++) {
                System.out.println(licenseDateEIP.get(i));
                System.out.println(applyPeriodEIP.get(i));
                System.out.println("-------------------------------------------------------------------------------------------------------------------");
            }

            for(int i = 0; i < licenseDateEIP.size(); i++ ) {
                // 정보처리기사의 각 시험일정을 임시적으로 저장할 객체 생성
                crawling newItem = new crawling();
                
                // i번째 자격증 일정을 toeic 객체에 저장
                newItem.setLicenseID(i+1);
                newItem.setLicenseName("TOEIC");
                newItem.setLicenseDate(licenseDateEIP.get(i));
                newItem.setApplyPeriod(applyPeriodEIP.get(i));
                newItem.setLicenseLink(urlEIP);

                EIPList.add(newItem);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        return EIPList;
    }
}
