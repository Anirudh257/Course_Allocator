/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uwe_allocator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Umang
 */
@Entity
@Table(name = "course", catalog = "info_db", schema = "")
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")
    , @NamedQuery(name = "Course.findByCourseId", query = "SELECT c FROM Course c WHERE c.courseId = :courseId")
    , @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.courseName = :courseName")
    , @NamedQuery(name = "Course.findByCourseType", query = "SELECT c FROM Course c WHERE c.courseType = :courseType")
    , @NamedQuery(name = "Course.findByCredits", query = "SELECT c FROM Course c WHERE c.credits = :credits")
    , @NamedQuery(name = "Course.findByNoSeats", query = "SELECT c FROM Course c WHERE c.noSeats = :noSeats")})
public class Course implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_type")
    private String courseType;
    @Column(name = "credits")
    private Integer credits;
    @Column(name = "no_seats")
    private Integer noSeats;

    public Course() {
    }

    public Course(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        Integer oldCourseId = this.courseId;
        this.courseId = courseId;
        changeSupport.firePropertyChange("courseId", oldCourseId, courseId);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        String oldCourseName = this.courseName;
        this.courseName = courseName;
        changeSupport.firePropertyChange("courseName", oldCourseName, courseName);
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        String oldCourseType = this.courseType;
        this.courseType = courseType;
        changeSupport.firePropertyChange("courseType", oldCourseType, courseType);
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        Integer oldCredits = this.credits;
        this.credits = credits;
        changeSupport.firePropertyChange("credits", oldCredits, credits);
    }

    public Integer getNoSeats() {
        return noSeats;
    }

    public void setNoSeats(Integer noSeats) {
        Integer oldNoSeats = this.noSeats;
        this.noSeats = noSeats;
        changeSupport.firePropertyChange("noSeats", oldNoSeats, noSeats);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseId != null ? courseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.courseId == null && other.courseId != null) || (this.courseId != null && !this.courseId.equals(other.courseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uwe_allocator.Course[ courseId=" + courseId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
