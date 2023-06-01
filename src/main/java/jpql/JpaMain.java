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
        Team3 team = new Team3();
        team.setName("teamA");
        em.persist(team);
        
        Member3 member1 = new Member3();
        member1.setUsername("administ");
        member1.setAge(10);
        member1.setType(MemberType3.ADMIN);
        member1.setTeam(team);
        em.persist(member1);
    
    
        Member3 member2 = new Member3();
        member2.setUsername("administ2");
        member2.setAge(20);
        member2.setType(MemberType3.ADMIN);
        em.persist(member2);
        
        em.flush();
        em.clear();
        
//        String query = "select " +
//                               "case when m.age <=10 then '학생요금' " +
//                               " when m.age >=60 then '경로요금' " +
//                               " else '일반요금' " +
//                               "end " +
//                               "from Member3 m";
        
//        String query = "select 'a' || 'b' From Member m";
//        String query = "select locate('de', 'abcdeaf') From Member3 m";
        String query = "select t.members From Team3 t";
        Collection result = em.createQuery(query, Collection.class).getResultList();
    
        System.out.println("s = " + result);
    
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
