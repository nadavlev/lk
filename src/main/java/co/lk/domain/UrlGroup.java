package co.lk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UrlGroup.
 */
@Entity
@Table(name = "url_group")
public class UrlGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 60)
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    @Column(name = "state", nullable = false)
    private Integer state;

    @NotNull
    @Size(min = 4, max = 60)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "main_address", length = 60, nullable = false)
    private String mainAddress;

    @ManyToOne
    @JoinColumn(name = "creation_user_id")
    private User creationUser;

    @ManyToOne
    @JoinColumn(name = "update_user_id")
    private User updateUser;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private LkAccount account;

    @OneToMany(mappedBy = "urlGroup")
    @JsonIgnore
    private Set<ShortLink> shortLinkss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User user) {
        this.creationUser = user;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User user) {
        this.updateUser = user;
    }

    public LkAccount getAccount() {
        return account;
    }

    public void setAccount(LkAccount lkAccount) {
        this.account = lkAccount;
    }

    public Set<ShortLink> getShortLinkss() {
        return shortLinkss;
    }

    public void setShortLinkss(Set<ShortLink> shortLinks) {
        this.shortLinkss = shortLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrlGroup urlGroup = (UrlGroup) o;
        return Objects.equals(id, urlGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UrlGroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", creationDate='" + creationDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", description='" + description + "'" +
            ", state='" + state + "'" +
            ", mainAddress='" + mainAddress + "'" +
            '}';
    }
}
