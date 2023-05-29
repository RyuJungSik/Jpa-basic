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
        Child child1 = new Child();
        Child child2 = new Child();
    
        Parent parent = new Parent();
        parent.addChild(child1);
        parent.addChild(child2);
        
        em.persist(parent);
        em.persist(child1);
        em.persist(child2);
        
        em.flush();
        em.clear();
    
        Parent findParent = em.find(Parent.class, parent.getId());
        em.remove(findParent);
    
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
