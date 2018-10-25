package com.pwai.ocr.baidu;

import com.baidu.aip.ocr.AipOcr;
import com.pwai.ocr.common.OCRConstant;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by nanxi on 2018/10/19.
 */
public class OCRBaiduUtils {

    public static String ocrBankCard(String filePath) {
        StringBuffer result = new StringBuffer();
        AipOcr aipOcr = new AipOcr(OCRConstant.APPID_BAIDU, OCRConstant.APIKEY_BAIDU, OCRConstant.SECRETKEY_BAIDU);
        aipOcr.setConnectionTimeoutInMillis(2000);
        aipOcr.setSocketTimeoutInMillis(60000);
        JSONObject res = aipOcr.bankcard(filePath, new HashMap<String, String>());
        String aipResultStr = res.toString();
        System.out.println(aipResultStr);
        if (aipResultStr.indexOf("error_code") != -1) {
            result.append("error_code").append("：").append(res.get("error_code")).append("\n");
        }
        if (aipResultStr.indexOf("error_msg") != -1) {
            result.append("error_msg").append("：").append(res.get("error_msg")).append("\n");
        }

        JSONObject resultJson = null;
        try {
            resultJson = (JSONObject) res.get("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
         *  bank_card_type（银行卡类型，0:不能识别; 1: 借记卡; 2: 信用卡）
         */
        if (resultJson != null) {
            if (aipResultStr.indexOf("bank_card_number") != -1) {
                result.append("bank_card_number").append("：").append(resultJson.get("bank_card_number")).append("\n");
            }
            if (aipResultStr.indexOf("bank_name") != -1) {
                result.append("bank_name").append("：").append(resultJson.get("bank_name")).append("\n");
            }
            if (aipResultStr.indexOf("valid_date") != -1) {
                result.append("valid_date").append("：").append(resultJson.get("valid_date")).append("\n");
            }
            if (aipResultStr.indexOf("bank_card_type") != -1) {
                int bank_card_type = (int) resultJson.get("bank_card_type");
                if (bank_card_type == 0) {
                    result.append("bank_card_type").append("：").append("不能识别").append("\n");
                } else if (bank_card_type == 1) {
                    result.append("bank_card_type").append("：").append("借记卡").append("\n");
                } else if (bank_card_type == 2) {
                    result.append("bank_card_type").append("：").append("信用卡").append("\n");
                } else {
                    result.append("bank_card_type").append("：").append(bank_card_type).append("\n");
                }
            }
        }
        return result.toString();
    }
}
