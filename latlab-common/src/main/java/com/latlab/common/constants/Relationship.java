/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

/**
 *
 * @author edwin
 */
public enum Relationship {

    MOTHER("Mother"),
    FATHER("Father"),
    GUARDIAN("Guardian"),
    CHILD("Child"),
    SPOUSE("Spouse"),
    SISTER("Sister"),
    BROTHER("Brother"),
    SON("Son"),
    UNCLE("Uncle"),
    WIFE("Wife"),
    HUSBAND("Husband"),
    COUNSIN("Counsin"),
    CHILDREN("Children"),
    DAUGHTER("Daughter"),
    SIBLING("Sibling"),
    OTHER("Other");

    String label;
    String title;

    private Relationship(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
