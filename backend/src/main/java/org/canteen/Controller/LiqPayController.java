package org.canteen.Controller;

import org.canteen.LiqPay.LiqPay;
import org.canteen.models.Basket;
import org.canteen.models.LiqPayDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/pay")
public class LiqPayController {

   private static String PUBLIC_KEY = "sandbox_i95837290635";
   private static String PRIVATE_KEY = "sandbox_NKlu2qpGVIvAZU7ylrbusD1phJFGTeW3YIZYMQwl";

   @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<String> saveProduct(@RequestBody LiqPayDTO liqPayDTO){
      HttpHeaders headers = new HttpHeaders();
      HashMap<String, String> params = new HashMap<String, String>();
      params.put("action", liqPayDTO.getAction());
      params.put("amount", String.valueOf(liqPayDTO.getAmount()));
      params.put("currency", liqPayDTO.getCurrency());
      params.put("description", liqPayDTO.getDescription());
      params.put("order_id", liqPayDTO.getOrder_id());
      params.put("version", "3");

      LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
      String html = liqpay.cnb_form(params);
      return new ResponseEntity<>(html, headers, HttpStatus.CREATED);
   }
}
