package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member2 extends BaseEntity2 {
@Id
@GeneratedValue
@Column(name = "MEMBER_ID")
private Long id;
@Column(name = "USERNAME")
private String username;

@ManyToOne
@JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
private Team2 team;

@OneToOne
@JoinColumn(name = "LOCKER_ID")
private Locker locker;

@ManyToMany
@JoinTable(name = "member")
private List<MemberProduct> memberProducts = new ArrayList<>();

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}
}
