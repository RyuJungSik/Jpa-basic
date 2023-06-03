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
        
        String query = "select t From Team3 t";
        List<Team3> result = em.createQuery(query, Team3.class).setFirstResult(0)
                                     .setMaxResults(2)
                                     .getResultList();
    
        System.out.println("result.size() = " + result.size());
        
        for (Team3 team : result) {
            System.out.println("team.getName() = " + team.getName() + " | " + team.getMembers().size());
            for (Member3 member : team.getMembers()) {
                System.out.println("member = " + member);
            }
        }
        
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
