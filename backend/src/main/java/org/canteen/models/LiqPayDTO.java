package org.canteen.models;

public class LiqPayDTO {
   private String action;

   private int amount;

   private String currency;

   private String description;

   private String order_id;


   public LiqPayDTO(String action, int amount, String currency, String description, String order_id, String version) {
      this.action = action;
      this.amount = amount;
      this.currency = currency;
      this.description = description;
      this.order_id = order_id;
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
      return order_id;
   }

   public void setOrder_id(String order_id) {
      this.order_id = order_id;
   }

}
