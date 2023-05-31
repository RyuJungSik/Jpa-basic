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
        
        em.flush();
        em.clear();
    
        List<Member3DTO> result = em.createQuery("select new jpql.Member3DTO(m.username, m.age) from Member3 m", Member3DTO.class).getResultList();
    
        Member3DTO member3DTO = result.get(0);
    
        System.out.println("member3DTO.getUsername() = " + member3DTO.getUsername());
        System.out.println("member3DTO.getAge() = " + member3DTO.getAge());
    
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
