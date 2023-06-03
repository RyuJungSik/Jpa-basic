package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
        Team3 teamA = new Team3();
        teamA.setName("teamA");
        em.persist(teamA);
        
        Team3 teamB = new Team3();
        teamB.setName("teamB");
        em.persist(teamB);
        
        Member3 member1 = new Member3();
        member1.setUsername("guest1");
        member1.setTeam(teamA);
        em.persist(member1);
        
        Member3 member2 = new Member3();
        member2.setUsername("guest2");
        member2.setTeam(teamA);
        em.persist(member2);
        
        Member3 member3 = new Member3();
        member3.setUsername("guest3");
        member3.setTeam(teamB);
        em.persist(member3);
        
        em.flush();
        em.clear();
    
        int resultCount = em.createQuery("update Member3 m set m.age=20").executeUpdate();
        System.out.println("resultCount = " + resultCount);
    
    
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
