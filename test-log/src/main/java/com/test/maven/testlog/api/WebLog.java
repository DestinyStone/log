package com.test.maven.testlog.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: zhouxiaofeng
 * @create: 2021-04-12 17:03
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WebLog {
    private String id;

    @ApiModelProperty("请求类型")
    private String type;

    @ApiModelProperty("全路径")
    private String url;

    @ApiModelProperty("根路径")
    private String basePath;

    @ApiModelProperty("URI")
    private String uri;

    @ApiModelProperty("参数")
    private List<Map<String, Object>> params;

    @ApiModelProperty("操作描述")
    private String description;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("过程消耗时间")
    private Date processTime;

    @ApiModelProperty("是否成功返回")
    private boolean isSuccessResult;

    @ApiModelProperty("返回结果")
    private Object result;

    @ApiModelProperty("异常")
    private Map<String, Object> exception;

    @ApiModelProperty("调用者的ip")
    private String ip;
}
    