package org.nure.dto;

public class LiqPayDTO {
   private String action;

   private int amount;

   private String currency;

   private String description;

   private String orderId;


   public LiqPayDTO(String action, int amount, String currency, String description, String orderId, String version) {
      this.action = action;
      this.amount = amount;
      this.currency = currency;
      this.description = description;
      this.orderId = orderId;
   }

   public LiqPayDTO() {
   }

   public String getAction() {
      return action;
   }

   public void setAction(String pay) {
      this.action = pay;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public String getCurrency() {
      return currency;
   }

   public void setCurrency(String currency) {
      this.currency = currency;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getOrder_id() {
      return orderId;
   }

   public void setOrder_id(String orderId) {
      this.orderId = orderId;
   }

}
