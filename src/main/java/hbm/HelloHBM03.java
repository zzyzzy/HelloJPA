package hbm;


import model.SungJuk;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HelloHBM03 {
    public static void main(String[] args) {
        // jpa 설정 파일 읽어옴
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        // jpa에서 생성된 em을 session 객체로 전환
        Session session = em.unwrap(Session.class);
        SessionFactory sf = session.getSessionFactory();
        Session sess = sf.openSession();

        try {
            // hql을 이용한 조회
            Query query = sess.createQuery("from SungJuk");
            List sjs = query.list();

            System.out.println(sjs);

            query = sess.createQuery("from SungJuk where name = ?1");
            query.setParameter(1, "혜교");
            sjs = query.list();

            System.out.println(sjs);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sess.close();
            sf.close();
        }

    }
}
