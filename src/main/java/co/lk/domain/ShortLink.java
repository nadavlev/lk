package co.lk.domain;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ShortLink.
 */
@Entity
@Table(name = "short_link")
public class ShortLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 60)
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @NotNull
    @Size(min = 4)
    @Column(name = "destination", nullable = false)
    private String destination;

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

    @Column(name = "short_link")
    private String shortLink;

    @Min(value = 0)
    @Column(name = "total_clicks")
    private Long totalClicks;

    @Column(name = "last_click_date")
    private LocalDate lastClickDate;

    @Column(name = "index_of_keys")
    private String indexOfKeys;

    @Column(name = "timeout")
    private Long timeout;

    @ManyToOne
    @JoinColumn(name = "creation_user_id")
    private User creationUser;

    @ManyToOne
    @JoinColumn(name = "update_user_id")
    private User updateUser;

    @ManyToOne
    @JoinColumn(name = "url_group_id")
    private UrlGroup urlGroup;

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public Long getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(Long totalClicks) {
        this.totalClicks = totalClicks;
    }

    public LocalDate getLastClickDate() {
        return lastClickDate;
    }

    public void setLastClickDate(LocalDate lastClickDate) {
        this.lastClickDate = lastClickDate;
    }

    public String getIndexOfKeys() {
        return indexOfKeys;
    }

    public void setIndexOfKeys(String indexOfKeys) {
        this.indexOfKeys = indexOfKeys;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
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

    public UrlGroup getUrlGroup() {
        return urlGroup;
    }

    public void setUrlGroup(UrlGroup urlGroup) {
        this.urlGroup = urlGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortLink shortLink = (ShortLink) o;
        return Objects.equals(id, shortLink.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShortLink{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", destination='" + destination + "'" +
            ", creationDate='" + creationDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", description='" + description + "'" +
            ", state='" + state + "'" +
            ", shortLink='" + shortLink + "'" +
            ", totalClicks='" + totalClicks + "'" +
            ", lastClickDate='" + lastClickDate + "'" +
            ", indexOfKeys='" + indexOfKeys + "'" +
            ", timeout='" + timeout + "'" +
            '}';
    }
}
