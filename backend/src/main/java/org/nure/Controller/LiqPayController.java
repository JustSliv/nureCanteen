package org.nure.Controller;

import org.nure.dto.LiqPayDTO;
import org.nure.service.LiqPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiqPayController {

   @Autowired
   private final LiqPayService liqPayService;

   public LiqPayController(LiqPayService liqPayService) {
      this.liqPayService = liqPayService;
   }

   @RequestMapping(value = "/api/pay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<String> PayLink(@RequestBody LiqPayDTO liqPayDTO){
      String payLink = liqPayService.generatePayLink(liqPayDTO);
      return ResponseEntity.ok(payLink);
   }

   @RequestMapping(value = "/",method = RequestMethod.POST, produces =  MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<String> checkPay(String data, String signature){
      String sign = liqPayService.checkPay(data);
      if(sign != signature){
         return ResponseEntity.ok("Сигнатура неверна");
      }

      return ResponseEntity.ok("Сигнатура верна");
   }
}
