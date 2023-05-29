package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
        Team2 team = new Team2();
        team.setName("teamA");
        em.persist(team);
        
        Member2 member1 = new Member2();
        member1.setUsername("user1");
        member1.setTeam(team);
        em.persist(member1);
        
        em.flush();
        em.clear();
        
//        Member2 m = em.find(Member2.class, member1.getId());
    
        List<Member2> members = em.createQuery("select m from Member2 m", Member2.class).getResultList();

        tx.commit();
    } catch (Exception e) {
        tx.rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
    emf.close();
}

private static void logic(Member2 m1, Member2 m2) {
    System.out.println("m1 == m2  " + (m1 instanceof Member2));
    System.out.println("m1 == m2  " + (m2 instanceof Member2));
}


}
