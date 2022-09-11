package com.itemservice.domain.item;

import lombok.Data;

//@Data // Data Lombok 위험한 Lombok 임 예측하지 못하는 상황이 나옴
//@Getter @Setter
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // int 로 사용 시 0으로라도 무조건 들어가야함 null 허용하지않음
    private Integer quantity;

    public Item() {
    }

    //id 제외한 생성자
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
