package com.ll.project_13_backend.test_security;

public class memo {
    //todo 이것을 참고하여 값 모두를 검사하자

//    @DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
//    @Test
//    void createProductWithoutType() throws Exception {
//        // given
//        ProductCreateRequest request = ProductCreateRequest.builder()
//                .sellingStatus(ProductSellingStatus.SELLING)
//                .name("아메리카노")
//                .price(4000)
//                .build();
//
//        // when // then
//        mockMvc.perform(
//                        post("/api/v1/products/new")
//                                .content(objectMapper.writeValueAsString(request))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
//                .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
//                .andExpect(jsonPath("$.data").isEmpty())
//        ;
//    }
}
