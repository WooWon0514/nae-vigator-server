package com.naevigator.nae_vigator_server;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;

public class PdfGenerationTest {

    public static void main(String[] args) {
        String aiGeneratedText = "네이버 클라우드의 HyperCLOVA AI를 연동하는 과정에서 가장 큰 기술적 난관은, 사용자의 경험 데이터를 AI가 이해할 수 있는 프롬프트로 가공하는 것이었습니다. 이는 데이터의 형태와 성격이 다양하기 때문에, 적절한 변환 방법을 찾기 어려웠습니다.\n" +
                "\n" +
                "이를 해결하기 위해, 저는 데이터 전처리에 대한 이론과 실무 경험을 바탕으로 다양한 변환 방법을 시도했습니다. 예를 들어, 텍스트 데이터의 경우 자연어 처리 기술을 활용하여 토큰화, 정규화, 품사 태깅 등을 수행했고, 이미지 데이터의 경우 딥러닝 모델을 활용하여 특징 추출 및 분류를 수행했습니다.\n" +
                "\n" +
                "이러한 시도를 통해, 사용자의 경험 데이터를 AI가 이해할 수 있는 형태로 가공하는 데 성공했습니다. 이를 통해, 스위프 웹 11기 프로젝트의 AI 자기소개서 생성 플랫폼의 성능을 크게 향상시킬 수 있었습니다.";
        String destinationFile = "MyResume.pdf";

        try {
            PdfWriter writer = new PdfWriter(destinationFile);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(
                    "C:/Windows/Fonts/malgun.ttf",
                    PdfEncodings.IDENTITY_H
            );

            document.setFont(font);
            document.add(new Paragraph(aiGeneratedText));
            document.close();

            System.out.println("PDF 파일 생성 성공! 프로젝트 폴더에서 " + destinationFile + " 파일을 확인하세요.");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("PDF 생성 중 오류 발생: " + e.getMessage());
        }
    }
}