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
        Member3 member = new Member3();
        member.setUsername("member1");
        member.setAge(10);
        em.persist(member);
    
        TypedQuery<Member3> query1 = em.createQuery("select m from Member3 m", Member3.class);
        TypedQuery<String> query2 = em.createQuery("select m.username from Member3 m", String.class);
        Query query = em.createQuery("select m.username, m.age from Member3 m");
        TypedQuery<Member3> query4 = em.createQuery("select m from Member3 m where m.username = :username", Member3.class);
        query4.setParameter("username", "member1");
        Member3 singleResult = query4.getSingleResult();
        System.out.println("singleResult = " + singleResult);
        System.out.println("singleResult = " + singleResult.getUsername());
    
        List<Member3> resultList = query1.getResultList();
    
        for (Member3 member1 : resultList) {
            System.out.println("member1 = " + member1);
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
