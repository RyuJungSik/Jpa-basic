package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
        Member2 member1 = new Member2();
        member1.setUsername("user1");
        em.persist(member1);
        
        em.flush();
        em.clear();
        
        Member2 refMember = em.getReference(Member2.class, member1.getId());
        System.out.println("refMember = " + refMember.getClass());
    
        em.detach(refMember);
        System.out.println("refMember.getUsername() = " + refMember.getUsername());
    
//        refMember.getUsername();
        em.close();
        
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
