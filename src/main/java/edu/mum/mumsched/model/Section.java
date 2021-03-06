package edu.mum.mumsched.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Section {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer capacity;
    private Integer enrolled;//amount of enrolled student
    private Integer availableSeats;

    //students enrolled for section
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "section_student",
            joinColumns = {@JoinColumn(name = "section_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName="id")})
    private Set<Student> students;
    //schedule of section
    @ManyToOne
   private Schedule schedule;

    @OneToOne
    private Block block;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Faculty faculty;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Course course;

    public Section() {

        this.capacity = 0;
        this.enrolled = 0;
        this.availableSeats = 0;

    }

    public void addStudent(Student stu){
        students.add(stu);

    }

    public void removeStudent(Student stu){
        students.remove(stu);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
