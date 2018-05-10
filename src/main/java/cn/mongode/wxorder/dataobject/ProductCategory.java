package cn.mongode.wxorder.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 商品类目DAO:data access object(数据访问对象)
 * @author: Mongo
 * @date: 2018/4/24
 * @description: 商品的类目信息：名称、编号
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    
    /* 类目id. */
    @Id
    @GeneratedValue
    private Integer categoryId;
    
    /* 类目名字. */
    private String categoryName;
    
    /* 类目编号. */
    private Integer categoryType;
    
    public ProductCategory() {
    }
    
    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
    
}
