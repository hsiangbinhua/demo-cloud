/*
 * Copyright   Loy Fu. 付厚俊
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.loy.upm.sys.domain.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loy.e.core.entity.BaseEntity;
import com.loy.e.core.entity.LogicDeleteable;

import com.loy.e.common.annotation.Author;
@Author(author = "Loy Fu", website = "http://www.17jee.com", contact = "qq群 540553957")

@Entity
@Table(name = "e_user", indexes = {
        @Index(columnList = "username", unique = true, name = "index_user_username") })
@Inheritance(strategy = InheritanceType.JOINED)

public class UserEntity extends BaseEntity implements LogicDeleteable {

    private static final long serialVersionUID = 1392366136947635659L;
    @Column(length = 100, nullable = false)
    private String username;
    @JsonIgnore
    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100)
    private String name;
    //@ApiModelProperty(name="email" ,value="EMAIL")
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    private Boolean deleted = Boolean.FALSE;

    private Boolean enabled = Boolean.FALSE;

    @Column(length = 50)
    private String salt;

    @Lob
    @JsonIgnore
    private byte[] photoData;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "e_user_role", joinColumns = {
            @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    //@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<RoleEntity> roles = new HashSet<RoleEntity>();

    @Transient
    private String roleNames;

    @Transient
    private String roleIds;

    @Transient
    private Boolean photo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void markDeleted() {
        this.deleted = Boolean.TRUE;

    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleNames() {

        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoleIds() {

        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public Boolean getPhoto() {
        return this.photoData != null;
    }

    public String buildRoleIdAnadName() {
        if (this.roles != null) {
            List<String> roles = new ArrayList<String>();
            for (RoleEntity role : this.roles) {
                roles.add(role.getId());
            }
            this.roleIds = StringUtils.join(roles, ",");

            roles = new ArrayList<String>();
            for (RoleEntity role : this.roles) {
                roles.add(role.getName());
            }
            this.roleNames = StringUtils.join(roles, " , ");
        }
        return this.roleNames;
    }

}
