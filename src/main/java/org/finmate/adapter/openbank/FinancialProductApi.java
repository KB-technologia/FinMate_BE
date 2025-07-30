package org.finmate.adapter.openbank;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.finmate.adapter.mydata.dto.MyDataResponseDTO;
import org.finmate.exception.NotFoundException;
import org.finmate.product.domain.*;
import org.finmate.product.dto.ProductDTO;
import org.finmate.product.mapper.ProductMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FinancialProductApi {

    private final ObjectMapper objectMapper;

    private final ProductMapper productMapper;


    //TODO:주기 설정 바꾸긴해야됨
    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void fetchFinancialProducts() {
        try (InputStream is = getClass().getResourceAsStream("/dummy/products/financial_products.json")) {
            JsonNode jsonNodes = objectMapper.readTree(is);
            for (JsonNode node : jsonNodes) {
                ProductType productType = ProductType.valueOf(node.get("productType").asText());
                ProductDTO<?> dto = null;

                switch (productType) {
                    case FUND -> dto = objectMapper.readValue(node.traverse(), new TypeReference<ProductDTO<FundVO>>() {});
                    case DEPOSIT -> dto = objectMapper.readValue(node.traverse(), new TypeReference<ProductDTO<DepositVO>>() {});
                    case SAVINGS -> dto = objectMapper.readValue(node.traverse(), new TypeReference<ProductDTO<SavingsVO>>() {});
                    default -> throw new IllegalArgumentException("Unknown product type");
                }

                ProductVO productVO = dto.toVO();
                Long id = productMapper.findProductIdByNameAndBankName(productVO.getName(), productVO.getBankName());
                if(id == null) {
                    productMapper.insertProduct(productVO);
                    Long productId = productVO.getId();
                    productVO.getProductRate().setProductId(productId);
                    switch (productType) {
                        case FUND -> {
                            productVO.getFund().setProductId(productId);
                            productMapper.insertFund(productVO.getFund());
                        }
                        case DEPOSIT -> {
                            productVO.getDeposit().setProductId(productId);
                            productMapper.insertDeposit(productVO.getDeposit());
                        }
                        case SAVINGS -> {
                            productVO.getSavings().setProductId(productId);
                            productMapper.insertSavings(productVO.getSavings());
                        }
                    }
                    productMapper.insertProductRate(productVO.getProductRate());
                }
                else {
                    productVO.setId(id);
                    productVO.getProductRate().setProductId(id);
                    productMapper.updateProduct(productVO);
                    switch (productType) {
                        case FUND -> {
                            productVO.getFund().setProductId(id);
                            productMapper.updateFund(productVO.getFund());
                        }
                        case DEPOSIT -> {
                            productVO.getDeposit().setProductId(id);
                            productMapper.updateDeposit(productVO.getDeposit());
                        }
                        case SAVINGS -> {
                            productVO.getSavings().setProductId(id);
                            productMapper.updateSavings(productVO.getSavings());
                        }
                    }
                    productMapper.updateProductRate(productVO.getProductRate());
                }

            }

        } catch (IOException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
