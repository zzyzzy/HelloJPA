package dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class HelloQueryDSL03 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // 사원 추가
            /*tx.begin();
                Employee e = new Employee();
                em.persist(e);
            tx.commit();*/

            // 사원 수정
            QEmployee qemp = QEmployee.employee;
            JPAQueryFactory query = new JPAQueryFactory(em);

            /*tx.begin();
                query.update(qemp)
                .set(qemp.fname, "abc123").set(qemp.lname, "987xyz")
                .set(qemp.sal, 15500).set(qemp.jobid, "Game_QC")
                .where(qemp.empid.eq(207L))
                .execute();
            tx.commit();*/

            // 데이터 삭제
            tx.begin();
                query.delete(qemp)
                    .where(qemp.empid.eq(207L))
                    .execute();
            tx.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }

    }
}
