package jpql;

import javax.persistence.*;

@Entity
@NamedQuery(
        name="Member3.findByUsername",
        query="select m from Member3 m where m.username = :username"
)
public class Member3 {

@Id
@GeneratedValue
private Long id;
private String username;
private int age;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "TEAM_ID")
private Team3 team;

private MemberType3 type;


public void changeTeam(Team3 team) {
    this.team = team;
    team.getMembers().add(this);
}

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

public int getAge() {
    return age;
}

public void setAge(int age) {
    this.age = age;
}

public Team3 getTeam() {
    return team;
}

public void setTeam(Team3 team) {
    this.team = team;
}

public MemberType3 getType() {
    return type;
}

public void setType(MemberType3 type) {
    this.type = type;
}

@Override
public String toString() {
    return "Member3{" +
                   "id=" + id +
                   ", username='" + username + '\'' +
                   ", age=" + age +
                   '}';
}
}
