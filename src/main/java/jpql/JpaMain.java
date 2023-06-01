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
        member.setUsername("member1");
        member.setAge(10);
        member.setType(MemberType3.ADMIN);
        member.setTeam(team);
        em.persist(member);
        
        em.flush();
        em.clear();
        
        String query = "select m.username, 'HELLO', true From Member3 m " +
                               "where m.type = :userType3";
        List<Object[]> result = em.createQuery(query).setParameter("userType3", MemberType3.ADMIN).getResultList();
        
        for (Object[] objects : result) {
            System.out.println("objects[0] = " + objects[0]);
            System.out.println("objects[0] = " + objects[1]);
            System.out.println("objects[0] = " + objects[2]);
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
