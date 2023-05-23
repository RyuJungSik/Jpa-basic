package hellojpa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
        Team2 team = new Team2();
        team.setName("TeamA");
        em.persist(team);
        
        Member2 member = new Member2();
        member.setUsername("member1");
        em.persist(member);
    
        team.addMember(member);
    
//        team.getMembers().add(member);
        
        em.flush();
        em.clear();
    
        Member2 findMember = em.find(Member2.class, member.getId());
        List<Member2> members = findMember.getTeam().getMembers();

        for (Member2 m : members) {
            System.out.println("m.getUsername() = " + m.getUsername());
        }
        
        tx.commit();
    } catch (Exception e) {
        tx.rollback();
    }finally {
        em.close();
    }
    emf.close();
    
}

}
