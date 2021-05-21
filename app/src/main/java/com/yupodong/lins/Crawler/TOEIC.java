package com.yupodong.lins.Crawler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yupodong.lins.DTO.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TOEIC {
    public static void main(String[] args) {
        try{
            // 크롤링할 TOEIC 시험일정 URL 
            String url = "https://exam.toeic.co.kr/receipt/examSchList.php";
            // TOEIC의 시험일을 담을 List
            List<String> licenseDate = new ArrayList<String>();
            // TOEIC의 접수기간을 담을 List
            List<String> applyPeriod = new ArrayList<String>();

            // TOEIC 시험일정 페이지를 Connection 객체를 통해 접속
            Document document = Jsoup.connect(url).get();
            // 해당 Selector에 해당하는 HTML tag 가져오기
            Elements elements = document.select(".link_list tbody tr");

            // TOEIC의 시험일과 접수기간을 각각의 list에 저장
            for (int i = 0; i < elements.size(); i++) {
                int splitEndIndex = 0;
                // 시험일정이 있는 tag의 text 가져오기
                licenseDate.add(elements.get(i).select("td:nth-child(2)").text());
                applyPeriod.add(elements.get(i).select("td:nth-child(4)").text());
                
                // 특별 추가접수가 진행되는 일정 부분 삭제하기
                splitEndIndex = applyPeriod.get(i).indexOf("특별추가 : ");
                applyPeriod.set(i, applyPeriod.get(i).substring(splitEndIndex));
                
                // `정기접수 : ` 문자열 제거하기
                applyPeriod.set(i, applyPeriod.get(i).replace("정기접수 : ", ""));
            }

            // TestCode
            for (int i = 0; i < licenseDate.size(); i++) {
                System.out.println(licenseDate.get(i));
                System.out.println(applyPeriod.get(i));
                System.out.println("-------------------------------------------------------------------------------------------------------------------");
            }

            /* Firebase에 해당 정보를 저장하기 위한 부분 */
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
            }

        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}
