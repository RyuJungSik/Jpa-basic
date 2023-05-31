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
        for (int i = 0; i < 100; i++) {
            Member3 member = new Member3();
            member.setUsername("member1" + i);
            member.setAge(i);
            em.persist(member);
        }
        
        em.flush();
        em.clear();
    
        List<Member3> result = em.createQuery("select m from Member3 m order by m.age desc", Member3.class).setFirstResult(1).setMaxResults(10).getResultList();
    
        System.out.println("result.size() = " + result.size());
        for (Member3 member3 : result) {
            System.out.println("member3 = " + member3);
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
