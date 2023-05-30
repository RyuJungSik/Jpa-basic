package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    
    try {
        Member2 member = new Member2();
        member.setUsername("member1");
        member.setHomeAddress(new Address("city1", "street", "10000"));
        
        member.getFavoriteFoods().add("chicken");
        member.getFavoriteFoods().add("pizza");
        member.getFavoriteFoods().add("hamburger");
        
        member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
        member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));
        
        em.persist(member);
        
        em.flush();
        em.clear();
        
        System.out.println("========== START  =============");
        Member2 findMember = em.find(Member2.class, member.getId());
//
//        //homeCity -> newCity
////        findMember.getHomeAddress().setCity("newCity");
//
//        Address a = findMember.getHomeAddress();
//        findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));
//
//        //chicken -> korean
//        findMember.getFavoriteFoods().remove("chicken");
//        findMember.getFavoriteFoods().add("korean");
//
//        System.out.println("========== START2  =============");
//        findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
//        findMember.getAddressHistory().add(new Address("newCity1", a.getStreet(), a.getZipcode()));
        
        
        
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
