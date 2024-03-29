package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.product.CreateProductVO;
import com.nju.edu.erp.model.vo.product.ProductInfoVO;

import java.util.List;

public interface ProductService {

    /**
     * 新增一个商品
     * @param inputVO
     * @return productInfoVO
     */
    ProductInfoVO createProduct(CreateProductVO inputVO);

    /**
     * 修改商品信息
     * @param productInfoVO
     * @return productInoVO
     */
    ProductInfoVO updateProduct(ProductInfoVO productInfoVO);

    /**
     * 获取全部商品信息
     */
    List<ProductInfoVO> queryAllProduct();

    /**
     * 删除某商品
     * @param id
     */
    void deleteById(String id);


    /**
     * 通过pid获取商品详情
     * @param pid 商品id
     * @return 商品详情
     */
    ProductInfoVO getOneProductByPid(String pid);
}
