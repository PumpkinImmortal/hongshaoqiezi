package com.pwai.ocr.servlet;

import com.pwai.ocr.baidu.OCRBaiduUtils;
import com.pwai.ocr.common.OCRConstant;
import com.pwai.ocr.hehe.OCRHeheUtils;
import com.pwai.ocr.tencent.OCRTencentUtils;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by nanxi on 2018/10/18.
 */
public class OCRRecognizeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectOCR = req.getParameter("selectOCR");
        String filePath = req.getParameter("filePath");

        // 进行识别
        long l1 = System.currentTimeMillis();
        String ocrResult = "";
        if (OCRConstant.OPTION_TENCENT.equals(selectOCR)) {
            ocrResult = OCRTencentUtils.ocrBankCard(filePath);
        } else if (OCRConstant.OPTION_BAIDU.equals(selectOCR)) {
            ocrResult = OCRBaiduUtils.ocrBankCard(filePath);
        } else if (OCRConstant.OPTION_HEHE.equals(selectOCR)) {
            ocrResult = OCRHeheUtils.ocrBankCard(filePath);
        }
        long l2 = System.currentTimeMillis();
        String result = "识别用时：" + (l2 - l1) / 1000.0 + "秒";
        System.out.println(result);
        result = result + "\n\n" + ocrResult;

        resp.setContentType("text/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("result", result);
        out.print(jsonobj);
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
