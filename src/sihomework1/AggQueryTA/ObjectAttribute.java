/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.AggQueryTA;

/**
 *
 * @author cool
 */
public class ObjectAttribute {
    /*DataStructure to store Object and its one attribute val*/

    private String objectName;
    private Double attribute;

    public void setObjectName(String val) {
        this.objectName = val;
    }

    public String getObjectName() {
        return this.objectName;
    }
    
     public void setAttribute(Double val) {
        this.attribute=val;
    }

    public Double getAttribute() {
        return this.attribute;
    }

}
