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
        Address address = new Address("city", "street", "10000");
        
        Member2 member = new Member2();
        member.setUsername("member1");
        member.setHomeAddress(address);
        em.persist(member);
    
        Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
    
        Member2 member2 = new Member2();
        member2.setUsername("member2");
        member2.setHomeAddress(copyAddress);
        em.persist(member2);
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
