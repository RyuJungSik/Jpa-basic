package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
        Team3 team = new Team3();
        team.setName("teamA");
        em.persist(team);
        
        Member3 member = new Member3();
        member.setUsername("member1" );
        member.setAge(10);
        member.setTeam(team);
        em.persist(member);
        
        em.flush();
        em.clear();
        
        String query = "select m from Member3 m left join Team3 t on m.username=t.name";
        List<Member3> result = em.createQuery(query, Member3.class).getResultList();
        
        tx.commit();
    } catch (Exception e) {
        tx.rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
    emf.close();
}
}
