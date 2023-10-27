package com.george.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "stop", uniqueConstraints = {@UniqueConstraint(columnNames = {"program_id", "stop_index"})})
public class Stop implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "program_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Program program;

    @Column(name = "stop_index", nullable = false)
    private Integer stopIndex;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ElementCollection
    @CollectionTable(name = "stop_valve", uniqueConstraints = {@UniqueConstraint(columnNames = {"stop_id", "valve_index"})})
    @Column(name = "valve_index")
    private Set<Integer> valves;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Integer getStopIndex() {
        return stopIndex;
    }

    public void setStopIndex(Integer stopIndex) {
        this.stopIndex = stopIndex;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<Integer> getValves() {
        return valves;
    }

    public void setValves(Set<Integer> valves) {
        this.valves = valves;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id=" + id +
                ", program=" + program +
                ", stopIndex=" + stopIndex +
                ", duration=" + duration +
                ", valves=" + valves +
                '}';
    }

}
