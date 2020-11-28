package org.canteen.Repositories.dto;

public interface BasketDtoRepo {

   public Long getBasket_id();
   public Long getProduct_id();
   public String getName();
   public String getCategory();
   public String getDescription();
   public int getPrice();
   public int getCount();
}
