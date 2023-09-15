package github.serliunx.varytalk.project.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import github.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class SystemPermission extends BaseEntity {
    @JsonProperty(index = 1)
    private Long id;

    @Length(min = 4, max = 60, message = "权限节点值长度必须为4到25个字符")
    @NotEmpty(message = "权限节点值不能为空!")
    @JsonProperty(index = 2)
    private String value;

    @NotEmpty(message = "权限节点名称不能为空!")
    @JsonProperty(index = 3)
    private String nodeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
