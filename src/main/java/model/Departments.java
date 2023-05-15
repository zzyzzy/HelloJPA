package model;

// deptid, dname, mgrid, locid

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
@Data
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPARTMENT_ID")
    private Integer deptid;

    @Column(name="DEPARTMENT_NAME")
    private String dname;

    @Column(name="MANAGER_ID")
    private Integer mgrid;

    @Column(name="LOCATION_ID")
    private Long locid;

    @OneToMany(mappedBy = "dept")
    private List<Employees> employees;

}
