package github.serliunx.varytalk.project.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import github.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }
}
