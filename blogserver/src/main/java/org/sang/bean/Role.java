package org.sang.bean;
public class Role {
    private Long id;
    private String name;

    public Role() {
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



    public Role(Long id, String name) {

        this.id = id;
        this.name = name;
    }
}
