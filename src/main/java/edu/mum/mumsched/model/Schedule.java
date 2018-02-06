package edu.mum.mumsched.model;

import edu.mum.mumsched.exception.EntryException;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule implements Serializable{
    @Id @GeneratedValue
    private Integer id;
    private Date generatedDate;
    private Date approvedDate;
    private ScheduleStatus status;//should be an enum
    //entry of the schedule
    @OneToOne(mappedBy = "schedule",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Entry entry;
    //sections that was generated by this schedule
    @OneToMany(mappedBy = "schedule")
    private Set<Section> sections;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
    public Schedule generate(List<Course> courseList) throws RuntimeException {
        if(this.getEntry()==null)
            throw new EntryException("The entry is not valid!");
        if (this.getEntry().getMppNumber()+this.getEntry().getFppNumber() <= 0)
            throw new EntryException("The MPP and FPP projection is incorrect, edit the entry details to proceed");
        //this method will check if I got enough block,and will throw a runtime exception if not
        this.entry.checkBlockRequirements();
        //let's say in which order we want to offer the courses
       List<Course> courses=Course.orderCourses(courseList);

        for(Block block:this.getEntry().getBlocks()){
            block.createSections(courses,this.getEntry());
        }
        this.getEntry().setSchedule(this);
        this.setGeneratedDate(new Date(System.currentTimeMillis()));
        this.setStatus(ScheduleStatus.DRAFT);
        return this;
    }

    public void addSection(Section section) {
        if (section != null) {
            sections.add(section);
            section.setSchedule(this);
        }
    }

    public void removeSection(Section section) {
        if (section != null) {
            sections.remove(section);
            section.setSchedule(null);
        }
    }


    public void onApproved() {
        entry.checkBlockRequirements();
    }
}
