package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();
    
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
        //등록
//        Member member = new Member();
//        member.setId(3L);
//        member.setName("HelloC");
//        em.persist(member);
        
        //조회
//        Member findMember = em.find(Member.class, 1L);
        
        //수정
//        findMember.setName("helloChange");
        
        //조건 조회
        List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
        for (Member member : result) {
            System.out.println("member = " + member.getName());
        }
        
        tx.commit();
    } catch (Exception e) {
        System.out.println("e = " + e);
        tx.rollback();
    } finally {
        em.close();
    }
    emf.close();
    
}

}
