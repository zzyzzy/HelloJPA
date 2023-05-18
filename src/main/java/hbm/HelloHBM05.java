package hbm;


import model.Department;
import model.Employee;
import model.SungJuk;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;

import java.util.List;

public class HelloHBM05 {
    public static void main(String[] args) {
        // SessionFactory 초기화
        Configuration cfg = new Configuration().configure();
        SessionFactory sf = cfg.addAnnotatedClass(Employee.class)
            .addAnnotatedClass(Department.class).buildSessionFactory();
        Session sess = sf.openSession();

        try {
            // 조회 - 전체 사원
           /* Criteria query = sess.createCriteria(Employee.class);
            List<Employee> emps = query.list();

            System.out.println(emps);*/

            // 조회 - 전체 사원, 페이징 (6번째 사원부터 10명)
            /*Criteria query = sess.createCriteria(Employee.class);
            List<Employee> emps =
                query.setFirstResult(5).setMaxResults(10).list();

            System.out.println(emps);*/

            // 조회 - 부분조회 : 이름, 연봉, setProjection
            /*Criteria query = sess.createCriteria(Employee.class);
            query.setProjection(Projections.projectionList()
                    .add(Projections.property("fname"))
                    .add(Projections.property("sal"))
            );

            List<Object[]> items = query.list();
            for (Object[] item : items)
                System.out.println(item[0] + "/" + item[1]);*/

            // 조건검색 : where, 직책이 IT_PROG인 사원 조회
            /*Criteria query = sess.createCriteria(Employee.class);
            query.add(Expression.eq("jobid", "IT_PROG"));

            List<Employee> emps = query.list();
            System.out.println(emps);*/

            // 조건검색 : 연봉이 20000 이상인 사원 조회
            /*Criteria query = sess.createCriteria(Employee.class);
            query.add(Expression.ge("sal", 20000));

            List<Employee> emps = query.list();
            System.out.println(emps);*/

            // 정렬조회 : 부서번호 기준, addOrder
            /*Criteria query = sess.createCriteria(Employee.class);
            query.addOrder(Order.desc("deptid"));

            List<Employee> emps = query.list();
            System.out.println(emps);*/

            // 직책 수 조회
            /*Criteria query = sess.createCriteria(Employee.class);
            query.setProjection(Projections.projectionList()
                .add(Projections.countDistinct("jobid"))
            );

            List cntjob = query.list();
            System.out.println(cntjob);*/

            // 그룹핑 : 직책별 최대, 최소, 평균 연봉, 직책수 조회
            /*Criteria query = sess.createCriteria(Employee.class);
            query.setProjection(Projections.projectionList()
                    .add(Projections.groupProperty("jobid"))
                    .add(Projections.max("sal"))
                    .add(Projections.min("sal"))
                    .add(Projections.avg("sal"))
                    .add(Projections.count("jobid"))
            );
            List<Object[]> items = query.list();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1] + "/"
                    + item[2] + "/" + item[3] + "/" + item[4]);*/

            // 쿼리, 조건 조회 - 이름 부분 검색
            /*Criteria query = sess.createCriteria(Employee.class);
            //query.add(Expression.like("fname", "Steven")); // 완전일치
            //query.add(Expression.like("fname", "eve"));
            //query.add(Expression.like("fname", "St", MatchMode.START));
            //query.add(Expression.like("fname", "en", MatchMode.END));
            query.add(Expression.like("fname", "eve", MatchMode.ANYWHERE));

            List<Employee> emps = query.list();
            System.out.println(emps);*/

            // 서브쿼리 : 부서명이 IT인 사원들의 이름, 직책, 입사일 조회
            // DetachedCriteria로 서브쿼리 정의 후
            // Criteria로 정의된 주 쿼리에 부착해서 실행
            /*DetachedCriteria subqry =
                DetachedCriteria.forClass(Department.class)
                    .add(Restrictions.eq("dname", "IT"))
                    .setProjection(Projections.property("deptid"));

            Criteria query = sess.createCriteria(Employee.class)
                .add(Subqueries.propertyEq("deptid", subqry))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("fname"))
                        .add(Projections.property("jobid"))
                        .add(Projections.property("hdate"))
                );

            List<Object[]> items = query.list();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1] + "/"
                        + item[2]);*/


            // join : 부서번호가 60번인 사원들의 이름, 직책, 부서명 조회
            // FetchMode : 조인의 종류 정의
            // createAlias : 조인 대상 테이블 정의
            /*Criteria query =
                    sess.createCriteria(Employee.class, "emp");
            query.setFetchMode("emp.department", FetchMode.JOIN);
            query.createAlias("emp.department", "dept");

            query.setProjection(Projections.projectionList()
                    .add(Projections.property("fname"))
                    .add(Projections.property("jobid"))
                    .add(Projections.property("dept.dname"))
            );

            List<Object[]> items = query.list();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1] + "/"
                        + item[2]);*/

            // 동적 쿼리
            // 직책이 IT_PROG 인 사원 조회
            // 연봉이 10000 이상인 사원 조회
            // 직책이 IT_PROG이고 연봉이 6000 이상인 사원 조회
            String fname = null;
            String jobid = "IT_PROG";
            Integer sal = 6000;

            Criteria query = sess.createCriteria(Employee.class);
            if (fname != null)
                query.add(Expression.like("fname", fname, MatchMode.ANYWHERE));

            if (jobid != null)
                query.add(Expression.eq("jobid", jobid));

            if (sal != null)
                query.add(Expression.ge("sal", sal));

            List<Employee> emps = query.list();
            System.out.println(emps);


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sess.close();
            sf.close();
        }

    }
}
