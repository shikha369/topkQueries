/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.AggQueryTA;

/**
 *
 * @author cool
 */
public class DataPoint {
    
    /*DataStructure to store object and its score*/

    private String objectName;
    private Double fVal;

    public void setfVal(Double val) {
        this.fVal = val;
    }

    public void setObjectName(String val) {
        this.objectName = val;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public Double getfVal() {
        return this.fVal;
    }

}
