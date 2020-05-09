package com.wuwen.server.domain.entity;

import com.wuwen.api.enums.DeleteStatusEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author wuwen
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 租户ID
     */
    private Long companyId;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime gmtModified;

    /**
     * 删除标志 0：表示删除 1：表示未删除
     */
    private DeleteStatusEnum status = DeleteStatusEnum.NORMAL;
}
