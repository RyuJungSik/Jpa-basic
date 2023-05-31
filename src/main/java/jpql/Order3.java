package jpql;

import javax.persistence.*;

@Entity
@Table(name="ORDERS3")
public class Order3 {

@Id
@GeneratedValue
private Long id;

private int orderAmount;

@Embedded
private Address3 address3;

@ManyToOne
@JoinColumn(name = "PRODUCT_ID")
private Product3 product;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public int getOrderAmount() {
    return orderAmount;
}

public void setOrderAmount(int orderAmount) {
    this.orderAmount = orderAmount;
}

public Address3 getAddress3() {
    return address3;
}

public void setAddress3(Address3 address3) {
    this.address3 = address3;
}

public Product3 getProduct() {
    return product;
}

public void setProduct(Product3 product) {
    this.product = product;
}
}
