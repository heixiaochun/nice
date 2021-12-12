package com.example.nice.controller;

import com.example.nice.controller.base.BaseController;
import com.example.nice.dto.DemoDTO;
import com.example.nice.dto.DemoPostDTO;
import com.example.nice.dto.JsonResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * 测试或有演示用的DemoController
 *
 * @author heixiaochun
 */
@Api(tags = "商品品牌管理")
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    /**
     * 简单的Get请求示例
     */
    @ApiOperation("演示>简单查看>查看卡片信息")
    @GetMapping("/simple-get/{accountNo}")
    public JsonResponseDTO simpleGet(@PathVariable String accountNo,
                                     @RequestParam String cardNo) {
        logger.info("开始simpleGet处理");
        DemoDTO demoDTO = new DemoDTO();
        demoDTO.setAccountNo(StringUtils.trim(accountNo));
        demoDTO.setCardNo(StringUtils.trim(cardNo));
        demoDTO.setAccountBalance(RandomUtils.nextInt());
        demoDTO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        logger.info("完成simpleGet处理");
        return this.jsonSuccessResult(demoDTO);
    }

    /**
     * 简单的Post请求示例
     */
    @ApiOperation("演示>简单提交>提交片信息")
    @PostMapping("/simple-post")
    public JsonResponseDTO simplePost(@Valid @RequestBody DemoPostDTO demoPostDTO) {
        logger.info("开始simplePost处理");
        DemoDTO demoDTO = new DemoDTO();
        demoDTO.setAccountNo(StringUtils.trim(demoPostDTO.getAccountNo()));
        demoDTO.setCardNo(StringUtils.trim(demoPostDTO.getCardNo()));
        demoDTO.setAccountBalance(RandomUtils.nextInt());
        demoDTO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        logger.info("完成simplePost处理");
        return this.jsonSuccessResult(demoDTO);
    }

}
