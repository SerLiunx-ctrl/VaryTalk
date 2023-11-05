package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@JsonPropertyOrder({"id", "roleName"})
public class SystemRole extends BaseEntity {
    private Long id;

    /**
     * 父角色id
     */
    private Long fatherId;

    @Length(min = 2, message = "角色名称长度过短!")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;
}
