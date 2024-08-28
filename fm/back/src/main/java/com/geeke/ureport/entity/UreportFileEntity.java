package com.geeke.ureport.entity;
import java.util.Date;

import com.geeke.common.persistence.DataEntity;
import com.geeke.org.entity.Company;
import lombok.Data;

/**
 * @PROJECT_NAME: fm
 * @NAME: UreportFileEntity
 * @USER: yuy
 * @DATE: 2023/4/24 15:34
 * @DESCRIPTION: 自定义报表存储器实体
 */

@Data
public class UreportFileEntity {
	private Long id;
	private String name;
	private byte[] content;
	private Company company; // 公司
	private Integer type; // 报表类型（1：利润表，2：资产负债，3：现金流量）
	private Integer status; // 报表状态（0：停用，1：启用）
	private Date createTime;
	private Date updateTime;
	private String updateBy;
	private String tenantId;
}
