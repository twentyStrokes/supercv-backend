package com.xzgedu.supercv.llm;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PdfParser {

    public static String parsePdfFromFile(String filePath) throws IOException {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            if (document.isEncrypted()) {
                throw new IOException("加密的PDF文件暂不支持");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public static String parsePdfFromUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(5000); // 5秒连接超时
        connection.setReadTimeout(30000); // 30秒读取超时
        try (
                InputStream inputStream = connection.getInputStream();
                PDDocument document = Loader.loadPDF(inputStream.readAllBytes())
        ) {
            if (document.isEncrypted()) {
                throw new IOException("加密的PDF文件暂不支持");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public static void main(String[] args) {
        try {
            String fileUrl = "https://static.supercv.cn/pdf/test_1745211241263.pdf";
            String text = parsePdfFromUrl(fileUrl);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String filePath = "/Users/wangzheng/Desktop/000/test.pdf";
            String text = parsePdfFromFile(filePath);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}