package com.yupodong.lins.Crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TOPCIT {
    public static void main(String[] args ) {
        try {
            // 크롤링할 TOPCIT 시험일정 URL
            String urlTOPCIT = "https://www.topcit.or.kr/receipt/evalList.do";
            // TOPCIT의 시험일을 담을 List
            List<String> licenseDateTOPCIT = new ArrayList<String>();
            // TOPCIT의 접수기간을 담을 List
            List<String> applyPeriodTOPCIT = new ArrayList<String>();

            // TOPCIT 시험일정 페이지를 Connection 객체를 통해 접속
            Document document = Jsoup.connect(urlTOPCIT).get();
            // 해당 Selector에 해당하는 HTML tag 가져오기
            Elements elements = document.select(".table-scroll table tbody tr");

            System.out.println(document);

            // TOPCIT의 시험일과 접수기간을 각각의 list에 저장
            for (int i = 0; i < elements.size(); i++) {
                // 시험일정이 있는 tag의 text 가져오기
                licenseDateTOPCIT.add(elements.get(i).select("td:nth-child(3)").text());
                // 접수기간이 있는 tag의 text 가져오기
                applyPeriodTOPCIT.add(elements.get(i).select("td:nth-child(4)").text());
            }

            // TestCode
            for (int i = 0; i < licenseDateTOPCIT.size(); i++) {
                System.out.println(licenseDateTOPCIT.get(i));
                System.out.println(applyPeriodTOPCIT.get(i));
                System.out.println("-------------------------------------------------------------------------------------------------------------------");
            }

            /* Firebase에 해당 정보를 저장하기 위한 부분
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            crawling toeic = new crawling();

            for(int i = 0; i < licenseDate.size(); i++ ) {
                // i번째 자격증 일정을 toeic 객체에 저장
                toeic.setLicenseName("TOEIC");
                toeic.setLicenseDate(licenseDate.get(i));
                toeic.setApplyPeriod(applyPeriod.get(i));
                toeic.setLicenseLink(url);

                firestore.collection("TOEIC").document(Integer.toString(i)).set(toeic)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("성공");
                            }
                });
            }*/
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
