package org.nure.service;

import org.nure.LiqPay.LiqPay;
import org.nure.dto.LiqPayDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LiqPayService {


   private static String PUBLIC_KEY = "sandbox_i95837290635";
   private static String PRIVATE_KEY = "sandbox_NKlu2qpGVIvAZU7ylrbusD1phJFGTeW3YIZYMQwl";

   public String generatePayLink(LiqPayDTO liqPayDTO){
      HashMap<String, String> params = new HashMap<String, String>();
      params.put("action", liqPayDTO.getAction());
      params.put("amount", String.valueOf(liqPayDTO.getAmount()));
      params.put("currency", liqPayDTO.getCurrency());
      params.put("description", liqPayDTO.getDescription());
      params.put("orderId", liqPayDTO.getOrder_id());
      params.put("version", "3");

      LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
      String html = liqpay.cnb_form(params);

      return html;
   }

   public String checkPay(String data){
      LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
      String sign = liqpay.str_to_sign(
         PRIVATE_KEY +
            data +
            PRIVATE_KEY
      );
      return sign;

   }
}
