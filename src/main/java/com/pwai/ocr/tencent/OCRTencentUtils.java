package com.pwai.ocr.tencent;

import com.pwai.ocr.common.OCRConstant;
import com.qcloud.image.ImageClient;
import com.qcloud.image.request.OcrBankCardRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by nanxi on 2018/10/18.
 */
public class OCRTencentUtils {

    public static String ocrBankCard(String filePath) {
        StringBuffer result = new StringBuffer();
//        Map<String, String> resultMap = new HashMap<>();
        ImageClient imageClient = new ImageClient(OCRConstant.APPID_TENCENT,
                OCRConstant.SECRETID_TENCENT, OCRConstant.SECRETKEY_TENCENT,
                ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com/*根据文档说明选择域名*/);
        OcrBankCardRequest request = new OcrBankCardRequest(OCRConstant.BUCKETNAME_TENCENT, new File(filePath));
        String ret = imageClient.ocrBankCard(request);
        System.out.println("ocrBankCard:" + ret);
        JSONObject json = new JSONObject(ret);
        result.append("code").append("：").append(json.get("code")).append("\n")
                .append("message").append("：").append(json.get("message")).append("\n");

        JSONObject data = (JSONObject) json.get("data");
        JSONArray items = data.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = (JSONObject) items.get(i);
            System.out.println(item.get("item") + "：" + item.get("itemstring"));
            result.append(item.get("item")).append("：").append(item.get("itemstring")).append("\n");
//            resultMap.put((String) item.get("item"), (String) item.get("itemstring"));
        }
        return result.toString();
    }
}
