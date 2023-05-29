package hellojpa;


import javax.persistence.*;

@Entity
public class Member2 extends BaseEntity2 {
@Id
@GeneratedValue
@Column(name = "MEMBER_ID")
private Long id;
@Column(name = "USERNAME")
private String username;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "TEAM_ID")
private Team2 team;

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

public Team2 getTeam() {
    return team;
}

public void setTeam(Team2 team) {
    this.team = team;
}
}
