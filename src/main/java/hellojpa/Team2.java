package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team2 {
@Id
@GeneratedValue
@Column(name="TEAM_ID")
private Long id;
private String name;
@OneToMany
@JoinColumn(name="TEAM_ID")
private List<Member2> members = new ArrayList<>();

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

public List<Member2> getMembers() {
    return members;
}

public void setMembers(List<Member2> members) {
    this.members = members;
}



//public void addMember(Member2 member) {
//    member.setTeam(this);
//    members.add(member);
//}

}
